package Cloud_reponse_generator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class CloudResponseSender implements Runnable {
    
    private InetAddress iotIPAddress;
    private int iotPortNumber;
    private string info;
    
    public CloudResponseSender(InetAddress IotAddr , int Port ,string data ){
        this.iotIPAddress = InetAddress;
        this.iotPortNumber = Port;
        this.info = data;
    }
    
    @Override
    public void run() {
        
        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket(); // make a Datagram socket
        } catch (SocketException ex) {
            System.out.println("Cannot make Datagram Socket!");
            ex.printStackTrace();
        }
        
        byte[] sendData = new byte[info.getBytes().length]; // make a Byte array of the data to be sent
        sendData = info.getBytes(); // get the bytes of the message
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, iotIPAddress, iotPortNumber); // craft the message to be sent
        try {
            clientSocket.send(sendPacket); // send the message
        } catch (IOException ex) {
            System.out.println("I/O exception happened!");
            ex.printStackTrace();
            
        }
        
    }
