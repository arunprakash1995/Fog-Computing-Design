package FogNode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/* 
 * This class is used to send response to the iot request generator
 */
public class ResponseSender implements Runnable {

	private String info;
    private request[] requestReceived;
    private int index;
    private String hostname ;
    
    
    public ResponseSender( request[] req ,int Count , String Hname ){
        /*
         * This method initializes the fog's ownhostname , request queue and the count of
         * number of requests
         */
    	this.requestReceived = req ;
    	this.hostname = Hname ;
    	this.index = Count;
   
    } 	
    
    @Override
    public void run() {
    	
        /*
         * This methods invokes the method to send responses to the respective iot generators
         */
        
    	int length = index;
    	int Index = 0;
        while( true ){
        requestReceived[Index].info = requestReceived[Index].info ;
        send_data(requestReceived[Index].iotIPAddress,requestReceived[Index].iotPortNumber,requestReceived[Index].info ,Index);
        Index = next (length) ;
       }
     
    }
    
    private void send_data(InetAddress Addr , int port , String Info ,int index) {
    	/*
    	 * This method sends the response to the IOT nodes
    	 */
    	String S= "The Request is Processed by FogNode " + hostname;//The information of the processing node is appended
    	this.info = null ;
    	try{
        	Thread.sleep(requestReceived[index].processTime);
        } catch (InterruptedException ex) {
        	ex.printStackTrace();
        }
    	DatagramSocket clientSocket = null ;
        try {
            clientSocket = new DatagramSocket();// make a Datagram socket
        } catch (SocketException ex) {
            System.out.println("Cannot make Datagram Socket!");
            ex.printStackTrace();
        }
        this.info = Info + S;
        
        
        byte[] sendData = new byte[info.getBytes().length]; // make a Byte array of the data to be sent
        sendData = info.getBytes(); // get the bytes of the message
        
        System.out.print(" Data Sent " + new String(sendData) + "\n");
        System.out.print(" size "+new String(sendData).getBytes().length);
       DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, Addr, port); // craft the message to be sent
        
        try {
            clientSocket.send(sendPacket); // send the message
        } catch (IOException ex) {
            System.out.println("I/O exception happened!");
            ex.printStackTrace();
            
        }
    }
    
    
    int next ( int len ) {
    	/*
    	 * This method determines the next pending request in the queue
    	 */
    	index = (index + 1) % len;
    	return index;
    }
    
    

}
