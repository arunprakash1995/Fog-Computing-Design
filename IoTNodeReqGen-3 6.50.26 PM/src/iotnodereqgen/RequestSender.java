package iotnodereqgen;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author Ashkan Yousefpour
 *
 * This class sends requests to all specified fog nodes in predefined intervals.
 * The nodes are chosen in a Round Robin fashion
 */
public class RequestSender implements Runnable {

    private int currentFogNodeIndex = 0; // an index of the current fog node that the request must be sent to
    private long seqNumber = 0; // squence number of the request to be sent

    private long interval; // local version of the interval (in ms) in ConfigFileReader
    private int forwardLimit; // local version of forward limit in ConfigFileReader
    private String ownHostName; // local version of ownHostName in ConfigFileReader
    private int listenerPortNumber; // local version of listenerPortNumber in ConfigFileReader
    private InetAddress[] FogIPAddress;  // local version of FogIPAddress[] in ConfigFileReader
    private int[] FogPortNumber; // local version of FogPortNumber[] in ConfigFileReader

    /**
     * This is the constructor of the class
     *
     * @param interval interval between sending packets to destinations (in ms)
     * @param forwardLimit the forward limit of fog nodes
     * @param ownHostName the IP address (or hostname) of the machine where this
     * program runs
     * @param FogIPAddress an array containing the IP addresses (or hostnames)
     * of all the fog nodes
     * @param FogPortNumber an array containing port number of all the fog nodes
     */
    public RequestSender(long interval, int forwardLimit, String ownHostName, int listenerPortNumber, InetAddress[] FogIPAddress, int[] FogPortNumber) {
        this.interval = interval;
        this.forwardLimit = forwardLimit;
        this.ownHostName = ownHostName;
        this.listenerPortNumber = listenerPortNumber;
        this.FogPortNumber = FogPortNumber;
        this.FogIPAddress = FogIPAddress;
    }

    /**
     * This method sends a request to an array of fog nodes (specified with an
     * array of IP addresses/port numbers) in predefined intervals.
     */
    @Override
    public void run() {
        int length = FogIPAddress.length; // get the number of fog nodes
        int current = 0; //  index of the current fog nodes
        String messageBody = "";

        while (true) {
            messageBody = constructMessage(); // construct the message

            send(FogIPAddress[current], FogPortNumber[current], messageBody); // send the message to the current fog node
//            System.out.println("SENT: " + messageBody);
            try {
                Thread.sleep(interval); // wait for interval ms
            } catch (InterruptedException ex) {
                System.out.println("Thread cannot sleep!");
                ex.printStackTrace();

            }

            current = next(length); // current++
        }
    }

    /**
     * This function will send a message to a particular UDP listener on IP
     * address specified in 'IPAddress' and port number specified in 'port'
     *
     * @param IPAddress the IP address of the destination (UDP listener)
     * @param port the port number of the destination (UDP listener)
     * @param data the message to be sent
     */
    private void send(InetAddress IPAddress, int port, String data) {

        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket(); // make a Datagram socket
        } catch (SocketException ex) {
            System.out.println("Cannot make Datagram Socket!");
            ex.printStackTrace();
        }

        byte[] sendData = new byte[data.getBytes().length]; // make a Byte array of the data to be sent
        sendData = data.getBytes(); // get the bytes of the message
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port); // craft the message to be sent
        try {
            clientSocket.send(sendPacket); // send the message
        } catch (IOException ex) {
            System.out.println("I/O exception happened!");
            ex.printStackTrace();
        }

    }

    /**
     * This function performs Round-Robin on the index of fog nodes and returns
     * the next index of the fog nodes that a message must be sent to.
     *
     * @param numberOfNodes number of nodes
     * @return returns the next index of the fog nodes
     */
    private int next(int numberOfNodes) {
        currentFogNodeIndex = (currentFogNodeIndex + 1) % numberOfNodes; // circular increment
        return currentFogNodeIndex;
    }

    /**
     * This method constructs the message to be sent
     *
     * @return returns the message to be sent now
     */
    private String constructMessage() {

        String message = "";
        message = message.concat("#:" + String.valueOf(seqNumber)); // add sequence number
        message = message.concat(" ");
        int randomType = (int) (Math.random() * 10 + 1); // there are 10 message types. pick a random message type
        message = message.concat("T:" + String.valueOf(randomType)); // add message type
        message = message.concat(" ");
        message = message.concat("FL:" + String.valueOf(forwardLimit)); // add forward limit
        message = message.concat(" ");
        message = message.concat("IP:" + ownHostName); // add ownHostName
        message = message.concat(" ");
        message = message.concat("P:" + String.valueOf(listenerPortNumber)); // add own listening port number
        seqNumber++;
        return message;
    }

}
