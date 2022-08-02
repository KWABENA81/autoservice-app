package com.sas.app;

import java.text.DecimalFormat;

import com.sas.entity.Region;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class FXmlUtils {

	private static StageStyle style = StageStyle.UNIFIED;

	/**
	 * 
	 * @param inputControl
	 * @return
	 */
	public static boolean isValid(TextInputControl inputControl) {
		boolean isvalid = true;
		if (inputControl instanceof TextField) {
			TextField txt = (TextField) inputControl;
			isvalid = !(txt == null || txt.getText() == null || txt.getText().isEmpty());
		}
		if (inputControl instanceof TextArea) {
			TextArea ta = (TextArea) inputControl;
			isvalid = ta != null && ta.getText() != null && !ta.getText().isEmpty();
		}
		return isvalid;
	}

	/**
	 * CONFIRMATION alert type
	 * 
	 * @param msg
	 * @param owner
	 * 
	 * @return - The boolean Alert ButtonType object (YES/NO)
	 */
	public static Alert showConfirmAlert(Window owner, String msg) {
		Alert alert = new Alert(AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		// alert.setTitle(title); alert.setHeaderText(null);
		// alert.setContentText(msg.toUpperCase());
		alert.initOwner(owner);
		alert.showAndWait();
		return alert;
	}

	/**
	 * Default alert type
	 * 
	 * @param alertType
	 * @param owner
	 * @param title
	 * @param message
	 */
	public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message.toUpperCase());
		alert.initOwner(owner);
		alert.show();
	}

	/**
	 * 
	 * @param owner
	 * @param msg
	 * 
	 */
	public static void showInfoAlert(Window owner, String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("INFORMATION");
		alert.setHeaderText(null);
		alert.initStyle(style);
		alert.setContentText(msg);
		alert.initOwner(owner);
		alert.show();
	}

	/**
	 * 
	 * @param owner
	 * @param msg
	 *
	 */
	public static void showWarningAlert(Window owner, String msg, char... c) {
		String message = ((c != null) && (c[0] == 'u' || c[0] == 'U')) ? msg.toUpperCase() : msg;
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("WARNING");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}

	/**
	 * 
	 * @param value
	 * @param n
	 * @return
	 */
	public static String decFormat(float value, int n) {
		DecimalFormat decForm = null;
		if (n == 1)
			decForm = new DecimalFormat(".#");
		else
			decForm = new DecimalFormat(".##");
		return decForm.format(value);
	}

	/**
	 * 
	 * @param window
	 * @param msg
	 */
	public static void showErrorAlert(Window window, String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.initOwner(window);
		alert.show();
	}

	/**
	 * 
	 * @param cbo
	 * @return
	 */
	public static boolean isValid(ComboBox<?> cbo) {
		return (cbo != null) && (cbo.getSelectionModel() != null)
				&& (cbo.getSelectionModel().getSelectedItem() != null);
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isOptionalEmptyTextString(String str) {
		return (str == null || str.isEmpty());
	}

}
