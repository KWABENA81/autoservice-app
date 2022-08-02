package com.sas.fxmlviews;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sas.app.AppUtils;
import com.sas.app.AutoCompleteTextField;
import com.sas.app.FXmlUtils;
import com.sas.app.XMLUtils;
import com.sas.entity.Address;
import com.sas.entity.BodyType;
import com.sas.entity.Colours;
import com.sas.entity.Country;
import com.sas.entity.DriveType;
import com.sas.entity.EConfig;
import com.sas.entity.Fuels;
import com.sas.entity.Inventory;
import com.sas.entity.Make;
import com.sas.entity.Model;
import com.sas.entity.Models;
import com.sas.entity.Owner;
import com.sas.entity.Parts;
import com.sas.entity.Region;
import com.sas.entity.SStatus;
import com.sas.entity.ServiceOrder;
import com.sas.entity.Trim;
import com.sas.entity.Vehicle;
import com.sas.entity.Work;
import com.sas.reports.PDFEstimateWriter;
import com.sas.reports.PDFInvoiceWriter;
import com.sas.service.AddressService;
import com.sas.service.BodyTypeService;
import com.sas.service.ColourService;
import com.sas.service.CountryService;
import com.sas.service.DriveTypeService;
import com.sas.service.EConfigService;
import com.sas.service.FuelService;
import com.sas.service.InventoryService;
import com.sas.service.MakeService;
import com.sas.service.ModelService;
import com.sas.service.ModelsService;
import com.sas.service.OwnerService;
import com.sas.service.PartsService;
import com.sas.service.RegionService;
import com.sas.service.SStatusService;
import com.sas.service.ServiceOrderService;
import com.sas.service.TrimService;
import com.sas.service.VehicleService;
import com.sas.service.WorkService;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
//import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;

enum _CLIENT_CRITERIA {
	FIRST_LASTNAME("Firstname\nLastname"), NAME_PHONE("Name\nPhone");
	//
	private String criteria;

	_CLIENT_CRITERIA(String s) {
		this.setCriteria(s);
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
};

enum _SERVICE_CRITERIA {
	JOB_ID("Job Id\n..."), DATE_RANGE("...\nDate Range"), VEHICLE("Vehicle VIN\n...");
	//
	private String criteria;

	_SERVICE_CRITERIA(String s) {
		this.setCriteria(s);
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
};

enum _VEHICLE_CRITERIA {
	VIN("VIN"), MODEL("Model"), PLATE("Plate");
	//
	private String criteria;

	_VEHICLE_CRITERIA(String s) {
		this.setCriteria(s);
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
};

enum REPORT_TYPE {
	YEAR_END("End of Year"), SALES_TAX("Sales Tax"), HST_GST("HST / GST"), INVENTORY("Inventory"), SERVICE_REVENUE(
			"Service Revenue"), SALES("Sales");

	//
	private String criteria;

	REPORT_TYPE(String s) {
		this.setCriteria(s);
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria.toUpperCase();
	}
}

@Component
@FXMLController
public class RootController {

	@FXML
	private Label lblSummary;
	@FXML
	private Label lblStatusBar0;
	@FXML
	private Label lblStatusBar1;
	@FXML
	private Label lblStatusBar2;
	@FXML
	private Label lblStatusBar3;
	@FXML
	private TextFlow tfReportSummary;

	@FXML
	private TabPane mainTabPane;
	@FXML
	private Tab tabOther;
	@FXML
	private Tab tabReport;
	@FXML
	private Tab tabService;
	@FXML
	private Tab tabClient;
	@FXML
	private Hyperlink hlinkParts;
	@FXML
	private RadioButton rbPend;
	@FXML
	private RadioButton rbComplete;
	@FXML
	private HBox hboxStatusBar;

	@FXML
	private Button btnClearAllSearch;
	@FXML
	private Button btnSearchClient;
	@FXML
	private Button btnSearchVehicle;
	@FXML
	private Button btnQueryByNames;
	@FXML
	private Button btnByAddress;
	@FXML
	private Button btnByPlateQuery;
	@FXML
	private Button btnByPhonesQuery;
	@FXML
	private Button btnClearClientInfo;
	@FXML
	private Button btnEstimateService;
	@FXML
	private Button btnCreateServiceOrder;
	@FXML
	private Button btnDeleteServiceItem;
	@FXML
	private Button btnClearServiceItem;
	@FXML
	private Button btnUpdateServiceItem;
	@FXML
	private Button btnSaveServiceItem;
	@FXML
	private Button btnCompleteService;
	@FXML
	private Button btnClearVehicleInfo;
	@FXML
	private Button btnProcessClient;
	@FXML
	private Button btnClearClientQuery;
	@FXML
	private Button btnClearServiceQuery;
	@FXML
	private Button btnClearVehicleQuery;
	@FXML
	private Button btnQueryByService;
	@FXML
	private Button btnQueryByVehicle;
	@FXML
	private Button btnCancelAllReport;
	@FXML
	private Button btnExportReport;

	@FXML
	private ComboBox<String> cboQueryByVehicle;
	private ObservableList<String> cboQueryByVehicleData;
	@FXML
	private ComboBox<String> cboQueryByNames;
	private ObservableList<String> cboQueryByNamesData;
	@FXML
	private ComboBox<String> cboQueryByService;
	private ObservableList<String> cboQueryByServiceData;
	@FXML
	private ComboBox<String> cboReportType;
	private ObservableList<String> cboReportTypeData;
	//
	@FXML
	private TextField txtQueryService01;
	@FXML
	private TextField txtSODetailsJobId;
	@FXML
	private TextField txtSODetailsDateIn;
	@FXML
	private TextField txtServiceVehicleRef;
	@FXML
	private TextField txtSODetailsOdometer;
	@FXML
	private TextField txtSODetailsStatus;
	@FXML
	private TextField txtSODetailsComment;
	@FXML
	private TextArea taSODetailsDescription;
	@FXML
	private TextField txtVehicleDetailsEngine;
	@FXML
	private TextField txtVehicleDetailsTrim;
	@FXML
	private TextField txtVehicleDetailsMakeModel;
	@FXML
	private TextField txtVehicleDetailsVIN;
	@FXML
	private TextField txtVehicleOwnerRef;
	@FXML
	private TextField txtVehicleDetailsPlate;
	@FXML
	private TextField txtOwnerDetailsFirstname;
	@FXML
	private TextField txtOwnerDetailsLastname;
	@FXML
	private TextField txtOwnerDetailsPhones;
	@FXML
	private TextField txtOwnerDetailsAddress;
	@FXML
	private TextField txtOwnerDetailsEmail;

	@FXML
	private TextField txtQueryVehicle01;
	@FXML
	private TextField txtQueryNames02;
	@FXML
	private TextField txtQueryNames01;
	@FXML
	private TextField txtSearchName;
	@FXML
	private TextField txtSearchPhone;
	@FXML
	private TextField txtSearchPlate;
	@FXML
	private TextField txtSearchVIN;
	@FXML
	private TextField txtFName;
	@FXML
	private TextField txtLName;
	@FXML
	private TextField txtAddress;
	@FXML
	private TextField txtAddressOther;
	@FXML
	private TextField txtCity;
	@FXML
	private TextField txtMCode;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtPhone;
	@FXML
	private TextField txtPhoneOther;
	@FXML
	private TextField txtVIN;
	@FXML
	private TextField txtPlate;
	@FXML
	private TextField txtColour;
	@FXML
	private TextField txtYear;
	@FXML
	private TextField txtMake;
	@FXML
	private TextField txtModel;
	@FXML
	private TextField txtTrimDrive;
	@FXML
	private TextField txtTrimDoors;
	@FXML
	private TextField txtTrimLevel;
	@FXML
	private TextField txtTrimDescription;
	@FXML
	private TextField txtTrimOther;
	@FXML
	private TextField txtEngineCapacity;
	@FXML
	private TextField txtEngineBuild;
	@FXML
	private TextField txtEngineFuel;
	@FXML
	private TextField txtEngineOther;
	@FXML
	private TextField txtEngineDescription;
	@FXML
	private TextField txtRegion;
	@FXML
	private TextField txtSOPhoneInfo;
	@FXML
	private TextField txtSOEmailInfo;
	@FXML
	private TextField txtSOContactInfo0;
	@FXML
	private TextField txtSOVehicleInfo;
	@FXML
	private TextField txtSOVehicleVINInfo;
	@FXML
	private TextField txtSOVehicleTrimInfo;
	@FXML
	private TextField txtSOVehiclePlateInfo;
	@FXML
	private TextField txtSOVehicleEngineInfo;
	@FXML
	private TextField txtSOPartNr;
	@FXML
	private TextField txtSOPartQty;
	@FXML
	private TextField txtSOPartDescription;
	@FXML
	private TextField txtSOServiceDescription;
	@FXML
	private TextField txtSOPartPrice;
	@FXML
	private TextField txtSOLabourHrs;
	@FXML
	private TextField txtSOLabourRate;
	@FXML
	private TextField txtSOFName;
	@FXML
	private TextField txtSOLName;
	@FXML
	private TextField txtOdometerReading;
	@FXML
	private TextField txtSOOdometer;
	@FXML
	private DatePicker dateBegin;
	@FXML
	private DatePicker dateEnd;
	@FXML
	private DatePicker dpReportStartDate;
	@FXML
	private DatePicker dpReportEndDate;
	//
	@FXML
	private TableView<Vehicle> tblVehicle;
	@FXML
	private TableColumn<Vehicle, String> colPlate;
	@FXML
	private TableColumn<Vehicle, String> colVIN;
	@FXML
	private TableColumn<Vehicle, String> colCustomer;
	@FXML
	private TableColumn<Vehicle, String> colModelName;
	//
	@FXML
	private TableView<ServiceOrder> tblServiceQueryResult;
	@FXML
	private TableColumn<ServiceOrder, String> colQueryPartDesc;
	@FXML
	private TableColumn<ServiceOrder, String> colQueryStatus;
	@FXML
	private TableColumn<ServiceOrder, String> colQuerySDate;
	@FXML
	private TableColumn<ServiceOrder, String> colQueryJobId;

	@FXML
	private TableView<Vehicle> tblVehicleQueryResult;
	@FXML
	private TableColumn<Vehicle, String> colQueryVIN;
	@FXML
	private TableColumn<Vehicle, String> colQueryPlate;
	@FXML
	private TableColumn<Vehicle, String> colQueryTrim;
	@FXML
	private TableColumn<Vehicle, String> colQueryEngine;

	@FXML
	private TableView<Owner> tblOwnerQueryResult;
	@FXML
	private TableColumn<Owner, String> colFirstname;
	@FXML
	private TableColumn<Owner, String> colLastname;
	// @FXML
	// private TableColumn<Owner, String> colCity;
	@FXML
	private TableColumn<Owner, String> colContact;
	// @FXML private TableColumn<Owner, String> colPhoneOther;
	// @FXML
	// private TableColumn<Owner, String> colEmail;

	//
	@FXML
	private TableView<ServiceOrder> tblServiceVehicle;
	@FXML
	private TableColumn<ServiceOrder, String> colSOStartDate;
	@FXML
	private TableColumn<ServiceOrder, String> colJobId;
	@FXML
	private TableColumn<ServiceOrder, String> colSOMake;
	@FXML
	private TableColumn<ServiceOrder, String> colSOModel;
	@FXML
	private TableColumn<ServiceOrder, String> colSOPlate;
	//
	@FXML
	private TableView<Work> tblWorkItems;
	@FXML
	private TableColumn<Work, String> colPartDescription;
	@FXML
	private TableColumn<Work, Float> colQuantity;
	@FXML
	private TableColumn<Work, Float> colItemPrice;
	@FXML
	private TableColumn<Work, Float> colLabourHrs;
	@FXML
	private TableColumn<Work, String> colWorkDescription;
	@FXML
	private TableColumn<Work, Boolean> colWorkStatus;

	private ToggleGroup radioToggle;
	//
	@Autowired
	private CountryService countryService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private OwnerService ownerService;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private MakeService makeService;
	@Autowired
	private EConfigService buildService;
	@Autowired
	private DriveTypeService driveTypeService;
	@Autowired
	private ModelService modelService;
	@Autowired
	private ModelsService modelsService;
	@Autowired
	private FuelService fuelService;
	@Autowired
	private ColourService colourService;
	@Autowired
	private TrimService trimService;
	@Autowired
	private BodyTypeService bodyTypeService;
	@Autowired
	private ServiceOrderService serviceOrderService;
	@Autowired
	private SStatusService sstatusService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private PartsService partsService;
	@Autowired
	private WorkService workService;

	private ObservableList<Work> observableWorkItems = null;
	private ObservableList<ServiceOrder> observableServiceOrderList;
	private ObservableList<Vehicle> observableVehicleList;
	private ObservableList<Owner> observableOwnerQueryList;
	private ObservableList<Vehicle> observableVehicleQueryList;
	private ObservableList<ServiceOrder> observableServiceOrderQueryList;

	private Map<TextField, String> vehicleTxtFieldMap = null;
	private Map<TextField, String> clientTxtFieldMap = null;
	private HashMap<Object, String> workMap;
	private boolean boolNewClient = false;
	private boolean boolNewVehicle = false;
	private boolean boolExistingClient = false;
	private boolean boolExistingVehicle = false;
	private boolean boolConfirmedChange = false;
	// private boolean boolUpdatedVehicle = false;
	private boolean boolHasExistingPlate = false;
	private List<TextField> vehicleTextFields = null;
	private List<TextField> clientFormTextFields = null;
	private List<TextField> workTextFields = null;

	private List<String> Labour_Rates;
	private List<String> partDescs;
	private List<String> partNumbers;
	private List<String> _Regions;
	private List<String> colorsList;
	private List<String> _ModelsList;
	private List<String> _MakeList;
	private List<Model> _MODELS;
	private AutoCompleteTextField autoCompleteLabourRate;
	private AutoCompleteTextField autoFieldPartDesc;
	private AutoCompleteTextField autocompletePartNumber;
	private AutoCompleteTextField autocompleteRegions;
	private AutoCompleteTextField autocompleteColors;
	private AutoCompleteTextField autocompleteModel;
	private AutoCompleteTextField autocompleteMake;
	private AutoCompleteTextField autocompleteCity;
	private AutoCompleteTextField autocompleteEngineBuild;
	private AutoCompleteTextField autocompleteEngineFuel;
	private AutoCompleteTextField autocompleteTrimDrive;
	private AutoCompleteTextField autocompleteTrimLevel;
	private AutoCompleteTextField autocompleteTrimDoors;
	protected AutoCompleteTextField autocompleteEngineConfigurations;
	private String queryByNameCriteria = null;
	// private AutoCompleteTextField autocompleteTrims;
	// private AutoCompleteTextField autoCompleteFieldTrimDesc;
	// private AutoCompleteTextField autoCompleteFieldEngineDesc;
	private String queryByServiceCriteria = null;
	private String queryByVehicleCriteria = null;
	protected boolean hasValidQueryStartDate = false;
	protected boolean hasValidQueryEndDate = false;
	private Stage inventoryStage;
	private RootController rootControllerCaller;

	private static final Logger LOG = LoggerFactory.getLogger("AutoService");
	private static final Float INV_MARKUP = 1.45f;

	// private String mailCodePattern; private AppUtils appUtils;
	private static Integer SERVICEYEARRANGE = 30;
	private static Integer currentYear = 2018;
	private static double WRAP_FACTOR = .875;

	public RootController() {
		AppUtils.getInstance();

		tblVehicle = new TableView<Vehicle>();
		colPlate = new TableColumn<Vehicle, String>();
		colVIN = new TableColumn<Vehicle, String>();
		colModelName = new TableColumn<Vehicle, String>();
		colCustomer = new TableColumn<Vehicle, String>();

		tblServiceVehicle = new TableView<ServiceOrder>();
		colSOModel = new TableColumn<ServiceOrder, String>();
		colSOStartDate = new TableColumn<ServiceOrder, String>();
		colSOPlate = new TableColumn<ServiceOrder, String>();
		colSOMake = new TableColumn<ServiceOrder, String>();

		tblWorkItems = new TableView<Work>();
		colLabourHrs = new TableColumn<Work, Float>();
		colItemPrice = new TableColumn<Work, Float>();
		colQuantity = new TableColumn<Work, Float>();
		colWorkStatus = new TableColumn<Work, Boolean>();
		colPartDescription = new TableColumn<Work, String>();
		colWorkDescription = new TableColumn<Work, String>();

		tblOwnerQueryResult = new TableView<Owner>();
		colFirstname = new TableColumn<Owner, String>();
		colLastname = new TableColumn<Owner, String>();
		colContact = new TableColumn<Owner, String>();

		tblServiceQueryResult = new TableView<ServiceOrder>();
		colQuerySDate = new TableColumn<ServiceOrder, String>();
		colQueryJobId = new TableColumn<ServiceOrder, String>();
		colQueryPartDesc = new TableColumn<ServiceOrder, String>();
		colQueryStatus = new TableColumn<ServiceOrder, String>();

		tblVehicleQueryResult = new TableView<Vehicle>();
		colQueryVIN = new TableColumn<Vehicle, String>();
		colQueryPlate = new TableColumn<Vehicle, String>();
		colQueryTrim = new TableColumn<Vehicle, String>();
		colQueryEngine = new TableColumn<Vehicle, String>();
		// colPhoneOther = new TableColumn<Owner, String>();
		// = new TableColumn<Owner, String>();

		cboQueryByService = new ComboBox<String>();
		cboQueryByVehicle = new ComboBox<String>();
		cboReportType = new ComboBox<String>();
	}

	@FXML
	private void initialize() {
		// load once lists
		loadLabourRatesAutoFill();
		loadRegionsAutoFill();
		setRadios();

		loadPartNumbersAutoFill();
		loadPartDescriptionsAutoFill();
		//
		txtSearchName.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtSearchName.setText(newValue.toUpperCase());
			}
		});
		//
		txtSearchVIN.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtSearchVIN.setText(newValue.toUpperCase());
			}
		});
		//
		txtSearchPlate.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtSearchPlate.setText(newValue.toUpperCase());
			}
		});
		//
		txtFName.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtFName.setText(newValue.toUpperCase());
			}
		});
		txtFName.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue)
					validateClientRecord();
			}
		});
		//
		txtLName.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtLName.setText(newValue.toUpperCase());
			}
		});
		txtLName.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue)
					validateClientRecord();
			}
		});
		//
		txtPhone.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean focusLost, Boolean focusGain) {
				if (focusLost)
					validateClientRecord();
			}

		});
		txtPhone.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				String keyString = ke.getCharacter();
				if (!keyString.matches("[0123456789]")) {
					ke.consume();
				}
			}
		});
		//
		txtPhoneOther.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				String keyString = ke.getCharacter();
				if (!keyString.matches("[0123456789]")) {
					ke.consume();
				}
			}
		});
		//
		txtMake.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtMake.setText(newValue.toUpperCase());
			}
		});
		//
		txtMake.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean oldValue, Boolean newValue) {
				if (oldValue && isValidMake()) {
					_MODELS = getModels();
					loadModelNamesAutoFill();
					// loadTrimEngineDescriptionsAutoFill();
				} /*
					 * else { txtMake.requestFocus(); txtMake.selectAll(); }
					 */
				if (newValue) {
					loadMakeAutoFill();
				}
			}

			private boolean isValidMake() {
				return _MakeList.contains(txtMake.getText());
			}
		});
		//
		txtModel.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtModel.setText(newValue.toUpperCase());
			}
		});
		txtModel.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean oldValue, Boolean newValue) {
				if (oldValue && isValidModel()) {
					// _MODELS = getModels();
					loadTrimNameAutoFill();
					loadEngineDescAutoFill();
				}
			}

			private boolean isValidModel() {
				return _ModelsList.contains(txtModel.getText());
			}
		});
		//
		txtTrimDescription.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtTrimDescription.setText(newValue.toUpperCase());
			}
		});
		//
		txtSOPartQty.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				String keyString = ke.getCharacter();
				if (!keyString.matches("[.0123456789]")) {
					ke.consume();
				}
			}
		});
		//
		txtSOPartPrice.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				String keyString = ke.getCharacter();
				if (!keyString.matches("[.0123456789]")) {
					ke.consume();
				}
			}
		});
		//
		txtSOLabourHrs.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				String keyString = ke.getCharacter();
				if (!keyString.matches("[.0123456789]")) {
					ke.consume();
				}
			}
		});
		//
		txtYear.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				String keyString = ke.getCharacter();
				if (!keyString.matches("[0123456789]")) {
					ke.consume();
				}
			}
		});
		txtYear.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean oldValue, Boolean newValue) {
				if (oldValue && !FXmlUtils.isOptionalEmptyTextString(txtYear.getText())) {
					int year = Integer.parseInt(txtYear.getText());

					if ((year < (currentYear - SERVICEYEARRANGE)) || (year > currentYear)) {
						txtYear.requestFocus();
						txtYear.selectAll();
					}
				}
			}
		});
		//
		txtOdometerReading.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				String keyString = ke.getCharacter();
				if (!keyString.matches("[0123456789]")) {
					ke.consume();
				}
			}
		});
		txtOdometerReading.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean oldValue, Boolean newValue) {
				if (oldValue) {
					Long l = Long.parseLong(txtOdometerReading.getText());
					txtOdometerReading.setText(l.toString());
				}
			}
		});
		//
		txtColour.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtColour.setText(newValue.toUpperCase());
			}
		});
		//
		txtColour.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean oldValue, Boolean newValue) {
				if (newValue)
					loadColourAutoFill();
			}
		});
		//
		txtCity.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtCity.setText(newValue.toUpperCase());
			}
		});
		//
		txtSearchPhone.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				String keyString = ke.getCharacter();
				if (!keyString.matches("[-0123456789]")) {
					ke.consume();
				}
			}
		});
		//
		txtMCode.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtMCode.setText(newValue.toUpperCase());
			}
		});
		txtMCode.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.SPACE) {
					event.consume();
				}
			}
		});
		txtMCode.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean focusLost, Boolean gained) {
				String mcode = null;
				if (focusLost) {
					if ((mcode = txtMCode.getText()) != null) {
						Pattern pattern = Pattern.compile(XMLUtils.MAILCODE_FORMAT);
						Matcher matcher = pattern.matcher(mcode);
						if (!matcher.matches()) {
							txtMCode.requestFocus();
							txtMCode.selectAll();
						}
					}
				}
			}
		});
		//
		txtEmail.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.SPACE) {
					event.consume();
				}
			}
		});
		txtEmail.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean focusLost, Boolean gained) {
				String email = null;
				if (focusLost) {
					if ((email = txtEmail.getText()) != null) {
						Pattern pattern = Pattern.compile(XMLUtils.EMAIL_PATTERN);
						Matcher matcher = pattern.matcher(email);
						if (!matcher.matches()) {
							txtEmail.setText("");
						}
					}
				}
			}
		});
		///
		txtAddress.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtAddress.setText(newValue.toUpperCase());
			}
		});
		txtAddressOther.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtAddressOther.setText(newValue.toUpperCase());
			}
		});
		//
		txtRegion.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtRegion.setText(newValue.toUpperCase());
			}
		});
		txtRegion.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue && isValidRegion()) {
					loadCityAutoFill(txtRegion.getText().trim());
				} /*
					 * else { txtRegion.requestFocus(); txtRegion.selectAll(); }
					 */
			}

			private boolean isValidRegion() {
				return _Regions.contains(txtRegion.getText());
			}
		});
		//
		txtVIN.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtVIN.setText(newValue.toUpperCase());
			}
		});
		txtVIN.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue) {
					txtPlate.clear();
					validateVehicleRecord();
				}
			}
		});
		//
		txtPlate.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtPlate.setText(newValue.toUpperCase());
			}
		});
		txtPlate.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue) {
					Vehicle platedVehicle = vehicleService.findByPlate(txtPlate.getText().trim());

					if (isNewClient() && isExistingVehicle() && (platedVehicle != null)) {
						txtPlate.requestFocus();
						txtPlate.selectAll();
						return;
					}
					validateVehicleRecord();
				}
			}
		});
		//
		txtEngineCapacity.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue) {
					setEngineDescription();
				}
			}
		});
		txtEngineCapacity.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				String keyString = keyEvent.getCharacter();
				if (!keyString.matches("[.0123456789]")) {
					keyEvent.consume();
				}
			}
		});
		//
		txtEngineBuild.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtEngineBuild.setText(newValue.toUpperCase());
			}
		});
		txtEngineBuild.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue) {
					setEngineDescription();
				}
			}
		});
		//
		txtEngineFuel.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtEngineFuel.setText(newValue.toUpperCase());
			}
		});
		txtEngineFuel.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue) {
					setEngineDescription();
				}
			}
		});
		txtEngineOther.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				String keyString = keyEvent.getCharacter();
				if (keyString.matches("[,]")) {
					keyEvent.consume();
				}
			}
		});
		txtEngineOther.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue) {
					setEngineDescription();
				}
			}
		});
		txtEngineOther.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtEngineOther.setText(newValue.toUpperCase());
			}
		});
		//
		txtTrimLevel.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtTrimLevel.setText(newValue.toUpperCase());
			}
		});
		txtTrimLevel.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue) {
					setTrimDescription();
				}
			}
		});
		//
		txtTrimDoors.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue) {
					setTrimDescription();
				}
			}
		});
		//
		txtTrimDrive.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtTrimDrive.setText(newValue.toUpperCase());
			}
		});
		txtTrimDrive.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue) {
					setTrimDescription();
				}
			}
		});
		//
		txtTrimOther.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue) {
					setTrimDescription();
				}
			}
		});
		txtTrimOther.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtTrimOther.setText(newValue.toUpperCase());
			}
		});
		txtTrimOther.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				String keyString = keyEvent.getCharacter();
				if (keyString.matches("[,]")) {
					keyEvent.consume();
				}
			}
		});
		//
		txtSOPartNr.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean outFocus, Boolean inFocus) {
				if (outFocus) {
					setPartsDescriptionDetails();
				}
				if (inFocus/* !isInventoryItem() */) {
					// windowCaller(this);
					callInventoryForm();
				}
			}

			private boolean isInventoryItem() {
				boolean isValid = false;
				String sPartNr = (FXmlUtils.isValid(txtSOPartNr)) ? txtSOPartNr.getText() : "";
				if (!sPartNr.isEmpty()) {
					Inventory inv = inventoryService.findByPartNr(sPartNr);

					if (inv != null) {
						txtSOPartDescription.setText(inv.getDescription());
						txtSOPartPrice.setText(FXmlUtils.decFormat(inv.getCostPrice(), 2));
						isValid = (inv != null);
					}
				}
				return isValid;
			}
		});
		txtSOPartNr.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtSOPartNr.setText(newValue.toUpperCase());
			}
		});
		//
		txtSOLabourRate.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue) {
					String str = txtSOLabourRate.getText().trim();
					if (FXmlUtils.isOptionalEmptyTextString(str)) {
						txtSOLabourRate.setText(FXmlUtils.decFormat(new Float(XMLUtils.COMPANY_LABOURRATE_REG), 2));
					} else {
						str = txtSOLabourRate.getText();
						Float value = Float.parseFloat(str);
						txtSOLabourRate.setText(FXmlUtils.decFormat(new Float(value), 2));
					}
				}
			}
		});
		txtSOLabourRate.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				String keyString = keyEvent.getCharacter();
				if (!keyString.matches("[.0123456789]")) {
					keyEvent.consume();
				}
			}
		});
		//
		txtSOPartDescription.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtSOPartDescription.setText(newValue.toUpperCase());
			}
		});
		txtSOPartDescription.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue && !FXmlUtils.isOptionalEmptyTextString(txtSOPartDescription.getText())) {
					txtSOPartDescription.setTooltip(new Tooltip(Parts.default_partNr));
					txtSOPartDescription.setPromptText(Parts.default_partNr);
					//
					if (customerItem()) {
						txtSOPartNr.setText(Parts.default_partNr);
						txtSOPartPrice.setText(FXmlUtils.decFormat(0, 2));
						txtSOPartQty.setText(FXmlUtils.decFormat(1, 1));
						txtSOServiceDescription.requestFocus();
					}
				}
			}

			private boolean customerItem() {
				boolean isCustItem = txtSOPartDescription.getText().contains("CUSTOMER ITEM")
						|| txtSOPartDescription.getText().contains("CUSTOMER PART")
						|| txtSOPartDescription.getText().contains("CUSTOMER SUPPLY")
						|| txtSOPartDescription.getText().contains("CUSTOMER SUPPLIED");
				return isCustItem;
			}
		});
		//
		txtSOServiceDescription.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtSOServiceDescription.setText(newValue.toUpperCase());
			}
		});
		//
		txtSOOdometer.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue && !FXmlUtils.isOptionalEmptyTextString(txtSOOdometer.getText())) {
					ServiceOrder sorder = tblServiceVehicle.getSelectionModel().getSelectedItem();
					long lodo = 100l;
					lodo = Long.parseLong(txtSOOdometer.getText());
					if (lodo < sorder.getOdometer()) {
						txtSOOdometer.requestFocus();
						txtSOOdometer.selectAll();
					}
				}
			}
		});
		txtSOOdometer.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				String keyString = keyEvent.getCharacter();
				if (!keyString.matches("[0123456789]")) {
					keyEvent.consume();
				}
			}
		});
		//
		dateBegin.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					dateBegin.setValue(LocalDate.now());
				}
				if (oldValue) {
					LocalDate ldate = dateBegin.getValue();
					if (ldate != null) {
						hasValidQueryStartDate = true;
						dateEnd.setValue(ldate.plusDays(7l));
					}
				}
			}
		});
		dateEnd.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue) {
					LocalDate ldate = dateEnd.getValue();
					if (ldate != null) {
						hasValidQueryEndDate = true;
					}
				}
			}
		});
		//
		tabReport.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				refreshReportTab();
			}
		});
		//
		tabService.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				refreshServiceTab();
			}
		});
		//
		txtQueryNames01.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtQueryNames01.setText(newValue.toUpperCase());
			}
		});
		//
		txtQueryNames02.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtQueryNames02.setText(newValue.toUpperCase());
			}
		});
		//
		txtQueryService01.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtQueryService01.setText(newValue.toUpperCase());
			}
		});
		txtQueryVehicle01.textProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null) {
				txtQueryVehicle01.setText(newValue.toUpperCase());
			}
		});
		//
		compileClientFormTxtFields();
		compileVehicleFormTxtfields();
		loadServiceOrderTable(null);

		// set selection action on Vehicle search table
		tblVehicle.getSelectionModel().selectedItemProperty()
				.addListener((obs, oValue, nValue) -> populateForm(nValue));
		//
		// set selection action
		tblServiceVehicle.getSelectionModel().selectedItemProperty()
				.addListener((obs, oValue, nValue) -> populateServiceInfo(nValue));
		//
		// set selection action
		tblWorkItems.getSelectionModel().selectedItemProperty()
				.addListener((obs, oValue, nValue) -> populateWorkInfo(nValue));
		//
		// set selection action
		tblOwnerQueryResult.getSelectionModel().selectedItemProperty()
				.addListener((obs, oValue, nValue) -> populateOwnerQuery(nValue));
		//
		// set selection action
		tblServiceQueryResult.getSelectionModel().selectedItemProperty()
				.addListener((obs, oValue, nValue) -> populateServiceQuery(nValue));
		//
		// set selection action
		tblVehicleQueryResult.getSelectionModel().selectedItemProperty()
				.addListener((obs, oValue, nValue) -> populateVehicleQuery(nValue));
	}

	/**
	 * 
	 */
	protected void callInventoryForm() {
		try {
			InventoryDialog inventoryDialog = new InventoryDialog(inventoryService);
			inventoryDialog.displayInventoryDialog();
			//
			Object[] selectedObj = inventoryDialog.getSelectedInvetory();
			Inventory sinv = (Inventory) selectedObj[0];
			String price = (String) selectedObj[1];
			String qty = (String) selectedObj[2];
			txtSOPartPrice.setText(price);
			txtSOPartQty.setText(qty);
			txtSOPartDescription.setText(sinv.getDescription());
			txtSOPartNr.setText(sinv.getPartNumber());
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * @param selectedVehicle
	 */
	private void populateVehicleQuery(Vehicle selectedVehicle) {
		clearVehicleQueryFields();
		if (selectedVehicle != null) {
			observableServiceOrderQueryList = FXCollections.observableArrayList();
			final List<ServiceOrder> finalSOrders = selectedVehicle.getServiceOrderList();
			Platform.runLater(() -> {
				tblServiceQueryResult.getItems().clear();
				observableServiceOrderQueryList.addAll(finalSOrders);
				tblServiceQueryResult.setItems(observableServiceOrderQueryList);

				displayServicesQueryTable(finalSOrders);
			});
			//
			Model model = selectedVehicle.getModel();
			Make make = model.getMake();
			txtVehicleDetailsEngine.setText(model.getEngineDescription());
			txtVehicleDetailsTrim.setText(model.getTrimDescription());
			txtVehicleDetailsMakeModel.setText(make.getShortName() + " - " + model.getName());

			Owner ownr = selectedVehicle.getOwner();
			String s = ownr.getFirstname() + ", " + ownr.getLastname().substring(0, 1);
			txtVehicleOwnerRef.setText(s);

			txtVehicleDetailsPlate.setText(selectedVehicle.getPlate());
			txtVehicleDetailsVIN.setText(selectedVehicle.getVIN());
		}
	}

	/**
	 * 
	 * @param selectedServiceOrder
	 */
	private void populateServiceQuery(ServiceOrder selectedServiceOrder) {
		clearServicesQueryFields();
		if (selectedServiceOrder != null) {
			txtServiceVehicleRef.setText(selectedServiceOrder.getVehicle().getVIN());
			txtSODetailsComment.setText(selectedServiceOrder.getComment());

			txtSODetailsDateIn.setText(selectedServiceOrder.getSDate().toString());
			txtSODetailsJobId.setText(selectedServiceOrder.getJobID());
			txtSODetailsOdometer.setText(new Long(selectedServiceOrder.getOdometer()).toString());

			txtSODetailsStatus.setText(selectedServiceOrder.getSStatus().getStatus());
			taSODetailsDescription.setText(selectedServiceOrder.getDescription());
		}
	}

	private void populateOwnerQuery(Owner selectedOwner) {
		clearOwnerQueryFields();
		if (selectedOwner != null) {
			observableVehicleQueryList = FXCollections.observableArrayList();
			final List<Vehicle> finalVehicles = selectedOwner.getVehicleList();
			Platform.runLater(() -> {
				tblVehicleQueryResult.getItems().clear();
				observableVehicleQueryList.addAll(finalVehicles);
				tblVehicleQueryResult.setItems(observableVehicleQueryList);

				displayVehicleQueryTable(finalVehicles);
			});
			//
			StringBuilder sbuilder = new StringBuilder();
			txtOwnerDetailsFirstname.setText(selectedOwner.getFirstname());
			txtOwnerDetailsLastname.setText(selectedOwner.getLastname());
			sbuilder.append(selectedOwner.getPhone());
			sbuilder.append(FXmlUtils.isOptionalEmptyTextString(selectedOwner.getPhoneOther()) ? ""
					: ", " + selectedOwner.getPhoneOther());
			txtOwnerDetailsPhones.setText(sbuilder.toString());
			//
			sbuilder = new StringBuilder();
			Address adress = selectedOwner.getAddress();
			sbuilder.append(adress.getStreet()).append(", ");
			sbuilder.append(adress.getCity()).append(", ");
			sbuilder.append(adress.getRegion().getName());
			txtOwnerDetailsAddress.setText(sbuilder.toString());

			sbuilder = new StringBuilder();
			sbuilder.append(
					FXmlUtils.isOptionalEmptyTextString(selectedOwner.getEmail()) ? "" : selectedOwner.getEmail());
			txtOwnerDetailsEmail.setText(sbuilder.toString());
		}
	}

	private void refreshReportTab() {
		List<String> slist = null;
		if (cboReportType == null) {
			cboReportType = new ComboBox<String>();
		}
		//
		if (cboReportType.getItems().isEmpty()) {
			cboReportTypeData = FXCollections.observableArrayList();

			slist = new ArrayList<String>();
			slist.add(REPORT_TYPE.SALES.getCriteria());
			slist.add(REPORT_TYPE.SERVICE_REVENUE.getCriteria());
			slist.add(REPORT_TYPE.INVENTORY.getCriteria());
			slist.add(REPORT_TYPE.HST_GST.getCriteria());
			slist.add(REPORT_TYPE.SALES_TAX.getCriteria());
			slist.add(REPORT_TYPE.YEAR_END.getCriteria());
			Collections.sort(slist);
			// //
			Collections.sort(slist);
			cboReportTypeData.addAll(FXCollections.observableArrayList(slist));
			cboReportType.setItems(cboReportTypeData);
		}
	}

	/**
	 * 
	 */
	private void refreshServiceTab() {
		List<String> slist = null;
		if (cboQueryByNames == null) {
			cboQueryByNames = new ComboBox<String>();
		}
		if (cboQueryByNames.getItems().isEmpty()) {
			cboQueryByNamesData = FXCollections.observableArrayList();

			slist = new ArrayList<String>();
			slist.add(_CLIENT_CRITERIA.FIRST_LASTNAME.getCriteria());
			slist.add(_CLIENT_CRITERIA.NAME_PHONE.getCriteria());
			Collections.sort(slist);
			// //
			cboQueryByNamesData.addAll(FXCollections.observableArrayList(slist));
			cboQueryByNames.setItems(cboQueryByNamesData);
		}
		//
		if (cboQueryByService == null) {
			cboQueryByService = new ComboBox<String>();
		}
		if (cboQueryByService.getItems().isEmpty()) {
			cboQueryByServiceData = FXCollections.observableArrayList();

			slist = new ArrayList<String>();
			slist.add(_SERVICE_CRITERIA.DATE_RANGE.getCriteria());
			slist.add(_SERVICE_CRITERIA.VEHICLE.getCriteria());
			slist.add(_SERVICE_CRITERIA.JOB_ID.getCriteria());
			Collections.sort(slist);
			// // // //
			cboQueryByServiceData.addAll(FXCollections.observableArrayList(slist));
			cboQueryByService.setItems(cboQueryByServiceData);
		}
		//
		if (cboQueryByVehicle == null) {
			cboQueryByVehicle = new ComboBox<String>();
		}
		if (cboQueryByVehicle.getItems().isEmpty()) {
			cboQueryByVehicleData = FXCollections.observableArrayList();

			slist = new ArrayList<String>();
			slist.add(_VEHICLE_CRITERIA.MODEL.getCriteria());
			slist.add(_VEHICLE_CRITERIA.VIN.getCriteria());
			slist.add(_VEHICLE_CRITERIA.PLATE.getCriteria());
			Collections.sort(slist);
			//
			cboQueryByVehicleData.addAll(FXCollections.observableArrayList(slist));
			cboQueryByVehicle.setItems(cboQueryByVehicleData);
		}
	}

	/**
	 * 
	 */
	private void setRadios() {
		radioToggle = new ToggleGroup();
		rbPend.setToggleGroup(radioToggle);
		rbComplete.setToggleGroup(radioToggle);
		radioToggle.selectToggle(rbPend);
	}

	/**
	 * 
	 * @param veh
	 */
	private void populateForm(Vehicle veh) {
		if (veh != null) {
			//
			new Thread(() -> {
				Platform.runLater(() -> {
					populateClientForm(veh.getOwner());
					populateVehicleForm(veh);
				});
			}).start();
		}
	}

	/**
	 * populateClientInfo(vehicle.getOwner());
	 * 
	 */
	private void compileClientFormTxtFields() {
		if (clientFormTextFields == null)
			clientFormTextFields = new ArrayList<>();
		//
		clientFormTextFields.add(txtFName);
		clientFormTextFields.add(txtLName);
		clientFormTextFields.add(txtAddress);
		clientFormTextFields.add(txtAddressOther);
		clientFormTextFields.add(txtCity);
		clientFormTextFields.add(txtEmail);
		clientFormTextFields.add(txtMCode);
		clientFormTextFields.add(txtPhone);
		clientFormTextFields.add(txtRegion);
		clientFormTextFields.add(txtPhoneOther);
	}

	/**
	 * 
	 */
	private void compileVehicleFormTxtfields() {
		if (vehicleTextFields == null)
			vehicleTextFields = new ArrayList<>();
		vehicleTextFields.add(txtVIN);
		vehicleTextFields.add(txtPlate);
		vehicleTextFields.add(txtColour);
		vehicleTextFields.add(txtYear);
		vehicleTextFields.add(txtMake);
		vehicleTextFields.add(txtModel);
		vehicleTextFields.add(txtOdometerReading);
		vehicleTextFields.add(txtTrimDoors);
		vehicleTextFields.add(txtTrimDrive);
		vehicleTextFields.add(txtTrimLevel);
		vehicleTextFields.add(txtTrimOther);
		vehicleTextFields.add(txtTrimDescription);
		vehicleTextFields.add(txtEngineBuild);
		vehicleTextFields.add(txtEngineCapacity);
		vehicleTextFields.add(txtEngineDescription);
		vehicleTextFields.add(txtEngineFuel);
		vehicleTextFields.add(txtEngineOther);
	}

	/**
	 * 
	 */
	protected void validateClientRecord() {
		if (FXmlUtils.isValid(txtFName) && FXmlUtils.isValid(txtLName) && FXmlUtils.isValid(txtPhone)) {
			Owner ownerByNamesAndPhone = ownerService.findByNamesAndPhone(txtFName.getText(), txtLName.getText(),
					txtPhone.getText());
			Owner ownerByNames = ownerService.findByNames(txtFName.getText(), txtLName.getText());
			// setNewClient();
			if (ownerByNamesAndPhone != null && ownerByNames != null) {

				if (ownerByNames.equals(ownerByNamesAndPhone)) {
					// existing client
					populateClientForm(ownerByNamesAndPhone);
					setNewClient(false);
				}
			} else if (ownerByNamesAndPhone == null && ownerByNames != null) {
				if (!ownerByNames.getPhone().equals(txtPhone.getText())) {
					Alert alert = FXmlUtils.showConfirmAlert(txtPhone.getScene().getWindow(),
							"Entered Phone Nr does not match DB record.\nPlease confirm first & last names.\nDo you wish to change Phone Nr.");
					//
					if (alert.getResult() != ButtonType.YES) {
						txtPhone.requestFocus();
						txtPhone.selectAll();
						return;
					} else {
						populateClientForm(ownerByNames, txtPhone);
						setNewClient(false);
					}
				}
			} else if (ownerByNames == null) {
				setNewClient(true);
			}
			// monitor possible change in core client information
			if (!isConfirmedChange()
					&& (hasChangedValue(txtFName, clientTxtFieldMap) || hasChangedValue(txtLName, clientTxtFieldMap))) {
				//
				clearClientForm(txtFName, txtLName, txtPhone);
				setConfirmChange(true);
			}
		}
		resetButton();
	}

	/**
	 * 
	 * @return
	 */
	private boolean isConfirmedChange() {
		return boolConfirmedChange;
	}

	/**
	 * 
	 * @param bool
	 */
	private void setConfirmChange(boolean bool) {
		boolConfirmedChange = bool;
	}

	/**
	 * 
	 * @param owner
	 *            - Owner object
	 * @param textfields
	 *            - Exempted TextFields
	 */
	private void populateClientForm(Owner owner) {
		clientTxtFieldMap = new HashMap<>();
		clientTxtFieldMap.put(txtFName, owner.getFirstname());
		clientTxtFieldMap.put(txtLName, owner.getLastname());
		clientTxtFieldMap.put(txtPhone, owner.getPhone());

		String otherPhone = (owner.getPhoneOther() != null) ? owner.getPhoneOther() : "";
		clientTxtFieldMap.put(txtPhoneOther, otherPhone);

		String emailOptional = (owner.getEmail() != null) ? owner.getEmail() : "";
		clientTxtFieldMap.put(txtEmail, emailOptional);

		Address address = owner.getAddress();
		clientTxtFieldMap.put(txtAddress, address.getStreet());
		String otherAddress = (address.getOther() != null) ? address.getOther() : "";
		clientTxtFieldMap.put(txtAddressOther, otherAddress);
		clientTxtFieldMap.put(txtRegion, address.getRegion().getName());
		clientTxtFieldMap.put(txtCity, address.getCity());
		clientTxtFieldMap.put(txtMCode, address.getMailcode());
		//
		txtFName.setText(owner.getFirstname());
		txtLName.setText(owner.getLastname());
		txtPhone.setText(owner.getPhone());

		txtPhoneOther.setText(otherPhone);
		txtEmail.setText(emailOptional);
		//
		txtAddress.setText(address.getStreet());
		txtAddressOther.setText(otherAddress);
		txtMCode.setText(address.getMailcode());
		setNewClient(false);
		refill(txtRegion, address.getRegion().getName(), autocompleteRegions, _Regions);
		refill(txtCity, address.getCity(), autocompleteCity, new ArrayList<String>(getCities(txtRegion.getText())));
	}

	/**
	 * 
	 * @param owner
	 * @param textfields
	 */
	private void populateClientForm(Owner owner, TextField... textfields) {
		List<TextField> txts = new ArrayList<TextField>();
		if (textfields != null) {
			for (TextField tf : textfields)
				txts.add(tf);
		}
		clientTxtFieldMap = new HashMap<>();
		//
		txtFName.setText(owner.getFirstname());
		clientTxtFieldMap.put(txtFName, owner.getFirstname());
		txtLName.setText(owner.getLastname());
		clientTxtFieldMap.put(txtLName, owner.getLastname());

		if (txts.isEmpty() || txts.contains(txtPhone)) {
			txtPhone.setText(owner.getPhone());
			clientTxtFieldMap.put(txtPhone, owner.getPhone());
		}
		String strOptional = (owner.getPhoneOther() != null) ? owner.getPhoneOther() : "";
		txtPhoneOther.setText(strOptional);
		clientTxtFieldMap.put(txtPhoneOther, strOptional);

		strOptional = (owner.getEmail() != null) ? owner.getEmail() : "";
		txtEmail.setText(strOptional);
		clientTxtFieldMap.put(txtEmail, strOptional);
		//
		Address address = owner.getAddress();
		txtAddress.setText(address.getStreet());
		clientTxtFieldMap.put(txtAddress, address.getStreet());
		strOptional = (address.getOther() != null) ? address.getOther() : "";
		txtAddressOther.setText(strOptional);
		clientTxtFieldMap.put(txtAddressOther, strOptional);

		refill(txtCity, address.getCity(), autocompleteCity, new ArrayList<String>(getCities(txtRegion.getText())));
		clientTxtFieldMap.put(txtCity, address.getCity());
		txtMCode.setText(address.getMailcode());
		clientTxtFieldMap.put(txtMCode, address.getMailcode());

		refill(txtRegion, address.getRegion().getName(), autocompleteRegions, _Regions);
		clientTxtFieldMap.put(txtRegion, address.getRegion().getName());
		setNewClient(false);
		clearVehicleRecord(owner);
	}

	/**
	 * 
	 * @param ow
	 */
	private void clearVehicleRecord(Owner ow) {
		if (ow == null)
			clearVehicleInfo();
		else if (FXmlUtils.isValid(txtVIN)) {
			Vehicle dveh = vehicleService.findByVIN(txtVIN.getText());
			if (dveh != null && ow.equals(dveh.getOwner()))
				clearVehicleInfo();
		}
	}

	/**
	 * 
	 */
	protected void validateVehicleRecord() {
		if (FXmlUtils.isValid(txtVIN) && FXmlUtils.isValid(txtPlate)) {

			Owner ownerByNames = ownerService.findByNames(txtFName.getText(), txtLName.getText());
			Vehicle vehicleByPlate = vehicleService.findByPlate(txtPlate.getText());
			if (ownerByNames == null && vehicleByPlate != null)
				return;
			//
			setHasExistingPlate(vehicleByPlate != null);
			String message = null;
			Alert alert = null;

			Owner ownerByNamesPhone = ownerService.findByNamesAndPhone(txtFName.getText(), txtLName.getText(),
					txtPhone.getText());
			Vehicle vehicleByVIN = vehicleService.findByVIN(txtVIN.getText());
			setNewVehicle(vehicleByVIN == null);// String newPlate =
												// txtPlate.getText();
			//
			if (vehicleByVIN != null) {
				if (vehicleByPlate != null) {

					if (vehicleByVIN.equals(vehicleByPlate)) {
						populateVehicleForm(vehicleByVIN);

					} else if (!ownerByNames.equals(vehicleByVIN.getOwner())
							&& ownerByNames.equals(vehicleByPlate.getOwner())) {
						//
						message = "Entered VIN & Plate will result in plate & ownership transfer.\n\tOne vehicle will be disowned."
								+ "\n\t\tDo you wish to proceed?";
						//
						alert = FXmlUtils.showConfirmAlert(txtVIN.getScene().getWindow(), message);
						if (alert.getResult() == ButtonType.YES) {
							populateVehicleForm(vehicleByVIN, txtPlate);
						} else {
							txtPlate.requestFocus();
							txtPlate.selectAll();
						}

					} else if (ownerByNames.equals(vehicleByVIN.getOwner())
							&& !ownerByNames.equals(vehicleByPlate.getOwner())) {
						//
						message = "Entered plate is not registered to the Owner." + "\n\t\tCannot proceed.";
						FXmlUtils.showInfoAlert(txtVIN.getScene().getWindow(), message);
						txtPlate.requestFocus();
						txtPlate.selectAll();
						return;
					} else if (!ownerByNames.equals(vehicleByVIN.getOwner())
							&& !ownerByNames.equals(vehicleByPlate.getOwner())) {
						//
						FXmlUtils.showInfoAlert(txtVIN.getScene().getWindow(),
								"Entered Owner must match VIN or plate records.\n Cannot accept usage.");
						txtPlate.clear();
						txtVIN.clear();
						txtVIN.requestFocus();
						return;
					}
				} else { // existing VIN and new Plate
					message = "VIN has a record in DB, that does not match owner on form."
							+ "\n\tVehicle ownership and plate will change.  Proceed?";
					alert = FXmlUtils.showConfirmAlert(txtVIN.getScene().getWindow(), message);
					if (alert.getResult() == ButtonType.YES) {
						populateVehicleForm(vehicleByVIN, txtPlate);
					} else {
						txtPlate.requestFocus();
						txtPlate.selectAll();
					}
				}
			} else if (vehicleByVIN == null) { // NEW VEHICLE
				clearVehicleInfo(txtVIN, txtPlate);

				if (vehicleByPlate != null) {
					if (ownerByNames != null && vehicleByPlate.getOwner().equals(ownerByNames)) {
						message = "Existing Plate will be assigned to new vehicle.\n\tDo you wish to proceed?";
						alert = FXmlUtils.showConfirmAlert(txtVIN.getScene().getWindow(), message);

						if (alert.getResult() != ButtonType.YES) {
							txtPlate.requestFocus();
							txtPlate.selectAll();
						}
					}
				}
				if (vehicleByPlate == null) {
					setNewVehicle(true);
					System.out.println("VIN==null && PLATE == null");
				}
			}
		}
		resetButton();
	}

	/**
	 * 
	 */
	private void resetButton() {
		if (isNewClient()) {
			btnProcessClient.setText("Save Info");
		} else if (isExistingClient() && (validateUpdatedVehicle() || validateUpdatedClient())) {
			btnProcessClient.setText("Update Info");
		} else {
			btnProcessClient.setText("Process Info");
		}
	}

	/**
	 * 
	 * @param b
	 */
	private void setHasExistingPlate(boolean b) {
		boolHasExistingPlate = b;
	}

	/**
	 * 
	 * @return
	 */
	private boolean hasExistingPlate() {
		return boolHasExistingPlate;
	}

	/**
	 * 
	 */
	protected void setEngineDescription() {
		if (validatedEngineDesc()) {
			StringBuilder sbuilder = new StringBuilder();
			sbuilder.append(FXmlUtils.decFormat(Float.parseFloat(txtEngineCapacity.getText().trim()), 1));
			sbuilder.append("L, ").append(txtEngineBuild.getText().trim());
			sbuilder.append(", ").append(txtEngineFuel.getText().trim());
			sbuilder.append((!FXmlUtils.isValid(txtEngineOther)) ? "" : ", " + txtEngineOther.getText());
			//
			txtEngineDescription.setText(sbuilder.toString());
		}
	}

	/**
	 * @param string
	 * 
	 */
	protected void setEngineDescriptionDetails(String string) {
		String[] descArr = string.split(", ");
		if (descArr != null && descArr.length >= 3) {
			txtEngineCapacity.setText(descArr[0].replace("L", ""));
			txtEngineBuild.setText(descArr[1]);
			txtEngineFuel.setText(descArr[2]);
			if (descArr.length > 3 && descArr[3] != null)
				txtEngineOther.setText(descArr[3]);
		}
	}

	/**
	 * @param string
	 * 
	 */
	protected void setTrimDescriptionDetails(String string) {
		String[] descArr = string.split(", ");
		if (descArr != null && descArr.length >= 3) {
			txtTrimDoors.setText(descArr[0]);
			txtTrimLevel.setText(descArr[1]);
			txtTrimDrive.setText(descArr[2]);
			if (descArr.length > 3 && descArr[3] != null)
				txtTrimOther.setText(descArr[3]);
		}
	}

	/**
	 * 
	 */
	protected void setPartsDescriptionDetails() {
		Inventory invItem = inventoryService.findByPartNr(txtSOPartNr.getText());
		if (invItem != null) {
			txtSOPartDescription.setText(invItem.getDescription());
			txtSOPartPrice.setText(FXmlUtils.decFormat(invItem.getCostPrice() * INV_MARKUP, 2));
			txtSOPartQty.requestFocus();
		} else {
			txtSOPartDescription.requestFocus();
		}
	}

	/**
	 * 
	 */
	protected void setTrimDescription() {
		if (validatedTrimDesc()) {
			StringBuilder sb = new StringBuilder();
			sb.append(txtTrimDoors.getText().trim()).append(", ");
			sb.append(txtTrimLevel.getText().trim()).append(", ");
			sb.append(txtTrimDrive.getText().trim());
			sb.append((!FXmlUtils.isValid(txtTrimOther)) ? "" : ", " + txtTrimOther.getText());
			//
			txtTrimDescription.setText(sb.toString());
		}
	}

	/**
	 * 
	 */
	private void loadTrimNameAutoFill() {
		autocompleteTrimDrive = new AutoCompleteTextField(txtTrimDrive);
		autocompleteTrimDrive.getEntries().addAll(getDrives());
		//
		autocompleteTrimLevel = new AutoCompleteTextField(txtTrimLevel);
		autocompleteTrimLevel.getEntries().addAll(getTrimLevels());
		//
		autocompleteTrimDoors = new AutoCompleteTextField(txtTrimDoors);
		autocompleteTrimDoors.getEntries().addAll(getDoors());
	}

	/**
	 * 
	 */
	private void loadEngineDescAutoFill() {
		autocompleteEngineBuild = new AutoCompleteTextField(txtEngineBuild);
		autocompleteEngineBuild.getEntries().addAll(getEngineBuilds());
		//
		autocompleteEngineFuel = new AutoCompleteTextField(txtEngineFuel);
		autocompleteEngineFuel.getEntries().addAll(getFuels());
	}

	/**
	 * Retrieve Models based on specific Vehicle Make
	 * 
	 * @return
	 */
	protected List<Model> getModels() {
		List<Model> models = (List<Model>) modelService.findByName(txtModel.getText());
		return models;
	}

	/**
	 * 
	 * @return
	 */
	private List<String> getPartNumbers() {
		List<Inventory> inventoryPartsList = (List<Inventory>) inventoryService.findAll();
		List<String> invList = inventoryPartsList.stream().map(Inventory::getPartNumber).distinct()
				.collect(Collectors.toList());
		Collections.sort(invList);
		return invList;
	}

	/**
	 * 
	 */
	private void loadPartDescriptionsAutoFill() {
		partDescs = getPartDescriptions();
		autoFieldPartDesc = new AutoCompleteTextField(txtSOPartDescription);
		autoFieldPartDesc.getEntries().addAll(partDescs);
	}

	/**
	 * 
	 */
	private void loadLabourRatesAutoFill() {
		Labour_Rates = getLRates();
		autoCompleteLabourRate = new AutoCompleteTextField(txtSOLabourRate);
		autoCompleteLabourRate.getEntries().addAll(Labour_Rates);
	}

	/**
	 * 
	 */
	private void loadPartNumbersAutoFill() {
		partNumbers = getPartNumbers();
		autocompletePartNumber = new AutoCompleteTextField(txtSOPartNr);
		autocompletePartNumber.getEntries().addAll(partNumbers);
	}

	/**
	 * 
	 */
	private void loadColourAutoFill() {
		colorsList = getColours();
		autocompleteColors = new AutoCompleteTextField(txtColour);
		autocompleteColors.getEntries().addAll(colorsList);
	}

	/**
	 * 
	 */
	private void loadModelNamesAutoFill() {
		_ModelsList = getModelStrings();
		autocompleteModel = new AutoCompleteTextField(txtModel);
		autocompleteModel.getEntries().addAll(_ModelsList);
	}

	/**
	 * 
	 * @param region
	 */
	private void loadCityAutoFill(String region) {
		List<String> clist = new ArrayList<String>(getCities(region));
		autocompleteCity = new AutoCompleteTextField(txtCity);
		autocompleteCity.getEntries().addAll(clist);
	}

	/**
	 * 
	 */
	private void loadRegionsAutoFill() {
		_Regions = getRegions();
		autocompleteRegions = new AutoCompleteTextField(txtRegion);
		autocompleteRegions.getEntries().addAll(_Regions);
	}

	/**
	 * 
	 */
	private void loadMakeAutoFill() {
		_MakeList = getVehicleMakes();
		autocompleteMake = new AutoCompleteTextField(txtMake);
		autocompleteMake.getEntries().addAll(_MakeList);
	}

	/**
	 * 
	 * @return
	 */
	private List<String> getLRates() {
		List<String> rates = new ArrayList<String>();
		rates.add(XMLUtils.COMPANY_LABOURRATE_REG);
		rates.add(XMLUtils.COMPANY_LABOURRATE_SPE);
		rates.add(XMLUtils.COMPANY_LABOURRATE_XTR);
		Collections.sort(rates);
		return rates;
	}

	/**
	 * 
	 * @return
	 */
	private List<String> getPartDescriptions() {
		List<Parts> partsList = (List<Parts>) partsService.findAll();
		//
		List<String> plist = partsList.stream().map(Parts::getDescription).distinct().collect(Collectors.toList());
		Collections.sort(plist);
		return plist;
	}

	/**
	 * 
	 * @return
	 */
	private List<String> getRegions() {
		List<Region> regions = (List<Region>) regionService.findByCountry(getCountry());

		List<String> list = regions.stream().map(Region::getName).distinct().collect(Collectors.toList());
		Collections.sort(list);
		return list;
	}

	/**
	 * 
	 * @return
	 */
	private Region getRegion() {
		List<Region> regions = (List<Region>) regionService.findByCountry(getCountry());
		Region region = regions.stream().filter(x -> x.getName().equalsIgnoreCase(txtRegion.getText())).findAny()
				.orElse(null);
		return region;
	}

	/**
	 * 
	 * @return
	 */
	private Country getCountry() {
		List<Country> countries = (List<Country>) countryService.findAll();
		Country country = countries.stream().filter(c -> c.getName().equals(XMLUtils.LOCAL_COUNTRY)).findAny()
				.orElse(null);
		return country;
	}

	/**
	 * 
	 * @return
	 */
	private List<String> getDoors() {
		List<BodyType> bodies = (List<BodyType>) bodyTypeService.findAll();
		//
		List<String> list = bodies.stream().map(BodyType::getBType).distinct().collect(Collectors.toList());
		Collections.sort(list);
		return list;
	}

	/**
	 * 
	 * @return
	 */
	private Set<String> getTrimLevels() {
		List<Trim> trimLevels = (List<Trim>) trimService.findAll();
		//
		Set<String> levels = trimLevels.stream().map(Trim::getLevel).sorted((x1, x2) -> x1.compareTo(x2)).distinct()
				.collect(Collectors.toSet());
		return levels;
	}

	@FXML
	protected void handleClearClientInfoAction(ActionEvent event) {
		clearClientForm();
	}

	@FXML
	protected void handleEstimateServiceOrderAction(ActionEvent event) {
		ServiceOrder sorder = tblServiceVehicle.getSelectionModel().getSelectedItem();
		if (sorder == null) {
			FXmlUtils.showInfoAlert(btnEstimateService.getScene().getWindow(), "Invalid Request");
			return;
		}
		List<Work> incompleteWork = pendingWork(sorder);
		if (sorder != null && !hasSavedWork() && !incompleteWork.isEmpty()) {
			setWorkItemButtons(true);
			new Thread(() -> {
				new PDFEstimateWriter(incompleteWork, sorder);
				setWorkItemButtons(false);
			}).start();
		} else {
			FXmlUtils.showInfoAlert(btnEstimateService.getScene().getWindow(), "Invalid Request");
		}
	}

	/**
	 * 
	 * @param sorder
	 * @return
	 */
	private List<Work> pendingWork(ServiceOrder sorder) {
		SStatus status = sstatusService.findByStatus("COMPLETED");
		Stream<Work> wStream = sorder.getWorkList().stream();
		List<Work> incompleteWork = wStream.filter(w -> !w.getWorkStatus().equals(status)).collect(Collectors.toList());
		return incompleteWork;
	}

	@FXML
	protected void handleCompleteServiceOrderAction(ActionEvent event) {
		ServiceOrder sorder = tblServiceVehicle.getSelectionModel().getSelectedItem();
		if (sorder == null) {
			FXmlUtils.showInfoAlert(btnCompleteService.getScene().getWindow(), "No Service Selected");
			return;
		}
		List<Work> incompleteWork = pendingWork(sorder);
		if (incompleteWork.isEmpty() && hasCompletedWorkItems(sorder)) {
			setWorkItemButtons(true);
			SStatus status = sstatusService.findByStatus("COMPLETED");
			//
			Calendar cal = Calendar.getInstance();
			sorder.setEDate(cal.getTime());
			sorder.setSStatus(status);
			sorder.setOdometer(Long.parseLong(txtSOOdometer.getText().trim()));
			createInvoice(sorder);
			serviceOrderService.update(sorder);
			loadServiceOrderTable(null);
			tblWorkItems.getItems().clear();

			setWorkItemButtons(false);
		} else if (!incompleteWork.isEmpty()) {
			FXmlUtils.showInfoAlert(btnCompleteService.getScene().getWindow(), "Service Order has pending items");
			return;
		} else {
			FXmlUtils.showInfoAlert(btnCompleteService.getScene().getWindow(), "Invalid Request: No Service Item");
			return;
		}
	}

	/**
	 * 
	 * @param sorder
	 * @return
	 */
	private boolean hasCompletedWorkItems(ServiceOrder sorder) {
		List<Work> works = sorder.getWorkList();
		boolean isCompleted = !works.isEmpty();
		for (Work wk : works) {
			isCompleted = isCompleted && wk.getWorkStatus().getStatus().equalsIgnoreCase("COMPLETED");
		}
		return isCompleted;
	}

	/**
	 * 
	 * @param bool
	 */
	private void setWorkItemButtons(boolean bool) {
		btnDeleteServiceItem.disableProperty() //
				.bind(BooleanExpression.booleanExpression(new SimpleBooleanProperty(bool)));
		btnClearServiceItem.disableProperty() //
				.bind(BooleanExpression.booleanExpression(new SimpleBooleanProperty(bool)));
		btnCompleteService.disableProperty() //
				.bind(BooleanExpression.booleanExpression(new SimpleBooleanProperty(bool)));
		btnEstimateService.disableProperty() //
				.bind(BooleanExpression.booleanExpression(new SimpleBooleanProperty(bool)));
		btnSaveServiceItem.disableProperty() //
				.bind(BooleanExpression.booleanExpression(new SimpleBooleanProperty(bool)));
		btnUpdateServiceItem.disableProperty() //
				.bind(BooleanExpression.booleanExpression(new SimpleBooleanProperty(bool)));
	}

	/**
	 * 
	 * @param sorder
	 */
	private void createInvoice(ServiceOrder sorder) {
		TextInputDialog dialog = new TextInputDialog("Enter Service Summary");
		dialog.setWidth(520);
		dialog.setTitle("Auto Service Summary");
		dialog.setContentText("Service Comment:");
		dialog.setHeaderText("Auto Service Summary");

		Optional<String> result = dialog.showAndWait();

		result.ifPresent(comment -> {
			sorder.setDescription(getServiceDescription(sorder));
			sorder.setComment(reform(comment));
		});
		setWorkItemButtons(true);
		new Thread(() -> {
			new PDFInvoiceWriter(sorder);
			setWorkItemButtons(false);
		}).start();

	}

	/**
	 * 
	 * @param comment
	 * @return
	 */
	private String reform(String comment) {
		StringBuilder sb = new StringBuilder();
		//
		String[] sar = comment.split(" ");
		for (int n = 0; n < sar.length; n++) {
			sb.append(sar[n].substring(0, 1).toUpperCase()).append(sar[n].substring(1).toLowerCase()).append(" ");
		}
		return (sb.length() > 150) ? sb.substring(0, 149).toString() : sb.toString().trim();
	}

	/**
	 * 
	 * @param sorder
	 * @return
	 */
	private String getServiceDescription(ServiceOrder sorder) {
		List<Work> wkList = sorder.getWorkList();
		StringBuilder sb = new StringBuilder();
		Parts parts = null;
		for (Work wk : wkList) {
			sb.append(wk.getWorkDesc());
			sb.append(", ");
			sb.append(wk.getParts());
			sb.append("\n");
		}
		return (sb.length() > 255) ? sb.substring(0, 254).toString() : sb.toString();
	}

	/**
	 * 
	 * @return
	 */
	private boolean hasSavedWork() {
		return FXmlUtils.isValid(txtSOPartDescription) && FXmlUtils.isValid(txtSOPartPrice)
				&& FXmlUtils.isValid(txtSOPartQty) && FXmlUtils.isValid(txtSOLabourHrs)
				&& FXmlUtils.isValid(txtSOLabourHrs);
	}

	@FXML
	protected void handleSaveServiceOrderItemAction(ActionEvent event) {
		if (tblServiceVehicle.getSelectionModel().getSelectedItem() != null && isValidServiceOrderEntry()) {
			final ServiceOrder servOrder = tblServiceVehicle.getSelectionModel().getSelectedItem();
			setWorkItemButtons(true);
			new Thread(() -> {
				String sWorkDesc = txtSOServiceDescription.getText();
				String sPartDesc = txtSOPartDescription.getText();
				//
				float fLabourHrs = Float.parseFloat(txtSOLabourHrs.getText());
				float fPartPrice = Float.parseFloat(txtSOPartPrice.getText());
				float fPartQty = Float.parseFloat(txtSOPartQty.getText());
				Float rate = Float.parseFloat(txtSOLabourRate.getText());

				Parts parts = new Parts();
				parts.setPrice(fPartPrice);
				parts.setQuantity(fPartQty);
				Inventory invPart = null;

				if (FXmlUtils.isValid(txtSOPartNr)) {
					invPart = getInventoryItem(txtSOPartNr.getText());
					parts.setInventory(invPart);
					if (invPart.getPartNumber().equals(XMLUtils.DEF_PART_NUMBER)) {
						parts.setDescription(sPartDesc);
						parts.setPrice(fPartPrice);
					} else {
						parts.setDescription(invPart.getDescription());
						parts.setPrice(invPart.getCostPrice() * INV_MARKUP);
					}
					System.out.println(" invPart = getInventoryItem()");
				} else {
					invPart = getInventoryItem(XMLUtils.DEF_PART_NUMBER);
					parts.setInventory(invPart);
					parts.setDescription(sPartDesc);
					parts.setPrice(fPartPrice);
				}
				partsService.create(parts);

				Work work = new Work();
				work.setDuration(fLabourHrs);
				work.setWorkDesc(sWorkDesc);
				float wCost = rate * fLabourHrs;
				work.setWorkCost(wCost);
				//
				work.setServiceOrder(servOrder);
				work.setParts(parts);
				SStatus wStatus = getWorkStatus();
				if (wStatus == null)
					return;
				work.setWorkStatus(wStatus);
				parts.setWork(work);

				Work tmpWork = workService.create(work);
				servOrder.addWork(tmpWork);
				servOrder.setOdometer(Long.parseLong(txtSOOdometer.getText()));
				serviceOrderService.update(servOrder);

				loadWorkItemsTable(servOrder.getWorkList());
				tblServiceVehicle.refresh();
				// if (wStatus.getStatus().equals("COMPLETED"))
				clearServiceOrderFields();

				setWorkItemButtons(false);
			}).start();
		} else {
			FXmlUtils.showInfoAlert(btnSaveServiceItem.getScene().getWindow(), "Invalid Service Item entry");
			return;
		}
	}

	@FXML
	private void handleQueryByVehicleCBOAction(ActionEvent event) {
		ComboBox<?> cboSource = (ComboBox<?>) event.getSource();
		if (cboSource != null) {
			if (FXmlUtils.isValid(cboSource))
				queryByVehicleCriteria = (String) cboSource.getSelectionModel().getSelectedItem();
		}
	}

	@FXML
	private void handleQueryByServiceCBOAction(ActionEvent event) {
		ComboBox<?> cboSource = (ComboBox<?>) event.getSource();
		if (cboSource != null) {
			if (FXmlUtils.isValid(cboSource))
				queryByServiceCriteria = (String) cboSource.getSelectionModel().getSelectedItem();
			//
			if (queryByServiceCriteria.equals(_SERVICE_CRITERIA.DATE_RANGE))
				txtQueryService01.setDisable(true);
		}
	}

	@FXML
	private void handleQueryByNamesCBOAction(ActionEvent event) {
		ComboBox<?> cboSource = (ComboBox<?>) event.getSource();
		if (cboSource != null) {
			if (FXmlUtils.isValid(cboSource))
				queryByNameCriteria = (String) cboSource.getSelectionModel().getSelectedItem();
		}
	}

	/**
	 * 
	 * @return
	 */
	private SStatus getWorkStatus() {
		RadioButton selectedRadioButton = (RadioButton) radioToggle.getSelectedToggle();
		SStatus status = sstatusService.findByStatus(selectedRadioButton.getText().toUpperCase());
		//
		return status;
	}

	/**
	 * 
	 */
	private void clearServiceOrderFields() {
		txtSOLabourHrs.clear();
		// txtSOOdometer.clear();
		txtSOPartQty.clear();
		txtSOPartPrice.clear();
		// txtSOLabourRate.clear();
		txtSOPartNr.clear();
		txtSOPartDescription.clear();
		txtSOServiceDescription.clear();
	}

	/**
	 * 
	 * @param strings
	 * @return
	 */
	private Inventory getInventoryItem(String... strings) {
		Inventory invItem = null;
		if (strings != null && strings.length > 0) {
			invItem = inventoryService.findByPartNr(strings[0]);
		} else {
			invItem = inventoryService.findByPartNr(XMLUtils.DEF_PART_NUMBER);
		}
		return invItem;
	}

	/**
	 * 
	 * @return
	 */
	private boolean isValidServiceOrderEntry() {
		boolean isValid = FXmlUtils.isValid(txtSOPartDescription) && FXmlUtils.isValid(txtSOPartPrice)
				&& FXmlUtils.isValid(txtSOPartQty) && FXmlUtils.isValid(txtSOLabourHrs)
				&& FXmlUtils.isValid(txtSOOdometer) && FXmlUtils.isValid(txtSOServiceDescription);
		//
		return isValid;
	}

	@FXML
	protected void handleQueryByServiceAction(ActionEvent event) {
		clearOwnerVehicleInfo();
		clearVehicleQueryFields();
		clearServicesQueryFields();
		tblVehicleQueryResult.getItems().clear();
		tblOwnerQueryResult.getItems().clear();
		//
		if (queryByServiceCriteria == null) {
			FXmlUtils.showInfoAlert(btnQueryByService.getScene().getWindow(), "No ComboBox Selection made");
			return;
		} else {
			String strQuery = null;

			if (!FXmlUtils.isValid(txtQueryService01) && (dateBegin.getValue() == null)
					&& (dateEnd.getValue() == null)) {
				FXmlUtils.showInfoAlert(btnQueryByService.getScene().getWindow(), "No Query String, or dates");
				return;
			}
			strQuery = txtQueryService01.getText();

			List<ServiceOrder> sorderList = null;
			if (queryByServiceCriteria.equals(_SERVICE_CRITERIA.JOB_ID.getCriteria())) {
				if (strQuery.contains("*")) {
					String _strQuery = getJobIdString(strQuery);
					if (_strQuery == null) {
						FXmlUtils.showInfoAlert(btnQueryByService.getScene().getWindow(), "Invalid Query String");
						return;
					}
					sorderList = serviceOrderService.findLikeJobId(_strQuery);

				} else {
					ServiceOrder serviceOrder = serviceOrderService.findByJobId(strQuery);
					sorderList = new ArrayList<ServiceOrder>();
					sorderList.add(serviceOrder);
				}
			}
			//
			if (queryByServiceCriteria.equals(_SERVICE_CRITERIA.VEHICLE.getCriteria())) {
				Vehicle vehicle = vehicleService.findByVIN(strQuery);
				if (vehicle == null) {
					FXmlUtils.showInfoAlert(btnQueryByService.getScene().getWindow(), "No Vehicle Record");
					return;
				}
				sorderList = (List<ServiceOrder>) serviceOrderService.findByVehicle(vehicle);
			}
			//
			if (queryByServiceCriteria.equals(_SERVICE_CRITERIA.DATE_RANGE.getCriteria())) {
				if (hasValidQueryStartDate && hasValidQueryEndDate) {
					LocalDate ldState = dateBegin.getValue();
					LocalDate ldEnd = dateEnd.getValue();
					if (ldState == null || ldEnd == null)
						return;
					//
					sorderList = serviceOrderService.findByDateRange(convertToDate(ldState),
							convertToDate(ldEnd.plusDays(1l)));
				}
			}
			//
			observableServiceOrderQueryList = FXCollections.observableArrayList();

			final List<ServiceOrder> fSerOrder = sorderList;
			//
			Platform.runLater(() -> {
				tblServiceQueryResult.getItems().clear();
				observableServiceOrderQueryList.addAll(fSerOrder);
				tblServiceQueryResult.setItems(observableServiceOrderQueryList);

				displayServicesQueryTable(fSerOrder);
			});
		}
	}

	private String getJobIdString(String str) {
		if (str.charAt(str.length() - 1) != '*')
			return null;
		//
		String s = str.substring(0, str.length() - 1);
		Integer num = 0;
		try {
			num = Integer.parseInt(s);
			if (num < 10)
				num = null;
		} catch (NumberFormatException e) {
			num = null;
		}
		return (num != null) ? num.toString() : null;
	}

	/**
	 * Converts LocalDate object to Date
	 * 
	 * @param localDate
	 * @return - Date
	 */
	public Date convertToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	@FXML
	protected void handleQueryByVehicleAction(ActionEvent event) {
		clearOwnerVehicleInfo();
		clearVehicleQueryFields();
		clearServicesQueryFields();
		tblOwnerQueryResult.getItems().clear();
		tblServiceQueryResult.getItems().clear();
		//
		if (queryByVehicleCriteria == null) {
			FXmlUtils.showInfoAlert(btnQueryByVehicle.getScene().getWindow(), "No ComboBox Selection made");
			return;
		} else {
			String strQuery = null;
			List<Vehicle> vehicleList = new ArrayList<Vehicle>();
			//
			if (!FXmlUtils.isValid(txtQueryVehicle01)) {
				FXmlUtils.showInfoAlert(btnQueryByVehicle.getScene().getWindow(), "No query string");
				return;
			}

			strQuery = txtQueryVehicle01.getText();
			Vehicle vehicle = null;
			if (queryByVehicleCriteria.equals(_VEHICLE_CRITERIA.PLATE.getCriteria())) {
				vehicle = vehicleService.findByPlate(strQuery);
				if (vehicle != null)
					vehicleList.add(vehicle);
			}
			//
			if (queryByVehicleCriteria.equals(_VEHICLE_CRITERIA.VIN.getCriteria())) {
				vehicle = vehicleService.findByVIN(strQuery);
				if (vehicle != null)
					vehicleList.add(vehicle);
			}
			//
			if (queryByVehicleCriteria.equals(_VEHICLE_CRITERIA.MODEL.getCriteria())) {
				List<Model> models = modelService.findByName(strQuery);
				//
				for (Model mdl : models) {
					vehicleList.addAll(mdl.getVehicleList());
				}
			}
			//
			observableVehicleQueryList = FXCollections.observableArrayList();
			final List<Vehicle> fVehicles = vehicleList;
			Platform.runLater(() -> {
				tblVehicleQueryResult.getItems().clear();
				observableVehicleQueryList.addAll(fVehicles);
				tblVehicleQueryResult.setItems(observableVehicleQueryList);

				displayVehicleQueryTable(fVehicles);
			});
		}
	}

	@SuppressWarnings("unchecked")
	@FXML
	protected void handleQueryByOwnerAction(ActionEvent event) {
		clearOwnerVehicleInfo();
		clearVehicleQueryFields();
		clearServicesQueryFields();
		tblVehicleQueryResult.getItems().clear();
		tblServiceQueryResult.getItems().clear();
		//
		if (queryByNameCriteria == null) {
			FXmlUtils.showInfoAlert(btnQueryByNames.getScene().getWindow(), "No ComboBox Selection made");
			return;
		} else {
			if (!FXmlUtils.isValid(txtQueryNames01) && !FXmlUtils.isValid(txtQueryNames02)) {
				FXmlUtils.showInfoAlert(btnQueryByNames.getScene().getWindow(), "No Query String");
				return;
			} else {
				String[] clientQuryArray = new String[2];
				clientQuryArray[0] = "";
				clientQuryArray[1] = "";

				if (FXmlUtils.isValid(txtQueryNames01)) {
					clientQuryArray[0] = txtQueryNames01.getText();
				}
				//
				if (FXmlUtils.isValid(txtQueryNames02)) {
					clientQuryArray[1] = txtQueryNames02.getText();
				}
				//
				List<Owner> owners = null;
				if (queryByNameCriteria.equals(_CLIENT_CRITERIA.FIRST_LASTNAME.getCriteria())) {

					if (!clientQuryArray[0].isEmpty() && !clientQuryArray[1].isEmpty())
						owners = (List<Owner>) ownerService.findByNames(clientQuryArray[0], clientQuryArray[1]);
					//
					if (clientQuryArray[0].isEmpty() && !clientQuryArray[1].isEmpty())
						owners = (List<Owner>) ownerService.findByName(clientQuryArray[1]);
					//
					if (!clientQuryArray[0].isEmpty() && clientQuryArray[1].isEmpty())
						owners = (List<Owner>) ownerService.findByName(clientQuryArray[0]);
					//
					if (clientQuryArray[0].equals("*") && clientQuryArray[1].equals("*"))
						owners = (List<Owner>) ownerService.findAll();
					//
				}
				//
				if (queryByNameCriteria.equals(_CLIENT_CRITERIA.NAME_PHONE.getCriteria())) {
					if (!clientQuryArray[0].isEmpty() && !clientQuryArray[1].isEmpty())
						owners = (List<Owner>) ownerService.findByNameAndPhone(clientQuryArray[0], clientQuryArray[1]);
					//
					if (clientQuryArray[0].isEmpty() && !clientQuryArray[1].isEmpty())
						owners = (List<Owner>) ownerService.findByPhone(clientQuryArray[1]);
					//
					if (!clientQuryArray[0].isEmpty() && clientQuryArray[1].isEmpty())
						owners = (List<Owner>) ownerService.findByName(clientQuryArray[0]);
					//
				}
				//
				observableOwnerQueryList = FXCollections.observableArrayList();
				final List<Owner> fOwners = owners;
				Platform.runLater(() -> {
					tblOwnerQueryResult.getItems().clear();
					observableOwnerQueryList.addAll(fOwners);
					tblOwnerQueryResult.setItems(observableOwnerQueryList);

					displayOwnerQueryTable(fOwners);
				});
			}
		}
	}

	@FXML
	protected void handleClearClientQueryAction(ActionEvent event) {
		if (FXmlUtils.isValid(txtQueryNames01))
			txtQueryNames01.clear();
		//
		if (FXmlUtils.isValid(txtQueryNames02))
			txtQueryNames02.clear();
		if (cboQueryByNames.getSelectionModel().getSelectedItem() != null)
			cboQueryByNames.getSelectionModel().select(null);
	}

	@FXML
	protected void handleClearServiceQueryAction(ActionEvent event) {
		if (FXmlUtils.isValid(txtQueryService01))
			txtQueryService01.getText();
		//
		if (cboQueryByService.getSelectionModel().getSelectedItem() != null)
			cboQueryByService.getSelectionModel().select(null);
		//
		dateBegin.setValue(LocalDate.now());
		dateEnd.setValue(dateBegin.getValue().plusDays(7l));
	}

	@FXML
	protected void handleClearVehicleQueryAction(ActionEvent event) {
		if (FXmlUtils.isValid(txtQueryVehicle01))
			txtQueryVehicle01.clear();
		//
		if (cboQueryByVehicle.getSelectionModel().getSelectedItem() != null)
			cboQueryByVehicle.getSelectionModel().select(null);
		//
	}

	@FXML
	protected void handleUpdateServiceOrderItemAction(ActionEvent event) {
		if (tblServiceVehicle.getSelectionModel().getSelectedItem() == null) {
			FXmlUtils.showInfoAlert(btnUpdateServiceItem.getScene().getWindow(), "No Service Item selected");
			return;
		} else {
			Work work = tblWorkItems.getSelectionModel().getSelectedItem();
			if (hasUpdatedWorkItem(work)) {
				setWorkItemButtons(true);
				new Thread(() -> {
					//
					Float hrs = Float.parseFloat(txtSOLabourHrs.getText());
					work.setDuration(hrs);
					Float rate = Float.parseFloat(txtSOLabourRate.getText());
					work.setWorkCost(rate * hrs);
					work.setWorkDesc(txtSOServiceDescription.getText());
					work.setWorkStatus(getWorkStatus());

					Parts prt = work.getParts();
					prt.setPrice(Float.parseFloat(txtSOPartPrice.getText()));
					prt.setDescription(txtSOPartDescription.getText());
					prt.setQuantity(Float.parseFloat(txtSOPartQty.getText()));

					ServiceOrder sorder = work.getServiceOrder();
					sorder.setDescription(txtSOServiceDescription.getText());
					sorder.setOdometer(Long.parseLong(txtSOOdometer.getText()));

					if ((workService.update(work) != null) && (partsService.update(prt) != null)
							&& (serviceOrderService.update(sorder) != null)) {
						FXmlUtils.showInfoAlert(btnUpdateServiceItem.getScene().getWindow(), "Work Update Sucessful");

						tblWorkItems.getSelectionModel().clearSelection();
						clearServiceOrderData();
						loadWorkItemsTable(sorder.getWorkList());
						tblWorkItems.getSelectionModel().select(work);
					} else {
						FXmlUtils.showInfoAlert(btnUpdateServiceItem.getScene().getWindow(), "Work Update Failed");
					}
					setWorkItemButtons(false);
				}).start();
			}
		}
	}

	@FXML
	protected void handleDeleteServiceOrderItemAction(ActionEvent event) {
		ServiceOrder sOrder = null;
		if ((sOrder = tblServiceVehicle.getSelectionModel().getSelectedItem()) != null) {

			Work work = null;
			if ((work = tblWorkItems.getSelectionModel().getSelectedItem()) != null) {
				Parts part = work.getParts();
				if (part != null) {
					if (txtSOPartNr.getText().equals(XMLUtils.DEF_PART_NUMBER)) {
						work.setParts(null);
						partsService.delete(part.getID());
						if (sOrder.getWorkList().remove(work) && (serviceOrderService.update(sOrder) != null)) {
							//
							workService.delete(work.getID());
							FXmlUtils.showInfoAlert(btnDeleteServiceItem.getScene().getWindow(), "Work Item Deleted");
							return;
						}
					} else {
						Inventory inv = part.getInventory();
						if (inv.getPartsList().remove(part) && inventoryService.update(inv) != null) {
							work.setParts(null);
							partsService.delete(part.getID());
							//
							if (sOrder.getWorkList().remove(work) && (serviceOrderService.update(sOrder) != null)) {
								workService.delete(work.getID());
								FXmlUtils.showInfoAlert(btnDeleteServiceItem.getScene().getWindow(),
										"Work Item Deleted");
								return;
							}
						}
					}
				}
			}
		} else {
			FXmlUtils.showInfoAlert(btnUpdateServiceItem.getScene().getWindow(), "No Service Item selected");
			return;
		}
	}

	@FXML
	protected void handleSearchClientAction(ActionEvent event) {
		clearOwnerVehicleInfo();
		clearVehicleQueryFields();
		clearServicesQueryFields();
		tblVehicleQueryResult.getItems().clear();
		tblServiceQueryResult.getItems().clear();
		//
		List<Owner> ownerList = null;
		String name = (FXmlUtils.isValid(txtSearchName)) ? txtSearchName.getText() : "";
		String phone = (FXmlUtils.isValid(txtSearchPhone)) ? txtSearchPhone.getText() : "";

		if (!FXmlUtils.isOptionalEmptyTextString(name) && !FXmlUtils.isOptionalEmptyTextString(phone)) {

			Owner owner = ownerService.findByNameAndPhone(name, phone);
			if (owner != null) {
				ownerList = new ArrayList<>();
				ownerList.add(owner);
			} else {
				FXmlUtils.showInfoAlert(btnSearchClient.getScene().getWindow(),
						"Client :" + name + " @ " + phone + ": No Records found");
			}
			//
		} else if (FXmlUtils.isOptionalEmptyTextString(name) && !FXmlUtils.isOptionalEmptyTextString(phone)) {
			ownerList = ownerService.findByPhone(phone);

		} else if (!FXmlUtils.isOptionalEmptyTextString(name) && FXmlUtils.isOptionalEmptyTextString(phone)) {
			ownerList = ownerService.findByName(name);

		} else if (FXmlUtils.isOptionalEmptyTextString(name) && FXmlUtils.isOptionalEmptyTextString(phone)) {
			FXmlUtils.showInfoAlert(btnSearchClient.getScene().getWindow(), "Invalid Search Input");
			return;
		}
		//
		if (ownerList != null && !ownerList.isEmpty()) {
			if (ownerList.size() == 1 && ownerList.get(0).getVehicleList().isEmpty()) {
				//
				final Owner fOwner = ownerList.get(0);
				Platform.runLater(() -> {
					populateClientForm(fOwner);
				});
				clearVehicleRecord(fOwner);
				setNewClient(false);
				return;
			}
			handleOwnerList(ownerList);
		} else if (ownerList == null || ownerList.isEmpty()) {
			FXmlUtils.showInfoAlert(btnSearchClient.getScene().getWindow(), "Invalid Search Input");
		}
	}

	@FXML
	protected void handleClearServiceOrderItemAction(ActionEvent event) {
		if (tblServiceVehicle.getSelectionModel().getSelectedItem() != null) {
			clearServiceOrderData();
		}
	}

	/**
	 * 
	 */
	private void clearServiceOrderData() {
		txtSOVehicleEngineInfo.clear();
		txtSOVehicleTrimInfo.clear();
		txtSOVehiclePlateInfo.clear();
		txtSOVehicleVINInfo.clear();
		txtSOVehicleInfo.clear();
		txtSOContactInfo0.clear();
		txtSOEmailInfo.clear();
		txtSOPhoneInfo.clear();
		txtSOLName.clear();
		txtSOFName.clear();
		//
		tblServiceVehicle.getSelectionModel().clearSelection();
	}

	/**
	 * 
	 */
	private void clearWorkDetails() {
		txtSOLabourHrs.clear();
		txtSOPartDescription.clear();
		txtSOPartNr.clear();
		txtSOPartPrice.clear();
		txtSOPartQty.clear();
		txtSOServiceDescription.clear();

		radioToggle.selectToggle(rbPend);
	}

	@FXML
	protected void hlinkPartsAction(ActionEvent event) {
		callInventoryForm();
	}

	/**
	 * 
	 * @param owners
	 */
	private void handleOwnerList(List<Owner> owners) {
		if (!owners.isEmpty()) {
			List<Vehicle> vehicles = new ArrayList<Vehicle>();
			if (owners.size() == 1) {
				vehicles.addAll(owners.get(0).getVehicleList());
			} else {
				for (Owner ow : owners) {
					vehicles.addAll(ow.getVehicleList());
				}
			}
			Platform.runLater(() -> {
				tblVehicle.getItems();
				tblVehicle.setItems(vehiclesObservableList(vehicles));
				displayVehiclesTable(vehicles);
			});
		}
	}

	/**
	 * 
	 */
	private void clearOwnerVehicleInfo() {
		Platform.runLater(() -> {
			clearClientForm();
			clearVehicleInfo();
			tblVehicle.getItems().clear();
		});
	}

	@FXML
	protected void handleCancelAllReportAction(ActionEvent event) {
	}

	@FXML
	protected void handleExportReportAction(ActionEvent event) {
	}

	@FXML
	protected void handleDisplayReportAction(ActionEvent event) {
		Text text = new Text("Text Message");
		tfReportSummary.getChildren().add(text);
	}

	@FXML
	protected void handleSearchVehicleAction(ActionEvent event) {
		clearOwnerVehicleInfo();
		String vin = FXmlUtils.isValid(txtSearchVIN) ? txtSearchVIN.getText() : "";
		String plate = FXmlUtils.isValid(txtSearchPlate) ? txtSearchPlate.getText() : "";
		Vehicle vehicle = null;

		if (!FXmlUtils.isOptionalEmptyTextString(plate) && !FXmlUtils.isOptionalEmptyTextString(vin)) {
			vehicle = vehicleService.findByVINAndPlate(vin, plate);
			//
		} else if (!FXmlUtils.isOptionalEmptyTextString(plate) && FXmlUtils.isOptionalEmptyTextString(vin)) {
			vehicle = vehicleService.findByPlate(plate);
			//
		} else if (FXmlUtils.isOptionalEmptyTextString(plate) && !FXmlUtils.isOptionalEmptyTextString(vin)) {
			vehicle = vehicleService.findByVIN(vin);
			//
		} else if (FXmlUtils.isOptionalEmptyTextString(plate) && FXmlUtils.isOptionalEmptyTextString(vin)) {
			FXmlUtils.showInfoAlert(btnSearchVehicle.getScene().getWindow(), "Invalid Search Input");
			return;
		}
		//
		if (vehicle != null) {
			populateVehicleForm(vehicle);
		}
	}

	/**
	 * 
	 * @param work
	 */
	private void populateWorkInfo(Work work) {
		if (workTextFields == null)
			setWorkFields();

		if (work != null) { //
			workMap = new HashMap<>();
			Parts prt = work.getParts();
			ServiceOrder sordr = work.getServiceOrder();
			String value = "";
			//
			Float hrs = work.getDuration();
			txtSOLabourHrs.setText(FXmlUtils.decFormat(hrs, 1));
			workMap.put(txtSOLabourHrs, FXmlUtils.decFormat(hrs, 1));

			Float wcosts = work.getWorkCost();
			Float lRate = wcosts / hrs;
			txtSOLabourRate.setText(FXmlUtils.decFormat(lRate, 2));
			workMap.put(txtSOLabourRate, FXmlUtils.decFormat(lRate, 2));
			//
			value = FXmlUtils.decFormat(prt.getPrice(), 2);
			txtSOPartPrice.setText(value);
			workMap.put(txtSOPartPrice, value);
			//
			value = work.getWorkDesc();
			txtSOServiceDescription.setText(value);
			workMap.put(txtSOServiceDescription, value);
			//
			value = FXmlUtils.decFormat(prt.getQuantity(), 1);
			txtSOPartQty.setText(value);
			workMap.put(txtSOPartQty, value);
			//
			value = prt.getDescription();
			refill(txtSOPartDescription, value, autoFieldPartDesc, getPartDescriptions());
			workMap.put(txtSOPartDescription, value);
			//
			try {
				value = prt.getInventory().getPartNumber();
				refill(txtSOPartNr, value, autocompletePartNumber, getPartNumbers());
				workMap.put(txtSOPartNr, value);
			} catch (Exception e) {
				value = XMLUtils.DEF_PART_NUMBER;
				refill(txtSOPartNr, value, autocompletePartNumber, getPartNumbers());
				workMap.put(txtSOPartNr, null);
			}
			//
			value = new Long(sordr.getOdometer()).toString();
			txtSOOdometer.setText(value);
			workMap.put(txtSOOdometer, value);
			//
			if (work.getWorkStatus().getStatus().equals("PENDING")) {
				radioToggle.selectToggle(rbPend);
			} else {
				radioToggle.selectToggle(rbComplete);
			}
			workMap.put(radioToggle, work.getWorkStatus().getStatus());
		}
	}

	/**
	 * 
	 */
	private void setWorkFields() {
		workTextFields = new ArrayList<>();
		workTextFields.add(txtSOLabourRate);
		workTextFields.add(txtSOLabourHrs);
		workTextFields.add(txtSOServiceDescription);
		workTextFields.add(txtSOPartQty);
		workTextFields.add(txtSOPartPrice);
		workTextFields.add(txtSOPartDescription);
		workTextFields.add(txtSOOdometer);
	}

	/**
	 * 
	 * @param work
	 * @return
	 */
	private boolean hasUpdatedWorkItem(Work work) {
		String mValue = null;
		boolean hasChangedText = false;
		for (TextField txt : workTextFields) {
			mValue = workMap.get(txt);
			if (mValue == null || FXmlUtils.isOptionalEmptyTextString(txt.getText()))
				continue;
			if (!mValue.equals(txt.getText())) {
				hasChangedText = true;
				break;
			}
		}
		RadioButton selectedRadioButton = (RadioButton) radioToggle.getSelectedToggle();
		boolean hasChangedRadioToggle = !selectedRadioButton.getText().toUpperCase()
				.equals(work.getWorkStatus().getStatus());
		//
		return hasChangedText || hasChangedRadioToggle;
	}

	/**
	 * 
	 * @param sorder
	 */
	private void populateServiceInfo(ServiceOrder sorder) {
		clearWorkDetails();
		if (sorder != null) {// clearServiceOrderData();
			Float fLRate = Float.parseFloat(XMLUtils.COMPANY_LABOURRATE_REG);
			refill(txtSOLabourRate, FXmlUtils.decFormat(fLRate, 2), autoCompleteLabourRate, Labour_Rates);

			Long odometer = sorder.getOdometer();
			txtSOOdometer.setText((odometer != null) ? String.format("%d", sorder.getOdometer()) : "");

			StringBuilder sb = new StringBuilder();
			Owner owner = sorder.getVehicle().getOwner();
			sb.append(owner.getFirstname());
			txtSOFName.setText(sb.toString());

			sb = new StringBuilder();
			sb.append(owner.getLastname());
			txtSOLName.setText(sb.toString());

			sb = new StringBuilder();
			sb.append(owner.getPhone());
			if (!FXmlUtils.isOptionalEmptyTextString(owner.getPhoneOther())) {
				sb.append(", ").append(owner.getPhoneOther());
			}
			txtSOPhoneInfo.setText(sb.toString());

			sb = new StringBuilder();
			sb.append(!FXmlUtils.isOptionalEmptyTextString(owner.getEmail()) ? owner.getEmail() : "nil");
			txtSOEmailInfo.setText(sb.toString());

			Address address = owner.getAddress();
			sb = new StringBuilder();
			sb.append(address.getCity()).append(", ").append(address.getRegion().getName());
			txtSOContactInfo0.setText(sb.toString());
			//
			sb = new StringBuilder();
			Vehicle vehicle = sorder.getVehicle();
			Model model = vehicle.getModel();
			sb.append(model.getMake().getShortName()).append(",  ").append(model.getName());
			txtSOVehicleInfo.setText(sb.toString());

			sb = new StringBuilder();
			sb.append(vehicle.getVIN());
			txtSOVehicleVINInfo.setText(sb.toString());

			sb = new StringBuilder();
			sb.append(vehicle.getPlate());
			txtSOVehiclePlateInfo.setText(sb.toString());

			sb = new StringBuilder();
			sb.append(model.getTrimDescription());
			txtSOVehicleTrimInfo.setText(sb.toString());

			sb = new StringBuilder();
			sb.append(model.getEngineDescription());
			txtSOVehicleEngineInfo.setText(sb.toString());
			// populate Work Table
			loadWorkItemsTable(sorder.getWorkList());
		}
	}

	/**
	 * 
	 * @param txt
	 * @param str
	 * @param autoText
	 * @param list
	 */
	private void refill(TextField txt, String str, AutoCompleteTextField autoText, List<String> list) {
		if (autoText == null || list == null) {
			txt.setText(str);
			return;
		}
		autoText.getEntries().clear();
		txt.setText(str);
		autoText.getEntries().addAll(list);
	}

	/**
	 * 
	 * @param vehicle
	 */
	private void populateVehicleForm(Vehicle vehicle) {
		vehicleTxtFieldMap = new HashMap<>();
		//
		txtVIN.setText(vehicle.getVIN());
		vehicleTxtFieldMap.put(txtVIN, vehicle.getVIN());
		txtPlate.setText(vehicle.getPlate());
		vehicleTxtFieldMap.put(txtPlate, vehicle.getPlate());
		//
		refill(txtColour, vehicle.getVColor(), autocompleteColors, colorsList);
		vehicleTxtFieldMap.put(txtColour, vehicle.getVColor());

		Model mdl = vehicle.getModel();
		txtYear.setText(mdl.getYear().toString());
		vehicleTxtFieldMap.put(txtYear, mdl.getYear());
		refill(txtModel, mdl.getName(), autocompleteModel, _ModelsList);
		vehicleTxtFieldMap.put(txtModel, mdl.getName());

		refill(txtMake, mdl.getMake().getShortName(), autocompleteMake, _MakeList);
		vehicleTxtFieldMap.put(txtMake, mdl.getMake().getShortName());

		ServiceOrder lastService = serviceOrderService.findLastSOrder(vehicle);
		boolean hasLastService = (lastService != null && lastService.getOdometer() > 0);
		txtOdometerReading.setText(hasLastService ? lastService.getOdometer().toString() : new Long(1099l).toString());

		// txtTrimDescription.setText(mdl.getTrimDescription());
		setTrimDescriptionDetails(mdl.getTrimDescription());
		vehicleTxtFieldMap.put(txtTrimDescription, mdl.getTrimDescription());

		// txtEngineDescription.setText(mdl.getEngineDescription());
		setEngineDescriptionDetails(mdl.getEngineDescription());
		vehicleTxtFieldMap.put(txtEngineDescription, mdl.getEngineDescription());
		setNewVehicle(false);
	}

	/**
	 * 
	 * @param vehicle
	 * @param txt
	 */
	private void populateVehicleForm(Vehicle vehicle, TextField... txt) {
		List<TextField> textList = (txt != null) ? Arrays.asList(txt) : new ArrayList<TextField>();
		//
		if (vehicle != null) {
			setNewVehicle(false);
			Platform.runLater(() -> {
				vehicleTxtFieldMap = new HashMap<>();
				//
				if (!textList.isEmpty() && !textList.contains(txtVIN))
					txtVIN.setText(vehicle.getVIN());
				vehicleTxtFieldMap.put(txtVIN, txtVIN.getText());
				//
				if (!textList.isEmpty() && !textList.contains(txtPlate))
					txtPlate.setText(vehicle.getPlate());
				vehicleTxtFieldMap.put(txtPlate, txtPlate.getText());
				//
				refill(txtColour, vehicle.getVColor(), autocompleteColors, colorsList);
				vehicleTxtFieldMap.put(txtColour, vehicle.getVColor());

				Model mdl = vehicle.getModel();
				txtYear.setText(mdl.getYear().toString());
				vehicleTxtFieldMap.put(txtYear, mdl.getYear());
				refill(txtModel, mdl.getName(), autocompleteModel, _ModelsList);
				vehicleTxtFieldMap.put(txtModel, mdl.getName());

				refill(txtMake, mdl.getMake().getShortName(), autocompleteMake, _MakeList);
				vehicleTxtFieldMap.put(txtMake, mdl.getMake().getShortName());

				ServiceOrder lastService = serviceOrderService.findLastSOrder(vehicle);
				boolean hasLastService = (lastService != null && lastService.getOdometer() > 0);
				txtOdometerReading
						.setText(hasLastService ? lastService.getOdometer().toString() : new Long(1099l).toString());

				txtTrimDescription.setText(mdl.getTrimDescription());
				setTrimDescriptionDetails(mdl.getTrimDescription());
				vehicleTxtFieldMap.put(txtTrimDescription, mdl.getTrimDescription());

				txtEngineDescription.setText(mdl.getEngineDescription());
				setEngineDescriptionDetails(mdl.getEngineDescription());
				vehicleTxtFieldMap.put(txtEngineDescription, mdl.getEngineDescription());
				//
			});
		}
	}

	/**
	 * 
	 * @param vehList
	 */
	private void displayVehicleQueryTable(List<Vehicle> vehList) {
		if (vehList != null && (vehList.size() > 0)) {
			colQueryVIN.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVIN()));
			//
			colQueryPlate.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getPlate()));
			//
			colQueryTrim
					.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getModel().getTrimDescription()));
			colQueryTrim.setCellFactory(new Callback<TableColumn<Vehicle, String>, TableCell<Vehicle, String>>() {
				@Override
				public TableCell<Vehicle, String> call(TableColumn<Vehicle, String> param) {
					final TableCell<Vehicle, String> cell = new TableCell<Vehicle, String>() {
						private Text text;

						@Override
						public void updateItem(String item, boolean empty) {
							super.updateItem(item, empty);
							if (!isEmpty()) {
								text = new Text(item.toString());
								text.setWrappingWidth(colQueryTrim.getWidth() * WRAP_FACTOR);
								setGraphic(text);
							}
						}
					};
					return cell;
				}
			});
			//
			colQueryEngine.setCellValueFactory(
					cd -> new SimpleStringProperty(cd.getValue().getModel().getEngineDescription()));
			colQueryEngine.setCellFactory(new Callback<TableColumn<Vehicle, String>, TableCell<Vehicle, String>>() {
				@Override
				public TableCell<Vehicle, String> call(TableColumn<Vehicle, String> param) {
					final TableCell<Vehicle, String> cell = new TableCell<Vehicle, String>() {
						private Text text;

						@Override
						public void updateItem(String item, boolean empty) {
							super.updateItem(item, empty);
							if (!isEmpty()) {
								text = new Text(item.toString());
								text.setWrappingWidth(colQueryEngine.getWidth() * WRAP_FACTOR);
								setGraphic(text);
							}
						}
					};
					return cell;
				}
			});
		}
	}

	private void displayServicesQueryTable(List<ServiceOrder> orders) {
		clearServicesQueryFields();
		//
		if (orders != null && (orders.size() > 0)) {
			colQueryJobId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJobID()));
			colQuerySDate.setCellValueFactory(
					new Callback<CellDataFeatures<ServiceOrder, String>, ObservableValue<String>>() {
						public ObservableValue<String> call(CellDataFeatures<ServiceOrder, String> serv) {
							final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							Date date = serv.getValue().getSDate();
							return new SimpleStringProperty(sdf.format(date));
						}
					});
			colQueryPartDesc
					.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
			colQueryPartDesc
					.setCellFactory(new Callback<TableColumn<ServiceOrder, String>, TableCell<ServiceOrder, String>>() {
						@Override
						public TableCell<ServiceOrder, String> call(TableColumn<ServiceOrder, String> param) {
							final TableCell<ServiceOrder, String> cell = new TableCell<ServiceOrder, String>() {
								private Text text;

								@Override
								public void updateItem(String item, boolean empty) {
									super.updateItem(item, empty);
									if (!isEmpty()) {
										text = new Text(item.toString());
										text.setWrappingWidth(colQueryPartDesc.getWidth() * WRAP_FACTOR);
										setGraphic(text);
									}
								}
							};
							return cell;
						}
					});
		}
		colQueryStatus.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getSStatus().getStatus()));
	}

	/**
	 * 
	 */
	private void clearServicesQueryFields() {
		txtServiceVehicleRef.clear();
		txtSODetailsDateIn.clear();
		txtSODetailsJobId.clear();
		txtSODetailsComment.clear();
		txtSODetailsOdometer.clear();
		txtSODetailsStatus.clear();
		taSODetailsDescription.clear();
	}

	/**
	 * 
	 */
	private void clearVehicleQueryFields() {
		txtVehicleOwnerRef.clear();
		txtVehicleDetailsEngine.clear();
		txtVehicleDetailsMakeModel.clear();
		txtVehicleDetailsPlate.clear();
		txtVehicleDetailsTrim.clear();
		txtVehicleDetailsVIN.clear();
	}

	/**
	* 
	*/
	private void clearOwnerQueryFields() {
		txtOwnerDetailsAddress.clear();
		txtOwnerDetailsEmail.clear();
		txtOwnerDetailsFirstname.clear();
		txtOwnerDetailsLastname.clear();
		txtOwnerDetailsPhones.clear();
	}

	/**
	 * 
	 * @param owners
	 */
	private void displayOwnerQueryTable(List<Owner> owners) {
		if (owners != null && (owners.size() > 0)) {
			colFirstname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstname()));
			colLastname.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getLastname()));
			colContact.setCellValueFactory(cd -> new SimpleStringProperty(
					cd.getValue().getPhone() + "  " + cd.getValue().getPhoneOther() + "  " + cd.getValue().getEmail()));
			//
			colContact.setCellFactory(new Callback<TableColumn<Owner, String>, TableCell<Owner, String>>() {
				@Override
				public TableCell<Owner, String> call(TableColumn<Owner, String> param) {
					final TableCell<Owner, String> cell = new TableCell<Owner, String>() {
						private Text text;

						@Override
						public void updateItem(String item, boolean empty) {
							super.updateItem(item, empty);
							if (!isEmpty()) {
								text = new Text(item.toString());
								text.setWrappingWidth(colContact.getWidth() * WRAP_FACTOR);
								setGraphic(text);
							}
						}
					};
					return cell;
				}
			});
		}
	}

	/**
	 * 
	 * @param vehicleList
	 */
	private void displayVehiclesTable(List<Vehicle> vehicleList) {
		if (vehicleList != null && !vehicleList.isEmpty()) {
			colPlate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlate()));
			colVIN.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getVIN()));
			colModelName.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getModel().getName()));
			colCustomer.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getOwner().getFirstname()));
		}
	}

	/**
	 * 
	 * @param vehicles
	 * @return
	 */
	private ObservableList<Vehicle> vehiclesObservableList(List<Vehicle> vehicles) {
		observableVehicleList = FXCollections.observableArrayList();
		Platform.runLater(() -> {
			Set<Vehicle> vSet = vehicles.stream().filter(v -> !v.getPlate().contains(XMLUtils.DEF_PLATEPREFIX))
					.distinct().collect(Collectors.toSet());
			observableVehicleList.addAll(vSet);
		});
		//
		return observableVehicleList;
	}

	/**
	 * 
	 * @param vehicle
	 */
	private void addToServiceableVehicleTable(Vehicle vehicle) {
		List<ServiceOrder> pendingServiceOrders = serviceOrderService.findByNotCompletedStatus();
		//
		if (pendingServiceOrders == null)
			pendingServiceOrders = new ArrayList<ServiceOrder>();
		// search pending service orders for this vehicle
		ServiceOrder vehicleService = pendingServiceOrders.stream().filter(s -> s.getVehicle().equals(vehicle))
				.findAny().orElse(null);
		if (vehicleService == null)
			vehicleService = createServiceOrder(vehicle);
		else {
			// FXmlUtils.showInfoAlert(btnProcessClient.getScene().getWindow(),
			// "Vehicle ServiceOrder Exists.");
			loadServiceOrderTable(pendingServiceOrders);
			return;// serviceorder exist
		}
		pendingServiceOrders.add(vehicleService);
		//
		loadServiceOrderTable(pendingServiceOrders);
		// vehicleServiceOrder
		tblServiceVehicle.getSelectionModel().select(vehicleService);
		clearVehicleInfo();
		clearClientForm();
		setNewClient(false);
		setNewVehicle(false);
		tblVehicle.getItems().clear();
	}

	@FXML
	protected void handleCreateServiceOrderAction(ActionEvent event) {
		if (FXmlUtils.isValid(txtVIN) && FXmlUtils.isValid(txtPlate) && FXmlUtils.isValid(txtOdometerReading)) {
			Vehicle vehicle = vehicleService.findByVINAndPlate(txtVIN.getText(), txtPlate.getText());
			Owner owner = ownerService.findByNamesAndPhone(txtFName.getText(), txtLName.getText(), txtPhone.getText());
			//
			if (owner.equals(vehicle.getOwner()) && validedOdometerReading()) {
				final Vehicle fVehicle = vehicle;
				setVehicleClientButtons(true);
				Platform.runLater(() -> {
					addToServiceableVehicleTable(fVehicle);
					setVehicleClientButtons(false);
				});
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	private boolean validedOdometerReading() {
		Vehicle thisVehicle = vehicleService.findByVINAndPlate(txtVIN.getText(), txtPlate.getText());
		if (thisVehicle == null)
			return false;
		long longValue = 0l;
		longValue = Long.parseLong(txtOdometerReading.getText());
		long maxVehicleServiceOdometer = serviceOrderService.findVehicleMaxOdometerReading(thisVehicle);
		return (maxVehicleServiceOdometer <= longValue);
	}

	/**
	 * 
	 * @param sOrders
	 */
	private void loadServiceOrderTable(List<ServiceOrder> sOrders) {
		if (sOrders == null)
			sOrders = serviceOrderService.findByNotCompletedStatus();
		if (sOrders != null) {
			observableServiceOrderList = FXCollections.observableArrayList();
			observableServiceOrderList.addAll(sOrders);
			Platform.runLater(() -> {
				tblServiceVehicle.getItems().clear();
				tblServiceVehicle.setItems(observableServiceOrderList);
				displayServiceOrderTable();
			});
		}
	}

	/**
	 * 
	 * @param witems
	 */
	private void loadWorkItemsTable(List<Work> witems) {
		if (witems != null && witems.size() > 0) {
			tblWorkItems.getItems().clear();
			observableWorkItems = FXCollections.observableArrayList();
			observableWorkItems.addAll(witems);
			//
			Platform.runLater(() -> {
				tblWorkItems.setItems(observableWorkItems);
				displayWorkItemsTable();
			});
		} else {
			tblWorkItems.getItems().clear();
		}
	}

	/**
	 * 
	 * 
	 */
	private void displayWorkItemsTable() {
		colPartDescription.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getParts().getDescription()));
		//
		colQuantity.setCellValueFactory(
				cellData -> new SimpleFloatProperty(cellData.getValue().getParts().getQuantity()).asObject());
		colQuantity.setStyle("-fx-alignment: CENTER;");
		//
		colItemPrice.setCellValueFactory(
				cellData -> new SimpleFloatProperty(cellData.getValue().getParts().getPrice()).asObject());
		colItemPrice.setStyle("-fx-alignment: CENTER;");
		//
		colLabourHrs
				.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getDuration()).asObject());
		colLabourHrs.setStyle("-fx-alignment: CENTER;");
		//
		colWorkDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWorkDesc()));
		//
		colWorkStatus.setCellValueFactory(cellData -> new SimpleBooleanProperty(
				cellData.getValue().getWorkStatus().getStatus().equals("COMPLETED")));
		// colQuantity
		// .setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Work,
		// Float>, ObservableValue<Float>>() {
		// @SuppressWarnings("unchecked")
		// @Override
		// public ObservableValue<Float> call(final CellDataFeatures<Work,
		// Float> param) {
		// return (ObservableValue<Float>) param.getValue();
		// }
		// });
		// colQuantity.setCellValueFactory(
		// cellData -> new
		// SimpleFloatProperty(cellData.getValue().getParts().getQuantity()).asObject());
		// colPartNumber.setCellValueFactory(
		// cellData -> new
		// SimpleStringProperty(cellData.getValue().getParts().getInventory().getPartNumber()));
		//
		// colLabourHrs
		// .setCellValueFactory(cellData -> new
		// SimpleFloatProperty(cellData.getValue().getDuration()).asObject());
		// colLabourHrs.setCellValueFactory(new Callback<CellDataFeatures<Work,
		// String>, ObservableValue<String>>() {
		// public ObservableValue<String> call(CellDataFeatures<Work, String>
		// work) {
		// Float costs = work.getValue().getDuration();
		// return new SimpleStringProperty(String.format("%.2f",
		// costs.floatValue()));
		// }
		// });
		// colLabourHrs.setCellValueFactory(new Callback<CellDataFeatures<Work,
		// Float>, ObservableValue<Float>>() {
		// public ObservableValue<Float> call(CellDataFeatures<Work, Float> wk)
		// {
		// // p.getValue() returns the Person instance for a particular
		// TableView row
		// Float hrs = wk.getValue().getDuration();
		// return ObservableValue<Float>(hrs.floatValue());// new
		// SimpleFloatProperty(/*String.format("%.2f",*/
		// hrs.toString()/*.floatValue()*/));
		// }
		// });

		// colLabourHrs.setCellValueFactory(new
		// Callback<TableColumn.CellDataFeatures<Work, Float>,
		// ObservableValue<Float>>() {
		//
		// @Override
		// public ObservableValue<Float> call(final CellDataFeatures<Work,
		// Float> param) {
		// return /*new
		// SimpleFloatProperty(*/param.getValue().getDuration().floatValue();
		// }
		// });

		//

		// colPartDescription.setCellFactory(new Callback<TableColumn<Work,
		// String>, TableCell<Work, String>>() {
		// @Override
		// public TableCell<Work, String> call(TableColumn<Work, String> param)
		// {
		// final TableCell<Work, String> cell = new TableCell<Work, String>() {
		// private Text text;
		//
		// @Override
		// public void updateItem(String item, boolean empty) {
		// super.updateItem(item, empty);
		// if (!isEmpty() && item != null) {
		// text = new Text(item.toString());
		// text.setWrappingWidth(colPartDescription.getWidth() * 1.5);
		// setGraphic(text);
		// }
		// }
		// };
		// return cell;
		// }
		// });

		//
		// colPartNumber.setCellValueFactory(
		// cellData -> new
		// SimpleFloatProperty(cellData.getValue().getPartsCost()).asObject());

		//

		// colWorkCost.setStyle("-fx-alignment: CENTER-RIGHT;");

		// colPartDescription.setCellValueFactory(
		// cellData -> new
		// SimpleStringProperty(cellData.getValue().getParts().getDescription()));
		//

	}

	/**
	 * 
	 */
	private void displayServiceOrderTable() {
		colSOStartDate
				.setCellValueFactory(new Callback<CellDataFeatures<ServiceOrder, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<ServiceOrder, String> serv) {
						final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH.mm");
						Date date = serv.getValue().getSDate();
						return new SimpleStringProperty(sdf.format(date));
					}
				});
		//
		colSOPlate
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVehicle().getPlate()));
		colSOModel.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getVehicle().getModel().getName()));
		//
		colSOMake.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getVehicle().getModel().getMake().getShortName()));
	}

	/**
	 * 
	 * @param vehicle
	 * @return
	 */
	private ServiceOrder createServiceOrder(Vehicle vehicle) {
		ServiceOrder sorder = new ServiceOrder();
		String s = "first service visit start";
		sorder.setVehicle(vehicle);

		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		sorder.setSDate(now);

		sorder.setDescription(s.toUpperCase());
		sorder.setComment(s.toUpperCase());
		sorder.setLabourCost(new Float(0.0));
		sorder.setPartCost(new Float(0.0));

		sorder.setOdometer(getOdometer(vehicle));
		SStatus sstatus = sstatusService.findByStatus("PENDING");
		sorder.setSStatus(sstatus);

		String jobId = serviceOrderService.findNextJobID();
		sorder.setJobID(jobId);
		return serviceOrderService.create(sorder);
	}

	/**
	 * 
	 * @param vehicle
	 * @return
	 */
	private Long getOdometer(Vehicle vehicle) {
		long lOdometer = 11102l;
		try {
			lOdometer = Long.parseLong(txtOdometerReading.getText());
		} catch (NumberFormatException e) {
		}
		List<ServiceOrder> sorders = serviceOrderService.findByVehicle(vehicle);
		Long odmeter = sorders.stream().mapToLong(ServiceOrder::getOdometer).max().orElse(lOdometer);
		//
		return odmeter;
	}

	@FXML
	protected void handleClearAllSearchAction(ActionEvent event) {
		txtSearchName.clear();
		txtSearchPhone.clear();
		txtSearchPlate.clear();
		txtSearchVIN.clear();
		tblVehicle.getItems().clear();
	}

	/**
	 * 
	 * @param existOwner
	 */
	private void updateClient(Owner existOwner) {
		if (hasChangedValue(txtEmail, clientTxtFieldMap))
			existOwner.setEmail(txtEmail.getText());
		if (hasChangedValue(txtPhoneOther, clientTxtFieldMap))
			existOwner.setPhoneOther(txtPhoneOther.getText());
		if (hasChangedValue(txtPhone, clientTxtFieldMap))
			existOwner.setPhone(txtPhone.getText());

		Address ownerAddress = existOwner.getAddress();
		if (hasChangedValue(txtAddress, clientTxtFieldMap))
			ownerAddress.setStreet(txtAddress.getText());
		if (hasChangedValue(txtAddressOther, clientTxtFieldMap))
			ownerAddress.setOther(txtAddressOther.getText());
		if (hasChangedValue(txtCity, clientTxtFieldMap))
			ownerAddress.setCity(txtCity.getText());
		if (hasChangedValue(txtMCode, clientTxtFieldMap))
			ownerAddress.setMailcode(txtMCode.getText());
		//
		if (hasChangedValue(txtRegion, clientTxtFieldMap))
			ownerAddress.setRegion(getRegion());
		addressService.update(ownerAddress);
		ownerService.update(existOwner);
	}

	/**
	 * 
	 * @param platedVehicle
	 */
	private void performSpecialPlateReAssignment(Vehicle platedVehicle) {
		StringBuilder sb = new StringBuilder();
		sb.append(XMLUtils.DEF_PLATEPREFIX);
		sb.append(Instant.now().get(ChronoField.MILLI_OF_SECOND));
		//
		platedVehicle.setPlate(sb.toString());
		Owner prevOwner = platedVehicle.getOwner();
		Owner ctrOwner = ownerService.getDefaultOwner();
		prevOwner.getVehicleList().remove(platedVehicle);
		platedVehicle.setOwner(ctrOwner);
		ctrOwner.addVehicle(platedVehicle);

		vehicleService.update(platedVehicle);
		ownerService.update(ctrOwner);
		ownerService.update(prevOwner);
		//
		if (tblServiceVehicle.getItems().contains(platedVehicle)) {
			int index = tblServiceVehicle.getItems().indexOf(platedVehicle);
			ServiceOrder sord = tblServiceVehicle.getItems().get(index);

			SStatus inactiveStatus = sstatusService.findByStatus("INACTIVE");
			sord.setSStatus(inactiveStatus);
			if ((serviceOrderService.update(sord) != null) && (tblServiceVehicle.getItems().remove(platedVehicle)))
				tblServiceVehicle.refresh();
		}
	}

	/**
	 * 
	 * @return
	 */
	private Model getModel() {
		Make make = makeService.findByShortName(txtMake.getText());
		Model model = modelService.findByNameYearMake(txtModel.getText(), make, txtTrimDescription.getText(),
				txtEngineDescription.getText());
		//
		if (model == null) {
			Model tmpModel = new Model();
			tmpModel.setName(txtModel.getText());
			tmpModel.setYear(txtYear.getText());
			//
			tmpModel.setEngineDescription(txtEngineDescription.getText());
			tmpModel.setTrimDescription(txtTrimDescription.getText());
			tmpModel.setMake(make);
			model = modelService.create(tmpModel);
		}
		return model;
	}

	/**
	 * 
	 * @return
	 */
	private boolean isNewClient() {
		return boolNewClient;
	}

	/**
	 * 
	 * @return
	 */
	private boolean isExistingClient() {
		return boolExistingClient;
	}

	/**
	 * 
	 * @param b
	 */
	private void setNewClient(boolean b) {
		boolNewClient = b;
		boolExistingClient = !b;
	}

	/**
	 * 
	 * @return
	 */
	private boolean isNewVehicle() {
		return boolNewVehicle;
	}

	/**
	 * 
	 * @return
	 */
	private boolean isExistingVehicle() {
		return boolExistingVehicle;
	}

	/**
	 * 
	 * @param b
	 */
	private void setNewVehicle(boolean b) {
		boolNewVehicle = b;
		boolExistingVehicle = !b;
	}

	/**
	 * 
	 * @param tfield
	 * @param map
	 * @return
	 */
	private boolean hasChangedValue(TextField tfield, Map<TextField, String> map) {
		if (map == null)
			return false;
		String mValue = null;
		if (map.containsKey(tfield)) {
			mValue = map.get(tfield);
			return !tfield.getText().equalsIgnoreCase(mValue);
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return
	 */
	private boolean validateUpdatedVehicle() {
		boolean b = !isNewVehicle() && validateVehicleEntry();
		for (TextField txt : vehicleTextFields) {
			b = b || hasChangedValue(txt, vehicleTxtFieldMap);
		}
		return b;
	}

	/**
	 * 
	 * @return
	 */
	private boolean validateUpdatedClient() {
		boolean b = !isNewClient() && validateClientEntry();
		for (TextField txt : clientFormTextFields) {
			b = b || hasChangedValue(txt, clientTxtFieldMap);
		}
		return b;
	}

	/**
	 * 
	 * @param event
	 */
	@FXML
	protected void handleClearVehicleInfoAction(ActionEvent event) {
		clearVehicleInfo();
	}

	/**
	 * 
	 */
	private void clearClientForm(TextField... txt) {
		List<TextField> list = new ArrayList<TextField>();
		if (txt != null) {
			for (int n = 0; n < txt.length; n++)
				list.add(txt[n]);
		}
		//
		for (TextField tf : clientFormTextFields) {
			if (!list.contains(tf)) {
				tf.clear();
			}
		}
		clearVehicleRecord(null);
	}

	/**
	 * 
	 * @param txt
	 */
	private void clearVehicleInfo(TextField... txt) {
		List<TextField> list = new ArrayList<TextField>();
		if (txt != null) {
			for (int n = 0; n < txt.length; n++)
				list.add(txt[n]);
		}
		///
		for (TextField tf : vehicleTextFields) {
			if (!list.contains(tf)) {
				tf.clear();
			}
		}
	}

	@FXML
	protected void handleProcessClientAction(ActionEvent event) {
		if (!validateClientEntry() || !validateVehicleEntry())
			return;
		//
		if (isExistingClient() && (validateUpdatedClient() || validateUpdatedVehicle())) {
			//
			setVehicleClientButtons(true);
			Platform.runLater(() -> {
				updateClientVehicleRecords();
				setVehicleClientButtons(false);
			});
			return;
		}
		if (isNewClient()) {
			System.out.println(" 2972   ");
			//
			setVehicleClientButtons(true);
			Platform.runLater(() -> {
				persistNewClientVehicleRecord();
				setVehicleClientButtons(false);
			});
			return;
		} // setVehicleClientBtn(false);
		else {
			System.out.println("Un trapped condition 2972   8 ");
			return;
		}
	}

	/**
	 * 
	 * @param bool
	 */
	private void setVehicleClientButtons(boolean bool) {
		btnClearClientInfo.disableProperty() //
				.bind(BooleanExpression.booleanExpression(new SimpleBooleanProperty(bool)));
		btnClearVehicleInfo.disableProperty() //
				.bind(BooleanExpression.booleanExpression(new SimpleBooleanProperty(bool)));
		btnCreateServiceOrder.disableProperty() //
				.bind(BooleanExpression.booleanExpression(new SimpleBooleanProperty(bool)));
		btnProcessClient.disableProperty() //
				.bind(BooleanExpression.booleanExpression(new SimpleBooleanProperty(bool)));
	}

	/**
	 * 
	 */
	private void updateClientVehicleRecords() {
		String fname = txtFName.getText(), lname = txtLName.getText(), phone = txtPhone.getText();
		Owner existingOwner = ownerService.findByNamesAndPhone(fname, lname, phone);
		Vehicle vehByVIN = vehicleService.findByVIN(txtVIN.getText());
		Vehicle vehByPlate = vehicleService.findByPlate(txtPlate.getText());

		updateClient(existingOwner);
		if (vehByVIN == null) {
			if (hasExistingPlate()) {
				Vehicle platedVehicle = vehicleService.findByPlate(txtPlate.getText());
				if (platedVehicle.getOwner().equals(existingOwner)) {
					performSpecialPlateReAssignment(platedVehicle);

					Vehicle newVehicle = createVehicleObj(existingOwner);
					ownerService.update(existingOwner);
					if (newVehicle != null) {
						addToServiceableVehicleTable(newVehicle);
					}
				} else if (existingOwner == null) {
					FXmlUtils.showInfoAlert(btnProcessClient.getScene().getWindow(),
							" Plate No. provided, is not assigned to Owner");
					return;
				}
			} else {

				Vehicle tmpVehicle = createVehicleObj(existingOwner);
				if (tmpVehicle != null) {
					addToServiceableVehicleTable(tmpVehicle);
				}
			}
			clearClientForm();
			clearVehicleInfo();
		} else /* if (isExistingVehicle()) */ {
			String message = null;
			Alert alert = null;

			Owner otherOwner = vehByVIN.getOwner();
			if (vehByPlate == null) {
				// message = "Confirm to proceed with vehicle record changes.";
				// alert =
				// FXmlUtils.showConfirmAlert(btnProcessClient.getScene().getWindow(),
				// message.toUpperCase());

				// if (alert.getResult() == ButtonType.YES) {
				boolean bool = otherOwner.getVehicleList().remove(vehByVIN);
				vehByVIN.setPlate(txtPlate.getText());
				vehByVIN.setOwner(existingOwner);

				if (bool && vehicleService.update(vehByVIN) != null && ownerService.update(otherOwner) != null) {
					existingOwner.addVehicle(vehByVIN);
				}
				if (ownerService.update(existingOwner) != null) {
					clearOwnerVehicleInfo();// clearClientForm();
					addToServiceableVehicleTable(vehByVIN);
				} else {
					FXmlUtils.showErrorAlert(btnProcessClient.getScene().getWindow(),
							"Vehicle Transfer did not complete" + existingOwner.getFirstname()
									+ " has ownership of Vehicle: " + vehByVIN.getVIN());
					return;
				} // }
			} else if (vehByPlate != null) {
				if (vehByPlate.getOwner().equals(existingOwner)) {
					message = "Confirm to proceed with vehicle ownership transfer, \nusing existing Plate.";
					alert = FXmlUtils.showConfirmAlert(btnProcessClient.getScene().getWindow(), message);

					if (alert.getResult() == ButtonType.YES) {
						boolean bool = otherOwner.getVehicleList().remove(vehByVIN);
						vehByVIN.setPlate(vehByPlate.getPlate());
						vehByVIN.setOwner(existingOwner);
						performSpecialPlateReAssignment(vehByPlate);
						if (bool && vehicleService.update(vehByVIN) != null
								&& ownerService.update(otherOwner) != null) {
							existingOwner.addVehicle(vehByVIN);
						}
						if (ownerService.update(existingOwner) != null) {
							FXmlUtils.showInfoAlert(btnProcessClient.getScene().getWindow(), "Transfer completed.");
							addToServiceableVehicleTable(vehByVIN);
						} else {
							FXmlUtils.showErrorAlert(btnProcessClient.getScene().getWindow(),
									"Vehicle Transfer did not complete");
							return;
						}
					}
				} /*
					 * else if (vehByPlate.getOwner().equals(existingOwner) &&
					 * !vehByVIN.getOwner().equals(existingOwner)) { message =
					 * "Confirm to proceed with vehicle ownership transfer, \nusing existing Plate."
					 * ; alert =
					 * FXmlUtils.showConfirmAlert(btnProcessClient.getScene().
					 * getWindow(), message);
					 * 
					 * if (alert.getResult() == ButtonType.YES) { boolean bool =
					 * otherOwner.getVehicleList().remove(vehByVIN);
					 * vehByVIN.setPlate(vehByPlate.getPlate());
					 * vehByVIN.setOwner(existingOwner);
					 * performSpecialPlateReAssignment(vehByPlate); if (bool &&
					 * vehicleService.update(vehByVIN) != null &&
					 * ownerService.update(otherOwner) != null) {
					 * existingOwner.addVehicle(vehByVIN); } } }
					 */
			}
		}
		boolNewVehicle = false;
		boolExistingVehicle = false;
	}

	/**
	 * 
	 */
	private void persistNewClientVehicleRecord() {
		Owner newOwner = createOwnerObj();
		if (newOwner != null) {
			if (isNewVehicle()) {
				Vehicle vehicle = createVehicleObj(newOwner);
				if (vehicle != null) {
					addToServiceableVehicleTable(vehicle);
				}
			} else if (isExistingVehicle()) {
				Vehicle vehicleByVIN = vehicleService.findByVIN(txtVIN.getText());
				//
				if (vehicleService.findByPlate(txtPlate.getText()) != null) {
					FXmlUtils.showErrorAlert(btnProcessClient.getScene().getWindow(),
							" Plate No. exists in DB.  \n\tPlease validate Plate Number.");
					return;
				} else {
					FXmlUtils.showConfirmAlert(btnProcessClient.getScene().getWindow(),
							"Validated for vehicle transfer.\n\tOK to proceed?");
					Owner existingVehicleOwner = vehicleByVIN.getOwner();

					if (existingVehicleOwner.getVehicleList().remove(vehicleByVIN)) {
						vehicleByVIN.setPlate(txtPlate.getText());
						vehicleByVIN.setOwner(newOwner);
						newOwner.addVehicle(vehicleByVIN);

						ownerService.update(newOwner);
						ownerService.update(existingVehicleOwner);
						vehicleService.update(vehicleByVIN);

						addToServiceableVehicleTable(vehicleByVIN);
					}
					return;
				}
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	private Owner createOwnerObj() {
		Owner owner = new Owner();
		owner.setFirstname(txtFName.getText().trim());
		owner.setLastname(txtLName.getText().trim());
		owner.setPhone(txtPhone.getText());
		String phoneNr = txtPhoneOther.getText();
		owner.setPhoneOther((phoneNr != null) ? phoneNr : "");
		//
		String email = txtEmail.getText();
		owner.setEmail((email != null) ? email : "");

		Address address = createAddressObj();
		owner.setAddress(address);
		address.addVehicleOwner(owner);
		Owner storedOwner = ownerService.create(owner);
		addressService.update(address);
		return storedOwner;
	}

	/**
	 * 
	 * @return
	 */
	private Address createAddressObj() {
		String other = null;
		Address address = new Address();
		address.setStreet(txtAddress.getText());
		other = txtAddressOther.getText();
		address.setOther((other != null) ? other : "");
		address.setCity(txtCity.getText().trim());
		address.setMailcode(txtMCode.getText());
		//
		Region region = regionService.findByName(txtRegion.getText());
		address.setRegion(region);

		Address storedAddress = addressService.create(address);
		region.addAddress(address);
		regionService.update(region);
		return storedAddress;
	}

	/**
	 * 
	 * @param owner
	 * @return
	 */
	private Vehicle createVehicleObj(Owner owner) {
		Model model = getModel();
		Vehicle vehicle = new Vehicle();
		//
		vehicle.setModel(model);
		vehicle.setVIN(txtVIN.getText().trim());
		vehicle.setPlate(txtPlate.getText().trim());
		vehicle.setVColor(txtColour.getText());
		vehicle.setOwner(owner);
		return vehicleService.create(vehicle);
	}

	/**
	 * 
	 * @return
	 */
	private boolean validateClientEntry() {
		boolean isValid = FXmlUtils.isValid(txtFName) && FXmlUtils.isValid(txtLName) && FXmlUtils.isValid(txtAddress)
				&& isValidAddressRegion() && FXmlUtils.isValid(txtCity) && FXmlUtils.isValid(txtMCode)
				&& FXmlUtils.isValid(txtPhone);
		return isValid;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	private boolean isValidAddressRegion() {
		boolean boolValidRegion = false;
		if (FXmlUtils.isValid(txtRegion)) {
			Region reg = regionService.findByName(txtRegion.getText());
			boolValidRegion = (reg != null);
		}
		return boolValidRegion;
	}

	/**
	 * 
	 * @return
	 */
	private boolean validateVehicleEntry() {
		boolean isValid = FXmlUtils.isValid(txtVIN) && FXmlUtils.isValid(txtPlate) && FXmlUtils.isValid(txtMake)
				&& FXmlUtils.isValid(txtModel) && FXmlUtils.isValid(txtColour) && FXmlUtils.isValid(txtYear)
				/* && FXmlUtils.isValid(txtOdometerReading) */ && validatedTrimDesc() && validatedEngineDesc();
		return isValid;
	}

	/**
	 * 
	 * @return
	 */
	private boolean validatedEngineDesc() {
		return FXmlUtils.isValid(txtEngineBuild) && FXmlUtils.isValid(txtEngineFuel)
				&& FXmlUtils.isValid(txtEngineCapacity);
	}

	/**
	 * 
	 * @return
	 */
	private boolean validatedTrimDesc() {
		return FXmlUtils.isValid(txtTrimLevel) && FXmlUtils.isValid(txtTrimDrive) && FXmlUtils.isValid(txtTrimDoors);
	}

	/**
	 * 
	 * @return
	 */
	private List<String> getColours() {
		List<Colours> colourList = (List<Colours>) colourService.findAll();
		List<String> list = colourList.stream().map(Colours::getColour).distinct().collect(Collectors.toList());
		Collections.sort(list);
		return list;
	}

	/**
	 * 
	 * @return
	 */
	private List<String> getFuels() {
		List<Fuels> fuelList = (List<Fuels>) fuelService.findAll();
		List<String> flist = fuelList.stream().map(Fuels::getFuel).distinct().distinct().collect(Collectors.toList());
		Collections.sort(flist);
		return flist;
	}

	/**
	 * 
	 * @return
	 */
	private List<String> getVehicleMakes() {
		List<Make> makeList = (List<Make>) makeService.findAll();
		// Collections.sort(makeList);
		List<String> mlist = makeList.stream().filter(m -> !m.getModelList().isEmpty()).map(Make::getShortName)
				.distinct().collect(Collectors.toList());
		Collections.sort(mlist);
		return mlist;
	}

	/**
	 * 
	 * @return
	 */
	private List<String> getEngineBuilds() {
		List<EConfig> buildsList = (List<EConfig>) buildService.findAll();
		List<String> list = buildsList.stream().map(EConfig::getConfig).distinct().collect(Collectors.toList());
		Collections.sort(list);
		return list;
	}

	/**
	 * 
	 * @return
	 */
	private List<String> getModelStrings() {
		String strMake = txtMake.getText();
		Make make = makeService.findByShortName(strMake);
		List<Models> mnames = (List<Models>) modelsService.findByMakeName(make.getShortName());
		//
		List<String> list = mnames.stream().filter(y -> y.getRef().equals(make.getShortName())).map(Models::getName)
				.distinct().collect(Collectors.toList());
		Collections.sort(list);
		return list;
	}

	/**
	 * 
	 * @return
	 */
	private List<String> getDrives() {
		List<DriveType> drives = (List<DriveType>) driveTypeService.findAll();
		List<String> list = drives.stream().map(DriveType::getDType).distinct().collect(Collectors.toList());
		Collections.sort(list);
		return list;
	}

	/**
	 * 
	 * @param reg
	 * @return
	 */
	private Set<String> getCities(String reg) {
		if (reg == null)
			return new TreeSet<String>();
		Region region = regionService.findByName(reg);
		List<Address> addresses = addressService.findAddressByRegion(region);
		Set<String> cities = addresses.stream().map(Address::getCity).distinct().collect(Collectors.toSet());
		//
		return cities;
	}

}

// private Work createServiceOrderWork() {
// Float rate = getLabourRate();
// String sWorkDesc = taPartDescription.getText();
// String sPartDesc = txtSOPartDescription.getText();
//
// float fLabourHrs = Float.parseFloat(txtSOLabourHrs.getText());
// float fPartPrice = Float.parseFloat(txtSOPartPrice.getText());
// float fPartQty = Float.parseFloat(txtSOPartQty.getText());
//
// Parts parts = new Parts();
// parts.setPrice(fPartPrice);
// parts.setQuantity(fPartQty);
// Inventory invPart = null;
//
// if (FXmlUtils.isValid(txtSOPartNr)) {
// invPart = getInventoryItem(txtSOPartNr.getText());
// parts.setInventory(invPart);
// if (invPart.getPartNumber().equals("GEN99999")) {
// parts.setDescription(sPartDesc);
// parts.setPrice(fPartPrice);
// } else {
// parts.setDescription(invPart.getDescription());
// parts.setPrice(invPart.getCostPrice() * INV_MARKUP);
// }
// } else {
// invPart = getInventoryItem("GEN99999");
// parts.setInventory(invPart);
// parts.setDescription(sPartDesc);
// parts.setPrice(fPartPrice);
// }
// partsService.create(parts);
//
// Work work = new Work();
// work.setDuration(fLabourHrs);
// work.setWorkDesc(sWorkDesc);
// float fvalue = new Float(rate * fLabourHrs);
// work.setWorkCost(fvalue);
//
// ServiceOrder serviceOrder =
// tblServiceVehicle.getSelectionModel().getSelectedItem();
// work.setServiceOrder(serviceOrder);
// work.setParts(parts);
// parts.setWork(work);
//
// Work tmpWork = workService.create(work);
// serviceOrder.addWork(tmpWork);
// // serviceOrderService.update(serviceOrder);
// for (Work w : serviceOrder.getWorkList())
// System.out.println(" 1641\t" + w.toString());
// tblServiceVehicle.refresh();
// return tmpWork;
// }

/**
 * 
 * @param event
 */
// @FXML
// protected void handleUpdateClientInfoAction(ActionEvent event) {
// if (isNewClient())
// return;
// if (validateClientEntry() && validateVehicleEntry() &&
// (validateUpdatedClient() || validateUpdatedVehicle())) {
// if (isExistingClient()) {
// String fname = txtFName.getText(), lname = txtLName.getText(), phone =
// txtPhone.getText();
// Owner existingOwner = ownerService.findByNamesAndPhone(fname, lname,
// phone);
// updateClient(existingOwner);
// if (isNewVehicle()) {
// if (hasExistingPlate()) {
// Vehicle platedVehicle = vehicleService.findByPlate(txtPlate.getText());
// if (platedVehicle.getOwner().equals(existingOwner)) {
// performSpecialPlateReAssignment(platedVehicle);
//
// Vehicle newVehicle = createVehicleObj(existingOwner);
// ownerService.update(existingOwner);
// if (newVehicle != null) {
// addToServiceableVehicleTable(newVehicle);
// }
// } else {
// FXmlUtils.showInfoAlert(btnSaveClientInfo.getScene().getWindow(),
// " Plate No. provided, is not assigned to Owner");
// return;
// }
// System.out.println("\thasExistingPlate()() 1922 " + hasExistingPlate());
// } else {
// System.out.println("\t NOT hasExistingPlate()() 1924 " +
// !hasExistingPlate());
// // existing
// // owner
// // ==>
// // new
// // vehicle
// // ok
// // if (hasChangedValue(txtEmail, clientTxtFieldMap))
// // existingOwner.setEmail(txtEmail.getText());
// // if (hasChangedValue(txtPhoneOther,
// // clientTxtFieldMap))
// // existingOwner.setPhoneOther(txtPhoneOther.getText());
// // if (hasChangedValue(txtPhone, clientTxtFieldMap))
// // existingOwner.setPhone(txtPhone.getText());
// //
// // Address ownerAddress = existingOwner.getAddress();
// // if (hasChangedValue(txtAddress, clientTxtFieldMap))
// // ownerAddress.setStreet(txtAddress.getText());
// // if (hasChangedValue(txtAddressOther,
// // clientTxtFieldMap))
// // ownerAddress.setOther(txtAddressOther.getText());
// // if (hasChangedValue(txtCity, clientTxtFieldMap))
// // ownerAddress.setCity(txtCity.getText());
// // if (hasChangedValue(txtMCode, clientTxtFieldMap))
// // ownerAddress.setMailcode(txtMCode.getText());
// // //
// // if (hasChangedValue(txtRegion, clientTxtFieldMap))
// // ownerAddress.setRegion(getRegion());
// // addressService.update(ownerAddress);
// // ownerService.update(existingOwner);
// // updateClient(existingOwner);
// Vehicle tmpVehicle = createVehicleObj(existingOwner);
// System.out.println(
// "\t NOT hasExistingPlate()() 1956 (tmpVehicle != null) --: " +
// (tmpVehicle != null));
// if (tmpVehicle != null) {
// addToServiceableVehicleTable(tmpVehicle);
// }
// }
// clearClientForm();
// clearVehicleInfo();
// } else if (isExistingVehicle()) {
// String message = null;
// Alert alert = null;
// Vehicle vehByPlate = vehicleService.findByPlate(txtPlate.getText());
// Vehicle vehByVIN = vehicleService.findByVIN(txtVIN.getText());
// Owner otherOwner = vehByVIN.getOwner();
//
// if (vehByPlate == null) {
// // new plates -> stored owner ->
// // stored vehicle w/ different
// // owner in DB
// message = "Confirm to perform Vehicle ownership transfer, with NEW
// Plate.";
// alert =
// FXmlUtils.showConfirmAlert(btnSaveClientInfo.getScene().getWindow(),
// message);
//
// if (alert.getResult() == ButtonType.YES) {
//
// boolean bool = otherOwner.getVehicleList().remove(vehByVIN);
// vehByVIN.setPlate(txtPlate.getText());
// vehByVIN.setOwner(existingOwner);
//
// if (bool && vehicleService.update(vehByVIN) != null
// && ownerService.update(otherOwner) != null) {
// existingOwner.addVehicle(vehByVIN);
// }
// if (ownerService.update(existingOwner) != null) {
// addToServiceableVehicleTable(vehByVIN);
// } else {
// FXmlUtils.showErrorAlert(btnSaveClientInfo.getScene().getWindow(),
// "Vehicle Transfer did not complete" + existingOwner.getFirstname()
// + " has ownership of Vehicle: " + vehByVIN.getVIN());
// return;
// }
// }
// } else if (vehByPlate != null) {
// // old plates -> existing owner -> existing
// // vehicle with different owner
// // Owner platedVehicleOwner = vehByPlate.getOwner();
// if (vehByPlate.getOwner().equals(existingOwner)) {
// message = "Confirm to proceed with vehicle ownership transfer, \nusing
// existing Plate.";
// // message += "\nThis will include a switch.";
// alert =
// FXmlUtils.showConfirmAlert(btnSaveClientInfo.getScene().getWindow(),
// message);
//
// if (alert.getResult() == ButtonType.YES) {
// //
// boolean bool = otherOwner.getVehicleList().remove(vehByVIN);
// vehByVIN.setPlate(vehByPlate.getPlate());
// vehByVIN.setOwner(existingOwner);
// performSpecialPlateReAssignment(vehByPlate);
// if (bool && vehicleService.update(vehByVIN) != null
// && ownerService.update(otherOwner) != null) {
// existingOwner.addVehicle(vehByVIN);
// }
// if (ownerService.update(existingOwner) != null) {
// FXmlUtils.showInfoAlert(btnSaveClientInfo.getScene().getWindow(),
// "Transfer completed.");
// addToServiceableVehicleTable(vehByVIN);
// } else {
// FXmlUtils.showErrorAlert(btnSaveClientInfo.getScene().getWindow(),
// "Vehicle Transfer did not complete");
// return;
// }
// }
// }
// }
// }
// } /*
// * else {
// * FXmlUtils.showErrorAlert(btnUpdateClientInfo.getScene().
// * getWindow(), " Input Error!"); }
// */
// boolNewVehicle = false;
// boolExistingVehicle = false;
// }
// }

// @FXML
// protected void handleSaveClientInfoAction(ActionEvent event) {
// if (validateClientEntry() && validateVehicleEntry()) {
// if (isNewClient()) {
// Owner newOwner = createOwnerObj();
//
// if (isNewVehicle()) {
// if (newOwner != null) {
// Vehicle vehicle = createVehicleObj(newOwner);
// if (vehicle != null) {
// addToServiceableVehicleTable(vehicle);
// }
// }
// } else if (isExistingVehicle()) {
// if (newOwner != null) {
// Vehicle vehicleByVIN = vehicleService.findByVIN(txtVIN.getText());
// //
// if (vehicleService.findByPlate(txtPlate.getText()) != null) {
// FXmlUtils.showErrorAlert(btnProcessClient.getScene().getWindow(),
// " Plate No. exists in DB. \n\tPlease validate Plate Number.");
// return;
// } else {
// FXmlUtils.showConfirmAlert(btnProcessClient.getScene().getWindow(),
// "Validated for vehicle transfer.\n\tOK to proceed?");
// Owner existingVehicleOwner = vehicleByVIN.getOwner();
//
// if (existingVehicleOwner.getVehicleList().remove(vehicleByVIN)) {
// vehicleByVIN.setPlate(txtPlate.getText());
// vehicleByVIN.setOwner(newOwner);
// newOwner.addVehicle(vehicleByVIN);
//
// ownerService.update(newOwner);
// ownerService.update(existingVehicleOwner);
// vehicleService.update(vehicleByVIN);
//
// addToServiceableVehicleTable(vehicleByVIN);
// }
// return;
// }
// }
// }
// }
// }
// }
