package request_msg;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class request {

    public long seqNumber;
    public int msgtype;
    public int forwardLimit;
    public String iotHostName;
    public int iotPortNumber;
    public InetAddress iotIPAddress;
    public String info;
    public processTime;
    
    public request (long SN,int MT,int FL,String IHN,int FPN,String IN){
        
        this.seqNumber = SN;
        this.msgtype = MT;
        this.forwardLimit = FL;
        this.String iotHostName = IHN;
        this.FogPortNumber = FPN;
        this.FogIPAddress = InetAddress.getByName(iotHostName);
        this.info = IN;
        this.processTime=2*msgtype;
        
    }
}
