package com.sas.config;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ApplicationLogger extends Logger {
	public static String filePath = "src\\application\\resources\\log4j.xml";

	protected ApplicationLogger(String name) {
		super(name);
		PropertyConfigurator.configure(filePath);
		this.info(name);
	}

}
