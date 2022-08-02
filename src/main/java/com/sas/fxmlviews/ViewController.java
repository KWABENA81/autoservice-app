package com.sas.fxmlviews;

import java.io.IOException;

import com.sas.MainApp;
import com.sas.entity.Users;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.stage.Modality;

@FXMLController
public class ViewController {

	private static ViewController instance;

	public void showPassResetView() {
		MainApp.showView(PassResetView.class/* , Modality.NONE */);
	}

	public void showInventoryAlertView() {
		MainApp.showView(InventoryAlertView.class/*, Modality.WINDOW_MODAL*/);
	}

	public void showRegisterView(ActionEvent event) {
		MainApp.showView(RegisterView.class/* , Modality.NONE */);
	}

	public void showApplicationEntryView() {
		MainApp.showView(ApplicationEntryView.class/* , Modality.NONE */);
	}

	public void showRootView(Users user) throws IOException {
		MainApp.showView(RootView.class);
	}

	public static ViewController getInstance() {
		if (instance == null) {
			synchronized (ViewController.class) {
				if (instance == null) {
					instance = new ViewController();
				}
			}
		}
		return instance;
	}
}
