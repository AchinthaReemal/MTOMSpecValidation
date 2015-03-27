package org.wso2.mtom.service;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.MTOM;
 
//Service Implementation Bean
@MTOM
@WebService(endpointInterface = "org.wso2.mtom.service.ImageServer")
@HandlerChain(file="handler-chain.xml")
public class ImageServerImpl implements ImageServer{
 
	@Override
	public Image uploadImage(Image data) {
 
		if(data!=null){
			//store somewhere
//			return "Upload Successful";
			
			URL url = null;
			try {
				url = new URL("https://s3.amazonaws.com/cloud.ohloh.net/attachments/21894/esb64_med.png");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Image image = null;
			try {
				image = ImageIO.read(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    return image;
 
		}
 
		throw new WebServiceException("Upload Failed!");
 
	}
	
	@Override
	public Image downloadImage(String name) {
 
		try {
 
//			File image = new File("/home/achintha/Desktop/" + name);
//			return ImageIO.read(image);
			
			URL url = new URL("https://s3.amazonaws.com/cloud.ohloh.net/attachments/21894/" + name);
		    Image image = ImageIO.read(url);
		    
		    return image;
 
		} catch (IOException e) {
 
			e.printStackTrace();
			return null; 
 
		}
	}
 
	
 
}
