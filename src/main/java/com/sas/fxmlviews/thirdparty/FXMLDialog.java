package com.sas.fxmlviews.thirdparty;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Callback;

public class FXMLDialog extends Stage/* implements DialogInterface */ {
	
	public FXMLDialog(DialogInterface diag, URL url, Window window) {
		this(diag, url, window, StageStyle.DECORATED);
	}

	public FXMLDialog(final DialogInterface diag, URL url, Window window, StageStyle style) {
		super(style);
		initOwner(window);
		initModality(Modality.WINDOW_MODAL);
		FXMLLoader loader = new FXMLLoader(url);
		try {
			loader.setControllerFactory(new Callback<Class<?>, Object>() {
				@Override
				public Object call(Class<?> aClass) {
					return diag;
				}
			});
			diag.setDialog(this);
			setScene(new Scene((Parent) loader.load()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
