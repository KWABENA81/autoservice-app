package com.sas.fxmlviews.others;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sas.app.FXmlUtils;
import com.sas.entity.Users;
import com.sas.repository.AppUserRepository;
import com.sas.repository.UsersRepository;
import com.sas.service.AppUserService;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;

@Component
@FXMLController
public class ApplicationInfoController {

	@FXML
	private Label lblInfo;
	@FXML
	private Label lblMsg;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnLogin;
	// @FXML
	// private Button btnContinue;

	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField passField;
	@FXML
	private Hyperlink hlinkLoginIssue;
	@Autowired
	private AppUserService appUserService;

	@Autowired(required = true)
	private AppUserRepository appUserRepository;

	@Autowired(required = true)
	private UsersRepository usersRepository;

	public ApplicationInfoController() {
		// lblInfo = new Label();//txtUsername=new TextField();
		// passField=new PasswordField();
		// webViewMessage = new WebView();
		// taMessage = new TextArea();
	}

	@FXML
	private void initialize() {
		// txtLogin.setText("jfdsld");
		String msg = "Default test message on to label";
		lblMsg.setText(msg);
		// btnLogin.disabledProperty().bind(Bindings.createBooleanBinding(()->
		// (txtLogin.getText().trim().isEmpty(),txtLogin.textProperty())
		// );
		// btnLogin.disableProperty().bind(txtLogin.textProperty().isEmpty()
		// /* .or(passField.textProperty().isEmpty())*/);

		// btnLogin.disableProperty().bind(BooleanExpression.booleanExpression(
		// new
		// SimpleBooleanProperty(txtLogin.getText().trim().isEmpty())/*.or(new
		// SimpleBooleanProperty(passField.getText().trim().isEmpty()))*/));
	}

	@FXML
	protected void handleLoginButtonAction(ActionEvent event) {
		Window owner = btnLogin.getScene().getWindow();
		if (!FXmlUtils.isValid(txtUsername) || !FXmlUtils.isValid(passField)) {
			FXmlUtils.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Invalid Username / password");
			return;
		}
		FXmlUtils.showAlert(Alert.AlertType.INFORMATION, owner, "Login Successful!", "Welcome " + txtUsername.getText());
	}

	@FXML
	protected void handleCancelButtonAction(ActionEvent event) {
		Window owner = btnCancel.getScene().getWindow();
		//

	}

	@FXML
	protected void hlinkLoginIssueAction(ActionEvent event) {
		Window owner = btnCancel.getScene().getWindow();
		//
		Users users = usersRepository.findOne((long) 1);
		if (users != null)// .getFirstname();
			lblMsg.setText(users.getEmail());
		else
			lblMsg.setText("null personnel ");
	}

}