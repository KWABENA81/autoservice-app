package com.sas.service;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

@Component
public class DefaultAwesomeActionService implements AwesomeActionService {

	private ResourceBundle bundle = ResourceBundle.getBundle("msgs.autoService");

	@Override
	public String processName(final String name) {
		System.out.println("jflsk kdlfsk :" + name);
		return MessageFormat.format(bundle.getString("greeting"), name);
	}

}
