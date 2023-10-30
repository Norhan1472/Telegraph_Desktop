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

import tgh.desktop.payload.response.Response_QueryBillingAccount;
import tgh.desktop.payload.response.lineInfo_Response;
import tgh.desktop.payload.response.response_SubscriberUsage;

@Service
public class SOAPConnector {
	
	private SOAPMessage getGeneralLineInfoSOAPReq(String serviceNumber) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        
        getGeneralLineInfoSoapEnv(soapMessage, serviceNumber);


        soapMessage.saveChanges();

        return soapMessage;
    }
	
	
	
	private void getGeneralLineInfoSoapEnv(SOAPMessage soapMessage, String serviceNumber) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "cal";
        String myNamespaceURI = "http://CALLS_WS/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("GetGeneralLineInfo", myNamespace);
        SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("serviceNumber");
        soapBodyElem3.addTextNode(serviceNumber);
    }
	
	public lineInfo_Response getGeneralLineInfo(String serviceNumber) {
		lineInfo_Response response = new lineInfo_Response();

		  System.out.println("NUMBER "+serviceNumber);
          //http://10.19.86.211:8001
        //http://161.97.82.42:7003
        String soapEndpointUrl ="http://10.19.86.211:8003/CALLS_WS-Project1-context-root-Sim/CALLS_WSPort?wsdl";//"http://10.27.0.145:7008/TelegrhapClient/BssApiPort?WSDL";
        System.out.println("soapEndpointUrl : "+soapEndpointUrl);
        try{
        	
        	System.out.println("ERRRR ==== ");
         response = getGeneralLineInfoCallSoapWS2(soapEndpointUrl,serviceNumber);
 
         System.out.println("responsessss  "+response);
        }catch(Exception e){
            System.out.println("eeeeee");
            e.printStackTrace();
//            response = "down;"+ e.getMessage();
        } 
        return response;
    }
	
	public lineInfo_Response getGeneralLineInfoCallSoapWS2
	(String soapEndpointUrl,String serviceNumber) {
		
		
        String paidMode = "";
        String returnDesc = "";
        String subscriberStatus = "";
        String returnCode = "";      
        lineInfo_Response response = new lineInfo_Response();
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            
            SOAPMessage soapResponse =
                soapConnection.call(getGeneralLineInfoSOAPReq(serviceNumber), soapEndpointUrl);
            System.out.println("usageResponse");
            String soapMessageString;

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            soapResponse.writeTo(outputStream);
            soapMessageString = outputStream.toString();

            String cleanxmlString= soapMessageString.replaceAll("&lt;", "<");

            
//            System.out.println("GeneralLineInfo Response SOAP Message:");
//            System.out.println("cleanxmlString="+cleanxmlString);
//            soapResponse.writeTo(System.out);
//            System.out.println();
//            returnCode=soapResponse.getSOAPBody()
//            .getElementsByTagName("returnCode")
//            .item(0)
//            .getTextContent();
            // Create a DocumentBuilderFactory
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
            
            NodeList nodeList2 = document.getElementsByTagName("paidMode");
            if (nodeList2.getLength() > 0) {
                Element returnCodeElement2 = (Element) nodeList2.item(0);
                paidMode =returnCodeElement2.getTextContent();
            }
            response .setPaidMode(paidMode);
            
       

            NodeList nodeList3 = document.getElementsByTagName("returnDesc");
            if (nodeList3.getLength() > 0) {
                Element returnCodeElement3 = (Element) nodeList3.item(0);
                returnDesc =returnCodeElement3.getTextContent();
            }
                response .setReturnDesc(returnDesc);
                
                
            NodeList nodeList4 = document.getElementsByTagName("subscriberStatus");
            if (nodeList4.getLength() > 0) {
                Element returnCodeElement4 = (Element) nodeList4.item(0);
                subscriberStatus =returnCodeElement4.getTextContent();
            }
                
            response .setSubscriberStatus(subscriberStatus);
            
            response .setReturnCode(returnCode);
            
     if(response.getPaidMode().equals("PREP")) {
            	
    	 System.out.println("usageResponse");   	
    	 GetSubscriberUsage subscriberUsage = new GetSubscriberUsage();
            	
            	response_SubscriberUsage usageResponse = new response_SubscriberUsage(); 
            	usageResponse = subscriberUsage.getCustomerPort(serviceNumber);
            	
            	response.setBalance(usageResponse.getBalance());
            	System.out.println("usageResponse"+usageResponse);
            	
            }
            else if(response.getPaidMode().equals("POST")) {
            	
            	QueryBillingAccount billingAccount = new QueryBillingAccount();
            	
            	Response_QueryBillingAccount usageResponse = new Response_QueryBillingAccount(); 
            	usageResponse = billingAccount.getCustomerPort(serviceNumber,"");
            	
            	response.setBalance(usageResponse.getBalance());
            	
            }
            else {
            	response .setPaidMode("non");
            	response.setSubscriberStatus("-1");
            }
            soapConnection.close();
            
            
            
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
            }
//        
//        System.out.println("Response ==> "+response );
//        System.out.println("paid ==> "+paidMode );
        return response;
    }
	
	public lineInfo_Response getGeneralLineInfoCallSoapWS(String soapEndpointUrl, String serviceNumber) {
		  String paidMode = "";
	      String returnDesc = "";
	      String subscriberStatus = "";
	      String returnCode = "";      
          String error = "";
        
        
        lineInfo_Response response = new lineInfo_Response();
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse =
                soapConnection.call(getGeneralLineInfoSOAPReq(serviceNumber), soapEndpointUrl);

            // Print the SOAP Response
//            System.out.println("GeneralLineInfo Response SOAP Message:");
//            soapResponse.writeTo(System.out);
//            System.out.println();
            if (soapResponse.getSOAPBody()
                            .getElementsByTagName("returnCode")
                            .item(0) != null && !soapResponse.getSOAPBody()
                                                             .getElementsByTagName("returnCode")
                                                             .item(0)
                                                             .getTextContent()
                                                             .equals("0")) {
                error = soapResponse.getSOAPBody()
                                    .getElementsByTagName("returnCode")
                                    .item(0)
                                    .getTextContent();
                response.setReturnCode(returnCode);
            } else {
                if (soapResponse.getSOAPBody()
                                .getElementsByTagName("paidMode")
                                .item(0) != null) {
                	paidMode = soapResponse.getSOAPBody()
                                        .getElementsByTagName("paidMode")
                                        .item(0)
                                        .getTextContent();
                } else {
                	paidMode = "";
                }
                
                response.setPaidMode(paidMode);
                
                if (soapResponse.getSOAPBody()
                                .getElementsByTagName("returnDesc")
                                .item(0) != null) {
                	returnDesc = soapResponse.getSOAPBody()
                                          .getElementsByTagName("returnDesc")
                                          .item(0)
                                          .getTextContent();
                } else {
                	returnDesc = "";
                }
                
                response.setReturnDesc(returnDesc);
                
                if (soapResponse.getSOAPBody()
                                .getElementsByTagName("subscriberStatus")
                                .item(0) != null) {
                	subscriberStatus = soapResponse.getSOAPBody()
                                         .getElementsByTagName("subscriberStatus")
                                         .item(0)
                                         .getTextContent();
                } else {
                	subscriberStatus = "";
                }
               
                response.setSubscriberStatus(subscriberStatus);
                
                
            }
            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
//            response.
        }
        return response;
    }
	
	
}