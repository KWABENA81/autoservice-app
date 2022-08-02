package com.sas.app;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AutoCompleteTextField {

	/** The existing autocomplete entries. */
	private final SortedSet<String> entries;
	
	/** The popup used to select an entry. */
	private ContextMenu entriesPopup;
	private TextField textField;

	/** Construct a new AutoCompleteTextField. */
	public AutoCompleteTextField(TextField text) {
		this.textField = text;
		entries = new TreeSet<>();
		entriesPopup = new ContextMenu();
		String style = "-fx-background-color: #EEEEEE; -fx-border-color: #0011FF; -fx-font-size: 11px; -fx-text-fill: #01579B; -fx-font-family: Arial Black;";
		entriesPopup.setStyle(style);
		//
		this.textField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s1, String s2) {
				if (s2.length() == 0) {
					entriesPopup.hide();
				} else {
					LinkedList<String> searchResult = new LinkedList<>();
					searchResult.addAll(entries.subSet(s2, s2 + Character.MAX_VALUE));
					if (entries.size() > 0) {
						populatePopup(searchResult);
						if (!entriesPopup.isShowing()) {
							entriesPopup.show(AutoCompleteTextField.this.textField, Side.BOTTOM, 5, 0);
						}
					} else {
						entriesPopup.hide();
					}
				}
			}
		});

		this.textField.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bool0, Boolean bool1) {
				entriesPopup.hide();
			}
		});
	}

	/**
	 * 
	 * @return
	 */
	public SortedSet<String> getEntries() {
		return entries;
	}

	/**
	 * 
	 * @param searchResult
	 */
	private void populatePopup(List<String> searchResult) {
		List<CustomMenuItem> menuItems = new LinkedList<>();

		// If you'd like more entries, modify this line.
		int maxEntries = 20;
		int count = Math.min(searchResult.size(), maxEntries);
		for (int i = 0; i < count; i++) {
			final String result = searchResult.get(i);
			Label entryLabel = new Label(result);
			CustomMenuItem item = new CustomMenuItem(entryLabel, true);

			item.setOnAction(new EventHandler<ActionEvent>() {
				final private TextField txt = AutoCompleteTextField.this.textField;

				@Override
				public void handle(ActionEvent actionEvent) {
					txt.setText(result);
					entriesPopup.hide();
				}
			});
			menuItems.add(item);
		}
		entriesPopup.getItems().clear();
		entriesPopup.getItems().addAll(menuItems);
	}
}