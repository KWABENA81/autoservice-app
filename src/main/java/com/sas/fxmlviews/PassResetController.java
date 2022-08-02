package com.sas.fxmlviews;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import com.sas.service.UsersService;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

@Component
@FXMLController
public class PassResetController {
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnSubmit;
	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtFirstname;
	@FXML
	private TextField txtLastname;
	@FXML
	private PasswordField passNew;
	@FXML
	private PasswordField passConfirm;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UsersService usersService;
	private ViewController viewControllerInstance;
	private static final Logger LOG = LoggerFactory.getLogger("AutoService");

	public PassResetController() {
		viewControllerInstance = ViewController.getInstance();
	}

	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) {
		windowClose(btnSubmit);
	}

	@FXML
	protected void handleCancelButtonAction(ActionEvent event) {
		windowClose(btnCancel);
	}

	private void windowClose(Button btn) {
		try {
			Stage stage = (Stage) btn.getScene().getWindow();
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					event.consume();
				}
			});
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			viewControllerInstance.showApplicationEntryView();
		}
	}
}
