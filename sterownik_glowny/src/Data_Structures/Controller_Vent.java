package Data_Structures;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class Controller_Vent extends Data_Structures.Module{

	public Controller_Vent(int moduleTyp, int moduleNo, String moduleName, boolean error, int ip1, int ip2,
			int ip3, int ip4, Date lastUDP, Date lastDiagnose) {
		super(moduleTyp, moduleNo, moduleName, error, ip1, ip2, ip3, ip4, lastUDP, lastDiagnose);
		
		//SET AFTER POWER UP
		for (int i=0; i<12; i++)
			hour[i] = 0;
		//8:00 - 8:15
		hour[4] = 128;
		//16:00-16:30
		hour[8] = 192; 
		//20:30-21:00
		hour[10] = 48;
	}

	public boolean fanON;
	public int[] hour = new int[12];
	public int[] NVhour = new int[12];


	// diagnose
	//TMP will be extend	

	//copy received UDP data into structure
	public void setData(int[] packetData) {
		int controllerFrameNumber = packetData[2];

		switch (controllerFrameNumber) {
			case 0: // standard frame 0	
				fanON = bitStatus(packetData[3], 7);
				
				// copy relevant data received into array
				for (int i=0; i<12; i++)
						hour[i] = packetData[4+i];
				
				if (!isUDPreqSendNewValues()) initialization_NewValues();
			break;
			
			case 200: //diagnostic frame
			break;
		}
	}
	//copy received SQL Data into structure
	public void setData(ResultSet resultSet) throws SQLException {
		frameLastUpdate  = resultSet.getDate(2);
		diagnosticLastUpdate   = resultSet.getDate(3);
		error = resultSet.getBoolean(4);
		
		fanON = resultSet.getBoolean(5);
		
		for (int i=0; i<12; i++)
			hour[i] = resultSet.getInt(6+i);	
	}
	
	public boolean compare(Controller_Vent controller_Vent) {		
		//Structure's are not the same then return false
		if (controller_Vent.fanON != fanON) return false;
		
		for (int i=0; i<12; i++)
			if (controller_Vent.hour[i] != hour[i])
					return false;
		
		// if doesn't return above it's mean all data the same and return true
		return true;
	}
	
	// Check if values are Up to Date	
	public boolean  isHour01UpToDate(){
		//return false if value not up to date	
		return (hour[0] == NVhour[0]);
	}
	public boolean  isHour23UpToDate(){
		//return false if value not up to date	
		return (hour[1] == NVhour[1]);
	}
	public boolean  isHour45UpToDate(){
		//return false if value not up to date	
		return (hour[2] == NVhour[2]);
	}
	public boolean  isHour67UpToDate(){
		//return false if value not up to date	
		return (hour[3] == NVhour[3]);
	}
	public boolean  isHour89UpToDate(){
		//return false if value not up to date	
		return (hour[4] == NVhour[4]);
	}
	public boolean  isHour1011UpToDate(){
		//return false if value not up to date	
		return (hour[5] == NVhour[5]);
	}
	public boolean  isHour1213UpToDate(){
		//return false if value not up to date	
		return (hour[6] == NVhour[6]);
	}
	public boolean  isHour1415UpToDate(){
		//return false if value not up to date	
		return (hour[7] == NVhour[7]);
	}
	public boolean  isHour1617UpToDate(){
		//return false if value not up to date	
		return (hour[8] == NVhour[8]);
	}
	public boolean  isHour1819UpToDate(){
		//return false if value not up to date	
		return (hour[9] == NVhour[9]);
	}
	public boolean  isHour2021UpToDate(){
		//return false if value not up to date	
		return (hour[10] == NVhour[10]);
	}
	public boolean  isHour2223UpToDate(){
		//return false if value not up to date	
		return (hour[11] == NVhour[11]);
	}
	
	public boolean allDataUpToDate() {		
		// return false if not all values up to date		
		return (isHour01UpToDate() && isHour23UpToDate() && isHour45UpToDate() && isHour67UpToDate() && isHour89UpToDate() &&
				isHour1011UpToDate() && isHour1213UpToDate() && isHour1415UpToDate() && isHour1617UpToDate() && isHour1819UpToDate() &&
				isHour2021UpToDate() &&isHour2223UpToDate());
	}
	
	public void initialization_NewValues() {
		for (int i=0; i<12; i++)
			NVhour[i] = hour[i];
	}

	// Check if values are Up to Date	
	public void  setnVhour01(int nVhour){
		NVhour[0] = nVhour;
		setUDPreqSendNewValues(true);
	}
	public void  setnVhour23(int nVhour){
		NVhour[1] = nVhour;
		setUDPreqSendNewValues(true);
	}
	public void  setnVhour45(int nVhour){
		NVhour[2] = nVhour;
		setUDPreqSendNewValues(true);
	}
	public void  setnVhour67(int nVhour){
		NVhour[3] = nVhour;
		setUDPreqSendNewValues(true);
	}
	public void  setnVhour89(int nVhour){
		NVhour[4] = nVhour;
		setUDPreqSendNewValues(true);
	}
	public void  setnVhour1011(int nVhour){
		NVhour[5] = nVhour;
		setUDPreqSendNewValues(true);
	}
	public void  setnVhour1213(int nVhour){
		NVhour[6] = nVhour;
		setUDPreqSendNewValues(true);
	}
	public void  setnVhour1415(int nVhour){
		NVhour[7] = nVhour;
		setUDPreqSendNewValues(true);
	}
	public void  setnVhour1617(int nVhour){
		NVhour[8] = nVhour;
		setUDPreqSendNewValues(true);
	}
	public void  setnVhour1819(int nVhour){
		NVhour[9] = nVhour;
		setUDPreqSendNewValues(true);
	}
	public void  setnVhour2021(int nVhour){
		NVhour[10] = nVhour;
		setUDPreqSendNewValues(true);
	}
	public void  setnVhour2223(int nVhour){
		NVhour[11] = nVhour;
		setUDPreqSendNewValues(true);
	}
	

	public void printData() {
	}
}
