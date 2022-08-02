package com.sas.fxmlviews.others;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import com.sas.fxmlviews.thirdparty.DialogInterface;
import com.sas.fxmlviews.thirdparty.FXMLDialog;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@FXMLController
public class LoginController implements DialogInterface {
	@Autowired
    private AuthenticationManager authenticationManager;
    //private ScreensConfiguration screens;
    private FXMLDialog dialog;
	public LoginController(ScreensConfiguration screensConfiguration) {
		//this.screens = screens;
	}

	@FXML
	private Label lblLogin;
	
	@FXML
	private TextField txtUsername;
	
	@FXML
	private TextField txtPassword;

	@Override
	public void setDialog(FXMLDialog dialog) {
		 this.dialog = dialog;
	}
}
