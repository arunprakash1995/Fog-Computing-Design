package FogNode;

import java.net.InetAddress;
import java.net.UnknownHostException;
/*
 * This class holds the paramters and attributes associated with a Neighbor packet
 */
public class NeighborUpdate {
	
	public InetAddress neighborIPAddress; // IP address of the neighbor
    public String neighborHostName; //hostname of the neighbor
    public int neighbortcpPortNo; // TCP destination port number of the neighbor
    public int neighborudpPortNo; //UDP destination port number of the neighbor
    public long processTime; // queing delay of the neighbor node
     
    public void neighborupdate ( String neighHN ,InetAddress neighIP , int tcpPort , int udpPort){
        /*
         * This method initializes the parameters from the received packet
         */
    	neighborIPAddress = neighIP ;
    	neighborHostName = neighHN ;
        neighbortcpPortNo = tcpPort ;
        neighborudpPortNo = udpPort ;
        
    }
    
 
    NeighborUpdate( ){
    	/*
    	 * This method is a constructor
    	 */
        neighborHostName = null;
        neighborIPAddress = null;
        processTime = 0;
        neighbortcpPortNo = 0;
        neighborudpPortNo = 0;
        
    }
    
    public void printpara() {
    	/*
    	 * This method prints the neighbor information
    	 */
    	System.out.println(neighborHostName);
        System.out.println(neighborIPAddress);
        System.out.println(neighbortcpPortNo);
        System.out.println(neighborudpPortNo);
    }
    
    NeighborUpdate(String neighHN , int tcpPort , long processT) {
       /* 
        * This method initializes with the information from the TCP listener
        */
    	
    	try {
			neighborIPAddress = InetAddress.getByName(neighHN) ;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	
        neighborHostName = neighHN ;
        neighbortcpPortNo = tcpPort ;
        neighborudpPortNo = 0  ;
        processTime = processT;
    }
    
    NeighborUpdate( NeighborUpdate neigh){
    	/*
    	 * This is a copy constructor
    	 */
    	
        this.neighborHostName = neigh.neighborHostName;
        this.neighborIPAddress = neigh.neighborIPAddress;
        this.processTime = neigh.processTime;
        this.neighbortcpPortNo = neigh.neighbortcpPortNo;
        this.neighborudpPortNo = neigh.neighborudpPortNo;
    }
   
}    
    



