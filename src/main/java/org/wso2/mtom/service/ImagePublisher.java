package org.wso2.mtom.service;

import java.util.logging.Logger;

import javax.xml.ws.Endpoint;
import org.wso2.mtom.service.*;

public class ImagePublisher{
	
	private static Logger logger = Logger.getAnonymousLogger();
	private final int PORT = 9090;
	
	Endpoint endpoint = Endpoint.create(new ImageServerImpl());
    
    public void startService(){
    	endpoint.publish("http://localhost:9090/ws/image");
    	logger.info("Service published on PORT " + PORT);
    }
 
    public void stopService(){
    	endpoint.stop();
    	logger.info("Service ended");
    }
}