package iotnodereqgen;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

/**
 *
 * @author Ashkan Yousefpour
 *
 * This class contains a method that listens for incoming UDP traffic on a
 * specified port number and prints the contents of the message to the console
 */
public class Listener {

    /**
     * This method will listen for incoming UDP traffic on a specified port
     * number and prints the contents of the message to the console
     *
     * @param portNumber the port number on which the UDP packets must be received
     */
    public static void listen(int portNumber) {

        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket(portNumber);
        } catch (SocketException ex) {
            System.out.println("Cannot make UDP Datagram Socket on port " + portNumber);
            ex.printStackTrace();
        }
        byte[] receiveData = new byte[20000]; // can recieve UDP responses up to 20Kb
        while (true) {
            Arrays.fill(receiveData, (byte) 0);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); // receive the response
            try {
                serverSocket.receive(receivePacket);
            } catch (IOException ex) {
                System.out.println("I/O exception happened!");
                ex.printStackTrace();

            }
            System.out.println("RECEIVED: " + new String(receivePacket.getData())); // print contents of the response
        }
    }
}
