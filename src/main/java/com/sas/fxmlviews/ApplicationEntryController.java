package com.sas.fxmlviews;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sas.MainApp;
import com.sas.app.FXmlUtils;
import com.sas.entity.Users;
import com.sas.service.AppUserService;
import com.sas.service.UsersService;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

@Component
@FXMLController
public class ApplicationEntryController {

	@FXML
	private Label lblInfo;
	@FXML
	private Label lblMsg;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnLogin;
	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField passField;
	@FXML
	private Hyperlink hlinkPasswordReset;
	@FXML
	private Hyperlink hlinkRegister;
	@FXML
	private AnchorPane apaneMain;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private UsersService usersService;

	private static final Logger LOG = LoggerFactory.getLogger("AutoService");

	private Authentication authenticationToken;
	private ViewController viewControllerInstance;

	public ApplicationEntryController() {
		viewControllerInstance = ViewController.getInstance();
		MainApp.getStage().setResizable(false);
	}

	@FXML
	private void initialize() {

	}

	@FXML
	protected void handleLoginButtonAction(ActionEvent event) {
		Window owner = btnLogin.getScene().getWindow();
		if (FXmlUtils.isValid(txtUsername) && FXmlUtils.isValid(passField)) {
			authenticationToken = new UsernamePasswordAuthenticationToken(txtUsername.getText(), passField.getText());
			try {
				authenticationToken = authenticationManager.authenticate(authenticationToken);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);

				FXmlUtils.showAlert(Alert.AlertType.INFORMATION, owner, "Login Successful!",
						"Welcome " + txtUsername.getText());
				LOG.info("Login Successful!", "Welcome " + txtUsername.getText());

				viewControllerInstance.showRootView(null);
			} catch (AuthenticationException | IOException e) {
				FXmlUtils.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Invalid Username / password");
				LOG.info("Form Error!", "Invalid Username / password");

				// viewControllerInstance.showRootView(null);
			}
		} else {
			FXmlUtils.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Incomplete Username / password");
		}

		if (usersService != null) {
			try {
				List<Users> users = (List<Users>) usersService.findAll();
				users.stream().map(Users::getUsername).forEach(x -> System.out.println(x));
			} catch (Exception e) {
				LOG.info("Window Error!", "Window Close Event failed");
			}
		}
		return;
	}

	@FXML
	protected void handleCancelButtonAction(ActionEvent event) {
		windowClose(btnCancel);
	}

	@FXML
	protected void hlinkPasswordResetAction(ActionEvent event) {
		// viewControllerInstance.showPassResetView();
		try {
			viewControllerInstance.showRootView(null);
		} catch (IOException e) {
			LOG.info("Root Window Transfer Error", "Root Window Opening Error");
		}
	}

	@FXML
	protected void hlinkRegisterAction(ActionEvent event) {
		viewControllerInstance.showRegisterView(event);
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
			LOG.info("Window Error!", "Window Close Event failed");
		} finally {
			// ViewController.showApplicationEntryView();
			try {
				viewControllerInstance.showRootView(null);
			} catch (IOException e) {
				LOG.info("Root Window Transfer Error", "Root Window Opening Error");
			}
		}
	}

}