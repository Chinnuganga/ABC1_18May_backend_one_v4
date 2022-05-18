package com.scripted.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import junit.framework.Assert;

public class SOAPUtil {
	public static Logger LOGGER = Logger.getLogger(SOAPUtil.class);

	public static SOAPMessage createSOAPRequest(String fileName) {

		SOAPMessage soapMessage = null;

		try {
			String prjctPath = System.getProperty("user.dir");
			final String filePath = prjctPath + "/src/main/resources/WebServices/SOAPRequest/" + fileName;
			soapMessage = MessageFactory.newInstance().createMessage();
			SOAPPart soapPart = soapMessage.getSOAPPart();
			soapPart.setContent(new StreamSource(new FileInputStream(filePath)));
			soapMessage.saveChanges();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while creating SOAP request : Exception : " + e);
			Assert.fail("Error while creating SOAP request : Exception : " + e);
		}
		return soapMessage;
	}

	public static SOAPMessage callSoapWebService(SOAPMessage soapMessage, String soapEndpointUrl) throws SOAPException 
	{

		SOAPConnection soapConnection = null;
		SOAPMessage soapResponse = null;

		try {
			class TrustAllHosts implements HostnameVerifier {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			}

			// Dummy class implementing X509TrustManager to trust all certificates

			class TrustAllCertificates implements X509TrustManager {

				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {

				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {

				}
			}

			HttpsURLConnection httpsConnection = null;
			SSLContext sslContext = SSLContext.getInstance("SSL");
			TrustManager[] trustAll = new TrustManager[] { new TrustAllCertificates() };
			sslContext.init(null, trustAll, new java.security.SecureRandom());

			// Set trust all certificates context to HttpsURLConnection
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
			URL soapUrl = new URL(soapEndpointUrl);
			httpsConnection = (HttpsURLConnection) soapUrl.openConnection();

			// Trust all hosts
			httpsConnection.setHostnameVerifier(new TrustAllHosts());
			httpsConnection.connect();
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			soapConnection = soapConnectionFactory.createConnection();
			soapMessage.getMimeHeaders().removeHeader("SOAPAction");
			soapResponse = soapConnection.call(soapMessage, soapEndpointUrl);
			// soapResponse.writeTo(System.out);
			LOGGER.info("SoapResponse :" + soapResponse.toString());
		} catch (Exception e) {
			System.err.println(
					"\nError occurred while sending SOAP Request to Server!\n Make sure you have the correct Endpoint URL and SOAP XML Request!\n");
			e.printStackTrace();
			LOGGER.error("Exception :" + e);
			Assert.fail("\nError occurred while sending SOAP Request to Server!\n Make sure you have the correct Endpoint URL and SOAP XML Request!\n Exception : " + e);
		} finally {
			if (soapConnection != null) {
				soapConnection.close();
			}
		}
		return soapResponse;
	}

	public static String getNodeValue(Document doc, String locator) {
		String nodeValue = null;
		try {
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xpath = xPathFactory.newXPath();
			XPathExpression expr = xpath.compile(locator);
			nodeValue = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			LOGGER.error("Error while getting the node value"+"Exception :" + e);
			Assert.fail("Error while getting the node value"+"Exception :" + e);
		}
		return nodeValue;
	}

	public static String getStatusCode(SOAPMessage soapMessage) {
		String status_code = null;
		try {
			SOAPBody body = soapMessage.getSOAPBody();
			NodeList returnList = body.getElementsByTagName("web:RES");
			for (int i = 0; i < returnList.getLength(); i++) {
				NodeList innerResultList = returnList.item(i).getChildNodes();
				for (int j = 0; j < innerResultList.getLength(); j++) {
					if (innerResultList.item(j).getNodeName().equalsIgnoreCase("web:RETURNCODE")) {
						status_code = String.valueOf(innerResultList.item(j).getTextContent().trim());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception :" + e);
			Assert.fail("Exception :" + e);
		}
		return status_code;
	}

	public static Document getDocumentObject(SOAPMessage soapMsg) throws Exception {
		Document doc = null;
		try {
			Source src = soapMsg.getSOAPPart().getContent();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMResult result = new DOMResult();
			transformer.transform(src, result);

			doc = (Document) result.getNode();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception :" + e);
			Assert.fail("Error while getting the document object :"+e);
		}
		return doc;
	}

	public static Document parseResponse(String xmlData) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new InputSource((new StringReader(xmlData))));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception :" + e);
			Assert.fail("Exception :" + e);
		}
		return doc;
	}

	public static void writeToFile(Document xmlDoc, String fileName) {
		try {
			String prjctPath = System.getProperty("user.dir");
			final String filePath = prjctPath + "/src/main/resources/WebServices/SOAPResponse/" + fileName;
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(xmlDoc);
			StreamResult streamResult = new StreamResult(new File(filePath));
			transformer.transform(domSource, streamResult);
			Thread.sleep(1000);
			System.out.println(" XML response file has been successfully created");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while writing XML response  file"+"Exception :"+e);
			Assert.fail("Error while writing XML response  file"+"Exception :"+e);
		}
	}
}
