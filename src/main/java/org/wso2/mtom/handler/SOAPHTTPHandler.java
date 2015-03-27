package org.wso2.mtom.handler;

import java.util.List;
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

public class SOAPHTTPHandler implements SOAPHandler<SOAPMessageContext> {

	@Override
	public boolean handleMessage(SOAPMessageContext context) {

		final Boolean outboundProperty = (Boolean) context
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if (Boolean.FALSE.equals(outboundProperty)) {
			System.out.println("handleMessage - dealing with inbound flow");
			System.out.println("Request Method - "
					+ context.get(MessageContext.HTTP_REQUEST_METHOD));
			System.out.println("Request Path Info - "
					+ context.get(MessageContext.PATH_INFO));
			System.out.println("Request query String - "
					+ context.get(MessageContext.QUERY_STRING));
			final Map<String, List<String>> headers = (Map<String, List<String>>) context
					.get(MessageContext.HTTP_REQUEST_HEADERS);
			for (final String headerName : headers.keySet()) {
				System.out.println("Request Header - " + headerName
						+ ", header value " + headers.get(headerName));
			}

			final Map inMsgAttachments = (Map) context
					.get(MessageContext.INBOUND_MESSAGE_ATTACHMENTS);
			System.out.println("Any incoming attachments ? "
					+ !inMsgAttachments.isEmpty());

		} else if (Boolean.TRUE.equals(outboundProperty)) {
			System.out.println("handleMessage - dealing with outbound flow");
			System.out.println("Response code "
					+ context.get(MessageContext.HTTP_RESPONSE_CODE));

			final Map<String, List<String>> headers = (Map<String, List<String>>) context
					.get(MessageContext.HTTP_RESPONSE_HEADERS);
			if (null == headers) {
				System.out.println("No response Headers");
			} else {
				for (final String headerName : headers.keySet()) {
					System.out.println("Response Header - " + headerName
							+ ", header value " + headers.get(headerName));
				}

			}

			final Map outMsgAttachments = (Map) context
					.get(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS);

			System.out.println("Any incoming attachments ? "
					+ !outMsgAttachments.isEmpty());
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
		} catch (SOAPException e) {
		}
	}

}
