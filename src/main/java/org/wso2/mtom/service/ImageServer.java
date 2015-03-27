package org.wso2.mtom.service;

import java.awt.Image;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
 
//Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.RPC)
@HandlerChain(file="handler-chain.xml")
public interface ImageServer{
 
	//download a image from server
	@WebMethod Image downloadImage(String name);
 
	//update image to server
	@WebMethod Image uploadImage(Image data);
 
}