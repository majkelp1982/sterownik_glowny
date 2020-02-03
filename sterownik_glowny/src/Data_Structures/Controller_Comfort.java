package Data_Structures;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class Controller_Comfort extends Data_Structures.Module{
	public Zone[] zone = new Zone[7];

	public Controller_Comfort(int moduleTyp, int moduleNo, String moduleName, boolean error, int ip1, int ip2, int ip3,
			int ip4, Date lastUDP, Date lastDiagnose) {
		super(moduleTyp, moduleNo, moduleName, error, ip1, ip2, ip3, ip4, lastUDP, lastDiagnose);
		// TODO Auto-generated constructor stub
		for (int i=0; i<7; i++)
			zone[i] = new Zone();
	}
	public class Zone {
		public float isTemp=0;
		public float reqTemp=0;
		public float NVreqTemp = 0;
		public int humidity=0;
		
	}

	public void setData(int[] packetData) {
		int controllerFrameNumber = packetData[2];
		switch (controllerFrameNumber) {
			case 0: // standard frame 0
				for (int i=0; i<7; i++) {
					zone[i].isTemp =  (float)(packetData[i*4+3]*10+packetData[i*4+4])/10;
					zone[i].reqTemp =  (float)(packetData[i*4+5]/2.00);
					zone[i].humidity = (int) (packetData[i*4+6]);				
				}
				
				if (!isUDPreqSendNewValues()) initialization_NewValues();
			break;
			
			case 200: //diagnostic frame
			break;
		}
	}
	
	public void setData(ResultSet resultSet) throws SQLException {
		frameLastUpdate=resultSet.getDate(2);
		diagnosticLastUpdate=resultSet.getDate(3);
		error= resultSet.getBoolean(4);
		for (int i=0; i<7; i++) {
			zone[i].isTemp = (float)resultSet.getFloat(i*3+5);
			zone[i].reqTemp = (float)resultSet.getFloat(i*3+6);
			zone[i].humidity = resultSet.getInt(i*3+7);
		}

	}
	
	public boolean compare(Controller_Comfort controller_comfort) {
		//return FALSE if compare data are different
		boolean result = true;
		for (int i=0; i<7; i++) {
			if (result) result = cmp(controller_comfort.zone[i].isTemp,zone[i].isTemp,0.5);
			if (result) result = cmp(controller_comfort.zone[i].reqTemp,zone[i].reqTemp,0);
			if (result) result = cmp(controller_comfort.zone[i].humidity,zone[i].humidity,5);			
		}
		
		return result;
		
//			if ((controller_comfort.zone[i].isTemp != zone[i].isTemp) || (controller_comfort.zone[i].reqTemp != zone[i].reqTemp) || 
//					(controller_comfort.zone[i].humidity != zone[i].humidity))
//				temp = true;
//		if ((controller_comfort.error != error) || (temp))
//		{			
//			//Structure's are not the same so return false
//			return false;
//		}
//		else return true;

	}
	
	public boolean  isReqZ0UpToDate(){
		//return false if value not up to date	
		return (zone[0].reqTemp == zone[0].NVreqTemp);
	}

	public boolean  isReqZ1UpToDate(){
		//return false if value not up to date	
		return (zone[1].reqTemp == zone[1].NVreqTemp);
	}
	public boolean  isReqZ2UpToDate(){
		//return false if value not up to date	
		return (zone[2].reqTemp == zone[2].NVreqTemp);
	}
	public boolean  isReqZ3UpToDate(){
		//return false if value not up to date	
		return (zone[3].reqTemp == zone[3].NVreqTemp);
	}
	public boolean  isReqZ4UpToDate(){
		//return false if value not up to date	
		return (zone[4].reqTemp == zone[4].NVreqTemp);
	}
	public boolean  isReqZ5UpToDate(){
		//return false if value not up to date	
		return (zone[5].reqTemp == zone[5].NVreqTemp);
	}
	public boolean  isReqZ6UpToDate(){
		//return false if value not up to date	
		return (zone[6].reqTemp == zone[6].NVreqTemp);
	}
	
	public boolean allDataUpToDate() {
		// return false if not all values up to date
		return !(!isReqZ0UpToDate() || !isReqZ1UpToDate() || !isReqZ2UpToDate() || !isReqZ3UpToDate() 
				|| !isReqZ4UpToDate() || !isReqZ5UpToDate() || !isReqZ6UpToDate());
	}

	public void setNVreqZ0(float NVreqTempZ0) {
		zone[0].NVreqTemp = NVreqTempZ0;
		setUDPreqSendNewValues(true);
	}

	public void setNVreqZ1(float NVreqTempZ1) {
		zone[1].NVreqTemp = NVreqTempZ1;
		setUDPreqSendNewValues(true);
	}

	public void setNVreqZ2(float NVreqTempZ2) {
		zone[2].NVreqTemp = NVreqTempZ2;
		setUDPreqSendNewValues(true);
	}

	public void setNVreqZ3(float NVreqTempZ3) {
		zone[3].NVreqTemp = NVreqTempZ3;
		setUDPreqSendNewValues(true);
	}

	public void setNVreqZ4(float NVreqTempZ4) {
		zone[4].NVreqTemp = NVreqTempZ4;
		setUDPreqSendNewValues(true);
	}

	public void setNVreqZ5(float NVreqTempZ5) {
		zone[5].NVreqTemp = NVreqTempZ5;
		setUDPreqSendNewValues(true);
	}

	public void setNVreqZ6(float NVreqTempZ6) {
		zone[6].NVreqTemp = NVreqTempZ6;
		setUDPreqSendNewValues(true);
	}

	public void initialization_NewValues() {
		for (int i=0; i<7; i++)
			zone[i].NVreqTemp = zone[i].reqTemp;
	}

	
	public void printData() {
		//TMP
	}
}
