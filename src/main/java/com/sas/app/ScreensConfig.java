package com.sas.app;

import java.util.HashMap;
import java.util.Map;

import com.sas.fxmlviews.ApplicationEntryView;
import com.sas.fxmlviews.RootView;

import de.felixroske.jfxsupport.AbstractFxmlView;

public class ScreensConfig {
	private static ScreensConfig screens;
	private Map<Object, Object> classMap;

	private ScreensConfig() {
		classMap = new HashMap<Object, Object>();
	}

	public static ScreensConfig getInstance() {
		if (screens == null) {
			synchronized (ScreensConfig.class) {
				if (screens == null) {
					screens = new ScreensConfig();
				}
			}
		}
		return screens;
	}

	

	public Object getScreensKey(Object value) {
		return classMap.get(value);
	}

	public void map(Class<ApplicationEntryView> class1, Object class2) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public void mapController(String string, Class<RootView> class2) throws ClassNotFoundException {
		StringBuilder sb = new StringBuilder();
		sb.append(string.replace("Controller", "View"));
		//
		Class<? extends AbstractFxmlView> cls = (Class<? extends AbstractFxmlView>) Class.forName(sb.toString());
		classMap.put(class2, cls);
	}

	public Class<? extends AbstractFxmlView> getView() {
		// TODO Auto-generated method stub
		return null;
	}
}