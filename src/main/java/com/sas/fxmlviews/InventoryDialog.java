package com.sas.fxmlviews;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sas.app.AutoCompleteTextField;
import com.sas.app.FXmlUtils;
import com.sas.entity.Inventory;
import com.sas.service.InventoryService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@Component
public class InventoryDialog {

	private TextField txtPartNr;
	private Map<String, Inventory> inventoryMap;
	private AutoCompleteTextField autocompletePart;
	private InventoryService inventoryService;
	private TextField txtPrice;
	private TextField txtCostPrice;
	private TextField txtPartDescription;
	private Inventory selectedInventory;
	private Object[] selectedObject;

	/**
	 * 
	 * @param invService
	 */
	public InventoryDialog(InventoryService invService) {
		this.inventoryService = invService;
	}

	/**
	 * 
	 */
	public void displayInventoryDialog() {
		double lblWidth = 55.;
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Inventory Selection Form");
		dialog.setHeaderText("Select from Inventory:");//dialog.getGraphic().setScaleX(.75);dialog.getGraphic().setScaleY(.75);
		dialog.setResizable(false);

		// Create layout and add to dialog
		VBox vbox = new VBox(2);
		vbox.setPadding(new Insets(2, 2, 2, 2));
		String[] styles = new String[] {
				"-fx-alignment: left;-fx-font-size: 10;-fx-background-color:linear-gradient(to bottom, "
						+ "derive(-fx-glass-color, 40%),-fx-glass-color);-fx-border-color:derive(-fx-glass-color, -40%);"
						+ "-fx-border-width: 2;-fx-background-insets:2;-fx-border-insets:2;-fx-border-radius:2;-fx-background-radius:2;",
				"-fx-font: 12px sans-serif; -fx-background-color: #cccccc;"
						+ "-fx-padding: 2 2 2 2;-fx-effect: dropshadow(one-pass-box, black, 1.8, 1.5, 2, 1.5);" };

		//
		HBox hxs0 = new HBox();
		String[] s = { "Item Nr.", "Buy $", "MUP %", "Sell $", "Item Qty", "Item Desc" };
		Label[] lbls = new Label[s.length];
		for (int n = 0; n < lbls.length; n++) {
			lbls[n] = new Label(s[n]);
			lbls[n].setPrefWidth(lblWidth);
			lbls[n].setAlignment(Pos.CENTER);
		}

		hxs0.getChildren().add(lbls[0]);
		txtPartNr = new TextField();
		txtPartNr.setPrefWidth(100);
		txtPartNr.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean oldValue, Boolean newValue) {
				if (oldValue && FXmlUtils.isValid(txtPartNr)) {
					selectedInventory = inventoryMap.get(txtPartNr.getText());
					txtPartDescription.setText(selectedInventory.getDescription());
					txtCostPrice.setText(FXmlUtils.decFormat(selectedInventory.getCostPrice(), 2));
				}
			}
		});
		txtPartNr.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtPartNr.setText(newValue.toUpperCase());
			}
		});
		hxs0.getChildren().add(txtPartNr);
		//
		hxs0.getChildren().add(lbls[1]);
		txtCostPrice = new TextField();
		txtCostPrice.setPrefWidth(65);
		txtCostPrice.setEditable(false);
		hxs0.getChildren().add(txtCostPrice);
		//
		hxs0.getChildren().add(lbls[2]);
		TextField txtMarkUp = new TextField();
		txtMarkUp.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				String keyString = ke.getCharacter();
				if (!keyString.matches("[-0123456789]")) {
					ke.consume();
				}
			}
		});
		txtMarkUp.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean oldValue, Boolean newValue) {
				if (oldValue && FXmlUtils.isValid(txtMarkUp)) {
					StringBuilder sbValue = new StringBuilder();
					sbValue.append(txtMarkUp.getText());

					boolean negValue = false;
					if (sbValue.charAt(0) == '-') {
						sbValue.deleteCharAt(0).toString();
						negValue = true;
					} else if (sbValue.charAt(sbValue.length() - 1) == '-') {
						sbValue.deleteCharAt(sbValue.length() - 1).toString();
						negValue = true;
					}
					//
					if (sbValue.indexOf("-") != -1) {
						txtMarkUp.setText("1");
						txtPrice.setText(txtCostPrice.getText());
					} else {
						Float dValue = Float.parseFloat(sbValue.toString());
						Float price = 1.f;
						try {
							price = Float.parseFloat(txtCostPrice.getText());

						} catch (NumberFormatException e) {
							price = 0.19999f;
						}

						if (negValue) {
							price = price * (1 - dValue / 100);
						} else {
							price = price * (1 + dValue / 100);
						}
						txtPrice.setText(FXmlUtils.decFormat(new Float(price), 2));
					}
				}
			}
		});
		txtMarkUp.setPrefWidth(40);
		hxs0.getChildren().add(txtMarkUp);

		//
		hxs0.getChildren().add(lbls[3]);
		txtPrice = new TextField();
		txtPrice.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				String keyString = ke.getCharacter();
				if (!keyString.matches("[0123456789]")) {
					ke.consume();
				}
			}
		});
		txtPrice.setPrefWidth(60);
		hxs0.getChildren().add(txtPrice);
		vbox.getChildren().add(hxs0);

		
		//
		hxs0.getChildren().add(lbls[4]);
		TextField txtQuantity = new TextField();
		txtQuantity.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				String keyString = ke.getCharacter();
				if (!keyString.matches("[0123456789]")) {
					ke.consume();
				}
			}
		});
		txtQuantity.setPrefWidth(40);
		hxs0.getChildren().add(txtQuantity);

		
		//
		HBox hxs1 = new HBox();
		hxs1.getChildren().add(lbls[5]);
		txtPartDescription = new TextField();
		txtPartDescription.setPrefWidth(525);
		hxs1.getChildren().add(txtPartDescription);
		vbox.getChildren().add(hxs1);

		//
		dialog.getDialogPane().setContent(vbox);
		dialog.getDialogPane().setStyle(styles[1]);
		autoFillInventory();

		// Show dialog
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {
			setSelectedInvetory(selectedInventory, txtPrice.getText(), txtQuantity.getText());
		}
	}

	/**
	 * 
	 */
	private void autoFillInventory() {
		inventoryMap = getMappedInventory();
		autocompletePart = new AutoCompleteTextField(txtPartNr);
		//
		List<String> partsList = inventoryMap.keySet().stream().collect(Collectors.toList());
		autocompletePart.getEntries().addAll(partsList);
	}

	/**
	 * 
	 * @return
	 */
	private Map<String, Inventory> getMappedInventory() {
		inventoryMap = new HashMap<>();
		List<Inventory> invList = inventoryService.findAll();
		//
		inventoryMap = invList.stream().filter(inv -> inv.getQuantity() > inv.getQuantityAdj())
				.collect(Collectors.toMap(inventory -> inventory.getPartNumber(), inventory -> inventory));
		return inventoryMap;
	}

	/**
	 * 
	 * @return
	 */
	public Object[] getSelectedInvetory() {
		return selectedObject;
	}

	/**
	 * 
	 * @param inv
	 * @param price
	 * @param qty
	 */
	public void setSelectedInvetory(Inventory inv, String price, String qty) {
		selectedObject = new Object[] { inv, price, qty };
	}

}
