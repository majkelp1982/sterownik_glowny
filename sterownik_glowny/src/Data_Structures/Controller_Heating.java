package Data_Structures;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class Controller_Heating extends Data_Structures.Module{

	public Controller_Heating(int moduleTyp, int moduleNo, String moduleName, boolean error, int ip1, int ip2, int ip3,
			int ip4, Date lastUDP, Date lastDiagnose) {
		super(moduleTyp, moduleNo, moduleName, error, ip1, ip2, ip3, ip4, lastUDP, lastDiagnose);
		// TODO Auto-generated constructor stub
	}

	public int heatSourceActive;
	public boolean pump_InHouse;
	public boolean pump_UnderGround;
	public boolean reqHeatingPumpOn;
	public boolean cheapTariffOnly;
	public boolean NVcheapTariffOnly;
	public boolean heatingActivated;
	public boolean NVheatingActivated;
	public boolean antyLegionellia;
	public boolean NVantyLegionellia;
	public int valve_3way;
	public int valve_bypass;
	public boolean[] zone = new boolean[7];

	public float reqTempBuforCO;
	public float NVreqTempBuforCO;
	public float reqTempBuforCWU;
	public float NVreqTempBuforCWU;

	public float tBuffCOdol;
	public float tBuffCOsrodek;
	public float tBuffCOgora;
	public float tBuffCWUdol;
	public float tBuffCWUsrodek;
	public float tBuffCWUgora;
	
	public float tZasilanie;
	public float tPowrot;
	public float tDolneZrodlo;
	public float tKominek;
	public float tRozdzielacze;
	public float tPowrotParter;
	public float tPowrotPietro;
	private int tmp;

	public void setData(int[] packetData) {
		int controllerFrameNumber = packetData[2];
		
		switch (controllerFrameNumber) {
			case 0: // standard frame 0	
				heatSourceActive = packetData[3] >> 6;
				pump_InHouse = bitStatus(packetData[3], 5);
				pump_UnderGround = bitStatus(packetData[3], 4);
				reqHeatingPumpOn = bitStatus(packetData[3], 3);
				cheapTariffOnly = bitStatus(packetData[3], 2);
				heatingActivated = bitStatus(packetData[3], 1);
				antyLegionellia = bitStatus(packetData[3], 0);
				
				valve_3way = packetData[4] >> 6;
				tmp = packetData[4] >> 6; valve_bypass = (packetData[4] - (tmp << 6));
				
				zone[0] = bitStatus(packetData[5], 7);
				zone[1] = bitStatus(packetData[5], 6);
				zone[2] = bitStatus(packetData[5], 5);
				zone[3] = bitStatus(packetData[5], 4);
				zone[4] = bitStatus(packetData[5], 3);
				zone[5] = bitStatus(packetData[5], 2);
				zone[6] = bitStatus(packetData[5], 1);
				
				reqTempBuforCO = (float)packetData[6] / 2;
				reqTempBuforCWU = (float)packetData[7] / 2;

				tBuffCOdol = (float)packetData[8] / 2;
				tBuffCOsrodek = (float)packetData[9] / 2;
				tBuffCOgora = (float)packetData[10] / 2;
				tBuffCWUdol = (float)packetData[11] / 2;
				tBuffCWUsrodek = (float)packetData[12] / 2;
				tBuffCWUgora = (float)packetData[13] / 2;
				
				tZasilanie = (float)packetData[14] / 2;
				tPowrot = (float)packetData[15] / 2;
				tDolneZrodlo = (float)packetData[16] / 2;
				tKominek = (float)packetData[17] / 2;
				tRozdzielacze = (float)packetData[18] / 2;
				tPowrotParter = (float)packetData[19] / 2;
				tPowrotPietro = (float)packetData[20] / 2;
								
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

		heatSourceActive = resultSet.getInt(5);
		pump_InHouse=resultSet.getBoolean(6);
		pump_UnderGround = resultSet.getBoolean(7);
		reqHeatingPumpOn = resultSet.getBoolean(8);
		cheapTariffOnly = resultSet.getBoolean(9);
		heatingActivated = resultSet.getBoolean(10);
		antyLegionellia = resultSet.getBoolean(11);
		valve_3way = resultSet.getInt(12);
		valve_bypass = resultSet.getInt(13);
		zone[0] = resultSet.getBoolean(14);
		zone[1] = resultSet.getBoolean(15);
		zone[2] = resultSet.getBoolean(16);
		zone[3] = resultSet.getBoolean(17);
		zone[4] = resultSet.getBoolean(18);
		zone[5] = resultSet.getBoolean(19);
		zone[6] = resultSet.getBoolean(20);

		reqTempBuforCO = resultSet.getFloat(21);
		reqTempBuforCWU = resultSet.getFloat(22);
		
		tBuffCOdol = resultSet.getFloat(23);
		tBuffCOsrodek = resultSet.getFloat(24);
		tBuffCOgora = resultSet.getFloat(25);
		tBuffCWUdol = resultSet.getFloat(26);
		tBuffCWUsrodek = resultSet.getFloat(27);
		tBuffCWUgora = resultSet.getFloat(28);
		
		tZasilanie = resultSet.getFloat(29);
		tPowrot = resultSet.getFloat(30);
		tDolneZrodlo = resultSet.getFloat(31);
		tKominek = resultSet.getFloat(32);
		tRozdzielacze = resultSet.getFloat(33);
		tPowrotParter = resultSet.getFloat(34);
		tPowrotPietro = resultSet.getFloat(35);
	}
	
	public boolean compare(Controller_Heating controller_Heating) {
		boolean result=true;
		if (result) result = cmp(controller_Heating.heatSourceActive, heatSourceActive);
		if (result) result = cmp(controller_Heating.cheapTariffOnly,cheapTariffOnly);
		if (result) result = cmp(controller_Heating.pump_UnderGround,pump_UnderGround);
		if (result) result = cmp(controller_Heating.reqHeatingPumpOn,reqHeatingPumpOn);
		if (result) result = cmp(controller_Heating.cheapTariffOnly,cheapTariffOnly);
		if (result) result = cmp(controller_Heating.heatingActivated,heatingActivated);
		if (result) result = cmp(controller_Heating.antyLegionellia,antyLegionellia);
		if (result) result = cmp(controller_Heating.valve_3way,valve_3way);
		if (result) result = cmp(controller_Heating.valve_bypass,valve_bypass);
		if (result) result = cmp(controller_Heating.zone[0],zone[0]);
		if (result) result = cmp(controller_Heating.zone[1],zone[1]);
		if (result) result = cmp(controller_Heating.zone[2],zone[2]);
		if (result) result = cmp(controller_Heating.zone[3],zone[3]);
		if (result) result = cmp(controller_Heating.zone[4],zone[4]);
		if (result) result = cmp(controller_Heating.zone[5],zone[5]);
		if (result) result = cmp(controller_Heating.zone[6],zone[6]);
		if (result) result = cmp(controller_Heating.reqTempBuforCO,reqTempBuforCO,0);
		if (result) result = cmp(controller_Heating.reqTempBuforCWU,reqTempBuforCWU,0);
		if (result) result = cmp(controller_Heating.tBuffCOdol,tBuffCOdol,2);
		if (result) result = cmp(controller_Heating.tBuffCOsrodek,tBuffCOsrodek,2);
		if (result) result = cmp(controller_Heating.tBuffCOgora,tBuffCOgora,2);
		if (result) result = cmp(controller_Heating.tBuffCWUdol,tBuffCWUdol,2);
		if (result) result = cmp(controller_Heating.tBuffCWUsrodek,tBuffCWUsrodek,2);
		if (result) result = cmp(controller_Heating.tBuffCWUgora,tBuffCWUgora,2);
		if (result) result = cmp(controller_Heating.tZasilanie,tZasilanie,1);
		if (result) result = cmp(controller_Heating.tPowrot,tPowrot,1);
		if (result) result = cmp(controller_Heating.tDolneZrodlo,tDolneZrodlo,1);
		if (result) result = cmp(controller_Heating.tKominek,tKominek,1);
		if (result) result = cmp(controller_Heating.tRozdzielacze,tRozdzielacze,1);
		if (result) result = cmp(controller_Heating.tPowrotParter,tPowrotParter,1);
		if (result) result = cmp(controller_Heating.tPowrotPietro,tPowrotPietro,1);		
		return result;
/*
 		if ((controller_Heating.heatSourceActive != heatSourceActive) || (controller_Heating.pump_InHouse != pump_InHouse) || (controller_Heating.pump_UnderGround != pump_UnderGround)
 
			|| (controller_Heating.reqHeatingPumpOn != reqHeatingPumpOn) || (controller_Heating.cheapTariffOnly != cheapTariffOnly) || (controller_Heating.heatingActivated != heatingActivated) 
			|| (controller_Heating.antyLegionellia != antyLegionellia) || (controller_Heating.valve_3way != valve_3way) || (controller_Heating.valve_bypass != valve_bypass)
			|| (controller_Heating.zone[0] != zone[0]) || (controller_Heating.zone[1] != zone[1]) || (controller_Heating.zone[2] != zone[2]) || (controller_Heating.zone[3] != zone[3])
			|| (controller_Heating.zone[4] != zone[4]) || (controller_Heating.zone[5] != zone[5]) || (controller_Heating.zone[6] != zone[6])
			|| (controller_Heating.reqTempBuforCO != reqTempBuforCO) || (controller_Heating.reqTempBuforCWU != reqTempBuforCWU) 
			|| (controller_Heating.tBuffCOdol != tBuffCOdol) || (controller_Heating.tBuffCOsrodek != tBuffCOsrodek) 
			|| (controller_Heating.tBuffCOgora != tBuffCOgora) || (controller_Heating.tBuffCWUdol != tBuffCWUdol) 
			|| (controller_Heating.tBuffCWUsrodek != tBuffCWUsrodek) || (controller_Heating.tBuffCWUgora != tBuffCWUgora) 
			|| (controller_Heating.tZasilanie != tZasilanie) || (controller_Heating.tPowrot != tPowrot) 
			|| (controller_Heating.tDolneZrodlo != tDolneZrodlo) || (controller_Heating.tKominek != tKominek) 
			|| (controller_Heating.tRozdzielacze != tRozdzielacze) || (controller_Heating.tPowrotParter != tPowrotParter) 
			|| (controller_Heating.tPowrotPietro != tPowrotPietro) || (controller_Heating.error != error))
		{
			//Structure's are not the same so return false
			return false;
		}
		else return true;
*/
	}

	public void initialization_NewValues() {
		NVcheapTariffOnly = cheapTariffOnly;
		NVheatingActivated = heatingActivated;
		NVantyLegionellia= antyLegionellia;
		NVreqTempBuforCO = reqTempBuforCO;
		NVreqTempBuforCWU = reqTempBuforCWU;
	}
	
	public void printData() {
	}
	
	// Check if values are Up to Date
	public boolean isCheapTariffOnlyUpToDate() {
		//return false if value not up to date
		return (cheapTariffOnly == NVcheapTariffOnly);
	}
	public boolean isHeatingActivatedOnUpToDate() {
		//return false if value not up to date
		return (heatingActivated == NVheatingActivated);
	}
	public boolean isAntyLegionelliaOnUpToDate() {
		//return false if value not up to date
		return (antyLegionellia == NVantyLegionellia);
	}
	public boolean isReqTempBuforCOUpToDate() {
		//return false if value not up to date
		return (reqTempBuforCO == NVreqTempBuforCO);
	}
	public boolean isReqTempBuforCWUUpToDate() {
		//return false if value not up to date
		return (reqTempBuforCWU == NVreqTempBuforCWU);
	}

	public boolean allDataUpToDate() {
		// return false if not all values up to date
		return !(!isCheapTariffOnlyUpToDate() || !isHeatingActivatedOnUpToDate() || !isAntyLegionelliaOnUpToDate() || !isReqTempBuforCOUpToDate() || !isReqTempBuforCWUUpToDate());
	}


	public void setNVcheapTariffOnly(boolean nVcheapTariffOnly) {
		NVcheapTariffOnly = nVcheapTariffOnly;
		setUDPreqSendNewValues(true);
	}

	public void setNVheatingActivated(boolean nVheatingActivated) {
		NVheatingActivated = nVheatingActivated;
		setUDPreqSendNewValues(true);
	}

	public void setNVantyLegionellia(boolean nVantyLegionellia) {
		NVantyLegionellia = nVantyLegionellia;
		setUDPreqSendNewValues(true);
	}

	public void setNVreqTempBuforCO(float nVreqTempBuforCO) {
		NVreqTempBuforCO = nVreqTempBuforCO;
		setUDPreqSendNewValues(true);
	}

	public void setNVreqTempBuforCWU(float nVreqTempBuforCWU) {
		NVreqTempBuforCWU = nVreqTempBuforCWU;
		setUDPreqSendNewValues(true);
	}

}
