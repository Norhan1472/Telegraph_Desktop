package tgh.desktop.services;

import java.io.ByteArrayOutputStream;


import java.io.StringReader;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.springframework.stereotype.Service;

import org.w3c.dom.Element;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import tgh.desktop.payload.response.response_SubscriberUsage;

@Service
public class GetSubscriberUsage {
	
	
	public response_SubscriberUsage getCustomerPort(String serviceNumber) {
		response_SubscriberUsage response = new response_SubscriberUsage();
    
        
        String soapEndpointUrl =
             //   "http://10.19.86.211:8001"
                //http://161.97.82.42:7003
        		"http://10.19.86.211:8003/CALLS_WS-Project1-context-root-Sim/CALLS_WSPort?WSDL";
        System.out.println("getCustomerPort Url : "+soapEndpointUrl);
        try{
         response = GetSubscriberUsage(soapEndpointUrl,serviceNumber);
        }catch(Exception e){
            System.out.println("eeeeee");
            e.printStackTrace();
//            response = "down;"+ e.getMessage();
        }
        return response;
    }
	

	
	public response_SubscriberUsage GetSubscriberUsage
	(String soapEndpointUrl,String serviceNumber) {
        String balance = "";
        String returnCode = "";      
        response_SubscriberUsage response = new response_SubscriberUsage();
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse =
                soapConnection.call(getSubscriberUsageReq(serviceNumber), soapEndpointUrl);

            String soapMessageString;

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            soapResponse.writeTo(outputStream);
            soapMessageString = outputStream.toString();

            String cleanxmlString= soapMessageString.replaceAll("&lt;", "<");

            System.out.println("getCustomerPort Response SOAP Message:");
            System.out.println("cleanxmlString="+cleanxmlString);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Create a DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the cleaned XML string into a Document object
            Document document = builder.parse(new InputSource(new StringReader(cleanxmlString)));

            // Get the value of the returnCode element
            NodeList nodeList = document.getElementsByTagName("returnCode");
            if (nodeList.getLength() > 0) {
                Element returnCodeElement = (Element) nodeList.item(0);
                returnCode =returnCodeElement.getTextContent();
            }
            
          
                
            NodeList nodeList1 = document.getElementsByTagName("totalBalanceInEgb");
            if (nodeList1.getLength() > 0) {
                Element returnCodeElement1 = (Element) nodeList1.item(0);
                balance =returnCodeElement1.getTextContent();
            }
                
            response.setBalance(balance);
            
            response .setReturnCode(returnCode);
            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
            }
        
        System.out.println("Response ==> "+response );
        System.out.println("balance ==> "+balance );
        return response;
    }
	
	private SOAPMessage getSubscriberUsageReq(String serviceNumber) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        SubscriberUsage(soapMessage, serviceNumber);
 
//        MimeHeaders headers = soapMessage.getMimeHeaders();
//        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("getCustomerPort Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");
        System.out.println("soapMessage="+soapMessage);

        return soapMessage;
    }
	
	
	
	
	private void SubscriberUsage (SOAPMessage soapMessage, String serviceNumber)
			throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "cal";
        String myNamespaceURI = "http://CALLS_WS/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("GetSubscriberUsage", myNamespace);
        SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("SubscriberNumber");
        soapBodyElem3.addTextNode(serviceNumber);
    }

	

	
	
	
}  	
