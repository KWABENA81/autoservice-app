package com.sas.fxmlviews;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;

public class AutoListFill<T> extends BorderPane {
	private TextField textField;
	private double dPrefWidth;
	private Popup popup;
	private ListView<T> listView;
	private ObservableList<Cell> selectedItemCells;
	private FlowPane flowPane;
	private NodeFactory<T> nodeFactory;
	private ScrollPane scrollPane;
	private TextInputControl nextFocusableNode = null;
	private final int CELL_HEIGHT = 20;
	protected int lastTextFieldLength;
	protected StringBuilder sb;
	/**
	 * AutoFill Textfield unit of width
	 */
	public static final double AutoFill_Unit = 20;

	/**
	 * 
	 * @param tf
	 * @param node
	 */
	public AutoListFill(TextField tf, double width) {
		textField = tf;
		dPrefWidth = width;
		sb = new StringBuilder();
		//
		viewBuilder();
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public Property<String> textFieldProperty() {
		return textField.textProperty();
	}

	/**
	 * 
	 * @return
	 */
	public TextField textField() {
		return textField;
	}

	/**
	 * 
	 */
	public void clearTextField() {
		textField.clear();
	}

	/**
	 * 
	 * @return
	 */
	public ObservableList<T> getListView() {
		return listView.getItems();
	}

	/**
	 * 
	 * @return
	 */
	public MultipleSelectionModel<T> getListViewSelectionModel() {
		return listView.getSelectionModel();
	}

	/**
	 * 
	 */
	private void viewBuilder() {
		selectedItemCells = FXCollections.observableArrayList();
		flowPane = new FlowPane(Orientation.VERTICAL, 1, 1);
		// //
		textField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> source, String ov, String nv) {
				if (nv != null && nv.trim().length() > 0) {
					if (!popup.isShowing()) {
						showPopup();
					}
				}
			}

			private void showPopup() {
				if (textField.getScene() != null) {
					double xCoord = textField.getScene().getWindow().getX() + textField.localToScene(0, 0).getX()
							+ textField.getScene().getX();
					//
					double yCoord = textField.getScene().getWindow().getY() + textField.localToScene(0, 0).getY()
							+ textField.getScene().getY() + textField.getHeight();
					//
					popup.show(textField, xCoord, yCoord);
				}
			}
		});
		//
		scrollPane = new ScrollPane();
		scrollPane.setFitToWidth(true);
		scrollPane.setContent(flowPane);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		setCenter(scrollPane);
		//
		scrollPane.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				textField.requestFocus();
				selectedItemCells.clear();
				selectedItemCells = null;
			}
		});
		// add a focus listener such that if not in focus, reset the filtered
		// typed keys
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			@SuppressWarnings("rawtypes")
			public void changed(ObservableValue obs, Boolean oldBool, Boolean newBool) {
				if (!newBool) {
					lastTextFieldLength = 0;
					sb.delete(0, sb.length());
					selectBestResult(false, false);
				}
			}
		});
		//
		textField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if ((event.getCode() == KeyCode.UNDEFINED) && getListView().isEmpty()) {
					popup.hide();
				}
			}
		});
		createPopup();
	}

	/**
	 * 
	 * @param bool
	 * @param inFocus
	 */
	@SuppressWarnings("restriction")
	protected void selectBestResult(boolean bool, boolean inFocus) {
		ObservableList<T> items = getListView();
		boolean found = false;
		String s = textField.getText();
		for (int i = 0; i < items.size(); i++) {
			if (!found && s.toLowerCase().equals(items.get(i).toString().toLowerCase())) {
				try {
					listView.getSelectionModel().clearAndSelect(i);
					listView.scrollTo(listView.getSelectionModel().getSelectedIndex());
					found = true;
					break;
				} catch (Exception e) {
				}
			}
		}
		if (!found && bool) {
			getListViewSelectionModel().clearSelection();
			textField.setText(s);
			textField.end();
		}
		if (found) {
			popup.hide();
		}
		if (!inFocus && (s != null) && (s.trim().length() > 0)) {
			// // press enter key programmatically to have this entry added
			com.sun.glass.ui.Robot robot = com.sun.glass.ui.Application.GetApplication().createRobot();
			robot.keyPress(java.awt.event.KeyEvent.VK_ENTER);
		}
	}

	/**
	 * 
	 */
	protected void listSelectedItem() {
		ObservableList<T> list = listView.getSelectionModel().getSelectedItems();
		if (list != null && !list.isEmpty()) {
			T selected = listView.getSelectionModel().getSelectedItem();
			Cell cell = new Cell(selected);
			if (!selectedItemCells.contains(cell))
				selectedItemCells.add(cell);

			if (cell != null)
				flowPane.getChildren().add(flowPane.getChildren().size(), cell);
			popup.hide();
		}
	}

	/**
	 * 
	 */
	public void popupClose() {
		popup.autoHideProperty();
	}

	/**
	 */
	private void createPopup() {
		popup = new Popup();
		popup.setAutoHide(true);
		// popup.setAutoFix(true);
		// popup.setConsumeAutoHidingEvents(true);
		listView = new ListView<T>();
		int value = (listView.getItems().size() + 1) * CELL_HEIGHT/* + 2 */;

		listView.setPrefHeight(value);
		listView.setPrefWidth(dPrefWidth);
		listView.getItems().addListener(new ListChangeListener<Object>() {

			@SuppressWarnings("rawtypes")
			@Override
			public void onChanged(ListChangeListener.Change change) {
				if (listView.getItems().size() == 0) {
					listView.setPrefHeight(0);
				} else {
					int nl = listView.getItems().size();
					if (nl > 10)
						nl = 10;
					listView.setPrefHeight((nl + 1) * CELL_HEIGHT + 5);
				}
			}
		});
		//
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				listSelectedItem();
			}
		});
		//
		popup.showingProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					T s = listView.getSelectionModel().getSelectedItem();
					if (s != null) {
						textField.setText((String) s);
					}
				}
			}
		});
		//
		popup.getContent().add(listView);
		popup.hide();
		popup.setAutoHide(true);
	}

	/**
	 * 
	 * @author Solomon SA
	 *
	 */
	public class Cell extends HBox {

		private Cell(final T item) {
			setSpacing(2);
			Node node = (nodeFactory != null) ? nodeFactory.createNode(item) : new Label(String.valueOf(item));
			getChildren().add(node);
		}
	}

	/**
	 * 
	 * @author Solomon SA
	 *
	 * @param <DataType>
	 */
	public interface NodeFactory<DataType> {
		Node createNode(DataType item);
	}
}
