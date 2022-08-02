package com.sas.fxmlviews;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sas.app.FXmlUtils;
import com.sas.app.XMLUtils;
import com.sas.entity.Address;
import com.sas.entity.Country;
import com.sas.entity.Region;
import com.sas.entity.UStatus;
import com.sas.entity.Users;
import com.sas.service.AddressService;
import com.sas.service.CountryService;
import com.sas.service.RegionService;
import com.sas.service.UStatusService;
import com.sas.service.UsersService;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

@Component
@FXMLController
public class RegisterController {
	@FXML
	private Label lblInfo;
	@FXML
	private TextField txtFirstname;
	@FXML
	private TextField txtLastname;
	@FXML
	private TextField txtAddress;
	@FXML
	private TextField txtOtherAddress;
	@FXML
	private TextField txtMailCode;
	@FXML
	private TextField txtCity;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtPhoneMain;
	@FXML
	private TextField txtPhoneOther;
	@FXML
	private TextField txtUsername;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnSubmit;
	@FXML
	private PasswordField passField;
	@FXML
	private PasswordField passConfirm;
	// @Autowired private AuthenticationManager authenticationManager;
	@Autowired
	private UsersService usersService;
	@Autowired
	private UStatusService ustatusService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private AddressService addressService;
	@FXML
	private ComboBox<Region> cboRegion;
	private ObservableList<Region> cboRegionData;

//	private String localeCountry;
//	private Boolean boolEnforcedMailCode;
//	private String mailCodePattern;
	private ViewController viewControllerInstance;
	private static final Logger LOG = LoggerFactory.getLogger("AutoService");
	//private AppUtils appUtils;

	public RegisterController() {
		//appUtils = AppUtils.getInstance();
		viewControllerInstance = ViewController.getInstance();
	}

	public void initialize() {
		loadRegions();

		txtFirstname.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtFirstname.setText(newValue.toUpperCase());
			}
		});
		txtLastname.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtLastname.setText(newValue.toUpperCase());
			}
		});
		txtAddress.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtAddress.setText(newValue.toUpperCase());
			}
		});
		txtCity.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtCity.setText(newValue.toUpperCase());
			}
		});
		txtUsername.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtUsername.setText(newValue.toUpperCase());
			}
		});
		txtMailCode.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtMailCode.setText(newValue.toUpperCase());
			}
		});
		txtMailCode.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.SPACE) {
					event.consume();
				}
			}
		});
		//
		txtMailCode.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean lost, Boolean gained) {
				String s = null;
				if (lost && (s = txtMailCode.getText()) != null) {
					Pattern pattern = Pattern.compile(XMLUtils.MAILCODE_FORMAT);
					Matcher matcher = pattern.matcher(s.replace(" ", ""));
					//
					if (!matcher.matches()) {
						txtMailCode.requestFocus();
						txtMailCode.selectAll();
					}
				}
			}
		});
		//
		txtEmail.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean lost, Boolean gained) {
				String s1 = txtEmail.getText();

				if (lost && s1 != null) {
					Pattern pattern = Pattern.compile(XMLUtils.EMAIL_PATTERN);
					Matcher matcher = pattern.matcher(s1);
					//
					if (!matcher.matches()) {
						txtEmail.clear();
					}
				}
			}

		});
		//
		passConfirm.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean lost, Boolean gained) {
				//
				if (lost && !passField.getText().equals(passConfirm.getText())) {
					passField.requestFocus();
					passField.clear();
					passConfirm.clear();
				}
			}
		});
		//
		cboRegion.setCellFactory(new Callback<ListView<Region>, ListCell<Region>>() {
			@Override
			public ListCell<Region> call(ListView<Region> param) {

				final ListCell<Region> cell = new ListCell<Region>() {
					@Override
					protected void updateItem(Region reg, boolean empty) {
						super.updateItem(reg, empty);
						if (reg != null) {
							setText(reg.getName().toUpperCase());
						} else {
							setText(null);
						}
					}
				};
				return cell;
			}
		});
		cboRegion.setButtonCell(new ListCell<Region>() {

			@Override
			protected void updateItem(Region r, boolean empty) {
				super.updateItem(r, empty);
				if (r != null) {
					setText(r.getName().toUpperCase());
				} else {
					setText(null);
				}
			}
		});
		// Define rendering of selected value shown in ComboBox.
		cboRegion.setConverter(new StringConverter<Region>() {

			@Override
			public Region fromString(String arg0) {
				return null;
			}

			@Override
			public String toString(Region r) {
				return (r != null) ? r.getName().toUpperCase() : "r";
			}
		});
	}

	// protected String getEmailPattern() {
	// XMLUtils xmlUtil = new XMLUtils();
	// String emailPattern = null;
	// try {
	// emailPattern = xmlUtil.readXMLBasicFormats()[0].trim();
	// } catch (SAXException | IOException | ParserConfigurationException e) {
	// e.printStackTrace();
	// }
	// return emailPattern;
	// }
	//
	// protected String getMailCodePattern() {
	// return mailCodePattern;
	// }

	// private void readProperties() {
	// XMLUtils xmlUtil = new XMLUtils();
	// try {
	// String[] xmlStrings = xmlUtil.readXMLMailFormat();
	// localeCountry = xmlStrings[0];
	// boolEnforcedMailCode = new Boolean(xmlStrings[1]);
	// if (boolEnforcedMailCode)
	// mailCodePattern = xmlStrings[2];
	// else
	// mailCodePattern = "[A-Za-z0-9]*";
	// } catch (SAXException | IOException | ParserConfigurationException e) {
	// LOG.info("Property Read Error", "Mail Pattern Property Read Error");
	// }
	// }

	// private String getXMLCountry() {
	// return (localeCountry != null) ? localeCountry : "CANADA";
	// }

	protected void loadRegions() {
		if (cboRegion == null)
			cboRegion = new ComboBox<Region>();
		cboRegionData = FXCollections.observableArrayList();
		//
		List<Country> countries = (List<Country>) countryService.findAll();
		Country country = countries.stream().filter(c -> c.getName().equals(XMLUtils.LOCAL_COUNTRY)).findAny()
				.orElse(null);
		//
		List<Region> regions = (List<Region>) regionService.findByCountry(country);
		Collections.sort(regions);
		Platform.runLater(() -> {
			cboRegionData.setAll(FXCollections.observableArrayList(regions));
			cboRegion.setItems(cboRegionData);
		});
	}

	// private Country getCountry() {
	// List<Country> countries = (List<Country>) countryService.findAll();
	// Country country = countries.stream().filter(c ->
	// c.getName().equals(appUtils.SERVICE_LOCALE)).findAny()
	// .orElse(null);
	// //
	// return (Country) country;
	// }

	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) {
		if (isValidInput()) {
			Address address = getAddress();
			Users user = getUserObject(address);
			user.setAddress(address);
			//
			usersService.create(user);
			windowClose(btnSubmit);
		} else {
			FXmlUtils.showAlert(Alert.AlertType.ERROR, btnSubmit.getScene().getWindow(), "User Registration Error!",
					"Incomplete Username / password");
		}
	}

	private Users getUserObject(Address address) {
		Users user = new Users();
		user.setAddress(address);
		user.setEmail(FXmlUtils.isValid(txtEmail) ? txtEmail.getText().trim() : "");
		user.setFirstname(txtFirstname.getText().trim());
		user.setLastname(txtLastname.getText().trim());
		user.setPassword(passField.getText().trim());
		user.setPhoneMain(txtPhoneMain.getText().trim());
		user.setEmail(FXmlUtils.isValid(txtEmail) ? txtEmail.getText().trim() : "");
		user.setPhoneAux(FXmlUtils.isValid(txtPhoneOther) ? txtPhoneOther.getText().trim() : "");
		//
		UStatus userStatus = ustatusService.findByStatus("INACTIVE");
		user.setStatus(userStatus);
		user.setUsername(txtUsername.getText().trim());
		return user;
	}

	private Address getAddress() {
		Address address = new Address();
		address.setStreet(txtAddress.getText().trim());
		address.setRegion(cboRegion.getSelectionModel().getSelectedItem());
		address.setOther(FXmlUtils.isValid(txtOtherAddress) ? txtOtherAddress.getText().trim() : "");
		address.setMailcode(txtMailCode.getText().trim());
		//
		address.setCity(txtCity.getText().trim());
		address.setRegion(cboRegion.getSelectionModel().getSelectedItem());
		Address dbAddress = addressService.create(address);
		//
		return dbAddress;
	}

	private boolean isValidInput() {
		boolean bool = true;
		bool = bool && FXmlUtils.isValid(txtUsername) && FXmlUtils.isValid(txtFirstname);
		bool = bool && FXmlUtils.isValid(txtLastname) && FXmlUtils.isValid(txtAddress);
		bool = bool && FXmlUtils.isValid(txtCity) && FXmlUtils.isValid(txtMailCode);
		bool = bool && FXmlUtils.isValid(cboRegion) && isValidPassword();
		return bool;
	}

	private boolean isValidPassword() {
		boolean bool = !passField.getText().isEmpty() && !passConfirm.getText().isEmpty()
				&& passField.getText().equals(passConfirm.getText());
		return bool;
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
			LOG.info("Root Window Transfer Error", "Root Window Opening Error");
		} finally {
			viewControllerInstance.showApplicationEntryView();
		}
	}
}
