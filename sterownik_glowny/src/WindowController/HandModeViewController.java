package WindowController;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import Communication.EventManager;
import Data_Structures.ChartSetting;
import Data_Structures.Controller_Comfort;
import Data_Structures.Controller_Fireplace;
import Data_Structures.Controller_Heating;
import Data_Structures.Controller_Vent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
public class HandModeViewController {
	
	//GLOBAL
    @FXML private Button globalResetButton;

	// TILE START
	//***************************************
    @FXML private Button tileKomfort;
    @FXML private Button tileWentylacja;
    @FXML private Button tileOgrzewanie;
    @FXML private Button tileKominek;
    @FXML private Button tileOgrod;
    @FXML private Button tileLazienki;
    @FXML private Button tilePogoda;
    @FXML private Button tileAlarm;
    @FXML private Button tileWykresy;
    @FXML private Button tileHardware;
    
    @FXML private AnchorPane paneKomfort;
    @FXML private AnchorPane paneWentylacja;
    @FXML private AnchorPane paneOgrzewanie;  
    @FXML private AnchorPane paneKominek;
    @FXML private AnchorPane paneOgrod;
    @FXML private AnchorPane paneLazienki;
    @FXML private AnchorPane panePogoda;
    @FXML private AnchorPane paneAlarm;
    @FXML private AnchorPane paneHardware;
    @FXML private AnchorPane paneWykresy;

    // KOMFORT START
	//***************************************
    @FXML private Label comfLabZone0;
    @FXML private Label comfLabZone1;
    @FXML private Label comfLabZone2;
    @FXML private Label comfLabZone3;
    @FXML private Label comfLabZone4;
    @FXML private Label comfLabZone5;
    @FXML private Label comfLabZone6;

	@FXML private Label comfLabReqTempZ0 = new Label();
	@FXML private Label comfLabReqTempZ1 = new Label();
	@FXML private Label comfLabReqTempZ2 = new Label();
	@FXML private Label comfLabReqTempZ3 = new Label();
	@FXML private Label comfLabReqTempZ4 = new Label();
	@FXML private Label comfLabReqTempZ5 = new Label();
	@FXML private Label comfLabReqTempZ6 = new Label();

	@FXML private Slider comfSliderReqZ0 = new Slider();
	@FXML private Slider comfSliderReqZ1 = new Slider();
	@FXML private Slider comfSliderReqZ2 = new Slider();
	@FXML private Slider comfSliderReqZ3 = new Slider();
	@FXML private Slider comfSliderReqZ4 = new Slider();
	@FXML private Slider comfSliderReqZ5 = new Slider();
	@FXML private Slider comfSliderReqZ6 = new Slider();

	// VENT START
	//***************************************
    @FXML private FlowPane ventFlowPane0 = new FlowPane();
    @FXML private FlowPane ventFlowPane12 = new FlowPane();
   private Label[] ventH01 = new Label[8];
   private Label[] ventH23 = new Label[8];
   private Label[] ventH45 = new Label[8];
   private Label[] ventH67 = new Label[8];
   private Label[] ventH89 = new Label[8];
   private Label[] ventH1011 = new Label[8];
   private Label[] ventH1213 = new Label[8];
   private Label[] ventH1415 = new Label[8];
   private Label[] ventH1617 = new Label[8];
   private Label[] ventH1819 = new Label[8];
   private Label[] ventH2021 = new Label[8];
   private Label[] ventH2223 = new Label[8];
	
	// OGRZEWANIE START
	//***************************************
	@FXML private Button heatBcheap;
	@FXML private Button heatBheating;
	@FXML private Button heatBantilegionellia;
	
	@FXML private Label heatLabReqCO;
	@FXML private Slider heatSliderReqCO;
	@FXML private Label heatLabReqCWU;
	@FXML private Slider heatSliderReqCWU;
	
	@FXML private Label heatLabSource;
	@FXML private Label heatLabBypass;
	@FXML private Label heatLab3way;
 	@FXML private Label heatLabPumpHouse;
	@FXML private Label heatLabPC;
	@FXML private Label heatLabPumpGround;
	@FXML private Label heatLabZ0;
	@FXML private Label heatLabZ1;
	@FXML private Label heatLabZ2;
	@FXML private Label heatLabZ3;
	@FXML private Label heatLabZ4;
	@FXML private Label heatLabZ5;
	@FXML private Label heatLabZ6;
	
	@FXML private Label heatLabTsource;
	@FXML private Label heatLabTReturn;
	@FXML private Label heatLabTFirePlace;
	@FXML private Label heatLabTGround;
	
	@FXML private Label heatLabTBufCOHi;
	@FXML private Label heatLabTBufCOMid;
	@FXML private Label heatLabTBufCOLow;
	@FXML private Label heatLabTBufCWUHi;
	@FXML private Label heatLabTBufCWUMid;
	@FXML private Label heatLabTBufCWULow;
	
	@FXML private Label heatLabTdistributor;
	@FXML private Label heatLabTReturnFloor0;
	@FXML private Label heatLabTReturnFloor1;

	// WYKRESY START
	//***************************************
    @FXML private DatePicker datePickerFrom;
    @FXML private SplitPane splitPane;
    @FXML private AnchorPane[] anchorPane = new AnchorPane[5];
    @FXML private LineChart<Number, Number>[] lineCharts = new LineChart[4];
    @FXML private NumberAxis xAx[] = new NumberAxis[4];
    @FXML private NumberAxis yAx[] = new NumberAxis[4];
    @FXML private Button buttonZarzadzaj;   
    private ChartSetting chartSetting[] = new ChartSetting[4];
    private double mouseScrollBeginPos = 0;
    private Line chartLineMark[] = new Line[4];
    private Label chartLabel[] = new Label[4];
    
	// HARDWARE START
	//***************************************
    @FXML private TableView<Data_Structures.Module> tableViewModules;
    @FXML private TableColumn<Data_Structures.Module, Number> tableColumnTyp;
    @FXML private TableColumn<Data_Structures.Module, Number> tableColumnNumer;
    @FXML private TableColumn<Data_Structures.Module, String> tableColumnNazwa;
    @FXML private TableColumn<Data_Structures.Module, Boolean> tableColumnBlad;
    @FXML private TableColumn<Data_Structures.Module, Number> tableColumnIP1;
    @FXML private TableColumn<Data_Structures.Module, Number> tableColumnIP2;
    @FXML private TableColumn<Data_Structures.Module, Number> tableColumnIP3;
    @FXML private TableColumn<Data_Structures.Module, Number> tableColumnIP4;
    @FXML private TableColumn<Data_Structures.Module, String> tableColumnUDP;
    @FXML private TableColumn<Data_Structures.Module, String> tableColumnDiagnose;
    @FXML private Button  buttonNewHardware;
    
	// static variables
	//***************************************
	private static final Color COLOR_WARNING = Color.YELLOWGREEN;
	private static final Color COLOR_ALARM = Color.RED;
	private static final Color COLOR_ON = Color.GREEN;
	private static final Color COLOR_NORMAL = Color.WHITE;
	private static final Color COLOR_OFF = Color.GREY;
	private static final Color COLOR_WAITING4UPDATE = Color.ORANGE;

	private static final int TIME_OUT_UDP 		= 60000;				// in MS
	private static final int TIME_OUT_DIAGNOSE	= 120000;				// in MS

	// main variables
	//***************************************
	EventManager eventManager;
	Controller_Heating[] controller_heating;
	Controller_Fireplace[] controller_Fireplace;
	Controller_Vent[] controller_Vent;
	Controller_Comfort[] controller_Comfort;
	
	@FXML
	private void initialize() {
	}
	
	public void initialize_Menu() throws SQLException {
		if (eventManager.isController_Heating_Available()) tileOgrzewanie.setDisable(false);
		if (eventManager.isController_Comfort_Available()) tileKomfort.setDisable(false);
		if (eventManager.isController_Vent_Available()) tileWentylacja.setDisable(false);
		if (eventManager.isController_Fireplace_Available()) tileKominek.setDisable(false);
		if (eventManager.isController_Garden_Available()) tileOgrod.setDisable(false);
		if (eventManager.isController_Bath_Available()) tileLazienki.setDisable(false);
		if (eventManager.isController_Weather_Available()) tilePogoda.setDisable(false);
		if (eventManager.isController_Alarm_Available()) tileAlarm.setDisable(false);
		
		//first display refresh
		initialize_LineCharts();
		initialize_Hardware();
		initialize_Vent();
		initialize_Comfort();
		initialize_Heating();
	}
	
	private void initialize_LineCharts() throws SQLException {
		for (int i=0; i<4; i++) {
			xAx[i] = new NumberAxis();
			xAx[i].setForceZeroInRange(false);
			xAx[i].setSide(Side.BOTTOM);
			xAx[i].setAutoRanging(false);
			xAx[i].setLowerBound(0);
			xAx[i].setUpperBound(new Date().getHours()+1);
			
			yAx[i] = new NumberAxis();
			yAx[i].setForceZeroInRange(false);
			yAx[i].setSide(Side.RIGHT);
			
			lineCharts[i] = new LineChart<>(xAx[i], yAx[i]);
			lineCharts[i].setTitle("Wykres "+(i+1));  
			lineCharts[i].setAnimated(false);
			lineCharts[i].setLegendSide(Side.BOTTOM);

			lineCharts[i].setCreateSymbols(false);
			
			//Chart listeners
			lineCharts[i].setOnScroll(event -> ListnerLineChartScroll(event));
			lineCharts[i].setOnMousePressed(event -> ListnerLineChartPressed(event));
			lineCharts[i].setOnMouseDragged(event -> ListnerLineChartDragged(event));
			
			// Objects for charts
			chartSetting[i] = new ChartSetting();
			chartSetting[i].setHandModeViewController(this);
			chartSetting[i].setChartNo(i);

		}
		datePickerFrom.setValue(getCurrentDate().toLocalDate());
		datePickerFrom.valueProperty().addListener((observable, oldValue, newValue) -> {
			try {				
				for (int i=0; i<4; i++) {
					LineChartRefreshSeries(i, chartSetting[i].getTreeViewChart());
					xAx[i].setLowerBound(0);
					xAx[i].setUpperBound(24);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    });
		
		
		//TMP
		TMPtemporarySettings();
   }
    
   private void initialize_Hardware() {			
		//Settings for Columns in TabelView
	    tableColumnTyp.setCellValueFactory(new PropertyValueFactory<>("moduleTyp"));
		tableColumnNumer.setCellValueFactory(new PropertyValueFactory<>("moduleNo"));
		tableColumnNazwa.setCellValueFactory(new PropertyValueFactory<>("moduleName"));
		tableColumnBlad.setCellValueFactory(new PropertyValueFactory<>("error"));
		tableColumnIP1.setCellValueFactory(new PropertyValueFactory<>("IP1"));
		tableColumnIP2.setCellValueFactory(new PropertyValueFactory<>("IP2"));
		tableColumnIP3.setCellValueFactory(new PropertyValueFactory<>("IP3"));
		tableColumnIP4.setCellValueFactory(new PropertyValueFactory<>("IP4"));
		tableColumnUDP.setCellValueFactory(new PropertyValueFactory<>("lastUDP"));
		tableColumnDiagnose.setCellValueFactory(new PropertyValueFactory<>("lastDiagnose"));    	
		
		tableColumnUDP.setCellFactory(new Callback<TableColumn<Data_Structures.Module, String>, TableCell<Data_Structures.Module, String>>() {			
			@Override
			public TableCell<Data_Structures.Module, String> call(TableColumn<Data_Structures.Module, String> param) {
				return new TableCell<Data_Structures.Module, String>() {
			           @Override
			           protected void updateItem(String item, boolean empty) {
			        	   super.updateItem(item, empty);
			                if (item == null || empty) {
			                    setText(null);
			                    return;
			                }
			                Date result = null;
			                DateFormat df = new SimpleDateFormat("yyy.MM.dd HH:mm:ss");
			                try {
			                	result =  df.parse(item);
								// When over 60s module doesn't sent report, change color on ALARM
				                if (getCurrentDate().getTime()-result.getTime() > TIME_OUT_UDP) {
				                	setTextFill(COLOR_ALARM);		                	
				                }
				                else {
				                	setTextFill(COLOR_NORMAL);
				                }

				                setText(item); 
							} catch (ParseException e) {
								//e.printStackTrace();
							};    
			           }
				};
			}
		});
		
		tableColumnDiagnose.setCellFactory(new Callback<TableColumn<Data_Structures.Module, String>, TableCell<Data_Structures.Module, String>>() {			
			@Override
			public TableCell<Data_Structures.Module, String> call(TableColumn<Data_Structures.Module, String> param) {
				return new TableCell<Data_Structures.Module, String>() {
			           @Override
			           protected void updateItem(String item, boolean empty) {
			        	   super.updateItem(item, empty);
			                if (item == null || empty) {
			                    setText(null);
			                    return;
			                }
			                if (item == "") return;

			                Date result = null;
			                DateFormat df = new SimpleDateFormat("yyy.MM.dd HH:mm:ss");
			                try {
			                	result =  df.parse(item);
								
							} catch (ParseException e) {
								e.printStackTrace();
							};    
							// When over 120s module doesn't sent report, change color on ALARM
			                if (getCurrentDate().getTime()-result.getTime() > TIME_OUT_DIAGNOSE)
			                	setTextFill(COLOR_ALARM);
			                else setTextFill(COLOR_NORMAL);
			                setText(item);
			           }
				};
			}
		});
			
	}
    
   private void initialize_Vent() {
	   for (int i=0; i<=7; i++) {
		   ventH01[i] = new Label("   "); 
		   ventH23[i] = new Label("   "); 
		   ventH45[i] = new Label("   "); 
		   ventH67[i] = new Label("   "); 
		   ventH89[i] = new Label("   "); 
		   ventH1011[i] = new Label("   "); 
		   ventH1213[i] = new Label("   "); 
		   ventH1415[i] = new Label("   "); 
		   ventH1617[i] = new Label("   "); 
		   ventH1819[i] = new Label("   "); 
		   ventH2021[i] = new Label("   "); 
		   ventH2223[i] = new Label("   "); 
		   int temp = i;
		   ventH01[i].pressedProperty().addListener(event -> ListnerVentHour(0, 7-temp));
		   ventH23[i].pressedProperty().addListener(event -> ListnerVentHour(1, 7-temp));
		   ventH45[i].pressedProperty().addListener(event -> ListnerVentHour(2, 7-temp));
		   ventH67[i].pressedProperty().addListener(event -> ListnerVentHour(3, 7-temp));
		   ventH89[i].pressedProperty().addListener(event -> ListnerVentHour(4, 7-temp));
		   ventH1011[i].pressedProperty().addListener(event -> ListnerVentHour(5, 7-temp));
		   ventH1213[i].pressedProperty().addListener(event -> ListnerVentHour(6, 7-temp));
		   ventH1415[i].pressedProperty().addListener(event -> ListnerVentHour(7, 7-temp));
		   ventH1617[i].pressedProperty().addListener(event -> ListnerVentHour(8, 7-temp));
		   ventH1819[i].pressedProperty().addListener(event -> ListnerVentHour(9, 7-temp));
		   ventH2021[i].pressedProperty().addListener(event -> ListnerVentHour(10, 7-temp));
		   ventH2223[i].pressedProperty().addListener(event -> ListnerVentHour(11, 7-temp));
		   
	   }  
	   ventH01[0].setText("0  ");  ventH01[4].setText("1  "); 
	   ventH23[0].setText("2  ");  ventH23[4].setText("3  "); 
	   ventH45[0].setText("4  ");  ventH45[4].setText("5  "); 
	   ventH67[0].setText("6  ");  ventH67[4].setText("7  "); 
	   ventH89[0].setText("8  ");  ventH89[4].setText("9  "); 
	   ventH1011[0].setText("10");  ventH1011[4].setText("11"); 
	   ventH1213[0].setText("12");  ventH1213[4].setText("13"); 
	   ventH1415[0].setText("14");  ventH1415[4].setText("15 "); 
	   ventH1617[0].setText("16");  ventH1617[4].setText("17");
	   ventH1819[0].setText("18");  ventH1819[4].setText("19 "); 
	   ventH2021[0].setText("20");  ventH2021[4].setText("21"); 
	   ventH2223[0].setText("22");  ventH2223[4].setText("23 "); 
  
	   ventFlowPane0.getChildren().addAll(ventH01);
	   ventFlowPane0.getChildren().addAll(ventH23);
	   ventFlowPane0.getChildren().addAll(ventH45);
	   ventFlowPane0.getChildren().addAll(ventH67);
	   ventFlowPane0.getChildren().addAll(ventH89);
	   ventFlowPane0.getChildren().addAll(ventH1011);
	   ventFlowPane12.getChildren().addAll(ventH1213);
	   ventFlowPane12.getChildren().addAll(ventH1415);
	   ventFlowPane12.getChildren().addAll(ventH1617);
	   ventFlowPane12.getChildren().addAll(ventH1819);
	   ventFlowPane12.getChildren().addAll(ventH2021);
	   ventFlowPane12.getChildren().addAll(ventH2223);
	}
   
   private void initialize_Comfort() {
	   //TODO
	   comfSliderReqZ0.valueProperty().addListener((observable, oldValue, newValue) -> {
		   controller_Comfort[0].setNVreqZ0((float) newValue.byteValue()/2);
		   comfLabReqTempZ0.setTextFill(COLOR_WAITING4UPDATE);
		   comfLabReqTempZ0.setText(Double.toString(controller_Comfort[0].zone[0].NVreqTemp)+"°C");
		});

	   comfSliderReqZ1.valueProperty().addListener((observable, oldValue, newValue) -> {
		   controller_Comfort[0].setNVreqZ1((float) newValue.byteValue()/2);
		   comfLabReqTempZ1.setTextFill(COLOR_WAITING4UPDATE);
		   comfLabReqTempZ1.setText(Double.toString(controller_Comfort[0].zone[1].NVreqTemp)+"°C");
		});
	   
	   comfSliderReqZ2.valueProperty().addListener((observable, oldValue, newValue) -> {
		   controller_Comfort[0].setNVreqZ2((float) newValue.byteValue()/2);
		   comfLabReqTempZ2.setTextFill(COLOR_WAITING4UPDATE);
		   comfLabReqTempZ2.setText(Double.toString(controller_Comfort[0].zone[2].NVreqTemp)+"°C");
		});
	   
	   comfSliderReqZ3.valueProperty().addListener((observable, oldValue, newValue) -> {
		   controller_Comfort[0].setNVreqZ3((float) newValue.byteValue()/2);
		   comfLabReqTempZ3.setTextFill(COLOR_WAITING4UPDATE);
		   comfLabReqTempZ3.setText(Double.toString(controller_Comfort[0].zone[3].NVreqTemp)+"°C");
		});

	   comfSliderReqZ4.valueProperty().addListener((observable, oldValue, newValue) -> {
		   controller_Comfort[0].setNVreqZ4((float) newValue.byteValue()/2);
		   comfLabReqTempZ4.setTextFill(COLOR_WAITING4UPDATE);
		   comfLabReqTempZ4.setText(Double.toString(controller_Comfort[0].zone[4].NVreqTemp)+"°C");
		});
  
	   comfSliderReqZ5.valueProperty().addListener((observable, oldValue, newValue) -> {
		   controller_Comfort[0].setNVreqZ5((float) newValue.byteValue()/2);
		   comfLabReqTempZ5.setTextFill(COLOR_WAITING4UPDATE);
		   comfLabReqTempZ5.setText(Double.toString(controller_Comfort[0].zone[5].NVreqTemp)+"°C");
		});

	   comfSliderReqZ6.valueProperty().addListener((observable, oldValue, newValue) -> {
		   controller_Comfort[0].setNVreqZ6((float) newValue.byteValue()/2);
		   comfLabReqTempZ6.setTextFill(COLOR_WAITING4UPDATE);
		   comfLabReqTempZ6.setText(Double.toString(controller_Comfort[0].zone[6].NVreqTemp)+"°C");
		});
   }
  
   private void initialize_Heating() {
	   //TODO
		//Set start values
	   heatSliderReqCO.valueProperty().addListener((observable, oldValue, newValue) -> {
			controller_heating[0].setNVreqTempBuforCO((float) newValue.byteValue()/2);
			
			heatLabReqCO.setTextFill(COLOR_WAITING4UPDATE);
			heatLabReqCO.setText(Float.toString(controller_heating[0].NVreqTempBuforCO)+"°C");
	    });
	   
	   heatSliderReqCWU.valueProperty().addListener((observable, oldValue, newValue) -> {
			controller_heating[0].setNVreqTempBuforCWU((float) newValue.byteValue()/2);
			
			heatLabReqCWU.setTextFill(COLOR_WAITING4UPDATE);
			heatLabReqCWU.setText(Float.toString(controller_heating[0].NVreqTempBuforCWU)+"°C");
	    });
   }
   
   //Global 
   //***************************************/
  @FXML
   void globalResetButtonClick(MouseEvent event) {
   	//Global reset for number of fault temperature reading in heating controller
		byte[] buf = new byte[3];
		buf[0] = 1;
		buf[1] = 0;
		buf[2] = (byte)200;
		eventManager.udpController.UDPsend(buf);
   }
    
    // Tile definitions
	//***************************************
   @FXML
    void tileAlarmOnAction(ActionEvent event) {
	   showPaneVisable(EventManager.Alarm);
    }

    @FXML
    void tileHardwareOnAction(ActionEvent event) {
 	   showPaneVisable(EventManager.Hardware);
    }

    @FXML
    void tileKominekOnAction(ActionEvent event) {
 	   showPaneVisable(EventManager.Kominek);
    } 

    @FXML
    void tileLazienkiOnAction(ActionEvent event) {
 	   showPaneVisable(EventManager.Lazienka);
    }

    @FXML
    void tileOgrodOnAction(ActionEvent event) {
 	   showPaneVisable(EventManager.Ogrod);
    }

    @FXML
    void tileOgrzewanieOnAction(ActionEvent event) {
 	   showPaneVisable(EventManager.Ogrzewanie);
    }

    @FXML
    void tilePogodaOnAction(ActionEvent event) {
 	   showPaneVisable(EventManager.Pogoda);
    }

    @FXML
    void tileKomfortOnAction(ActionEvent event) {
 	   showPaneVisable(EventManager.Komfort);
    }

    @FXML
    void tileWentylacjaOnAction(ActionEvent event) {
    	showPaneVisable(EventManager.Wentylacja);
    }

    @FXML
    void tileWykresyOnAction(ActionEvent event) throws SQLException {
    	for (int i=0; i<4; i++)
    		LineChartRefreshSeries(i, chartSetting[i].getTreeViewChart());
    	showPaneVisable(EventManager.Wykresy);
    } 
    
    private void showPaneVisable(String paneName) {
    	paneKomfort.setVisible(paneName == EventManager.Komfort);
    	paneWentylacja.setVisible(paneName == EventManager.Wentylacja);
    	paneOgrzewanie.setVisible(paneName == EventManager.Ogrzewanie);
    	paneKominek.setVisible(paneName == EventManager.Kominek);
    	paneOgrod.setVisible(paneName == EventManager.Ogrod);
    	paneLazienki.setVisible(paneName == EventManager.Lazienka);
    	panePogoda.setVisible(paneName == EventManager.Pogoda);
    	paneAlarm.setVisible(paneName == EventManager.Alarm);
    	paneWykresy.setVisible(paneName == EventManager.Wykresy);
    	paneHardware.setVisible(paneName == EventManager.Hardware);
    	  
    	//refresh GUI 
    	eventManager.GUIInvalid();
    
    }  
   
    // Comfort definitions
	//***************************************
	private void ReqGUIControllerComfort() {
		
		comfLabZone0.setText("[Salon]	: %["+controller_Comfort[0].zone[0].humidity+"]	T["+controller_Comfort[0].zone[0].isTemp+"]");
		comfLabZone1.setText("[Pralnia]	: %["+controller_Comfort[0].zone[1].humidity+"]	T["+controller_Comfort[0].zone[1].isTemp+"]");
		comfLabZone2.setText("[Laz.dol]	: %["+controller_Comfort[0].zone[2].humidity+"]	T["+controller_Comfort[0].zone[2].isTemp+"]");
		comfLabZone3.setText("[Rodzice]	: %["+controller_Comfort[0].zone[3].humidity+"]	T["+controller_Comfort[0].zone[3].isTemp+"]");
		comfLabZone4.setText("[Natalia]	: %["+controller_Comfort[0].zone[4].humidity+"]	T["+controller_Comfort[0].zone[4].isTemp+"]");
		comfLabZone5.setText("[Karolina]	: %["+controller_Comfort[0].zone[5].humidity+"]	T["+controller_Comfort[0].zone[5].isTemp+"]");
		comfLabZone6.setText("[Laz.gora]	: %["+controller_Comfort[0].zone[6].humidity+"]	T["+controller_Comfort[0].zone[6].isTemp+"]");

		if (controller_Comfort[0].isReqZ0UpToDate())
			TextFillColor(comfLabReqTempZ0, true ,(float)controller_Comfort[0].zone[0].reqTemp, (float)controller_Comfort[0].zone[0].reqTemp, (float)1.0, (float)1.0);			
		if (controller_Comfort[0].isReqZ1UpToDate())
			TextFillColor(comfLabReqTempZ1, true ,(float)controller_Comfort[0].zone[1].reqTemp, (float)controller_Comfort[0].zone[1].reqTemp, (float)1.0, (float)1.0);			
		if (controller_Comfort[0].isReqZ2UpToDate())
			TextFillColor(comfLabReqTempZ2, true ,(float)controller_Comfort[0].zone[2].reqTemp, (float)controller_Comfort[0].zone[2].reqTemp, (float)1.0, (float)1.0);			
		if (controller_Comfort[0].isReqZ3UpToDate())
			TextFillColor(comfLabReqTempZ3, true ,(float)controller_Comfort[0].zone[3].reqTemp, (float)controller_Comfort[0].zone[3].reqTemp, (float)1.0, (float)1.0);			
		if (controller_Comfort[0].isReqZ4UpToDate())
			TextFillColor(comfLabReqTempZ4, true ,(float)controller_Comfort[0].zone[4].reqTemp, (float)controller_Comfort[0].zone[4].reqTemp, (float)1.0, (float)1.0);			
		if (controller_Comfort[0].isReqZ5UpToDate())
			TextFillColor(comfLabReqTempZ5, true ,(float)controller_Comfort[0].zone[5].reqTemp, (float)controller_Comfort[0].zone[5].reqTemp, (float)1.0, (float)1.0);			
		if (controller_Comfort[0].isReqZ6UpToDate())
			TextFillColor(comfLabReqTempZ6, true ,(float)controller_Comfort[0].zone[6].reqTemp, (float)controller_Comfort[0].zone[6].reqTemp, (float)1.0, (float)1.0);			


	}
	
    // Ventilation definitions
	//***************************************
    
    public void ListnerVentHour(int hour, int bitPos) { 
    	// Hour <0..12> zone <7-0> 
		int value = controller_Vent[0].hour[hour];
		
		switch (hour) {
			case 0 : controller_Vent[0].setnVhour01(changeBitStatus(value, bitPos)); break;
			case 1 : controller_Vent[0].setnVhour23(changeBitStatus(value, bitPos)); break;
			case 2 : controller_Vent[0].setnVhour45(changeBitStatus(value, bitPos)); break;
			case 3 : controller_Vent[0].setnVhour67(changeBitStatus(value, bitPos)); break;
			case 4 : controller_Vent[0].setnVhour89(changeBitStatus(value, bitPos)); break;
			case 5 : controller_Vent[0].setnVhour1011(changeBitStatus(value, bitPos)); break;
			case 6 : controller_Vent[0].setnVhour1213(changeBitStatus(value, bitPos)); break;
			case 7 : controller_Vent[0].setnVhour1415(changeBitStatus(value, bitPos)); break;
			case 8 : controller_Vent[0].setnVhour1617(changeBitStatus(value, bitPos)); break;
			case 9 : controller_Vent[0].setnVhour1819(changeBitStatus(value, bitPos)); break;
			case 10 : controller_Vent[0].setnVhour2021(changeBitStatus(value, bitPos)); break;
			case 11 : controller_Vent[0].setnVhour2223(changeBitStatus(value, bitPos)); break;
		}
    }

	private void ReqGUIControllerVent() {
		//TMP
		//FunON need to be visualized
//		TextFillColor(controller_Vent[0].fanON,ventButtonF);
		for (int i=0; i<=7; i++) 
		{
			BackgroundColor(ventH01[i],bitStatus(controller_Vent[0].hour[0],7-i),controller_Vent[0].isHour01UpToDate());
			BackgroundColor(ventH23[i],bitStatus(controller_Vent[0].hour[1],7-i),controller_Vent[0].isHour23UpToDate());
			BackgroundColor(ventH45[i],bitStatus(controller_Vent[0].hour[2],7-i),controller_Vent[0].isHour45UpToDate());
			BackgroundColor(ventH67[i],bitStatus(controller_Vent[0].hour[3],7-i),controller_Vent[0].isHour67UpToDate());
			BackgroundColor(ventH89[i],bitStatus(controller_Vent[0].hour[4],7-i),controller_Vent[0].isHour89UpToDate());
			BackgroundColor(ventH1011[i],bitStatus(controller_Vent[0].hour[5],7-i),controller_Vent[0].isHour1011UpToDate());
			BackgroundColor(ventH1213[i],bitStatus(controller_Vent[0].hour[6],7-i),controller_Vent[0].isHour1213UpToDate());
			BackgroundColor(ventH1415[i],bitStatus(controller_Vent[0].hour[7],7-i),controller_Vent[0].isHour1415UpToDate());
			BackgroundColor(ventH1617[i],bitStatus(controller_Vent[0].hour[8],7-i),controller_Vent[0].isHour1617UpToDate());
			BackgroundColor(ventH1819[i],bitStatus(controller_Vent[0].hour[9],7-i),controller_Vent[0].isHour1819UpToDate());
			BackgroundColor(ventH2021[i],bitStatus(controller_Vent[0].hour[10],7-i),controller_Vent[0].isHour2021UpToDate());
			BackgroundColor(ventH2223[i],bitStatus(controller_Vent[0].hour[11],7-i),controller_Vent[0].isHour2223UpToDate());
		}
		
	    java.util.Date today = new java.util.Date();
	    int hour = today.getHours();
		int min = today.getMinutes();

		if (hour == 0) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH01[0]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH01[1]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH01[2]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH01[3]);
		}
		if (hour == 1) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH01[4]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH01[5]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH01[6]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH01[7]);
		}
		if (hour == 2) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH23[0]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH23[1]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH23[2]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH23[3]);
		}
		if (hour == 3) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH23[4]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH23[5]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH23[6]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH23[7]);
		}
		if (hour == 4) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH45[0]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH45[1]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH45[2]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH45[3]);
		}
		if (hour == 5) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH45[4]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH45[5]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH45[6]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH45[7]);
		}
		if (hour == 6) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH67[0]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH67[1]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH67[2]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH67[3]);
		}
		if (hour == 7) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH67[4]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH67[5]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH67[6]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH67[7]);
		}
		if (hour == 8) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH89[0]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH89[1]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH89[2]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH89[3]);
		}
		if (hour == 9) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH89[4]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH89[5]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH89[6]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH89[7]);
		}
		if (hour == 10) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH1011[0]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH1011[1]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH1011[2]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH1011[3]);
		}
		if (hour == 11) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH1011[4]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH1011[5]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH1011[6]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH1011[7]);
		}
		if (hour == 12) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH1213[0]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH1213[1]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH1213[2]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH1213[3]);
		}
		if (hour == 13) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH1213[4]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH1213[5]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH1213[6]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH1213[7]);
		}
		if (hour == 14) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH1415[0]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH1415[1]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH1415[2]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH1415[3]);
		}
		if (hour == 15) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH1415[4]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH1415[5]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH1415[6]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH1415[7]);
		}
		if (hour == 16) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH1617[0]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH1617[1]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH1617[2]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH1617[3]);
		}
		if (hour == 17) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH1617[4]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH1617[5]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH1617[6]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH1617[7]);
		}
		if (hour == 18) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH1819[0]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH1819[1]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH1819[2]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH1819[3]);
		}
		if (hour == 19) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH1819[4]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH1819[5]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH1819[6]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH1819[7]);
		}
		if (hour == 20) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH2021[0]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH2021[1]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH2021[2]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH2021[3]);
		}
		if (hour == 21) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH2021[4]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH2021[5]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH2021[6]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH2021[7]);
		}
		if (hour == 22) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH2223[0]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH2223[1]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH2223[2]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH2223[3]);
		}
		if (hour == 23) {
			if ((min>=0) && (min<15)) BackgroundColorBlink(ventH2223[4]);
			if ((min>=15) && (min<30)) BackgroundColorBlink(ventH2223[5]);
			if ((min>=30) && (min<45)) BackgroundColorBlink(ventH2223[6]);
			if ((min>=45) && (min<60)) BackgroundColorBlink(ventH2223[7]);
		}
	}
		
        
    // Heating definitions
	//***************************************
    @FXML
    void heatBcheapOnClick(MouseEvent event) {
    	controller_heating[0].setNVcheapTariffOnly(!controller_heating[0].cheapTariffOnly);	
    	heatBcheap.setTextFill(COLOR_WAITING4UPDATE);
    }
    
    @FXML
    void heatBheatingOnClick(MouseEvent event) {
    	controller_heating[0].setNVheatingActivated(!controller_heating[0].heatingActivated);	
    	heatBheating.setTextFill(COLOR_WAITING4UPDATE);
   }
    
    @FXML
    void heatBantilegionelliaOnClick(MouseEvent event) {
    	controller_heating[0].setNVantyLegionellia(!controller_heating[0].antyLegionellia);
    	heatBantilegionellia.setTextFill(COLOR_WAITING4UPDATE);
    }

    public void ReqGUIControllerHeating() {

		// after this method no need to be refresh again
		eventManager.reqGUIrefreshHand.setValue(false);

		if (controller_heating[0].isCheapTariffOnlyUpToDate()) 
			if (controller_heating[0].cheapTariffOnly) heatBcheap.setTextFill(COLOR_ON);
			else heatBcheap.setTextFill(COLOR_OFF);

		if (controller_heating[0].isHeatingActivatedOnUpToDate()) 
			if (controller_heating[0].heatingActivated) heatBheating.setTextFill(COLOR_ON);
			else heatBheating.setTextFill(COLOR_OFF);
		
		if (controller_heating[0].isAntyLegionelliaOnUpToDate()) 
			if (controller_heating[0].antyLegionellia) heatBantilegionellia.setTextFill(COLOR_ON);
			else heatBantilegionellia.setTextFill(COLOR_OFF);

			if (controller_heating[0].isReqTempBuforCOUpToDate()) {
			heatSliderReqCO.setValue(controller_heating[0].reqTempBuforCO*2);
			TextFillColor(heatLabReqCO, controller_heating[0].heatingActivated,controller_heating[0].reqTempBuforCO, controller_heating[0].reqTempBuforCO, 1, 1);
		}
		
		if (controller_heating[0].isReqTempBuforCWUUpToDate()) {
			heatSliderReqCWU.setValue(controller_heating[0].reqTempBuforCWU*2);
			TextFillColor(heatLabReqCWU, true,controller_heating[0].reqTempBuforCWU, controller_heating[0].reqTempBuforCWU, 1, 1);			
		}
			
		switch (controller_heating[0].heatSourceActive) {
			case 1 : heatLabSource.setText("Pompa ciep³a"); break;
			case 2 : heatLabSource.setText("Bufor CO"); break;
			case 3 : heatLabSource.setText("Kominek"); break;
		}
		
		double value_bypassInProcent = controller_heating[0].valve_bypass*2.5;
		heatLabBypass.setText("["+Double.toString(value_bypassInProcent)+"%]");

		switch (controller_heating[0].valve_3way) {		
			case 1 : heatLab3way.setText("CO"); break;
			case 2 : heatLab3way.setText("CWU"); break;
			default: heatLab3way.setText("BLAD"); break;
		}
		
		TextFillColor(heatLabPumpHouse, controller_heating[0].pump_InHouse);				
		TextFillColor(heatLabPC, controller_heating[0].reqHeatingPumpOn);
		TextFillColor(heatLabPumpGround, controller_heating[0].pump_UnderGround);				

		TextFillColor(heatLabZ0, controller_heating[0].zone[0]);				
		TextFillColor(heatLabZ1, controller_heating[0].zone[1]);				
		TextFillColor(heatLabZ2, controller_heating[0].zone[2]);				
		TextFillColor(heatLabZ3, controller_heating[0].zone[3]);				
		TextFillColor(heatLabZ4, controller_heating[0].zone[4]);				
		TextFillColor(heatLabZ5, controller_heating[0].zone[5]);				
		TextFillColor(heatLabZ6, controller_heating[0].zone[6]);
		
		//Main temperatures
		TextFillColor(heatLabTsource, (controller_heating[0].reqHeatingPumpOn || controller_heating[0].heatSourceActive==3),controller_heating[0].tZasilanie, 50, 20, 30);
		TextFillColor(heatLabTReturn, controller_heating[0].pump_InHouse, controller_heating[0].tPowrot, controller_heating[0].tZasilanie, 20, 30);

		if (controller_heating[0].tKominek < 50) TextFillColor(heatLabTFirePlace, false,controller_heating[0].tKominek, 0, 0, 0);
		if (controller_heating[0].tKominek > 50) TextFillColor(heatLabTFirePlace, true,controller_heating[0].tKominek, controller_heating[0].tKominek, 5, 10);
		
		TextFillColor(heatLabTGround, false, controller_heating[0].tDolneZrodlo, 0, 0, 0);
		
		TextFillColor(heatLabTBufCOHi, false, controller_heating[0].tBuffCOgora, 0, 0, 0);
		TextFillColor(heatLabTBufCOMid, false, controller_heating[0].tBuffCOsrodek, 0, 0, 0);
		TextFillColor(heatLabTBufCOLow, false, controller_heating[0].tBuffCOdol, 0, 0, 0);

		TextFillColor(heatLabTBufCWUHi, false, controller_heating[0].tBuffCWUgora, 0, 0, 0);
		TextFillColor(heatLabTBufCWUMid, false, controller_heating[0].tBuffCWUsrodek, 0, 0, 0);
		TextFillColor(heatLabTBufCWULow, false, controller_heating[0].tBuffCWUdol, 0, 0, 0);

		TextFillColor(heatLabTdistributor, false, controller_heating[0].tRozdzielacze, 0, 0, 0);
		TextFillColor(heatLabTReturnFloor0, false, controller_heating[0].tPowrotParter, 0, 0, 0);
		TextFillColor(heatLabTReturnFloor1, false, controller_heating[0].tPowrotPietro, 0, 0, 0);
    }
       
	// Charts definitions
	//***************************************
	
	@FXML
    void buttonZarzadzajClick(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {     
		FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(getClass().getResource("/fxml/ChartsManager.fxml"));
	    Pane pane = loader.load();
	    
	    ChartsManagerController chartsManagerController = loader.getController();
	    chartsManagerController.setEventManager(eventManager);
	    chartsManagerController.getModules(eventManager.getModules());
	    chartsManagerController.setHandModeViewController(this);
	    chartsManagerController.setChartsSetting(chartSetting);
	    chartsManagerController.initializeCharts();
	    
	    Scene scene = new Scene(pane);
	    scene.getStylesheets().add("myStyle.css");
	    Stage stage = new Stage();
	    stage.setScene(scene);	        
	    stage.setTitle("Zarz¹dzaj"); 
	    stage.setAlwaysOnTop(true);
	    stage.initStyle(StageStyle.UTILITY);
	    stage.show();
	 }	
	
	//Add new Anchorpane and LineChart to main window
	public void LineChartAddChart(int chartNo) {
		anchorPane[chartNo] = new AnchorPane();
		anchorPane[chartNo].setTopAnchor(lineCharts[chartNo], 0.0);
		anchorPane[chartNo].setBottomAnchor(lineCharts[chartNo], 0.0);
		anchorPane[chartNo].setLeftAnchor(lineCharts[chartNo], 0.0);
		anchorPane[chartNo].setRightAnchor(lineCharts[chartNo], 0.0);	
		anchorPane[chartNo].getChildren().add(lineCharts[chartNo]);

		//Vetrical line through chart
		chartLineMark[chartNo] = new Line(0,0,0,0);
		chartLineMark[chartNo].setStroke(Color.WHITE);
		chartLineMark[chartNo].setStrokeWidth(1);	
		chartLineMark[chartNo].minHeight(0.00);
		anchorPane[chartNo].getChildren().add(chartLineMark[chartNo]);
		
		//Label for series results
		chartLabel[chartNo] = new Label();
		anchorPane[chartNo].getChildren().add(chartLabel[chartNo]);
		
		//Add move listener to chart
		lineCharts[chartNo].setOnMouseMoved(event -> ListnerLineChartMoved(event,chartNo));
		lineCharts[chartNo].setOnMouseExited(event -> ListnerLineChartExited(event,chartNo));

		splitPane.getItems().add(anchorPane[chartNo]); 
		splitPane.setDividerPositions(0.2f);	    	
	}
	
	//Delete Anchorpane if disabled
	public void LineChartRemoveChart(int chartNoToRemove) {		
//		splitPane.getItems().remove(splitPane.getItems().get(chartNoToRemove));
		splitPane.getItems().remove(splitPane.getItems().indexOf(anchorPane[chartNoToRemove]));
		
		anchorPane[chartNoToRemove] = null;
		chartLineMark[chartNoToRemove] = null;
		chartLabel[chartNoToRemove] = null;
	}
	
	public void LineChartAddSeries(int activeChart, String tableName, String columnName, String dataTyp, int controllerNo) throws SQLException {
		//Help variables
		int lastInt = 0;
		float lastFloat =(float)0.0;
		boolean lastBoolean = false;
		
		float BooleanFalseValue = (float) 20.000;
		float BooleanTrueValue = (float) 30.00;
		float lastBooleanValue = BooleanFalseValue;
		

		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName(columnName);
 		
		// Get and prepare date to data base format
		LocalDate localDate = datePickerFrom.getValue();													// Get date from GUI
		Date dateFrom = (Date) Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());		// Convert to DB format
	
		String query = null;
		query = "SELECT "+columnName+",frameLastUpdate FROM "+tableName+" WHERE frameLastUpdate>"+dateFrom.getTime()+" AND frameLastUpdate<"+(dateFrom.getTime()+86400000);
		
		ResultSet resultSet = eventManager.dataBaseController.receiveData(query);
		
		boolean firstTimeIn = true;		
		
		//TMP
		int pointsCount = 0;
		
		while (resultSet.next()) {
			float timeStamp = (float)((resultSet.getLong(2) - dateFrom.getTime())/3600000.000000000);
			if (dataTyp.equals("BOOL")) {
				boolean data = resultSet.getBoolean(1);	
				
				if (firstTimeIn) {
					if (data) series.getData().add(new XYChart.Data((float)0.0000000,BooleanTrueValue));
					else series.getData().add(new XYChart.Data((float)0.0000000,BooleanFalseValue));				
				}				
				if (lastBoolean != data) {
						// Add just before received data to avoid curve in graph
					series.getData().add(new XYChart.Data(timeStamp-(1/3600000.000000000),lastBooleanValue));
					
					if (data) series.getData().add(new XYChart.Data(timeStamp,BooleanTrueValue));
					else series.getData().add(new XYChart.Data(timeStamp,BooleanFalseValue));
					//TMP
					pointsCount++;
					
					if (data) lastBooleanValue = BooleanTrueValue;
					else lastBooleanValue = BooleanFalseValue;
					
					lastBoolean = data;
				}
			}
			if (dataTyp.equals("INTEGER")) {   
				int data = resultSet.getInt(1);

				if (firstTimeIn) series.getData().add(new XYChart.Data((float)0.0000000,data));

				if (lastInt != data) {     
					series.getData().add(new XYChart.Data(timeStamp,data));
					lastInt = data;
				} 
					//TMP
					pointsCount++;
			} 
			if (dataTyp.equals("FLOAT")) {
				float data = (float)resultSet.getFloat(1);

				if (data != lastFloat) {
					if ((data != 0) && (data < 100)) {
						if (firstTimeIn) series.getData().add(new XYChart.Data((float)0.0000000,data));
						series.getData().add(new XYChart.Data(timeStamp,data));
						lastFloat= data;
						
						//TMP
						pointsCount++;

					}
				}
			}
			
			firstTimeIn = false;
		}
		
		//Add at the end of series		
		if (dataTyp.equals("BOOL")) series.getData().add(new XYChart.Data((float)24,lastBooleanValue));
		if (dataTyp.equals("INTEGER")) series.getData().add(new XYChart.Data((float)24,lastInt));
		if (dataTyp.equals("FLOAT")) series.getData().add(new XYChart.Data((float)24,lastFloat));
		
		resultSet.close();
		lineCharts[activeChart].getData().add(series);
		
		//TMP
		System.out.println("Dodano : "+columnName+" Punktow["+pointsCount+"]");
		
		}
	
	public void LineChartRefreshSeries(int chartNumber, TreeView<String> treeViewChart) throws SQLException {
		//clear chart before refresh series
		lineCharts[chartNumber].getData().clear();
		for (TreeItem<String> module : treeViewChart.getRoot().getChildren()) {
			for (TreeItem<String> series : module.getChildren()) {
				
	    		String mod = module.getValue();
	    		String serie = series.getValue();
	    		
	    		int controllerNo = 0;
	    		String tableName = null;
	       		if (mod.indexOf(EventManager.Komfort) != -1) tableName = "Controller_Comfort_Archive";
	       		if (mod.indexOf(EventManager.Wentylacja) != -1) tableName = "Controller_Vent_Archive";
	       	    if (mod.indexOf(EventManager.Ogrzewanie) != -1) tableName = "Controller_Heating_Archive";
	       	    if (mod.indexOf(EventManager.Kominek) != -1) tableName = "Controller_Fireplace_Archive";
	       	    if (mod.indexOf(EventManager.Ogrod) != -1) tableName = "Controller_Garden_Archive";
	       	    if (mod.indexOf(EventManager.Alarm) != -1) tableName = "Controller_Alarm_Archive";
	       	    if (mod.indexOf(EventManager.Pogoda) != -1) tableName = "Controller_Weather_Archive";

	    		// Column name and dataTyp
	    		String[] stringSplit = serie.split("_");
	    		
	    		String columnName = stringSplit[0];
	    		String dataTyp = stringSplit[1];
	    		
	    		LineChartAddSeries(chartNumber,tableName, columnName, dataTyp, controllerNo);
			}
		}		
	}
	
    public void ListnerLineChartScroll (ScrollEvent event) {												// Get date from GUI
		Date dateFrom = (Date) Date.from(datePickerFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());		// Convert to DB format		
		double maxUpperBound = 24;	
    	if (dateFrom.getDate() == getCurrentDate().getDate())	
    		maxUpperBound = (float)((getCurrentDate().getTime() - dateFrom.getTime())/3600000.000000000);
    	
    	double lowerBound = xAx[0].getLowerBound();
    	double upperBound = xAx[0].getUpperBound();
    	
     	double newLowerBound = 0;
    	double newUpperBound = 0;
    	double wspolczynnik = 1.02;
    	
        if (event.getDeltaY()>0) {
           	newLowerBound = lowerBound*wspolczynnik;
           	newUpperBound = upperBound/wspolczynnik;
        }
        if (event.getDeltaY()<0) {
           	newLowerBound = lowerBound/wspolczynnik;
           	newUpperBound = upperBound*wspolczynnik;
        }
        
        if (newLowerBound<0) newLowerBound=0;
        if (newUpperBound>maxUpperBound) newUpperBound=maxUpperBound;
     
        if (newLowerBound<newUpperBound) {	
         	for (int i=0; i<4; i++) {
	         	xAx[i].setLowerBound(newLowerBound);        
	         	xAx[i].setUpperBound(newUpperBound); 
         	}
        }
   }
    
    public void ListnerLineChartPressed(MouseEvent mouseEvent) {
	    	 for (int i=0; i<4; i++) {
	    		  mouseScrollBeginPos = (double) xAx[i].getValueForDisplay(mouseEvent.getX());
	    		  if (!Double.isNaN(mouseScrollBeginPos)) break;
	    	 }
	}
    
	public void ListnerLineChartDragged(MouseEvent mouseEvent) {
		Date dateFrom = (Date) Date.from(datePickerFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());		// Convert to DB format		
		double maxUpperBound = 24;	
    	if (dateFrom.getDate() == getCurrentDate().getDate())	
    		maxUpperBound = (float)((getCurrentDate().getTime() - dateFrom.getTime())/3600000.000000000);
    	
	    	   	double posOnChart = 0.00;
	    	   	for (int i=0; i<4; i++) {
		    	   	posOnChart = (double) xAx[i].getValueForDisplay(mouseEvent.getX());
	    	   		if (!Double.isNaN(mouseScrollBeginPos)) break;
		    	 }
	         	double delta = mouseScrollBeginPos - posOnChart;
	         	
	         	
	         	for (int i=0; i<4; i++) {
		         	if ((xAx[i].getLowerBound()+delta)<0) {
		         		xAx[i].setLowerBound(0);
		         		continue;
		         	}
		         	if ((xAx[i].getUpperBound()+delta)>maxUpperBound) {
		         		xAx[i].setUpperBound(maxUpperBound);
		         		continue;
		         	}

		         	xAx[i].setLowerBound(xAx[i].getLowerBound()+delta);
		         	xAx[i].setUpperBound(xAx[i].getUpperBound()+delta);	
	         	}
	}
   
	public void ListnerLineChartMoved(MouseEvent mouseEvent, int chartNo) {	    	  		
		chartLabel[chartNo].setVisible(true);
	    	  // Line event
	    	  for (int i=0; i<4; i++) {
	    		  // if not exist, skip to new loop
	    		  if (chartLineMark[i] == null) continue;
	    		  
	    		  chartLineMark[i].setStartX(mouseEvent.getX()-5);
		    	  chartLineMark[i].setEndX(mouseEvent.getX()-5);
		    	  chartLineMark[i].setEndY(anchorPane[i].getHeight()-5);
		    	  
	    	  }
	    	  //Show results in line position
	    	  double x= (double) xAx[chartNo].getValueForDisplay(mouseEvent.getX());
	    	  
				int xH = (int)x;
				x = round(x, 2);
				double x1 = (x-xH)*100;
				int xM = (int)(60 * x1 / 100);
	    	  chartLabel[chartNo].setText("Czas["+xH+":"+xM+"]\n");
	    	  
	    	  for (XYChart.Series<Number, Number> series : lineCharts[chartNo].getData()) { 
	    		  float valueXBefore = 0;
	    		  float valueYBefore = 0;
	    		  float valueXAfter = 0;
	    		  float valueYAfter = 0;
	    		  
		          for (Data<Number, Number> entry : series.getData()) {
		        	  if (entry.getXValue().floatValue()>x) {
		        		  valueXAfter = entry.getXValue().floatValue();
		        		  valueYAfter = entry.getYValue().floatValue();
		        		  break;
		        	  }  
		        	  if (entry.getXValue().floatValue()<x) {
		        		  valueXBefore = entry.getXValue().floatValue();
		        		  valueYBefore = entry.getYValue().floatValue(); 
		        	  }
		          }
		          
		          float deltaX = valueXAfter-valueXBefore;
		          float proportion = (float)((x-valueXBefore)/deltaX);
		          double deltaY = valueYAfter-valueYBefore;
		          double result = valueYBefore+(deltaY*proportion);  
		          result = (double) round(result, 1);
		          
	    		  //TMP
		          chartLabel[chartNo].setText(chartLabel[chartNo].getText()+series.getName()+"["+result+"]\n");
	    	  }
	    	  
	    	  int space = 20;
	    	 
	    	  if (mouseEvent.getX()>(anchorPane[chartNo].getWidth())/2) 
	    		  chartLabel[chartNo].setLayoutX(mouseEvent.getX()- chartLabel[chartNo].getWidth()-space);
	    	  else 
	    		  chartLabel[chartNo].setLayoutX(mouseEvent.getX()+space);
	    	  
	    	  if (mouseEvent.getY()>(anchorPane[chartNo].getHeight())/2) 
	    		  chartLabel[chartNo].setLayoutY(mouseEvent.getY()- chartLabel[chartNo].getHeight()-space);
	    	  else 
	    		  chartLabel[chartNo].setLayoutY(mouseEvent.getY()+space);
	}
	
	public void ListnerLineChartExited(MouseEvent mouseEvent, int chartNo) {	    	  
    	  for (int i=0; i<4; i++) {
    		  // if not exist, skip to new loop
    		  if (chartLineMark[i] == null) continue;
    		  
    		  chartLineMark[i].setStartX(0);
	    	  chartLineMark[i].setEndX(0);
	    	  chartLineMark[i].setEndY(0);		  
    	  }
    	  
    	  chartLabel[chartNo].setVisible(false);
    	  chartLabel[chartNo].setLayoutX(0.00);
    	  chartLabel[chartNo].setLayoutY(0.00);
	}	

    // Hardware definitions
	//***************************************
	@FXML
    void buttonNewHardwareClick(MouseEvent event) throws IOException {     
		FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(getClass().getResource("/fxml/NewHardwareWindow.fxml"));
	    Pane pane = loader.load();
	    NewHardwareControl newHardwareControl = loader.getController();
	    newHardwareControl.setControllers(eventManager);
	    
	    Scene scene = new Scene(pane);
	    scene.getStylesheets().add("myStyle.css");
	    Stage stage = new Stage();
	    stage.setScene(scene);	        
	    stage.setTitle("Modu³"); 
	    stage.show();
	}	
	
	private void ReqGUIControllerHardware() throws SQLException {
		tableViewModules.setItems(eventManager.getModules());	
		tableViewModules.refresh();		
	}

	// UDP
 	//***************************************
	
	// UDP for controller Vent
	private void UDPcontrollerVent() {		
		byte[] buf = new byte[5];
		buf[0] = 1;
		buf[1] = 3;
		buf[2] = 0;
		
		if (!controller_Vent[0].isHour01UpToDate()) {
			buf[3] = 4;
			buf[4] = (byte) (controller_Vent[0].NVhour[0]);
			eventManager.udpController.UDPsend(buf);
		}
		if (!controller_Vent[0].isHour23UpToDate()) {
			buf[3] = 5;
			buf[4] = (byte) (controller_Vent[0].NVhour[1]);
			eventManager.udpController.UDPsend(buf);
		}
		if (!controller_Vent[0].isHour45UpToDate()) {
			buf[3] = 6;
			buf[4] = (byte) (controller_Vent[0].NVhour[2]);
			eventManager.udpController.UDPsend(buf);
		}
		if (!controller_Vent[0].isHour67UpToDate()) {
			buf[3] = 7;
			buf[4] = (byte) (controller_Vent[0].NVhour[3]);
			eventManager.udpController.UDPsend(buf);
		}
		if (!controller_Vent[0].isHour89UpToDate()) {
			buf[3] = 8;
			buf[4] = (byte) (controller_Vent[0].NVhour[4]);
			eventManager.udpController.UDPsend(buf);
		}
		if (!controller_Vent[0].isHour1011UpToDate()) {
			buf[3] = 9;
			buf[4] = (byte) (controller_Vent[0].NVhour[5]);
			eventManager.udpController.UDPsend(buf);
		}
		if (!controller_Vent[0].isHour1213UpToDate()) {
			buf[3] = 10;
			buf[4] = (byte) (controller_Vent[0].NVhour[6]);
			eventManager.udpController.UDPsend(buf);
		}
		if (!controller_Vent[0].isHour1415UpToDate()) {
			buf[3] = 11;
			buf[4] = (byte) (controller_Vent[0].NVhour[7]);
			eventManager.udpController.UDPsend(buf);
		}
		if (!controller_Vent[0].isHour1617UpToDate()) {
			buf[3] = 12;
			buf[4] = (byte) (controller_Vent[0].NVhour[8]);
			eventManager.udpController.UDPsend(buf);
		}
		if (!controller_Vent[0].isHour1819UpToDate()) {
			buf[3] = 13;
			buf[4] = (byte) (controller_Vent[0].NVhour[9]);
			eventManager.udpController.UDPsend(buf);
		}
		if (!controller_Vent[0].isHour2021UpToDate()) {
			buf[3] = 14;
			buf[4] = (byte) (controller_Vent[0].NVhour[10]);
			eventManager.udpController.UDPsend(buf);
		}
		if (!controller_Vent[0].isHour2223UpToDate()) {
			buf[3] = 15;
			buf[4] = (byte) (controller_Vent[0].NVhour[11]);
			eventManager.udpController.UDPsend(buf);
		}

	}
	
	private void UDPcontrollerComfort() {
		byte[] buf = new byte[5];
		buf[0] = 1;
		buf[1] = 10;
		buf[2] = 0;
				
		//Byte 3 check
		if (!controller_Comfort[0].isReqZ0UpToDate()) {
			buf[3] = 5;
			buf[4] = (byte) (controller_Comfort[0].zone[0].NVreqTemp*2);
			eventManager.udpController.UDPsend(buf);
		}
		if (!controller_Comfort[0].isReqZ1UpToDate()) {
			buf[3] = 9;
			buf[4] = (byte) (controller_Comfort[0].zone[1].NVreqTemp*2);
			eventManager.udpController.UDPsend(buf);
		}
		if (!controller_Comfort[0].isReqZ2UpToDate()) {
			buf[3] = 13;
			buf[4] = (byte) (controller_Comfort[0].zone[2].NVreqTemp*2);
			eventManager.udpController.UDPsend(buf);
		}
		if (!controller_Comfort[0].isReqZ3UpToDate()) {
			buf[3] = 17;
			buf[4] = (byte) (controller_Comfort[0].zone[3].NVreqTemp*2);
			eventManager.udpController.UDPsend(buf);
		}
		if (!controller_Comfort[0].isReqZ4UpToDate()) {
			buf[3] = 21;
			buf[4] = (byte) (controller_Comfort[0].zone[4].NVreqTemp*2);
			eventManager.udpController.UDPsend(buf);
		}
		if (!controller_Comfort[0].isReqZ5UpToDate()) {
			buf[3] = 25;
			buf[4] = (byte) (controller_Comfort[0].zone[5].NVreqTemp*2);
			eventManager.udpController.UDPsend(buf);
		}
		if (!controller_Comfort[0].isReqZ6UpToDate()) {
			buf[3] = 29;
			buf[4] = (byte) (controller_Comfort[0].zone[6].NVreqTemp*2);
			eventManager.udpController.UDPsend(buf);
		}
	}
	
	private void UDPcontrollerHeating() {
		//TODO
		byte[] buf = new byte[5];
		buf[0] = 1;
		buf[1] = 14;
		buf[2] = 0;
				
		//Byte 3 check
		
		if ((!controller_heating[0].isCheapTariffOnlyUpToDate()) || (!controller_heating[0].isHeatingActivatedOnUpToDate()) || (!controller_heating[0].isAntyLegionelliaOnUpToDate())) {
			buf[3] = 3;
			buf[4] = (byte) (((controller_heating[0].NVcheapTariffOnly? 1:0 ) << 2) | ((controller_heating[0].NVheatingActivated? 1:0 ) << 1) | ((controller_heating[0].NVantyLegionellia? 1:0 ) << 0));
			eventManager.udpController.UDPsend(buf);
		}
				
		//Byte 6 check
		if (!controller_heating[0].isReqTempBuforCOUpToDate()) {
			buf[3] = 6;
			buf[4] = (byte) (controller_heating[0].NVreqTempBuforCO*2);
			eventManager.udpController.UDPsend(buf);
		}
		
		//Byte 7 check
		if (!controller_heating[0].isReqTempBuforCWUUpToDate()) {
			buf[3] = 7;
			buf[4] = (byte) (controller_heating[0].NVreqTempBuforCWU*2);
			eventManager.udpController.UDPsend(buf);
		}
	}
	
	private void UDPCheckValuesReceived() {		
		// If only one from controller's values are not up to date set request true and break function
		if (!tileWentylacja.isDisabled())		
			if (controller_Vent[0].isUDPreqSendNewValues())
				controller_Vent[0].setUDPreqSendNewValues(!controller_Vent[0].allDataUpToDate());
		
		if (!tileKomfort.isDisabled())
			if (controller_Comfort[0].isUDPreqSendNewValues())
				controller_Comfort[0].setUDPreqSendNewValues(!controller_Comfort[0].allDataUpToDate());

		if (!tileOgrzewanie.isDisabled())
			if (controller_heating[0].isUDPreqSendNewValues())
				controller_heating[0].setUDPreqSendNewValues(!controller_heating[0].allDataUpToDate());
	}
	
	// Others definitions
 	//***************************************
	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}

	public void setDataStructure(Controller_Heating[] controller_heating) {
		this.controller_heating = controller_heating;	
		//Variables "new values" initialization
		controller_heating[0].initialization_NewValues();
	}

	
	public void setBackgroundWorker() {
		// Set new thread
		new Thread(new HandModeViewlRunBackground()).start();
	}
	
	public void setDataStructure(Controller_Fireplace[] controller_Fireplace) {
		this.controller_Fireplace = controller_Fireplace;
	}
	
	public void setDataStructure(Controller_Vent[] controller_Vent) {
		this.controller_Vent = controller_Vent;
		
		//Variables "new values" initialization
		controller_Vent[0].initialization_NewValues();
	}
	
	public void setDataStructure(Controller_Comfort[] controller_Comfort) {
		this.controller_Comfort = controller_Comfort;
		//TMP controller_Comfort[0].initialization_NewValues();
	}
	
	public void ReqGUIrefreshHand() throws SQLException {
		if (paneKomfort.isVisible()) ReqGUIControllerComfort();
		if (paneWentylacja.isVisible()) ReqGUIControllerVent();
		if (paneOgrzewanie.isVisible()) ReqGUIControllerHeating();
//TMP		if (paneKominek.isVisible()) ReqGUIControllerFirePlace();
//TMP		if (paneOgrod.isVisible()) ReqGUIControllerGarden();
//TMP		if (paneLazienki.isVisible()) ReqGUIControllerBath();
//TMP		if (panePogoda.isVisible()) ReqGUIControllerWeather();
//TMP		if (paneAlarm.isVisible()) ReqGUIControllerAlarm();
//TMP		if (paneKamery.isVisible()) ReqGUIControllerCameras();
		if (paneHardware.isVisible()) ReqGUIControllerHardware();		
	}
	
	// Background client each 1 sec for UDP send
	public class HandModeViewlRunBackground implements Runnable {
		@Override
		public void run() {
			while (true) {			
					if (!tileWentylacja.isDisable())
						if (controller_Vent[0].isUDPreqSendNewValues()) UDPcontrollerVent();
					
					if (!tileOgrzewanie.isDisable()) 
						if (controller_heating[0].isUDPreqSendNewValues()) UDPcontrollerHeating();

					if (!tileKomfort.isDisable()) 
						if (controller_Comfort[0].isUDPreqSendNewValues()) UDPcontrollerComfort();


					
					UDPCheckValuesReceived();
				
				// go sleep for 1000ms
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// Extra Visu update
				if (paneWentylacja.isVisible()) ReqGUIControllerVent();

			}
		}
	}
	
	private static java.sql.Date getCurrentDate() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}

      //Method to quick label color change depending of boolean value.
	private void BackgroundColor(Label label, boolean value, boolean upToDate) {
		if (!upToDate)
			label.setBackground(new Background(new BackgroundFill(COLOR_WAITING4UPDATE, CornerRadii.EMPTY, Insets.EMPTY)));
		else
			if (value) label.setBackground(new Background(new BackgroundFill(COLOR_ON, CornerRadii.EMPTY, Insets.EMPTY)));
			else label.setBackground(new Background(new BackgroundFill(COLOR_OFF, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	private void BackgroundColorBlink(Label label) {
	    java.util.Date today = new java.util.Date();
		int sec = today.getSeconds();
		//blink mode
		if ((sec & 1) == 0) return;
		
		// if second even to method below
		label.setBackground(new Background(new BackgroundFill(COLOR_NORMAL, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	private void TextFillColor(Label label, boolean value) {
		if (value) label.setTextFill(COLOR_ON);	else label.setTextFill(COLOR_OFF);	
	}
	
	private void TextFillColor(Label label,  boolean value, boolean upToDate) {
		if (upToDate)
			if (value) label.setTextFill(COLOR_ON);	else label.setTextFill(COLOR_OFF);	
		else label.setTextFill(COLOR_WAITING4UPDATE);
	}
	
	private void TextFillColor(Label label, boolean enabled, float value, float expectedValue, float tolerance, float warning) {
	//Method to change color according to difference between expected value and tolerance
		if ((value < (expectedValue-(expectedValue/100*warning))) || (value > (expectedValue+(expectedValue/100*warning)))) label.setTextFill(COLOR_ALARM);
		else if ((value < (expectedValue-(expectedValue/100*tolerance))) || (value > (expectedValue+(expectedValue/100*tolerance)))) label.setTextFill(COLOR_WARNING);
		else if (value == expectedValue) label.setTextFill(COLOR_ON);
		if (!enabled) label.setTextFill(COLOR_OFF);
		label.setText(Float.toString(value)+"°C");
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	private static int changeBitStatus(int data, int bitPos) {
		int value = 1;
		for (int i = 0; i<bitPos; i++)
			value = value << 1;	
		if (((data >> bitPos) & 1) == 1)
			data -= value;
		else data += value;
		return (data);
	}
	
	// return bit status from corresponding byte according to position in byte
	public boolean bitStatus(int data, int bytePos) {
		if (((data >> bytePos) & 1) == 1) return true;
		else return false;
	}	
	private void TMPtemporarySettings() throws SQLException {
//	   	LocalDate tempDate = NOW_LOCAL_DATE();
	 //  	tempDate = tempDate.minusDays(9);
	    	
//			datePickerFrom.setValue(tempDate);
//			LineChartAddSeries(0, "Controller_Heating_Archive", "tempBuforCOdol", "FLOAT", 0);
//			LineChartAddChart(0);
	 //   	chartSetting[0].setEnabled(true);
	    } 	
	
}