package com.cpms.virtualization.services;

import com.cpms.virtualization.models.*;
import nu.xom.*;
import org.apache.http.HttpResponse;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rakib on 5/23/2017.
 */
public class MTCommXmlManager {

    public MTCommXmlManager() {
    }

    /**
     * generate XML for deviceoperation for jobs
     */
    public String generateDeviceOperationForJobs(DeviceOperation deviceOperation) throws ParsingException, IOException {

        Document operationDoc = getOperationStarter();
        Document probeDoc = getProbe(deviceOperation.getDeviceId());

        // construct Operations element
        Element opElement = new Element("Operations");

        // construct header element
        Element headerElement = new Element("Header");
        headerElement.addAttribute(new Attribute("bufferSize", "10"));
        headerElement.addAttribute(new Attribute("instanceId", "1"));
        headerElement.addAttribute(new Attribute("creationTime", "2017-01-18T12:00:00"));
        headerElement.addAttribute(new Attribute("sender", "Cloud"));
        headerElement.addAttribute(new Attribute("version", "0.1"));
        headerElement.addAttribute(new Attribute("firstSequence", "9"));
        headerElement.addAttribute(new Attribute("lastSequence", "9"));
        headerElement.addAttribute(new Attribute("nextSequence", "10"));
        operationDoc.getRootElement().appendChild(headerElement);

        // construct Device element
        Element deviceElement = new Element("Device");
        deviceElement.addAttribute(new Attribute("name", "Ultimaker2"));
        deviceElement.addAttribute(new Attribute("uuid", "P2673"));

        // construct DeviceOperation element
        Element devOpElement = new Element("DeviceOperation");
        devOpElement.addAttribute(new Attribute("id", "Ultimaker2"));
        devOpElement.addAttribute(new Attribute("name", "Ultimaker2 3D Printer"));

        // get operation by XPATH
        XPathContext context = new XPathContext("xmlns", "urn:MTComm.org:MTCommDevices:1.2");
        Nodes deviceOperations = probeDoc.query("/xmlns:MTCommDevices/xmlns:Devices/xmlns:Device[@id=\""
                        + deviceOperation.getDeviceId() + "\"]/xmlns:Operations//xmlns:Operation[@id=\""
                        + deviceOperation.getOperationId() + "\"]",
                context);
        Element deviceOperationElement = (Element) deviceOperations.get(0);

        // construct Jobs element
        String opOrAction = "Jobs";
        if ("ACTION".equals(deviceOperationElement.getAttributeValue("category"))) {
            opOrAction = "Actions";
        }
        Element jobsElement = new Element(opOrAction);

        // Find the type of operation to create XML element
        String deviceOperationType = deviceOperationElement.getAttributeValue("type");
        String tagName = properCase(deviceOperationType);
        Element jobElement = new Element(tagName);
        jobElement.addAttribute(new Attribute("operationId", deviceOperation.getOperationId()));
        jobElement.addAttribute(new Attribute("name", deviceOperationElement.getAttributeValue("name")));
        jobElement.addAttribute(new Attribute("sequence", "1"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        jobElement.addAttribute(new Attribute("timestamp", sdf.format(new Date())));

        if ("Actions".equals(opOrAction)) {
//            jobElement.appendChild(deviceOperation.getOperationId());
            jobElement.appendChild("STOP");
//            jobElement.appendChild("RESET");
        } else {
            // create all parameters
            Nodes probeJobParamters = deviceOperationElement.query("/xmlns:MTCommDevices" +
                            "/xmlns:Devices/xmlns:Device[@id=\""
                            + deviceOperation.getDeviceId()
                            + "\"]/xmlns:Operations/xmlns:Operation[@id=\""
                            + deviceOperation.getOperationId()
                            + "\"]/xmlns:Parameters//xmlns:Parameter",
                    context);
            Element parameters = new Element("Parameters");
            for (int i = 0; i < probeJobParamters.size(); i++) {
                Element probeJobParameter = (Element) probeJobParamters.get(i);
                if (!"TEMPERATURE".equals(probeJobParameter.getAttributeValue("type"))) {
                    String parameterName = properCase(probeJobParameter.getAttributeValue("type"));
                    if ("FILE".equalsIgnoreCase(parameterName)) {
                        parameterName = "Object";
                    }
                    Element parameter = new Element(parameterName);
                    parameter.addAttribute(new Attribute("id", probeJobParameter.getAttributeValue("id")));
                    parameter.addAttribute(new Attribute("name", probeJobParameter.getAttributeValue("name")));
                    parameter.addAttribute(new Attribute("timestamp", sdf.format(new Date())));
                    for (Parameter param : deviceOperation.getParameters()) {
                        if (probeJobParameter.getAttributeValue("id").equals(param.getId())) {
                            parameter.appendChild(param.getValue());
                            break;
                        }
                    }
                    parameters.appendChild(parameter);
                }
            }
            jobElement.appendChild(parameters);
        }
        jobsElement.appendChild(jobElement);
        devOpElement.appendChild(jobsElement);
        deviceElement.appendChild(devOpElement);
        opElement.appendChild(deviceElement);

        // add child elements to root
        operationDoc.getRootElement().appendChild(opElement);

        prettyPrint(operationDoc);
        return operationDoc.toXML();
    }

    /**
     * generate XML for deviceoperation for actions
     */
    public void generateDeviceOperationForActions() {

    }

    /**
     * generate XML for component operation for jobs
     */
    public void generateComponentOperationForJobs() {

    }

    /**
     * generate XML for component operation for actions
     */
    public String generateComponentOperationForActions(ComponentOperation componentOperation)
            throws IOException, ParsingException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        Document operationDoc = getOperationStarter();
        Document probeDoc = getProbe(componentOperation.getDeviceId());

        // construct Operations element
        Element opElement = new Element("Operations");

        // construct header element
        Element headerElement = new Element("Header");
        headerElement.addAttribute(new Attribute("bufferSize", "10"));
        headerElement.addAttribute(new Attribute("instanceId", "1"));
        headerElement.addAttribute(new Attribute("creationTime", sdf.format(new Date())));
        headerElement.addAttribute(new Attribute("sender", componentOperation.getDeviceId()));
        headerElement.addAttribute(new Attribute("version", "0.1"));
        headerElement.addAttribute(new Attribute("firstSequence", "1"));
        headerElement.addAttribute(new Attribute("lastSequence", "1"));
        headerElement.addAttribute(new Attribute("nextSequence", "2"));
        operationDoc.getRootElement().appendChild(headerElement);

        // construct Device element
        Element deviceElement = new Element("Device");
        deviceElement.addAttribute(new Attribute("name", componentOperation.getDeviceId()));
        deviceElement.addAttribute(new Attribute("uuid", componentOperation.getUuid()));

        XPathContext context = new XPathContext("xmlns", "urn:MTComm.org:MTCommDevices:1.2");
        int sequence = 0;

        // get operation by XPATH
        Nodes probeCompOpNodes = probeDoc.query("//xmlns:Operation[@id=\""
                + componentOperation.getOperationId() + "\"]", context);
        Element probeCompOpElement = (Element) probeCompOpNodes.get(0);
        System.out.println(probeCompOpElement.toXML());

        // construct DeviceOperation element
        //component="Linear" componentId="x" name="X"
        Element compOpElement = new Element("ComponentOperation");
        compOpElement.addAttribute(new Attribute("component", componentOperation.getComponent()));
        compOpElement.addAttribute(new Attribute("componentId", componentOperation.getComponentId()));
        compOpElement.addAttribute(new Attribute("name", componentOperation.getComponentName()));

        // construct Actions element
        Element actionsElement = new Element("Actions");

        String elementName = properCase(probeCompOpElement.getAttributeValue("type"));
        Element actionElement = new Element(elementName);
        actionElement.addAttribute(new Attribute("operationId", componentOperation.getOperationId()));
        actionElement.addAttribute(new Attribute("name", probeCompOpElement.getAttributeValue("name")));
        actionElement.addAttribute(new Attribute("sequence", Integer.toString(++sequence)));
        actionElement.addAttribute(new Attribute("timestamp", sdf.format(new Date())));
        actionElement.appendChild(componentOperation.getComponentValue());

        actionsElement.appendChild(actionElement);
        compOpElement.appendChild(actionsElement);
        deviceElement.appendChild(compOpElement);

        opElement.appendChild(deviceElement);
        operationDoc.getRootElement().appendChild(opElement);

        prettyPrint(operationDoc);
        return operationDoc.toXML();
    }

    public Operations getOperationsFromProbe(String deviceId) throws IOException, ParsingException {
        Document probeDoc = getProbe(deviceId);
        XPathContext context = new XPathContext("xmlns", "urn:MTComm.org:MTCommDevices:1.2");
        Nodes deviceNodes = probeDoc.query("/xmlns:MTCommDevices/xmlns:Devices/xmlns:Device[@id=\""
                + deviceId + "\"]", context);
        Element deviceNode = (Element) deviceNodes.get(0);
        String uuid = deviceNode.getAttributeValue("uuid");

        Nodes deviceOperationNodes = probeDoc.query("/xmlns:MTCommDevices/xmlns:Devices/xmlns:Device[@id=\""
                + deviceId + "\"]/xmlns:Operations//xmlns:Operation", context);

        List<DeviceOperation> deviceOperations = new ArrayList<>();
        for (int i = 0; i < deviceOperationNodes.size(); ++i) {
            Element deviceOperationElement = (Element) deviceOperationNodes.get(i);
            DeviceOperation deviceOperation = new DeviceOperation();
            deviceOperation.setDeviceId(deviceId);
            deviceOperation.setOperationId(deviceOperationElement.getAttributeValue("id"));
            Nodes operationParameters = deviceOperationElement.query("/xmlns:MTCommDevices"
                    + "/xmlns:Devices/xmlns:Device[@id=\"" + deviceId + "\"]/xmlns:Operations/xmlns:Operation[@id=\""
                    + deviceOperation.getOperationId() + "\"]/xmlns:Parameters//xmlns:Parameter", context);
            List<Parameter> parameters = new ArrayList<>();
            for (int j = 0; j < operationParameters.size(); ++j) {
                Element operationParameter = (Element) operationParameters.get(j);
                if (!"TEMPERATURE".equals(operationParameter.getAttributeValue("type"))) {
                    Parameter parameter = new Parameter();
                    for (int k = 0; k < operationParameter.getAttributeCount(); ++k) {
                        Attribute attribute = operationParameter.getAttribute(k);
                        if ("id".equals(attribute.getLocalName())) {
                            parameter.setId(attribute.getValue());
                        } else if ("name".equals(attribute.getLocalName())) {
                            parameter.setName(attribute.getValue());
                        } else if ("type".equals(attribute.getLocalName())) {
                            parameter.setType(attribute.getValue());
                        } else {
                            parameter.getRestAttributes().put(attribute.getLocalName(), attribute.getValue());
                        }
                    }
                    parameters.add(parameter);
                }
            }
            deviceOperation.setParameters(parameters);
            deviceOperations.add(deviceOperation);
        }

        List<ComponentOperation> componentOperations = new ArrayList<>();
        Nodes componentNodes = probeDoc.query("/xmlns:MTCommDevices/xmlns:Devices/xmlns:Device[@id=\""
                + deviceId + "\"]/xmlns:Components/*", context);
        for (int i = 0; i < componentNodes.size(); ++i) {
            Element componentElement = (Element) componentNodes.get(i);
            Nodes componentOperationNodes = componentElement.query(
                    "/xmlns:MTCommDevices/xmlns:Devices/xmlns:Device[@id=\""
                            + deviceId + "\"]/xmlns:Components/xmlns:"
                            + componentElement.getLocalName()
                            + "[@id=\"" + componentElement.getAttributeValue("id")
                            + "\"]/xmlns:Operations"
                            + "//xmlns:Operation", context);
            for (int j = 0; j < componentOperationNodes.size(); ++j) {
                Element componentOperationElement = (Element) componentOperationNodes.get(j);
                ComponentOperation componentOperation = new ComponentOperation();
                componentOperation.setDeviceId(deviceId);
                componentOperation.setUuid(uuid);
                componentOperation.setComponentId(componentElement.getAttributeValue("id"));
                componentOperation.setComponentName(componentElement.getAttributeValue("name"));
                componentOperation.setComponent(componentElement.getLocalName());
                componentOperation.setOperationId(componentOperationElement.getAttributeValue("id"));
                componentOperation.setCategory(componentOperationElement.getAttributeValue("category"));
                componentOperation.setType(componentOperationElement.getAttributeValue("type"));
                componentOperation.setName(componentOperationElement.getAttributeValue("name"));
                componentOperations.add(componentOperation);
            }

            Nodes subComponentNodes = componentElement.query("/xmlns:MTCommDevices/xmlns:Devices"
                    + "/xmlns:Device[@id=\"" + deviceId + "\"]/xmlns:Components/xmlns:"
                    + componentElement.getLocalName() + "/xmlns:Components/*", context);
            for (int j = 0; j < subComponentNodes.size(); ++j) {
                Element subcomponentElement = (Element) subComponentNodes.get(j);
                Nodes subComponentOperations = subcomponentElement.query("/xmlns:MTCommDevices/xmlns:Devices"
                                + "/xmlns:Device[@id=\""
                                + deviceId
                                + "\"]/xmlns:Components/xmlns:"
                                + componentElement.getLocalName()
                                + "[@id=\"" + componentElement.getAttributeValue("id") + "\"]"
                                + "/xmlns:Components/xmlns:"
                                + subcomponentElement.getLocalName()
                                + "[@id=\"" + subcomponentElement.getAttributeValue("id") + "\"]"
                                + "/xmlns:Operations//xmlns:Operation",
                        context);
                for (int k = 0; k < subComponentOperations.size(); ++k) {
                    Element subComponentOperation = (Element) subComponentOperations.get(k);
                    ComponentOperation componentOperation = new ComponentOperation();
                    componentOperation.setDeviceId(deviceId);
                    componentOperation.setUuid(uuid);
                    componentOperation.setParentComponentId(componentElement.getAttributeValue("id"));
                    componentOperation.setComponentId(subcomponentElement.getAttributeValue("id"));
                    componentOperation.setComponentName(subcomponentElement.getAttributeValue("name"));
                    componentOperation.setComponent(subcomponentElement.getLocalName());
                    componentOperation.setOperationId(subComponentOperation.getAttributeValue("id"));
                    componentOperation.setCategory(subComponentOperation.getAttributeValue("category"));
                    componentOperation.setType(subComponentOperation.getAttributeValue("type"));
                    componentOperation.setName(subComponentOperation.getAttributeValue("name"));
                    componentOperations.add(componentOperation);
                }
            }
        }

        Operations operations = new Operations();
        operations.setDeviceOperations(deviceOperations);
        operations.setComponentOperations(componentOperations);
        return operations;
    }

    private void prettyPrint(Document document) throws IOException {
        System.out.println();
        Serializer serializer = new Serializer(System.out, "UTF-8");
        serializer.setIndent(4);
        serializer.write(document);
        System.out.println();
    }

    private String properCase(String type) {
        // Empty strings should be returned as-is.
        if (type.length() == 0) return "";
        // Strings with only one character uppercased.
        if (type.length() == 1) return type.toUpperCase();
        // Otherwise uppercase first letter, lowercase the rest.
        return type.substring(0, 1).toUpperCase()
                + type.substring(1).toLowerCase();
    }

    private Document getOperationStarter() throws ParsingException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("Operations.xml").getFile());
        Builder parser = new Builder();
        Document operationDoc = parser.build(file);
        return operationDoc;
    }

    private Document getProbe(String deviceId) throws IOException, ParsingException {
        Config config = new Config();
        MTCommConnectionManager connectionManager = new MTCommConnectionManager();
        String probeUrl = config.getMachine(deviceId).getProbe();
        HttpResponse probeResponse = connectionManager.getFromMachine(probeUrl);
        Builder parser = new Builder();
        Document probeDoc = parser.build(probeResponse.getEntity().getContent());
        return probeDoc;
    }
}
