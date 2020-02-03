package Communication;

import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import Data_Structures.Controller_Comfort;
import Data_Structures.Controller_Fireplace;
import Data_Structures.Controller_Heating;
import Data_Structures.Controller_Vent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventManager {
	
	public static final String Komfort 		= "Komfort";
	public static final String Wentylacja 	= "Wentylacja";
	public static final String Ogrzewanie 	= "Ogrzewanie";
	public static final String Kominek 		= "Kominek";
	public static final String Ogrod	 	= "Ogródek";
	public static final String Alarm 		= "Alarm";
	public static final String Lazienka 	= "£azienka";
	public static final String Pogoda 		= "Pogoda";
	public static final String Wykresy 		= "Wykresy";
	public static final String Hardware 	= "Hardware";
	public static final int maxNumberController = 15;

	//Feld's
	public BooleanProperty reqGUIrefreshHand = new SimpleBooleanProperty(false);
	private int timeSynchroLast = 100;

	//Objects
	//Data structures
	public Controller_Heating[] controller_Heating;
	public Controller_Fireplace[] controller_Fireplace;
	public Controller_Vent[] controller_Vent;
	public Controller_Comfort[] controller_Comfort;
	//List of modules already saved in DB
	private ObservableList<Data_Structures.Module> modules = FXCollections.observableArrayList();
	
	private boolean controller_Vent_Available = false;
	private boolean controller_Heating_Available = false;
	private boolean controller_Fireplace_Available = false;
	private boolean controller_Garden_Available = false;
	private boolean controller_Bath_Available = false;
	private boolean controller_Weather_Available = false;
	private boolean controller_Alarm_Available = false;
	private boolean controller_Cameras_Available = false;
	private boolean controller_Comfort_Available = false;
	
	//Controllers	
	public DataBaseController dataBaseController;// = new DataBaseController(controller_Room, controller_Heating);
	public UDPcontroller udpController;// = new CANController(dataBaseController);
	
	public EventManager() throws SQLException {
		dataBaseController = new DataBaseController();
		modules = dataBaseController.getHadwareFromDB();

		controller_Heating = dataBaseController.getController_Heating();
		controller_Fireplace = dataBaseController.getController_Fireplace();
		controller_Vent = dataBaseController.getController_Vent();
		controller_Comfort = dataBaseController.getController_Comfort();
		
		// Check if some of this controllers available to enable tiles in HMI
		for (int i=0; i<15; i++) {
			if (controller_Heating[i] != null) controller_Heating_Available=true;
			if (controller_Fireplace[i] != null) controller_Fireplace_Available=true;
			if (controller_Vent[i] != null) controller_Vent_Available=true;
			if (controller_Comfort[i] != null) controller_Comfort_Available=true;
		}
				
		udpController = new UDPcontroller(dataBaseController);

		// Set new thread
		new Thread(new EventRunBackground()).start();		
	}

	public ObservableList<Data_Structures.Module> getModules() {
		int index = 0;
		for (Data_Structures.Module mod : modules) {
			index = modules.indexOf(mod);
			
			switch (mod.getModuleName()) {
				case Wentylacja : {
					modules.get(index).setLastDiagnostic(controller_Vent[mod.getModuleNo()].diagnosticLastUpdate); 
					modules.get(index).setLastUDP(controller_Vent[mod.getModuleNo()].frameLastUpdate); 
					modules.get(index).setIP1(controller_Vent[mod.getModuleNo()].IP1);
					modules.get(index).setIP2(controller_Vent[mod.getModuleNo()].IP2);
					modules.get(index).setIP3(controller_Vent[mod.getModuleNo()].IP3);
					modules.get(index).setIP4(controller_Vent[mod.getModuleNo()].IP4);
					break;
				}
				case Ogrzewanie : {
					modules.get(index).setLastDiagnostic(controller_Heating[mod.getModuleNo()].diagnosticLastUpdate); 
					modules.get(index).setLastUDP(controller_Heating[mod.getModuleNo()].frameLastUpdate); 
					modules.get(index).setIP1(controller_Heating[mod.getModuleNo()].IP1);
					modules.get(index).setIP2(controller_Heating[mod.getModuleNo()].IP2);
					modules.get(index).setIP3(controller_Heating[mod.getModuleNo()].IP3);
					modules.get(index).setIP4(controller_Heating[mod.getModuleNo()].IP4);
					break;
				}

				case Komfort : {
					modules.get(index).setLastDiagnostic(controller_Comfort[mod.getModuleNo()].diagnosticLastUpdate); 
					modules.get(index).setLastUDP(controller_Comfort[mod.getModuleNo()].frameLastUpdate);
					modules.get(index).setIP1(controller_Comfort[mod.getModuleNo()].IP1);
					modules.get(index).setIP2(controller_Comfort[mod.getModuleNo()].IP2);
					modules.get(index).setIP3(controller_Comfort[mod.getModuleNo()].IP3);
					modules.get(index).setIP4(controller_Comfort[mod.getModuleNo()].IP4);
					break;
				}
			}

		}
		return modules;
	}

	public boolean isController_Heating_Available() {
		return controller_Heating_Available;
	}

	public boolean isController_Fireplace_Available() {
		return controller_Fireplace_Available;
	}
	public boolean isController_Vent_Available() {
		return controller_Vent_Available;
	}

	public boolean isController_Garden_Available() {
		return controller_Garden_Available;
	}

	public boolean isController_Bath_Available() {
		return controller_Bath_Available;
	}

	public boolean isController_Weather_Available() {
		return controller_Weather_Available;
	}

	public boolean isController_Alarm_Available() {
		return controller_Alarm_Available;
	}

	public boolean isController_Cameras_Available() {
		return controller_Cameras_Available;
	}
	
	public boolean isController_Comfort_Available() {
		return controller_Comfort_Available;
	}

	public void GUIInvalid() {
		reqGUIrefreshHand.set(!reqGUIrefreshHand.getValue());
	}
	
	private void isGUIinvalid() {
		if (dataBaseController.isGUIinvalid()) GUIInvalid();
		dataBaseController.setGUIvalid();
	}
	
//	@SuppressWarnings("deprecation")
	private void sendTimeSynchro() {
		Date actual = new Date();
		int minutes = actual.getMinutes();
		if (minutes != timeSynchroLast) {
			timeSynchroLast = minutes;
			
			byte[] buf = new byte[10];
			buf[0] = 1;
			buf[1] = 0;
			buf[2] = 0;
			buf[3] = (byte) (actual.getYear()-100);
			buf[4] = (byte) (actual.getMonth()+1);
			buf[5] = (byte) (actual.getDate());
			buf[6] = (byte) (actual.getDay());
			buf[7] = (byte) (actual.getHours());
			buf[8] = (byte) (actual.getMinutes());
			buf[9] = (byte) (actual.getSeconds());
			
			udpController.UDPsend(buf);
		}
	}
	
	public class EventRunBackground implements Runnable {
		@Override
		public void run() {
			while (true) {
				
				isGUIinvalid();
				sendTimeSynchro();
			
				// TMP start
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
