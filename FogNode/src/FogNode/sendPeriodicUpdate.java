package FogNode;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/*
 * This class send periodic TCP update packet to the neighbors about the queing delay of the fog node
 */

public class sendPeriodicUpdate implements Runnable {
	
	private int currentNeighborIndex;
	private long interval;
	private String ownHostName;
	private int ownPortNumber;
	private long processTime;
	private NeighborUpdate[] neighbor;
	
	
	public sendPeriodicUpdate(String hostname ,int port , NeighborUpdate[] neigh, long processT ,long inter) {
		/*
		 * This method initializes the parameters of the class
		 */
		this.ownHostName = hostname ;
		this.ownPortNumber = port ;
        this.neighbor = neigh ;
		this.processTime = processT;
		this.interval = inter;
	}

	@Override
	public void run()  {
		/*
		 * This method invokes the functions to construct the update message and send TCP packets
		 */
		
		int length = neighbor.length;
		int current = 0;
		String message = "";
		while (true) {
			message = constructMessage();// function call to construct the message
			try {
				send(neighbor[current].neighborHostName, neighbor[current].neighbortcpPortNo , message );
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(interval);// Sleep Period of Update
			}catch ( InterruptedException ex) {
				System.out.println(ex);
				ex.printStackTrace();
			}
			current = next(length);
		}
		
		
	}
	
	private void send(String IPAddr ,int port ,String data) throws IOException {
		/*
		 * This method send the TCP update messages to the neighbors  
		 */
		Socket socket = new Socket (IPAddr , port); // create TCP socket
		PrintWriter out = new PrintWriter(socket.getOutputStream(),true); // Convert data to output stream bytes
		out.print(data);// Sends TCP packets
		out.close();
		socket.close();
		
	}
	
	private int next(int noOfNeighbor){
		/*
		 * This method determines the index number of the next neighbor to which 
		 * update is to be sent
		 */
		currentNeighborIndex = (currentNeighborIndex+1) % noOfNeighbor;
		return currentNeighborIndex;
	}
	   
	
	private String constructMessage () {
		/* 
		 * This methods constructs the update message
		 * @return returns the constructed message
		 */
		String m = "";
		m = m.concat(ownHostName) ;//craft the message
		m = m.concat(" ");
		m = m.concat(String.valueOf(ownPortNumber)) ;//craft the message
		m = m.concat(" ");
		m = m.concat(String.valueOf(processTime));//craft the message
		return m ;
	}

}
