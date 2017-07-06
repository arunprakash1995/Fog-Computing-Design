package iotnodereqgen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Ashkan Yousefpour
 * 
 */
public class Main {

    
    /**
     * The main function
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        if (args.length > 0) { // if the input file is specified
            File inputFile = new File(args[0]); // read the file
            in = new Scanner(inputFile); // get a scanner of the input file
        } else { // the input file must be provided
            System.out.println("please provide the location of the config file as the first input argument.");
            in = null;
            System.exit(1);
        }
        
        ConfigFileReader fileReader = new ConfigFileReader(in); // read the config file from disk
        
        RequestSender intervalRequestSender = new RequestSender(fileReader.interval, 
                fileReader.forwardLimit, fileReader.ownHostName, fileReader.listenerPortNumber, fileReader.FogIPAddress, fileReader.FogPortNumber);
        Thread intervalRequestSenderThread = new Thread(intervalRequestSender); // make the thread to send periodic requests to fog nodes
        intervalRequestSenderThread.start();
         // start the thread
        
        System.out.println("Listenning on "+fileReader.ownHostName+" port "+fileReader.listenerPortNumber+ " for response...");
        Listener.listen(fileReader.listenerPortNumber); // start listening for incoming traffic
        
    }
    
}
