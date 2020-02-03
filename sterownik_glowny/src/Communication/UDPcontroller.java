package Communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class UDPcontroller {
	// reference to SQL class
	DataBaseController dataBaseController;
	
	// UDP variables
    private static DatagramSocket datagramSocket;
    private static final int BUFFER_SIZE = 128;
    private static byte[] buffer;
    private static int[] packetData;
    int localPort = 6000;
    private static final int PACKET_SIZE_MODUL_3 = 16;					// length of UDP data from module 3 "wentylacja"
    private static final int PACKET_SIZE_MODUL_10 = 31;					// length of UDP data from module 10 "komfort"
    private static final int PACKET_SIZE_MODUL_10_DIAG = 7;				// length of UDP diagnose from module 10 "komfort" 
    private static final int PACKET_SIZE_MODUL_14 = 21;					// length of UDP data from module 14 "Ogrzewanie"
    private static final int PACKET_SIZE_MODUL_14_DIAG = 8;				// length of UDP diagnose from module 14 "Ogrzewanie" 
	
	public UDPcontroller(DataBaseController dataBaseController) {
		this.dataBaseController = dataBaseController;
		//TMP		
		try {
			connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void connect() throws SQLException {
        try {
            datagramSocket = new DatagramSocket(localPort);  
            datagramSocket.setBroadcast(true);

    		new Thread(new UDPlistener()).start();	
    		dataBaseController.saveNoticeFrame("UDP", "Socket otwarty. Port nasluchu :"+localPort,false);
 
        } catch (SocketException ex) {
        	//TMP
        	dataBaseController.saveErrorFrame("UDP", "Blad :"+ex.toString(), false);
        }	     
    }	
	
	private boolean packetDataCorrect(int[] packetData, int packetLength) throws SQLException {
		boolean packetCorrect =false;
		if (packetData[2] == 200) {			// Only for diagnose frames
			switch (packetData[0]) {
				case 10:if (packetLength == PACKET_SIZE_MODUL_10_DIAG) packetCorrect = true; break;
				case 14:if (packetLength == PACKET_SIZE_MODUL_14_DIAG) packetCorrect = true; break;
				default : System.out.println("Zly format numeru modulu :["+packetData[0]+"]");
			}
		}
		else {			
			switch (packetData[0]) {		// Only for standard frames
				case 1: packetCorrect = true; break;
				case 3: if (packetLength == PACKET_SIZE_MODUL_3) packetCorrect = true; break;
				case 10:if (packetLength == PACKET_SIZE_MODUL_10) packetCorrect = true;break;
				case 14:if (packetLength == PACKET_SIZE_MODUL_14) packetCorrect = true;break;
				default : System.out.println("Zly format numeru modulu :["+packetData[0]+"]");
			}
		}
		//TMP first print message only to show fault. Then send fault to SQL
		if (!packetCorrect) dataBaseController.saveNoticeFrame("UDP", "Niekompletny pakiet. Modul ["+packetData[0]+"] odebrano ["+packetLength+"]",false);
		return packetCorrect;
	}
	
	public void UDPsend(byte[] packetData) {	

  		DatagramPacket dp = null;
 
		try {
			//Prepare broadcast address
			byte[] broadcastAddress = InetAddress.getLocalHost().getAddress();
			broadcastAddress[3] = (byte)0xff;
			dp = new DatagramPacket(packetData, packetData.length, InetAddress.getByAddress(broadcastAddress), localPort);			
		
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			datagramSocket.send(dp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public class UDPlistener implements Runnable {
		@Override
		public void run() {
			while (true) {
	            buffer = new byte[BUFFER_SIZE];
	            DatagramPacket packet = new DatagramPacket(buffer, BUFFER_SIZE);
	            packetData = new int[BUFFER_SIZE];
	            
	            try {
					datagramSocket.receive(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}

	            
	            if (packet.getLength() > 0) {
	            	for (int i=0; i<packet.getLength(); i++)
	            		packetData[i] = (packet.getData()[i] & 0xff);				// 0xFF to change values to unsigned int

	            	System.out.print("UDP=");
		            for (int i=0; i<packet.getLength(); i++)
	            		System.out.print("["+packetData[i] + "]");
		            System.out.println();
	            }
	            try {
					if (packetDataCorrect(packetData, packet.getLength()))
						dataBaseController.saveUDPframe(packetData);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}	
		}
	}
}
