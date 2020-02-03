package Data_Structures;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class Controller_Fireplace extends Data_Structures.Module{

	public Controller_Fireplace(int moduleTyp, int moduleNo, String moduleName, boolean error, int ip1, int ip2,
			int ip3, int ip4, Date lastUDP, Date lastDiagnose) {
		super(moduleTyp, moduleNo, moduleName, error, ip1, ip2, ip3, ip4, lastUDP, lastDiagnose);
	}

	public int mode;
	public boolean alarm;
	public boolean warning;
	public boolean pump;
	public boolean fireAlarm;
	public int throttle;
	public int pressure_emerency_water;
	
	public float tempSet;
	public float tempOutlet;
	public float tempInlet;
	public float tempChimney;

	// diagnose
	//TMP will be extend	

	public void setData(int[] packetData) {
		int controllerFrameNumber = packetData[2];

		switch (controllerFrameNumber) {
			case 0: // standard frame 0	
				mode = packetData[3] >> 6;
				alarm = bitStatus(packetData[3], 5);
				warning = bitStatus(packetData[3], 4);
				pump = bitStatus(packetData[3], 3);
				fireAlarm = bitStatus(packetData[3], 2);
				throttle = packetData[4];
				pressure_emerency_water = packetData[5];
				
				tempSet = (float)packetData[6] / 2;
				tempOutlet = (float)packetData[7] / 2;
				tempInlet = (float)packetData[8] / 2;
				tempChimney = (float)packetData[9] / 2;
				
				if (!isUDPreqSendNewValues()) initialization_NewValues();

			break;
			
			case 200: //diagnostic frame
			break;
		}
	}
	
	public void setData(ResultSet resultSet) throws SQLException {
		mode = resultSet.getInt(1);
		alarm=resultSet.getBoolean(2);
		warning= resultSet.getBoolean(3);
		pump = resultSet.getBoolean(4);
		fireAlarm = resultSet.getBoolean(5);
		throttle = resultSet.getInt(6);
		pressure_emerency_water = resultSet.getInt(7);
		
		tempSet = resultSet.getFloat(8);
		tempOutlet = resultSet.getFloat(9);
		tempInlet = resultSet.getFloat(10);
		tempChimney = resultSet.getFloat(11);
		
		frameLastUpdate  = resultSet.getDate(12);
		diagnosticLastUpdate   = resultSet.getDate(13);
		error = resultSet.getBoolean(14);
	}
	
	public boolean compare(Controller_Fireplace controller_Heating2) {
		if ((controller_Heating2.mode != mode) || (controller_Heating2.alarm != alarm) || (controller_Heating2.warning != warning)
			|| (controller_Heating2.pump != pump) || (controller_Heating2.fireAlarm != fireAlarm) || (controller_Heating2.throttle != throttle)
			|| (controller_Heating2.pressure_emerency_water != pressure_emerency_water) || (controller_Heating2.tempSet != tempSet) 
			|| (controller_Heating2.tempOutlet != tempOutlet) || (controller_Heating2.tempInlet!= tempInlet)
			|| (controller_Heating2.tempChimney != tempChimney) || (controller_Heating2.error != error))
		{
			//Structure's are not the same so return false
			return false;
		}
		else return true;
	}
	
	public void initialization_NewValues() {
		
	}

	public void printData() {
		System.out.println(mode+","+alarm+","+warning+","+pump+","+fireAlarm+","+throttle+","+pressure_emerency_water+
				","+tempSet+","+tempOutlet+","+tempInlet+","+frameLastUpdate+","+diagnosticLastUpdate+","+error);
	}
}
