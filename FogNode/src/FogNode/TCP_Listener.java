package FogNode;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
/*
 * @author Arun Prakash
 * This class contains the  method to listen to the incoming TCP update packets from
 * neighboring fog nodes on a specied port
 */
public class TCP_Listener implements Runnable {
	
	public String fogHostName; 
	public int fogport ;
	public InetAddress fogIPAddress;
	public int processTime;
	public int port=0;
	public NeighborUpdate[] neighbor ;
	

	TCP_Listener (int portNumber ,NeighborUpdate[] neigh )  {
		/*
		 * This method initializes the port number and neighbor table for listening 
		 */
		this.port = portNumber;
		this.neighbor = neigh ;
		
	}
	
	@Override
	public void run() {
		
		/* 
		 * This method listens for the incoming TCP update packets on the specified port 
		 */
	    System.out.println("Hey Dude Iam in TCP listener");
		ServerSocket serversocket = null;
		try {
			serversocket = new ServerSocket ( port );
		} catch (Exception ex) {
			System.out.println("cannot make TCP Socket on port" + port);
			ex.printStackTrace();
		}
		System.out.print("\n Listening for TCP Connection on " + port + "\n");
		Socket socket = null;
		try {
			socket = serversocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader input = null;
		try {
			input = new BufferedReader ( new InputStreamReader ( socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String data = null;
		try {
			data = input.readLine();// read the TCP update packet
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		extract (data);//function call to extract information from the update packet
		
	}
	

	public void extract( String in)  {
		/*
		 * This method extracts the information from the TCP packet 
		 * and updates the neighbor table
		 */
	   
		int start=1,i=0;
		int end=0;
		int next=0;
		in = in + " end";
		String[] n = new String[6];
		end = in.lastIndexOf(" ");
		for(start=0;start < end; start=next+1,++i)
		{
			next = in.indexOf(" ",start+1);
			n[i] = in.substring(start,next);
		}
		n[i] = in.substring(end);
		this.fogHostName = n[0];
		this.fogport= Integer.valueOf(n[1]) ;
		this.processTime = Integer.valueOf(n[2]);
		try{
			this.fogIPAddress = InetAddress.getByName(this.fogHostName);
		}catch (UnknownHostException e){
			System.out.println(e);
		}
		
		System.out.print("\n Extracted info " + fogHostName + " "  + fogport + " " + processTime);
	
	NeighborUpdate temp = new NeighborUpdate (fogHostName,fogport ,processTime);
    int flag=0;
    int j=0 ;
    for( j=0;j<neighbor.length;++j)// Updating the process time of the corresponding neighbor in the table
    {   
    	if( ((neighbor[j].neighborHostName).equals(temp.neighborHostName)) && (neighbor[j].neighbortcpPortNo == temp.neighbortcpPortNo))
        {
        	neighbor[j].processTime=temp.processTime;
            flag=1;
        }
    }
    
    for( j=0;j<neighbor.length;++j)// Print the neighbor table
    {
        System.out.print("\n Table " + neighbor[j].neighborIPAddress + " " +neighbor[j].neighbortcpPortNo + " " + neighbor[j].processTime);
      
    }
	
 }  
}
