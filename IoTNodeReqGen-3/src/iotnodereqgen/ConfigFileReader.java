package iotnodereqgen;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Ashkan Yousefpour
 * 
 * This class reads the config file from disk
 */
public class ConfigFileReader {

    protected long interval; // interval between sending packets to destinations (in ms)
    protected int forwardLimit; // the forward limit of fog nodes
    
    protected String ownHostName; // the IP address (or hostname) of the machine where this program runs
    protected int listenerPortNumber; // the port number of the machine where this program runs
    
    protected InetAddress[] FogIPAddress; // an array containing the IP addresses (or hostnames) of all the fog nodes 
    protected int[] FogPortNumber; // an array containing port number of all the fog nodes 
    
    /**
     * This function reads the config file from disk. 
     * @param in input scanner 
     */
    public ConfigFileReader(Scanner in) {
        
        ownHostName = in.next(); // first line is the IP address (or hostname) of the machine where this program runs
        listenerPortNumber = in.nextInt(); // second line is the listening port number of the machine where this program runs
        
        interval = in.nextLong(); // third line is the interval between sending packets (in ms)
        forwardLimit = in.nextInt(); // forth line is the forward limit
        
        int numFogNodes = in.nextInt(); // fifth line is the number of fog neighbors

        // in the next lines, there must be the next 'numFogNodes' IP address/hostname of the fog nodes
        String[] fogHostName = new String[numFogNodes]; 
        FogIPAddress = new InetAddress[numFogNodes];
        FogPortNumber = new int[numFogNodes];
        
	// read all fog nodes' info
	for (int i = 0; i < numFogNodes; i++) {
            fogHostName[i] = in.next(); // read fog's hostname/IP Address
            try {
                FogIPAddress[i] = InetAddress.getByName(fogHostName[i]);// create an InetAddress object of the fog hostname/IPAddress
            } catch (UnknownHostException ex) { // if the hostname cannot be resolved by DNS (unknown)
                System.out.println("Hostname "+ fogHostName[i] +" is unknown! cannot determine an IP address based on \""+fogHostName[i]+"\".");
                ex.printStackTrace();
            }
            FogPortNumber[i] = in.nextInt(); // read the fog's port number
	}
	in.close();
        
        
    }
    
    
}
