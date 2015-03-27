package org.wso2.mtom.handler;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;
 
public class SOAPHTTPBindingHeaderHandler implements SOAPHandler<SOAPMessageContext>{
 
	
   @Override
   public boolean handleMessage(SOAPMessageContext context) {
 
	System.out.println("Server : handleMessage()......");
 
	if(!(Boolean)context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)){
			
		
			//MessageContext messageContext=wsCtxt.getMessageContext();
			System.out.println("Handler Received");
			System.out.println("\n\n\t Got MessageContext context = "+context);
			Map map = (Map)context.get(MessageContext.HTTP_REQUEST_HEADERS);
			String str="";
			 
			Set entries = map.entrySet();
			Iterator iterator = entries.iterator();
			while (iterator.hasNext())
			{
			Map.Entry entry = (Map.Entry)iterator.next();
			str=str+"\nHeader:" + entry.getKey() + " \tValue: " + entry.getValue();
			System.out.println("\n\tHeader:" + entry.getKey() + " : " + entry.getValue());
			}
			 
			System.out.println("\n\n\t\tClient's HTTP Headers are : "+str);
//			return "\n\t Your HTTP Headers are : "+str;
    }

    return true;
	}
 
	@Override
	public boolean handleFault(SOAPMessageContext context) {
 
		System.out.println("Server : handleFault()......");
 
		return true;
	}
 
	@Override
	public void close(MessageContext context) {
		System.out.println("Server : close()......");
	}
 
	@Override
	public Set<QName> getHeaders() {
		System.out.println("Server : getHeaders()......");
		return null;
	}
 
     private void generateSOAPErrMessage(SOAPMessage msg, String reason) {
       try {
          SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();
          SOAPFault soapFault = soapBody.addFault();
          soapFault.setFaultString(reason);
          throw new SOAPFaultException(soapFault); 
       }
       catch(SOAPException e) { }
    }
 
}