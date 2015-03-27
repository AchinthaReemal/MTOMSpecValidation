package org.wso2.mtom.client;

import static org.testng.AssertJUnit.assertTrue;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMText;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.testng.annotations.Test;

public class ESBMTOMClient {

    private static final int BUFFER = 2048;

    private static String getProperty(String name, String def) {
        String result = System.getProperty(name);
        if (result == null || result.length() == 0) {
            result = def;
        }
        return result;
    }

    private static ServiceClient createServiceClient() throws AxisFault {
//       String repo = getProperty("repository", "client_repo");
    	String repo = "/home/achintha/Desktop/wso2esb-4.9.0-M7-SNAPSHOT/samples/axis2Client/client_repo";
        if (repo != null && !"null".equals(repo)) {
            ConfigurationContext configContext =
                    ConfigurationContextFactory.
                            createConfigurationContextFromFileSystem(repo,
                                    repo + File.separator + "conf" + File.separator + "axis2.xml");
            return new ServiceClient(configContext, null);
        } else {
            return new ServiceClient();
        }
    }

    @Test
    public void ESBMTOMValidationTest() throws Exception {

        String targetEPR = getProperty("opt_url", "http://localhost:7777/services/MTOMSwASampleService");
        String fileName = getProperty("opt_file", "/home/achintha/Desktop/wso2esb-4.9.0-M7-SNAPSHOT/repository/samples/resources/mtom/asf-logo.gif");
        String mode = getProperty("opt_mode", "mtom");

//        if (args.length > 0) mode = args[0];
//        if (args.length > 1) targetEPR = args[1];
//        if (args.length > 2) fileName = args[2];

        if ("mtom".equals(mode)) {
            sendUsingMTOM(fileName, targetEPR);
        } 
//        else if ("swa".equals(mode)) {
//            sendUsingSwA(fileName, targetEPR);
//        }
        assertTrue(true);
        // let the server read the stream before exit
        Thread.sleep(1000);   
//        System.exit(0);
    } 

    public static OMElement sendUsingMTOM(String fileName, String targetEPR) throws IOException {
    	
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace ns = factory.createOMNamespace("http://service.mtom.wso2.org/", "ser");
        OMElement payload = factory.createOMElement("downloadImage", ns);
        
        QName qName = new QName("arg0");
        OMElement argument = factory.createOMElement(qName);
//        OMElement request = factory.createOMElement("request", ns);
//        OMElement image = factory.createOMElement("image", ns);

        System.out.println("Sending file : " + fileName + " as MTOM");
        FileDataSource fileDataSource = new FileDataSource(new File(fileName));
        DataHandler dataHandler = new DataHandler(fileDataSource);
//        OMText textData = factory.createOMText(dataHandler, true);
        OMText textData = factory.createOMText("esb64_med.png");
        
//        image.addChild(textData);
//        request.addChild(image);
        argument.addChild(textData);
        payload.addChild(argument);

        ServiceClient serviceClient = createServiceClient();
        Options options = new Options();
        options.setTo(new EndpointReference(targetEPR));
        options.setAction("urn:downloadImage");
        options.setProperty(Constants.Configuration.ENABLE_MTOM, Constants.VALUE_TRUE);

        
        
        serviceClient.setOptions(options);
        OMElement response = serviceClient.sendReceive(payload);
//        OMText binaryNode = (OMText) response.
//                getFirstChildWithName(new QName("http://services.samples", "response")).
//                getFirstChildWithName(new QName("http://services.samples", "image")).
//                getFirstOMChild();
        OMText binaryNode = (OMText) response.
        		getFirstChildWithName(new QName("return")).
        		getFirstOMChild();
        dataHandler = (DataHandler) binaryNode.getDataHandler();
        InputStream is = dataHandler.getInputStream();

        File tempFile = File.createTempFile("mtom-", ".png");
        FileOutputStream fos = new FileOutputStream(tempFile);
        BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

        byte data[] = new byte[BUFFER];
        int count;
        while ((count = is.read(data, 0, BUFFER)) != -1) {
            dest.write(data, 0, count);
        }

        dest.flush();
        dest.close();
        System.out.println("Saved response to file : " + tempFile.getAbsolutePath());
        return response;
    }
}