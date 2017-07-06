package tcp_listener;

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

public class TCP_Listener {
	
	public String fogHostName; 
	public InetAddress fogIPAddress;
	public int processTime;
	
	public void listen(int portNumber) throws IOException {
		
		ServerSocket serversocket = null;
		try {
			serversocket = new ServerSocket ( portNumber );
		} catch (SocketException ex) {
			System.out.println("cannot make TCP Socket on port" + portNumber);
			ex.printStackTrace();
		}
		Socket socket = serversocket.accept();
		BufferedReader input = new BufferedReader ( new InputStreamReader ( socket.getInputStream()));
		String data;
		data = input.readLine();
		extract (data);
		
		
	}
	

	public void extract( String in)  {
	   
		int start=1,i=0;
		int end=0;
		int next=0;
		String[] n = new String[6];
		end = in.lastIndexOf(" ");
		for(start=0;start < end; start=next+1,++i)
		{
			next = in.indexOf(" ",start+1);
			n[i] = in.substring(start,next);
		}
		n[i] = in.substring(end);
		this.fogHostName = n[0];
		this.processTime = Integer.valueOf(n[1]);
		try{
			this.fogIPAddress = InetAddress.getByName(this.fogHostName);
		}catch (UnknownHostException e){
			System.out.println(e);
		}
		
	}
	
}
