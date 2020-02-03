package Communication;

/*
 * The class collect all entry's get from COM port and store them in data base. 
 */

import java.sql.*;

import Data_Structures.Controller_Comfort;
import Data_Structures.Controller_Fireplace;
import Data_Structures.Controller_Heating;
import Data_Structures.Controller_Vent;
import Data_Structures.TableColumnProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class DataBaseController {
	//the timeout value for connecting with the port
	final static int TIMEOUT_SQL = 2000;
	
	Connection connection;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet=null;
	Statement state=null;
	String query = null;
	
	private boolean guiInvalid = true;
	
	private Controller_Heating[] controller_Heating = new Controller_Heating[15];
	private Controller_Fireplace[] controller_Fireplace = new Controller_Fireplace[15];
	private Controller_Vent[] controller_Vent = new Controller_Vent[15];
	private Controller_Comfort[] controller_Comfort = new Controller_Comfort[15];
	
	private Controller_Heating temp_Controller_Heating = new Controller_Heating(4,100,"dummy",false,0,0,0,0,getCurrentDate(),getCurrentDate());
	private Controller_Fireplace temp_Controller_Fireplace = new Controller_Fireplace(5,100,"dummy",false,0,0,0,0,getCurrentDate(),getCurrentDate());
	private Controller_Vent temp_Controller_Vent = new Controller_Vent(3, 100, "dummy", false, 0, 0, 0, 0, getCurrentDate(), getCurrentDate());
	private Controller_Comfort temp_Controller_Comfort = new Controller_Comfort(3, 100, "dummy", false, 0, 0, 0, 0, getCurrentDate(), getCurrentDate());
	
	public static java.sql.Date getCurrentDate() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}

	// constructor	
	public DataBaseController () {
		connection =DB_Connector();
		
		// If connection with database is not possible, exit from program
		if (connection == null) {
		System.out.println("BLAD:SQL - Zestawienie połączenia z bazą danych");
		System.exit(1);
		}
	}
	
	private static Connection DB_Connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Database-DOM\\house.sqlite");
			return conn;			
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	private boolean isDBconnected() {
		try {
			return !connection.isClosed();
			
		} catch (Exception e) {
			System.out.println("BLAD:SQL - Brak polaczenia z baza danych");
			return false;
		}
	}

	public void saveUDPframe(int[] packetData) throws SQLException {	
		
		// How long takes save CAN frame
		Date enrtyTime = getCurrentDate();
		
		//return if DB is not connected
		if (!isDBconnected()) return;
		
		//set gui invalid
		guiInvalid = true;
		
		int controllerTyp = packetData[0];
		int controllerNumber = packetData[1];
		int controllerFrameNumber = packetData[2];
							
		// Data frame from module 5 received. (2-modul kominka z plaszczem wody) 
		if (controllerTyp == 3) {
			// get last state of module to compare afterwards if something was change since last update. 
			getLastValues(temp_Controller_Vent,controllerNumber);
			
			//Copy separated data to corresponding data structure according module frame number
			controller_Vent[controllerNumber].setData(packetData);
		
			switch (controllerFrameNumber) {
			case 0: // standard frame 0	
				//get exact time of this update
				controller_Vent[controllerNumber].frameLastUpdate = getCurrentDate();
				//Update entity in Controller_Fireplace table				
				// Set query for Controller_Fireplace update
				query = "UPDATE Controller_Vent SET controllerNo=0, frameLastUpdate=(?), fanON=(?), hour01=(?), hour23=(?), hour45=(?), hour67=(?), hour89=(?), hour1011=(?), hour1213=(?), hour1415=(?),"
						+ " hour1617=(?), hour1819=(?), hour2021=(?), hour2223=(?)";
				preparedStatement = connection.prepareStatement(query);					
				preparedStatement.setDate(1, controller_Vent[controllerNumber].frameLastUpdate);
				preparedStatement.setBoolean(2, controller_Vent[controllerNumber].fanON);
				preparedStatement.setInt(3, controller_Vent[controllerNumber].hour[0]);
				preparedStatement.setInt(4, controller_Vent[controllerNumber].hour[1]);
				preparedStatement.setInt(5, controller_Vent[controllerNumber].hour[2]);
				preparedStatement.setInt(6, controller_Vent[controllerNumber].hour[3]);
				preparedStatement.setInt(7, controller_Vent[controllerNumber].hour[4]);
				preparedStatement.setInt(8, controller_Vent[controllerNumber].hour[5]);
				preparedStatement.setInt(9, controller_Vent[controllerNumber].hour[6]);
				preparedStatement.setInt(10, controller_Vent[controllerNumber].hour[7]);
				preparedStatement.setInt(11, controller_Vent[controllerNumber].hour[8]);
				preparedStatement.setInt(12, controller_Vent[controllerNumber].hour[9]);
				preparedStatement.setInt(13, controller_Vent[controllerNumber].hour[10]);
				preparedStatement.setInt(14, controller_Vent[controllerNumber].hour[11]);

				preparedStatement.executeUpdate();			
				break;

			case 200: // diagnose frame
				//TMP create diagnostic frame
				break;
			}
		// if last state in Archive table is different, insert new entity
			if (!controller_Vent[controllerNumber].compare(temp_Controller_Vent)) {
				query = "INSERT INTO Controller_Vent_Archive SELECT * FROM Controller_Vent";
				preparedStatement = connection.prepareStatement(query);					
				preparedStatement.executeUpdate();
			}
		}
		
		// Data frame from module 5 received. (5-modul kominka z plaszczem wody) 
		if (controllerTyp == 5) {
			// get last state of module to compare afterwards if something was change since last update. 
			getLastValues(temp_Controller_Fireplace,controllerNumber);
			
			//Copy separated data to corresponding data structure according module frame number
			controller_Fireplace[controllerNumber].setData(packetData);
		
			switch (controllerFrameNumber) {
			case 0: // standard frame 0	
				//get exact time of this update
				controller_Fireplace[controllerNumber].frameLastUpdate = getCurrentDate();
				//Update entity in Controller_Fireplace table				
				// Set query for Controller_Fireplace update
				query = "UPDATE Controller_Fireplace SET mode=(?), alarm=(?), warning=(?), pump=(?), fireAlarm=(?), throttle=(?), pressure_emerency_water=(?), "
						+ "tempSet=(?), tempOutlet=(?), tempInlet=(?), tempChimney=(?), frameLastUpdate=(?)";
				preparedStatement = connection.prepareStatement(query);					
				preparedStatement.setInt(1, controller_Fireplace[controllerNumber].mode);
				preparedStatement.setBoolean(2, controller_Fireplace[controllerNumber].alarm);
				preparedStatement.setBoolean(3, controller_Fireplace[controllerNumber].warning);
				preparedStatement.setBoolean(4, controller_Fireplace[controllerNumber].pump);
				preparedStatement.setBoolean(5, controller_Fireplace[controllerNumber].fireAlarm);
				preparedStatement.setInt(6, controller_Fireplace[controllerNumber].throttle);
				preparedStatement.setInt(7, controller_Fireplace[controllerNumber].pressure_emerency_water);
				preparedStatement.setFloat(8, controller_Fireplace[controllerNumber].tempSet);
				preparedStatement.setFloat(9, controller_Fireplace[controllerNumber].tempOutlet);
				preparedStatement.setFloat(10, controller_Fireplace[controllerNumber].tempInlet);
				preparedStatement.setFloat(11, controller_Fireplace[controllerNumber].tempChimney);
				preparedStatement.setDate(12, controller_Fireplace[controllerNumber].frameLastUpdate);
				preparedStatement.executeUpdate();			
				break;

			case 200: // diagnose frame
				//TMP create diagnostic frame
				break;
			}
			
			// if last state in Archive table is different, insert new entity
			if (!controller_Fireplace[controllerNumber].compare(temp_Controller_Fireplace)) {
				query = "INSERT INTO Controller_Fireplace_Archive SELECT * FROM Controller_Fireplace";
				preparedStatement = connection.prepareStatement(query);					
				preparedStatement.executeUpdate();
			}
		}
		
		// Data frame from module 10 received. (10-modul komfortu) 
		if (controllerTyp == 10) {
			// get last state of module to compare afterwards if something was change since last update. 
			getLastValues(temp_Controller_Comfort,controllerNumber);
			
			//Copy separated data to corresponding data structure according module frame number
			controller_Comfort[controllerNumber].setData(packetData);
		
			switch (controllerFrameNumber) {
			case 0: // standard frame 0	
				//get exact time of this update
				controller_Comfort[controllerNumber].frameLastUpdate = getCurrentDate();

				//Update entity in Controller_Room table				
				// Set query for Controller_Room update
				query = "UPDATE Controller_Comfort SET frameLastUpdate=(?), "
						+ "zone0isTemp=(?), zone0reqTemp=(?), zone0humidity=(?), "
						+ "zone1isTemp=(?), zone1reqTemp=(?), zone1humidity=(?),"
						+ "zone2isTemp=(?), zone2reqTemp=(?), zone2humidity=(?),"
						+ "zone3isTemp=(?), zone3reqTemp=(?), zone3humidity=(?),"
						+ "zone4isTemp=(?), zone4reqTemp=(?), zone4humidity=(?),"
						+ "zone5isTemp=(?), zone5reqTemp=(?), zone5humidity=(?),"
						+ "zone6isTemp=(?), zone6reqTemp=(?), zone6humidity=(?)"
						+ " WHERE ControllerNo=?";

				preparedStatement = connection.prepareStatement(query);	
				preparedStatement.setDate(1, controller_Comfort[controllerNumber].frameLastUpdate);
				
				preparedStatement.setFloat(2, (float)controller_Comfort[controllerNumber].zone[0].isTemp);
				preparedStatement.setFloat(3, controller_Comfort[controllerNumber].zone[0].reqTemp);
				preparedStatement.setInt(4, controller_Comfort[controllerNumber].zone[0].humidity);
				
				preparedStatement.setFloat(5, controller_Comfort[controllerNumber].zone[1].isTemp);
				preparedStatement.setFloat(6, controller_Comfort[controllerNumber].zone[1].reqTemp);
				preparedStatement.setInt(7, controller_Comfort[controllerNumber].zone[1].humidity);
				
				preparedStatement.setFloat(8, controller_Comfort[controllerNumber].zone[2].isTemp);
				preparedStatement.setFloat(9, controller_Comfort[controllerNumber].zone[2].reqTemp);
				preparedStatement.setInt(10, controller_Comfort[controllerNumber].zone[2].humidity);

				preparedStatement.setFloat(11, controller_Comfort[controllerNumber].zone[3].isTemp);
				preparedStatement.setFloat(12, controller_Comfort[controllerNumber].zone[3].reqTemp);
				preparedStatement.setInt(13, controller_Comfort[controllerNumber].zone[3].humidity);

				preparedStatement.setFloat(14, controller_Comfort[controllerNumber].zone[4].isTemp);
				preparedStatement.setFloat(15, controller_Comfort[controllerNumber].zone[4].reqTemp);
				preparedStatement.setInt(16, controller_Comfort[controllerNumber].zone[4].humidity);

				preparedStatement.setFloat(17, controller_Comfort[controllerNumber].zone[5].isTemp);
				preparedStatement.setFloat(18, controller_Comfort[controllerNumber].zone[5].reqTemp);
				preparedStatement.setInt(19, controller_Comfort[controllerNumber].zone[5].humidity);

				preparedStatement.setFloat(20, controller_Comfort[controllerNumber].zone[6].isTemp);
				preparedStatement.setFloat(21, controller_Comfort[controllerNumber].zone[6].reqTemp);
				preparedStatement.setInt(22, controller_Comfort[controllerNumber].zone[6].humidity);

				preparedStatement.setInt(23, controllerNumber);
				preparedStatement.executeUpdate();			
				break;

			case 200: // diagnose frame
				//TMP create diagnostic frame
				controller_Comfort[controllerNumber].diagnosticLastUpdate= getCurrentDate();
				controller_Comfort[0].setIP1(packetData[3]);
				controller_Comfort[0].setIP2(packetData[4]);
				controller_Comfort[0].setIP3(packetData[5]);
				controller_Comfort[0].setIP4(packetData[6]);
				query = "UPDATE Controller_Comfort SET diagnosticLastUpdate=(?), error=(?)";

				preparedStatement = connection.prepareStatement(query);	
				preparedStatement.setDate(1, controller_Comfort[controllerNumber].diagnosticLastUpdate);
				preparedStatement.setBoolean(2, controller_Comfort[controllerNumber].error);
				preparedStatement.executeUpdate();			
				break;
			}
			// if last state in Archive table is different, insert new entity
			if (!controller_Comfort[controllerNumber].compare(temp_Controller_Comfort)) {
				query = "INSERT INTO controller_Comfort_Archive SELECT * FROM controller_Comfort WHERE ControllerNo=(?)";
				preparedStatement = connection.prepareStatement(query);					
				preparedStatement.setInt(1, controllerNumber);
				preparedStatement.executeUpdate();
				}
			
		}
			
			// Data frame from module 14 received. (14-modul ogrzewania) 
			if (controllerTyp == 14) {
				// get last state of module to compare afterwards if something was change since last update. 
				getLastValues(temp_Controller_Heating,controllerNumber);
				
				//Copy separated data to corresponding data structure according module frame number
				controller_Heating[controllerNumber].setData(packetData);
			
				switch (controllerFrameNumber) {
				case 0: // standard frame 0	
					//get exact time of this update
					controller_Heating[controllerNumber].frameLastUpdate = getCurrentDate();

					//Update entity in Controller_Room table				
					// Set query for Controller_Room update
					query = "UPDATE controller_Heating SET frameLastUpdate=(?), heatSourceActive=(?), pump_InHouse=(?), pump_UnderGround=(?), "
							+ "reqHeatingPumpOn=(?), cheapTariffOnly=(?), heatingActivated=(?), antyLegionellia=(?), "
							+ "valve_3way=(?), valve_bypass=(?), zone0=(?), zone1=(?), zone2=(?), zone3=(?), zone4=(?), zone5=(?), zone6=(?), "
							+ "reqTempBuforCO=(?), reqTempBuforCWU=(?), tBuffCOdol=(?), tBuffCOsrodek=(?), tBuffCOgora=(?), "
							+ "tBuffCWUdol=(?), tBuffCWUsrodek=(?), tBuffCWUgora=(?), tZasilanie=(?), tPowrot=(?), tDolneZrodlo=(?), "
							+ "tKominek=(?), tRozdzielacze=(?), tPowrotParter=(?), tPowrotPietro=(?)"
							+ " WHERE ControllerNo=?";

					preparedStatement = connection.prepareStatement(query);					
					preparedStatement.setDate(1, controller_Heating[controllerNumber].frameLastUpdate);
					preparedStatement.setInt(2, controller_Heating[controllerNumber].heatSourceActive);
					preparedStatement.setBoolean(3, controller_Heating[controllerNumber].pump_InHouse);
					preparedStatement.setBoolean(4, controller_Heating[controllerNumber].pump_UnderGround);
					preparedStatement.setBoolean(5, controller_Heating[controllerNumber].reqHeatingPumpOn);	
					preparedStatement.setBoolean(6, controller_Heating[controllerNumber].cheapTariffOnly);
					preparedStatement.setBoolean(7, controller_Heating[controllerNumber].heatingActivated);
					preparedStatement.setBoolean(8, controller_Heating[controllerNumber].antyLegionellia);
					
					preparedStatement.setInt(9, controller_Heating[controllerNumber].valve_3way);
					preparedStatement.setInt(10, controller_Heating[controllerNumber].valve_bypass);
					preparedStatement.setBoolean(11, controller_Heating[controllerNumber].zone[0]);
					preparedStatement.setBoolean(12, controller_Heating[controllerNumber].zone[1]);
					preparedStatement.setBoolean(13, controller_Heating[controllerNumber].zone[2]);
					preparedStatement.setBoolean(14, controller_Heating[controllerNumber].zone[3]);
					preparedStatement.setBoolean(15, controller_Heating[controllerNumber].zone[4]);
					preparedStatement.setBoolean(16, controller_Heating[controllerNumber].zone[5]);
					preparedStatement.setBoolean(17, controller_Heating[controllerNumber].zone[6]);
					preparedStatement.setFloat(18, controller_Heating[controllerNumber].reqTempBuforCO);			
					preparedStatement.setFloat(19, controller_Heating[controllerNumber].reqTempBuforCWU);			
					preparedStatement.setFloat(20, controller_Heating[controllerNumber].tBuffCOdol);			
					preparedStatement.setFloat(21, controller_Heating[controllerNumber].tBuffCOsrodek);			
					preparedStatement.setFloat(22, controller_Heating[controllerNumber].tBuffCOgora);			
					preparedStatement.setFloat(23, controller_Heating[controllerNumber].tBuffCWUdol);			
					preparedStatement.setFloat(24, controller_Heating[controllerNumber].tBuffCWUsrodek);			
					preparedStatement.setFloat(25, controller_Heating[controllerNumber].tBuffCWUgora);			
					preparedStatement.setFloat(26, controller_Heating[controllerNumber].tZasilanie);			
					preparedStatement.setFloat(27, controller_Heating[controllerNumber].tPowrot);			
					preparedStatement.setFloat(28, controller_Heating[controllerNumber].tDolneZrodlo);			
					preparedStatement.setFloat(29, controller_Heating[controllerNumber].tKominek);			
					preparedStatement.setFloat(30, controller_Heating[controllerNumber].tRozdzielacze);			
					preparedStatement.setFloat(31, controller_Heating[controllerNumber].tPowrotParter);			
					preparedStatement.setFloat(32, controller_Heating[controllerNumber].tPowrotPietro);			

					preparedStatement.setInt(33, controllerNumber);

					preparedStatement.executeUpdate();			
					break;
					
				case 200: // diagnose frame
					//TMP create diagnostic frame
					controller_Heating[controllerNumber].diagnosticLastUpdate= getCurrentDate();
					controller_Heating[0].setIP1(packetData[3]);
					controller_Heating[0].setIP2(packetData[4]);
					controller_Heating[0].setIP3(packetData[5]);
					controller_Heating[0].setIP4(packetData[6]);
					query = "UPDATE controller_Heating SET diagnosticLastUpdate=(?), error=(?)";

					preparedStatement = connection.prepareStatement(query);	
					preparedStatement.setDate(1, controller_Heating[controllerNumber].diagnosticLastUpdate);
					preparedStatement.setBoolean(2, controller_Heating[controllerNumber].error);
					preparedStatement.executeUpdate();			
					break;
				}
			// if last state in Archive table is different, insert new entity
			if (!controller_Heating[controllerNumber].compare(temp_Controller_Heating)) {
				query = "INSERT INTO controller_Heating_Archive SELECT * FROM controller_Heating WHERE ControllerNo=(?)";
				preparedStatement = connection.prepareStatement(query);					
				preparedStatement.setInt(1, controllerNumber);
				preparedStatement.executeUpdate();
				}
			}
		// Check if SQL function timeout. Show message if yes.		
		long deltaTime = (getCurrentDate().getTime()-enrtyTime.getTime());
		if (deltaTime>TIMEOUT_SQL) 
			saveNoticeFrame("SQL", "DĹ‚ugi czas zapisu ramki danych : Typ["+controllerTyp+"]No["+controllerNumber+"] frame["
					+controllerFrameNumber+"] czas["+deltaTime+"ms]", false);
	}
	
	public void saveErrorFrame(String module, String description, boolean req_ack) throws SQLException {
		if (!isDBconnected()) return;

		state = connection.createStatement();
		query = "SELECT req_ack FROM Errors WHERE modul='"+module+"' AND description='"+description+"'";
		resultSet = state.executeQuery(query);
	
		while (resultSet.next()) {
			if (resultSet.getBoolean(1)) {
				resultSet.close();
				return;
			}	
		}
		resultSet.close();
		
		query = "INSERT INTO Errors (timeStamp,modul,description,req_ack,ack) VALUES(?,?,?,?,0)";
		preparedStatement = connection.prepareStatement(query);					
		preparedStatement.setDate(1, getCurrentDate());
		preparedStatement.setString(2, module);
		preparedStatement.setString(3, description);
		preparedStatement.setBoolean(4, req_ack);
		preparedStatement.executeUpdate();	
		System.out.println("BLAD:"+module+" - "+description);
	}
	
	public void saveNoticeFrame(String module, String description, boolean req_ack) throws SQLException {
		
		System.out.println(module+": "+description);
		
		if (!isDBconnected()) return;

		state = connection.createStatement();
		query = "SELECT req_ack FROM Notice WHERE modul='"+module+"' AND description='"+description+"'";
		try {
			resultSet = state.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		while (resultSet.next()) {
			if (resultSet.getBoolean(1)) {
				resultSet.close();
				return;
			}	
		}
		resultSet.close();

		query = "INSERT INTO Notice (timeStamp,modul,description,req_ack,ack) VALUES(?,?,?,?,0)";
		preparedStatement = connection.prepareStatement(query);					
		preparedStatement.setDate(1, getCurrentDate());
		preparedStatement.setString(2, module);
		preparedStatement.setString(3, description);
		preparedStatement.setBoolean(4, req_ack);
		preparedStatement.executeUpdate();
	}

	public void getLastValues(Controller_Heating controller_Heating, int controllerNumber) throws SQLException {
		state = connection.createStatement();
		query = "SELECT * FROM Controller_Heating WHERE ControllerNo="+controllerNumber;	
		resultSet = state.executeQuery(query);
		if (resultSet.next()) controller_Heating.setData(resultSet);
		resultSet.close();
	}
	
	public void getLastValues(Controller_Fireplace controller_Fireplace, int controllerNumber) throws SQLException {
		state = connection.createStatement();
		query = "SELECT * FROM Controller_Fireplace WHERE ControllerNo="+controllerNumber;
		resultSet = state.executeQuery(query);
		if (resultSet.next()) controller_Fireplace.setData(resultSet);
		resultSet.close();
	}
	
	public void getLastValues(Controller_Vent controller_Vent, int controllerNumber) throws SQLException {
		state = connection.createStatement();
		query = "SELECT * FROM Controller_Vent WHERE ControllerNo="+controllerNumber;
		resultSet = state.executeQuery(query);
		if (resultSet.next()) controller_Vent.setData(resultSet);
		resultSet.close();
	}
	
	public void getLastValues(Controller_Comfort controller_Comfort, int controllerNumber) throws SQLException {
		state = connection.createStatement();
		query = "SELECT * FROM Controller_Comfort WHERE ControllerNo="+controllerNumber;
		resultSet = state.executeQuery(query);
		if (resultSet.next()) controller_Comfort.setData(resultSet);
		resultSet.close();
	}

	public ResultSet receiveData(String query) throws SQLException {				// receive data
		if (!isDBconnected()) return null;
		state = connection.createStatement();	
		resultSet = state.executeQuery(query);
		return resultSet;
	}
	
	public void addNewHadware(String moduleName, int moduleNumber) throws SQLException {
		query = "INSERT INTO Hardware (type,number,name,IP1,IP2,IP3,IP4) VALUES(?,?,?,?,?,?,?)";
		int type = 0;
		switch (moduleName) {
			case EventManager.Wentylacja 	: type=3;	break;
			case EventManager.Ogrzewanie 	: type=14; 	break;
			case EventManager.Kominek 		: type=5; 	break;
			case EventManager.Ogrod			: type=6; 	break;
			case EventManager.Alarm			: type=7;	break;
			case EventManager.Lazienka	 	: type=8; 	break;
			case EventManager.Pogoda 		: type=9; 	break;
			case EventManager.Komfort 		: type=10; 	break;
		}
		preparedStatement = connection.prepareStatement(query);					
		preparedStatement.setInt(1, type);
		preparedStatement.setInt(2, moduleNumber);
		preparedStatement.setString(3, moduleName);
		preparedStatement.setInt(4, 0);
		preparedStatement.setInt(5, 0);
		preparedStatement.setInt(6, 0);
		preparedStatement.setInt(7, 0);
		preparedStatement.executeUpdate();
	}
	
	public 	ObservableList<Data_Structures.Module> getHadwareFromDB() throws SQLException {
		// list for module founded in DB
		ObservableList<Data_Structures.Module> modules = FXCollections.observableArrayList();
		
		state = connection.createStatement();
		query = "SELECT * FROM Hardware ORDER BY type,number";	
		resultSet = state.executeQuery(query);
		while (resultSet.next()) {
			int type = resultSet.getInt(1);
			int number = resultSet.getInt(2);
			String name = resultSet.getString(3);
			int ip1 = resultSet.getInt(4);
			int ip2 = resultSet.getInt(5);
			int ip3 = resultSet.getInt(6);
			int ip4 = resultSet.getInt(7);

			Date dateOld = Date.valueOf("2000-01-01");
			switch (type)	{
				case 3 : controller_Vent[number] = new Controller_Vent(type, number, name, false, ip1, ip2, ip3, ip4, dateOld, dateOld); break;
				case 5 : controller_Fireplace[number] = new Controller_Fireplace(type, number, name, false, ip1, ip2, ip3, ip4, dateOld, dateOld); break;
				case 10 : controller_Comfort[number] = new Controller_Comfort(type, number, name, false, ip1, ip2, ip3, ip4, dateOld, dateOld); break;
				case 14 : controller_Heating[number] = new Controller_Heating(type, number, name, false, ip1, ip2, ip3, ip4, dateOld, dateOld); break;
				default : saveErrorFrame("Hardware", "Zly typ sterownika["+type+"] w DB", true); break;
			}
			modules.add(new Data_Structures.Module(type, number, name, false, ip1, ip2, ip3, ip4, dateOld, dateOld));
		}
		resultSet.close();
		
		for (int number=0; number<15; number++) {
			if (controller_Heating[number] != null) getLastValues(controller_Heating[number],number);
			if (controller_Fireplace[number] != null) getLastValues(controller_Fireplace[number],number);
			if (controller_Vent[number] != null) getLastValues(controller_Vent[number],number);
			if (controller_Comfort[number] != null) getLastValues(controller_Comfort[number],number);
			if (controller_Heating[number] != null) getLastValues(controller_Heating[number],number);
		}
		return modules;
	}
	public String returnDBTableName(String moduleName) {
		String tableName = null;
		switch (moduleName) {
			case EventManager.Ogrzewanie : tableName = "Controller_Heating"; break;
			case EventManager.Kominek : tableName = "Controller_FirePlace"; break;
			case EventManager.Wentylacja : tableName = "Controller_Vent"; break;
			case EventManager.Komfort : tableName = "Controller_Comfort"; break;
		}
		return tableName;
	}
	
	public 	ObservableList<TableColumnProperty> getTableColumnNames(String moduleName) throws SQLException {		
		String tableName = returnDBTableName(moduleName);
		
		ObservableList<TableColumnProperty> columnNames = FXCollections.observableArrayList();

		state = connection.createStatement();	
		query = "PRAGMA table_info("+tableName+");";		
			
		resultSet = state.executeQuery(query);
		while (resultSet.next()) {
			columnNames.add(new TableColumnProperty(resultSet.getString(2), resultSet.getString(3)));
		}
		return columnNames;
	}
	
	public void hardwareListUpdate () throws SQLException {

		state = connection.createStatement();	
		query = "SELECT name,number FROM Hardware";
		resultSet = state.executeQuery(query);
		while (resultSet.next()) {
			ResultSet subResultSet=null;
			Statement subState=null;
			String subQuery = null;

			String moduleName = resultSet.getString(1);
			int moduleNo = resultSet.getInt(2);
			
			String tableName = returnDBTableName(moduleName);
			
			subState = connection.createStatement();
			subQuery = "SELECT frameLastUpdate,diagnosticLastUpdate FROM "+tableName+" WHERE controllerNo="+moduleNo;

			subResultSet = subState.executeQuery(subQuery);
			while (subResultSet.next()) {
				//TODO
				String updateQuery = "UPDATE Hardware SET frameLastUpdate=(?),diagnosticLastUpdate=(?) WHERE name='"+moduleName+"' AND number='"+moduleNo+"'";
				preparedStatement = connection.prepareStatement(updateQuery);					
				preparedStatement.setDate(1, subResultSet.getDate(1));
				preparedStatement.setDate(2, subResultSet.getDate(2));
				preparedStatement.executeUpdate();			
			}
		}
	}

	
	public Controller_Heating[] getController_Heating() {
		return controller_Heating;
	}
	public Controller_Fireplace[] getController_Fireplace() {
		return controller_Fireplace;
	}
	public Controller_Vent[] getController_Vent() {
		return controller_Vent;
	}
	
	public Controller_Comfort[] getController_Comfort() {
		return controller_Comfort;
	}
	
	public boolean isGUIinvalid() {
		return guiInvalid;
	}
	
	public void setGUIvalid() {
		guiInvalid = false;
	}

}
