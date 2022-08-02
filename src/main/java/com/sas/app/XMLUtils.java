package com.sas.app;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLUtils {

	private File xmlFile = null;
	public static String APPLICATION_TITLE;
	private final String[] nodes = { "appdata", "properties" };
	public static Integer DEF_TAX_RATE0 = 0;
	public static Integer DEF_TAX_RATE1 = 0;
	public static Integer DEF_TAX_RATE2 = 0;
	public static Integer DEF_TAX_RATE3 = 0;
	public static String COMPANY_WEBPAGE;
	public static String COMPANY_EMAIL;
	public static Integer DEF_YEARS_LIMIT = 0;
	public static String DEF_INVOICE_PATH;
	public static String DEF_PART_NUMBER;
	public static Integer DEF_JOBIDLENGTH = 0;
	public static String DEF_PLATEPREFIX;
	public static String DEF_USER_FIRSTNAME;
	public static String DEF_USER_LASTNAME;
	public static String DEF_USER_PHONE;
	public static String DEF_USER_ADDRESS0;
	public static String DEF_USER_ADDRESS1;
	public static String DEF_USER_MAILCODE;
	public static String DEF_USER_REGION;
	public static String DEF_USER_COUNTRY;
	public static String COMPANY_ADDRESS0;
	public static String COMPANY_ADDRESS1;
	public static String COMPANY_OUTLET;
	public static String COMPANY_PROVSTATE;
	public static String COMPANY_POSTALCODE;
	public static String COMPANY_PHONE;
	public static String COMPANY_FAX;
	public static String COMPANY_PERSONNEL_MGR;
	public static String COMPANY_PERSONNEL_EMPLOYEE;
	public static String COMPANY_LABOURRATE_REG;
	public static String COMPANY_LABOURRATE_SPE;
	public static String COMPANY_LABOURRATE_XTR;
	public static String LOCAL_COUNTRY;
	public static String AUTHOR_NAME;
	public static String AUTHOR_CONTACT;
	public static String APPLICATION_REVISION;
	public static String COMPANY_BUS_NUMBER;
	public static String COMPANY_NAME;
	public static String COMPANY_CITY;
	public static String COMPANY_COUNTRY;
	public static String MAILCODE_ENFORCED;
	public static String MAILCODE_FORMAT;
	public static String EMAIL_PATTERN;

	/**
	 * 
	 */
	public XMLUtils() {
		xmlFile = new File("inputdata.xml");
		// C:\Users\SASEFA\workspace_springboot\AutoService".\\inputdata.xml"
	}

	/**
	 * 
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void readXMLFile() throws SAXException, IOException, ParserConfigurationException {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
		doc.getDocumentElement().normalize();
		//
		NodeList authorList = doc.getElementsByTagName("author");
		for (int n = 0; n < authorList.getLength(); n++) {
			Node authorNode = authorList.item(n);
			if (authorNode.getNodeType() == Node.ELEMENT_NODE) {
				Element authorElem = (Element) authorNode;
				//
				AUTHOR_NAME = (String) authorElem.getElementsByTagName("name").item(0).getChildNodes().item(0)
						.getNodeValue();
				AUTHOR_CONTACT = (String) authorElem.getElementsByTagName("contact").item(0).getChildNodes().item(0)
						.getNodeValue();
				APPLICATION_TITLE = (String) authorElem.getElementsByTagName("title").item(0).getChildNodes().item(0)
						.getNodeValue();
				APPLICATION_REVISION = (String) authorElem.getElementsByTagName("revdate").item(0).getChildNodes()
						.item(0).getNodeValue();
			}
		}
		//
		NodeList propList = doc.getElementsByTagName("company");
		for (int n = 0; n < propList.getLength(); n++) {
			Node propNode = propList.item(n);
			if (propNode.getNodeType() == Node.ELEMENT_NODE) {
				Element companyElem = (Element) propNode;
				//
				COMPANY_OUTLET = (String) companyElem.getElementsByTagName("outlet").item(0).getChildNodes().item(0)
						.getNodeValue();
				COMPANY_BUS_NUMBER = (String) companyElem.getElementsByTagName("busnr").item(0).getChildNodes().item(0)
						.getNodeValue();
				COMPANY_ADDRESS0 = (String) companyElem.getElementsByTagName("address").item(0).getChildNodes().item(0)
						.getNodeValue();
				COMPANY_ADDRESS1 = (String) companyElem.getElementsByTagName("address").item(1).getChildNodes().item(0)
						.getNodeValue();
				COMPANY_CITY = (String) companyElem.getElementsByTagName("city").item(0).getChildNodes().item(0)
						.getNodeValue();
				COMPANY_PROVSTATE = (String) companyElem.getElementsByTagName("provstate").item(0).getChildNodes()
						.item(0).getNodeValue();
				COMPANY_POSTALCODE = (String) companyElem.getElementsByTagName("postalcode").item(0).getChildNodes()
						.item(0).getNodeValue();
				COMPANY_COUNTRY = (String) companyElem.getElementsByTagName("country").item(0).getChildNodes().item(0)
						.getNodeValue();
				COMPANY_PHONE = (String) companyElem.getElementsByTagName("phone").item(0).getChildNodes().item(0)
						.getNodeValue();
				COMPANY_FAX = (String) companyElem.getElementsByTagName("fax").item(0).getChildNodes().item(0)
						.getNodeValue();
				COMPANY_EMAIL = (String) companyElem.getElementsByTagName("companyEmail").item(0).getChildNodes()
						.item(0).getNodeValue();
				COMPANY_WEBPAGE = (String) companyElem.getElementsByTagName("webpage").item(0).getChildNodes().item(0)
						.getNodeValue();
				COMPANY_PERSONNEL_MGR = (String) companyElem.getElementsByTagName("mgr").item(0).getChildNodes().item(0)
						.getNodeValue();
				COMPANY_PERSONNEL_EMPLOYEE = (String) companyElem.getElementsByTagName("employee").item(0)
						.getChildNodes().item(0).getNodeValue();
				COMPANY_LABOURRATE_REG = (String) companyElem.getElementsByTagName("regular").item(0).getChildNodes()
						.item(0).getNodeValue();
				COMPANY_LABOURRATE_SPE = (String) companyElem.getElementsByTagName("special").item(0).getChildNodes()
						.item(0).getNodeValue();
				COMPANY_LABOURRATE_XTR = (String) companyElem.getElementsByTagName("extra").item(0).getChildNodes()
						.item(0).getNodeValue();
			}
		}
	}

	/**
	 * 
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void readXMLCountryInfo() throws SAXException, IOException, ParserConfigurationException {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
		doc.getDocumentElement().normalize();
		//
		NodeList propList = doc.getElementsByTagName("countryInfo");
		for (int n = 0; n < propList.getLength(); n++) {
			Node propNode = propList.item(n);
			if (propNode.getNodeType() == Node.ELEMENT_NODE) {
				Element companyElem = (Element) propNode;
				LOCAL_COUNTRY = (String) companyElem.getElementsByTagName("countryName") //
						.item(0).getChildNodes().item(0).getNodeValue();
				MAILCODE_ENFORCED = (String) companyElem.getElementsByTagName("mailCodeEnforced")//
						.item(0).getChildNodes().item(0).getNodeValue();
				MAILCODE_FORMAT = (String) companyElem.getElementsByTagName("mailCodeFormat") //
						.item(0).getChildNodes().item(0).getNodeValue();
			}
		}
	}

	/**
	 * 
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void readXMLDefaults() throws SAXException, IOException, ParserConfigurationException {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
		doc.getDocumentElement().normalize();
		//
		NodeList propList = doc.getElementsByTagName("defaults");
		for (int n = 0; n < propList.getLength(); n++) {
			Node propNode = propList.item(n);
			String value = "";
			if (propNode.getNodeType() == Node.ELEMENT_NODE) {
				Element companyElem = (Element) propNode;
				//
				value = (String) companyElem.getElementsByTagName("yearsLimit").item(0).getChildNodes().item(0)
						.getNodeValue();
				DEF_YEARS_LIMIT = Integer.parseInt(value);
				//
				value = (String) companyElem.getElementsByTagName("taxRate").item(0).getChildNodes().item(0)
						.getNodeValue();
				DEF_TAX_RATE0 = Integer.parseInt(value);
				//
				value = (String) companyElem.getElementsByTagName("taxRate").item(1).getChildNodes().item(0)
						.getNodeValue();
				DEF_TAX_RATE1 = Integer.parseInt(value);
				//
				value = (String) companyElem.getElementsByTagName("taxRate").item(2).getChildNodes().item(0)
						.getNodeValue();
				DEF_TAX_RATE2 = Integer.parseInt(value);
				//
				value = (String) companyElem.getElementsByTagName("taxRate").item(3).getChildNodes().item(0)
						.getNodeValue();
				DEF_TAX_RATE3 = Integer.parseInt(value);
				//
				DEF_INVOICE_PATH = (String) companyElem.getElementsByTagName("invoice_output_path").item(0)
						.getChildNodes().item(0).getNodeValue();
				//
				DEF_PART_NUMBER = (String) companyElem.getElementsByTagName("partnumber").item(0).getChildNodes()
						.item(0).getNodeValue();
				//
				value = (String) companyElem.getElementsByTagName("jobIDLength").item(0).getChildNodes().item(0)
						.getNodeValue();
				DEF_JOBIDLENGTH = Integer.parseInt(value);
				//
				DEF_PLATEPREFIX = (String) companyElem.getElementsByTagName("platePrefix").item(0).getChildNodes()
						.item(0).getNodeValue();
			}
		}
		//
		propList = doc.getElementsByTagName("defaultuser");
		for (int n = 0; n < propList.getLength(); n++) {
			Node propNode = propList.item(n);
			if (propNode.getNodeType() == Node.ELEMENT_NODE) {
				Element companyElem = (Element) propNode;
				DEF_USER_FIRSTNAME = (String) companyElem.getElementsByTagName("firstname").item(0).getChildNodes()
						.item(0).getNodeValue();
				DEF_USER_LASTNAME = (String) companyElem.getElementsByTagName("lastname").item(0).getChildNodes()
						.item(0).getNodeValue();
				DEF_USER_PHONE = (String) companyElem.getElementsByTagName("phone").item(0).getChildNodes().item(0)
						.getNodeValue();
				//
				DEF_USER_ADDRESS0 = (String) companyElem.getElementsByTagName("street").item(0).getChildNodes().item(0)
						.getNodeValue();
				DEF_USER_ADDRESS1 = (String) companyElem.getElementsByTagName("city").item(0).getChildNodes().item(0)
						.getNodeValue();
				DEF_USER_MAILCODE = (String) companyElem.getElementsByTagName("mailcode").item(0).getChildNodes()
						.item(0).getNodeValue();
				DEF_USER_REGION = (String) companyElem.getElementsByTagName("region").item(0).getChildNodes().item(0)
						.getNodeValue();
				DEF_USER_COUNTRY = (String) companyElem.getElementsByTagName("country").item(0).getChildNodes().item(0)
						.getNodeValue();
			}
		}
	}

	/**
	 * 
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void readXMLBasicFormats() throws SAXException, IOException, ParserConfigurationException {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
		doc.getDocumentElement().normalize();
		////
		NodeList propList = doc.getElementsByTagName("definedformats");
		for (int n = 0; n < propList.getLength(); n++) {
			Node propNode = propList.item(n);
			if (propNode.getNodeType() == Node.ELEMENT_NODE) {
				Element companyElem = (Element) propNode;
				//
				EMAIL_PATTERN = (String) companyElem.getElementsByTagName("email").item(0).getChildNodes().item(0)
						.getNodeValue();
			}
		}
	}

	/**
	 * 
	 * @param vArray
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public void writeXMLFile(String... vArray)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(xmlFile);
		//
		Element rootElement = doc.getDocumentElement();
		Node firstChild = rootElement.getFirstChild();
		Node dataNode = firstChild.getNextSibling();

		dataNode.appendChild(getData(doc, vArray));

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(xmlFile);
		transformer.transform(source, result);
	}

	/**
	 * 
	 * @param doc
	 * @param sarray
	 * @return
	 */
	private Node getData(Document doc, String... sarray) {
		if (sarray.length != nodes.length)
			return null;
		Element dataElem = doc.createElement("data");
		for (int n = 0; n < sarray.length; n++) {
			dataElem.appendChild(getDataElements(doc, dataElem, nodes[n], sarray[n]));
		}
		return dataElem;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isXMLDataExist() { // File xfile = new File(xmlFile);
		Document doc = null;
		//
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
			Element dataElement = (Element) doc.getDocumentElement().getFirstChild().getNextSibling();
			NodeList nodeList = dataElement.getElementsByTagName("data");
			//
			return (nodeList.getLength() > 0);
			//
		} catch (SAXException | IOException | ParserConfigurationException e) {
			return false;
		}
	}

	/**
	 * 
	 * @param doc
	 * @param element
	 * @param name
	 * @param value
	 * @return
	 */
	private Node getDataElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

}