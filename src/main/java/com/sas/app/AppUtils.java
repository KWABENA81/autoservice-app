package com.sas.app;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class AppUtils {
	private static final Logger LOG = LoggerFactory.getLogger("AutoService");
	private static AppUtils instance = null;

	private AppUtils() {
		readProperties();
	}

	public static synchronized AppUtils getInstance() {
		if (instance == null) {
			instance = new AppUtils();
		}
		return instance;
	}

	private void readProperties() {
		XMLUtils xmlUtil = new XMLUtils();
		try {
			xmlUtil.readXMLBasicFormats();
			xmlUtil.readXMLCountryInfo();
			xmlUtil.readXMLFile();
			xmlUtil.readXMLDefaults();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			LOG.info("Error:", "readProperties -> SAXException | IOException | ParserConfigurationException Error");
		}

	}

}
