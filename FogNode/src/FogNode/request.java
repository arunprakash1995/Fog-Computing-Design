package FogNode;

import java.net.InetAddress;
/* 
 * This class holds the paramters and attributes associated with a request packet
 */

public class request {
    public long seqNumber; //sequence number of the packet
    public int msgtype; // message tupe of the request
    public int forwardLimit; //forward limit of the request
    public String iotHostName; //Host name of the iot request generator
    public int iotPortNumber; // Destination port number of the iot request generator
    public InetAddress iotIPAddress; // IP address of the request generator
    public String info; // appended information
    public long processTime; // Process time for the request
    
    public request() {
    	/* 
    	 * This method is a constructor to initialize the class request
    	 */
    	this.seqNumber = 0;
        this.msgtype = 0;
        this.forwardLimit = 0;
        this.iotHostName = null;
        this.iotPortNumber = 0;
        this.iotIPAddress = null;
        this.info = null;
        this.processTime=2*msgtype*1000;
    }
    
    public request (long SN,int MT,int FL,String IHN,InetAddress iotIP,int FPN,String IN){
        
        this.seqNumber = SN;
        this.msgtype = MT;
        this.forwardLimit = FL;
        this.iotHostName = IHN;
        this.iotPortNumber = FPN;
        this.iotIPAddress = iotIP;
        this.info = IN;
        this.processTime=2*msgtype*1000;
        
    }
    

	public void process_time() {
		/*
		 * This method calculates the process time from the message type 
		 */
		this.processTime=2*msgtype*1000;
		
	}
}

