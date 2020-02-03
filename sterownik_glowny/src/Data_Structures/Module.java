package Data_Structures;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Module {
	public int moduleTyp;
	public int moduleNo;
	public String moduleName;
	public int IP1;
	public int IP2;
	public int IP3;
	public int IP4;
	public boolean error = false;
	private boolean UDPreqSendNewValues = false;								// when values changed in application set request to send new values through UDP

	public Date frameLastUpdate;
	public Date diagnosticLastUpdate; 

	public Module(int moduleTyp,int moduleNo,String moduleName, boolean error, int IP1, int IP2, int IP3, int IP4, Date frameLastUpdate, Date diagnosticLastUpdate) {
		this.moduleTyp = moduleTyp;
		this.moduleNo = moduleNo;
		this.moduleName = moduleName;
		this.error = error;
		this.IP1 = IP1;
		this.IP2 = IP2;
		this.IP3 = IP3;
		this.IP4 = IP4;
		this.frameLastUpdate = frameLastUpdate;
		this.diagnosticLastUpdate = diagnosticLastUpdate;
	}
	
	public int getModuleTyp() {
		return moduleTyp;
	}

	public int getModuleNo() {
		return moduleNo;
	}
	
	public int getIP1() {
		return IP1;
	}
	public int getIP2() {
		return IP2;
	}
	public int getIP3() {
		return IP3;
	}
	public int getIP4() {
		return IP4;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public boolean isError() {
		return error;
	}

	public String getLastUDP() {
		DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		if (frameLastUpdate == null) return "";
		else return formatter.format(frameLastUpdate);
	}

	public String getLastDiagnose() {
		DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		if (diagnosticLastUpdate == null) return "";
		else return formatter.format(diagnosticLastUpdate);
	}

	public void setModuleTyp(int moduleTyp) {
		this.moduleTyp = moduleTyp;
	}

	public void setModuleNo(int moduleNo) {
		this.moduleNo = moduleNo;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void setIP1(int iP1) {
		IP1 = iP1;
	}

	public void setIP2(int iP2) {
		IP2 = iP2;
	}

	public void setIP3(int iP3) {
		IP3 = iP3;
	}

	public void setIP4(int iP4) {
		IP4 = iP4;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public void setLastUDP(Date lastUDP) {
		this.frameLastUpdate = lastUDP;
	}

	public void setLastDiagnostic(Date lastDiagnostic) {
		this.diagnosticLastUpdate = lastDiagnostic;
	}
	public boolean isUDPreqSendNewValues() {
		return UDPreqSendNewValues;
	}

	public void setUDPreqSendNewValues(boolean uDPreqSendNewValues) {
		UDPreqSendNewValues = uDPreqSendNewValues;
	}
	
	// return bit status from corresponding byte according to position in byte
	public boolean bitStatus(int data, int bytePos) {
		if (((data >> bytePos) & 1) == 1) return true;
		else return false;
	}
	
	public boolean cmp(int value1, int value2) {
		if (value1!=value2) {
			System.out.println("int FALSE");

			return false;
		}
		else return true;
		
	}

	public boolean cmp(double value1, double value2, double tolerance) {
		if (Math.abs(value1-value2) > tolerance) {
			System.out.println("FLOAT FALSE");
			
			return false;
		}
		else return true;
	}
	
	public boolean cmp(boolean value1, boolean value2) {
		if (value1!=value2) {
			System.out.println("BOOL FALSE");
			return false;
		}
		else return true;
	}
	
}
