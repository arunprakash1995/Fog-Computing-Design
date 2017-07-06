package iotnodereqgen;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class req_listener {
    
    public long seqNumber;
    public int msgtype;
    public int forwardLimit;
    public String iotHostName;
    public int FogPortNumber;
    public InetAddress FogIPAddress;
    public String info;

    
    public void listen(int portNumber) {
        
        DatagramSocket serversocket =null;
        try {
             serversocket = new DatagramSocket(portNumber);
            
        }  catch (SocketException ex) {
            System.out.println("Cannot make UDP Socket on port " + portNumber);
            ex.printStackTrace();
            }
        byte[] receiveData = new byte[30000];
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                serversocket.receive(receivePacket);
            } catch (IOException) {
                System.out.println("I/O exception !!!");
                ex.printStackTrace();
            }
        }
        string data = new String(receivePacket.getData());
        extract(data);
        
    }
    
    private void extract(string Rdata)
    {
        int start=1,i=0;
        int end=0;
        int next=0;
        String[] n=new String[6];
        end=Rdata.lastIndexOf(" ");
        for(start =0;start<end;start=next+1,++i)
        {
            next=Rdata.indexOf(" ",start+1);
            n[i]=Rdata.substring(start,next);
            
        }
        n[i]=Rdata.substring(end);
        this.seqNumber=Integer.valueOf(n[0].substring(2));
        this.msgtype=Integer.valueOf(n[1].substring(2));
        this.forwardLimit=Integer.valueOf(n[2].substring(3));
        this.iotHostName=n[3].substring(3);
        this.FogPortNumber=Integer.valueOf(n[4].substring(2));
        this.info=n[5].substring(6);

    }
}
