package FogNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class Main {
   
	/* 
	 * The Main Function of Fog Node
	 */
	
	public static void main(String[] args) throws InterruptedException, IOException {

		
		Scanner infile  ;
 		if (args.length > 0) { // If the input file is specified
			File input = new File(args[0]) ; //Read the file
			infile = new Scanner(input); // scanner of the input file
		} else { 
			System.out.println("Provide the Configure File as First input argument");
			infile = null ;
			System.exit(1);
		}
        
 		configReader fileReader = new configReader (infile) ; // read the config file
 	
 		NeighborUpdate[] neighbor = new NeighborUpdate[fileReader.numNeighbor] ; 
		
		for(int i=0 ; i < fileReader.numNeighbor ;++i) // set the neighbor information
		{
			neighbor[i] = new NeighborUpdate() ;
			neighbor[i].neighborupdate(fileReader.neighborHostName[i],fileReader.neighborIPAddress[i],fileReader.neighbortcpPortNo[i],fileReader.neighborudpPortNo[i]);
			
		}
        
		/* Start Listening for requests from iot and fog nodes */
 	    req_listener list = new req_listener(fileReader.udpHostPort , fileReader.MaxResponseTime , fileReader.HostName , neighbor) ;
	    list.start(); //Start the thread
 	    
	    /* Start Listening for update messages from neighbor fog nodes */
	    
        TCP_Listener l = new TCP_Listener(fileReader.tcpHostPort , neighbor);
		Thread tcplist = new Thread(l);
		tcplist.start();//Start the thread to listen for packets	
		
		
        /* Thread to send periodic updates to neighbor fog nodes */
		
        sendPeriodicUpdate intervalUpdateSender = new sendPeriodicUpdate(fileReader.HostName, fileReader.tcpHostPort , neighbor , 800 ,fileReader.interval);
        Thread UpdateSenderThread = new Thread(intervalUpdateSender);
        UpdateSenderThread.start(); // Start the thread to send update messages
       
	
	}
	
    

}
    



