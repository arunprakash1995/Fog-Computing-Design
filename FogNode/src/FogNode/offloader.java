package FogNode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
/*
 * This class forwards the request to the neighbor
 */
public class offloader {
	
    public static void offload(InetAddress IotAddr , int Port ,String data) {
        /*
         * This method offloads the request to the neighbor
         */

        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket(); // make a Datagram socket
        } catch (SocketException ex) {
            System.out.println("Cannot make Datagram Socket!");
            ex.printStackTrace();
        }
        
        byte[] sendData = new byte[data.getBytes().length]; // make a Byte array of the data to be sent
        sendData = data.getBytes(); // get the bytes of the message
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IotAddr, Port); // craft the message to be sent
        try {
            clientSocket.send(sendPacket); // send the message
        } catch (IOException ex) {
            System.out.println("I/O exception happened!");
            ex.printStackTrace();
            
        }
        
    }
}

        
    

