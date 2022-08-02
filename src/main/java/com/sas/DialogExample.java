package com.sas;

import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DialogExample extends Application {

	private Text actionStatus;
	private static final String titleTxt = "JavaFX Dialogs Example";

	public static void main(String[] args) {

		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle(titleTxt);

		// Window label
		Label label = new Label("A Dialog");
		label.setTextFill(Color.DARKBLUE);
		label.setFont(Font.font("Calibri", FontWeight.BOLD, 36));
		HBox labelHb = new HBox();
		labelHb.setAlignment(Pos.CENTER);
		labelHb.getChildren().add(label);

		// Button
		Button btn = new Button("Click to Enter Supplier Info");
		btn.setOnAction(new DialogButtonListener());
		HBox buttonHb = new HBox(10);
		buttonHb.setAlignment(Pos.CENTER);
		buttonHb.getChildren().add(new TextField());
		buttonHb.getChildren().add(new TextField());

		buttonHb.getChildren().addAll(btn);

		// Status message text
		actionStatus = new Text();
		actionStatus.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
		actionStatus.setFill(Color.FIREBRICK);

		// Vbox
		VBox vbox = new VBox(30);
		vbox.setPadding(new Insets(2, 2, 2, 2));
		vbox.getChildren().addAll(labelHb, buttonHb, actionStatus);

		// Scene
		Scene scene = new Scene(vbox, 500, 250); // w x h
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private class DialogButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			displayDialog();
		}
	}

	private void displayDialog() {

		actionStatus.setText("");

		// Custom dialog
		Dialog<Supplier> dialog = new Dialog<>();
		dialog.setTitle(titleTxt);
		dialog.setHeaderText("Enter Parts Supplier Info.");
		dialog.setResizable(true);

		// Widgets
		Label[] labels = new Label[4];
		int n = 0;
		for (Label lbl : labels) {
			lbl = new Label("Label_" + n);
			n++;
		}
		TextField[] texts = new TextField[4];
		texts[0] = new TextField();
		texts[1] = new TextField();
		texts[2] = new TextField();
		texts[3] = new TextField();

		// Create layout and add to dialog
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(2, 5, 2, 5));
		grid.add(new Label("Supplier Name"), 0, 0); // col=1, row=1
		grid.add(texts[0], 1, 0);
		grid.add(new Label("Ref. Id:"), 2, 0); // col=1, row=2
		grid.add(texts[1], 3, 0);
		grid.add(new Label("Contacts"), 0, 1); // col=1, row=3
		grid.add(texts[2], 1, 1);
		// grid.add(new Label("labels[3]"), 4, 1); // col=1, row=4
		grid.add(texts[3], 2, 1);
		dialog.getDialogPane().setContent(grid);

		// Add button to dialog
		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		// Result converter for dialog
		dialog.setResultConverter(new Callback<ButtonType, Supplier>() {
			@Override
			public Supplier call(ButtonType b) {

				if (b == buttonTypeOk) {
					String[] s = new String[4];
					s[0] = texts[0].getText();
					s[1] = texts[1].getText();
					s[2] = texts[2].getText();
					s[3] = texts[3].getText();
					return new Supplier(s);
				}

				return null;
			}
		});

		// Show dialog
		Optional<Supplier> result = dialog.showAndWait();

		if (result.isPresent()) {

			actionStatus.setText("Result: " + result.get());
		}
	}

	private class Supplier {

		private String name;
		private String phone0;
		private String email;
		private String phone1;

		Supplier(String... str) {
			name = str[0];
			phone0 = str[1];
			phone1 = str[2];
			email = str[3];
		}

		@Override
		public String toString() {

			return (name + ", " + phone0 + ", " + phone1 + ", " + email);
		}
	}
}
