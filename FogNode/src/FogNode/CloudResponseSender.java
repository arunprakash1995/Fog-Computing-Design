package FogNode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
/* 
 * This class is used to simulate and send cloud response to the iot request generator
 */
public class CloudResponseSender implements Runnable {

	
    private String info;
    private request[] cldreq;
    private int index;
    
    public CloudResponseSender( request[] cldR ){
        /*
         * This method initializes the cloud request queue from the req_listener class
         */
    	this.cldreq = cldR ;
    	this.index = 0;
    }
    
    @Override
    public void run() {
    	/*
         * This methods send the cloud responses to the respective iot generators
         */
    	int length = cldreq.length;
    	int Index = 0;
        String S = null;
        S=S.concat("The Request is Processed by Cloud"); // craft the message 
        
        while( true ){
        
        try{
        	Thread.sleep(cldreq[Index].processTime);
        } catch (InterruptedException ex) {
        	ex.printStackTrace();
        }
        	cldreq[Index].info = cldreq[Index].info.concat(S);
        	DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket(); // make a Datagram socket
        } catch (SocketException ex) {
            System.out.println("Cannot make Datagram Socket!");
            ex.printStackTrace();
        }
        this.info = cldreq[Index].info;
        byte[] sendData = new byte[info.getBytes().length]; // make a Byte array of the data to be sent
        sendData = info.getBytes(); // get the bytes of the message
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, cldreq[Index].iotIPAddress, cldreq[Index].iotPortNumber); // craft the message to be sent
        try {
            clientSocket.send(sendPacket); // send the message
        } catch (IOException ex) {
            System.out.println("I/O exception happened!");
            ex.printStackTrace();
            
        }
       
        Index = next (length) ;
       }
     
    }
    
    int next ( int len ) {
    	/*
    	 * This method determines the index of the next request in the cloud queue for processing
    	 */
    	index = (index + 1) % len;
    	return index;
    }
    
}    
