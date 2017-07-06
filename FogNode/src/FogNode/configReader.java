package FogNode;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
/*
 * This class reads the config file from specified location on the disk
 */
public class configReader {

	public long interval;
	public long MaxResponseTime ;
	public String HostName;
	public NeighborUpdate[] neighbor;
	public int tcpHostPort;
	public int udpHostPort; 
	public int numNeighbor ;
	public String[] neighborHostName ;
	public InetAddress[] neighborIPAddress;
	public int[] neighbortcpPortNo;
	public int[] neighborudpPortNo;
	
	public configReader (Scanner in) {
		
		
		this.HostName = in.next() ; //Fog Node host name 
		this.tcpHostPort = in.nextInt(); // Fog node tcp listening port
		this.udpHostPort = in.nextInt(); // Fog node udp listening port
		this.interval = in.nextInt(); // Fog node interval for periodic update to neighbors of the queing delay
		this.MaxResponseTime = in.nextLong();// Max Response time of the fog node
	    numNeighbor = in.nextInt(); // Number neighbors to the fog node
	    neighborHostName = new  String[numNeighbor] ;
		neighborIPAddress = new InetAddress[numNeighbor] ;
		neighbortcpPortNo = new int[numNeighbor] ;
		neighborudpPortNo = new int[numNeighbor] ;
	    for (int i=0; i < numNeighbor ; ++i) {
			
		    neighborHostName[i] = in.next(); // hostname of the neighbor
			try {
				neighborIPAddress[i]= InetAddress.getByName(neighborHostName[i]) ;
			} catch (UnknownHostException ex) {
				System.out.println(ex);
				ex.printStackTrace();
			}
			neighbortcpPortNo[i] = in.nextInt(); // tcp listening port number of the neighbor
			neighborudpPortNo[i] = in.nextInt();// udp listening port number of the neighbor
		  }
	    
	    /*
	     *creating and updating information to the neighbor table
	     */
	    NeighborUpdate[] neighbor = new NeighborUpdate [numNeighbor] ;
	    
	    for (int i=0; i < numNeighbor ; ++i) {
	    neighbor[i] = new NeighborUpdate() ;
		neighbor[i].neighborupdate(neighborHostName[i],neighborIPAddress[i],neighbortcpPortNo[i],neighborudpPortNo[i]);
	   
	   }
	  in.close();	
	}
	
	
	
}
