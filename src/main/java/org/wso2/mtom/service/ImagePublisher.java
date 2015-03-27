package org.wso2.mtom.service;

import javax.xml.ws.Endpoint;

public class ImagePublisher{
 
    public static void main(String[] args) {
 
	Endpoint.publish("http://localhost:9090/ws/image", new ImageServerImpl());
 
	System.out.println("Server is published!");
	
 
    }
 
}