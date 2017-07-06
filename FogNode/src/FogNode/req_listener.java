package FogNode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
/*
 * @author Arun Prakash
 * This class contains the  method to listen to the incoming UDP requests from
 * iot request generators and fog nodes on a specied port
 */

public class req_listener implements Runnable{

	
	public static int count = 0;
	public static int cloud_count = 0;
	public static long q_delay = 0 ;
	public static long maxRespT;
	public static request[] req = new request[10] ;
	public static request[] cloudreq = new request[10];
	public static NeighborUpdate[] neighbor ;
	public static String hostname;
	public static int portNumber;
	private Thread t ;
	
	req_listener (int portNum , long maxResponseTime , String HostName ,  NeighborUpdate[] neigh)  {
		
		neighbor = neigh ;
		hostname = HostName;
		maxRespT = maxResponseTime ;
		portNumber = portNum;
	}
	
	
	@Override
	public void run(){
		
		/* 
		 * This method listens for the incoming UDP request packets on the specified port 
		 */
		System.out.println("Hey dude IAm in RReq Listener");
		System.out.println("Listening on Port Number : " + portNumber);
		DatagramSocket serversocket = null;
		try {
			serversocket = new DatagramSocket(portNumber) ;
		} catch (SocketException ex){
			System.out.println(ex);
			ex.printStackTrace();
		}
		byte[] receiveData = new byte[80000];// can receive UDP packet upto 80kb
		DatagramPacket receivePacket = new DatagramPacket (receiveData, receiveData.length);
			try {
				serversocket.receive(receivePacket);
			} catch (IOException ex) {
				System.out.println(ex);
				ex.printStackTrace();
			}
		String data = new String(receivePacket.getData());//receive the request
		request x = new request();
		x=extract(data); //Function call to retrieve the contents of the request
		try {
			Process_Request(x);// Function call to process the request
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static request extract( String Rdata ){
		
		/*
		 * This method extracts the contents of the request from the message received
		 * @return returns the extracted request
		 */
		request newReq = new request() ;
		int start=1,i=0;
        int end=0;
        int next=0;
        String[] n=new String[6];
        Rdata = Rdata + " end !";
        end=Rdata.lastIndexOf(" ");
        for(start =0;start<end;start=next+1,++i)
        {
            next=Rdata.indexOf(" ",start+1);
            n[i]=Rdata.substring(start,next);
            
        }
        newReq.seqNumber=Integer.valueOf(n[0].substring(2));
        newReq.msgtype=Integer.valueOf(n[1].substring(2));
        newReq.forwardLimit=Integer.valueOf(n[2].substring(3));
        newReq.iotHostName=n[3].substring(3);
        newReq.iotPortNumber=Integer.valueOf(n[4].substring(2).trim());
        newReq.info=Rdata;
        newReq.process_time();
        try {
			newReq.iotIPAddress = InetAddress.getByName(newReq.iotHostName);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
       
		return newReq;
	}
	
	
	private static void Process_Request(request newReq) throws InterruptedException {   
	 /*
	  * This method processes the request .
	  * If the total delay is less  than the maxResponse time the request is processed by fog node
	  * if the total delay is greater than the maxResponse time and forwardlimit has reached then 
	  * cloud Response is simulated
	  * else off loaded to neighbor node with shortest response time
	  */
		
	   if(count == 0){ //if the request queue is empty
			req[0] = newReq; // enqueue the request to the fog processing queue
			count ++ ;
			process(req ,count , newReq.iotHostName );
		}
	
		else{ //if request queue is not empty
			
			q_delay = queing_delay(req); // function call to calculate the queing_delay
			
			if(q_delay+newReq.processTime < maxRespT) { //if total delay less than maxResponse  time
	        	count++ ;
	            req[count]=newReq; // enqueue the new request to the fog processing queue
	            process(req ,count, hostname); //function call to send response
	        }
			
			else 
            {
              newReq.forwardLimit-- ; // Reduce the forward limit by 1
              if(newReq.forwardLimit == 0) // if forward limit is 0
              {
            	  cloudreq[cloud_count] = newReq; // stimulate cloud processing
            	  forward_cloud(cloudreq);
              }
              else
            	  
				forward_neighbor(newReq,hostname); // off load the request to best neighbor
            }
            
		}		
		
  }

	
	public static long queing_delay(request[] r){
		
		/* 
		 * This method calculates the queingdelay of the fog node
		 * @return returns the queing delay of the node
		 */
    	long qDelay = 0;
        for(int i=0;i<r.length;++i)
            qDelay=qDelay+r[i].processTime;
        return qDelay ;
    }
	
    public static void process(request[] R ,int count,String Hname ) throws InterruptedException{
        /*
         * This method sends invokes a thread to send response to the Iot request generator
         */
        ResponseSender response=new ResponseSender(R ,count,Hname);
        Thread responsethread = new Thread (response);
		responsethread.start();//Start sending responses to the requests in the queue
        
        
    }

    public static void forward_cloud(request[] cloudReq ) throws InterruptedException{
    	/*
         *This method sends invokes a thread to simulate cloud processing of request 
         * to send response to the Iot request generator
    	 */
            CloudResponseSender cldresponse = new CloudResponseSender(cloudReq);
            Thread cldresponsethread = new Thread (cldresponse) ;
            cldresponsethread.start();//Start the thread
            
     }
    
    static void forward_neighbor( request newR , String ownHostName){
        /*
         * this method offloads the request to the neighbor with least processing time
         */
    	
    	/* Determine the neighbor with least queing_delay
    	 * Sort the neighbor table based on their queing delay
    	 */
    	int n = neighbor.length;
        int k;
        for (int m = n; m >= 0; m--) {
            for (int i = 0; i < n - 1; i++)  {
            	   k = i + 1;
            	   if (neighbor[i].processTime > neighbor[k].processTime) 
                   {
                   NeighborUpdate temp;
                   temp = neighbor[i];
                   neighbor[i] = neighbor[k];
                   neighbor[k] = temp;
                   }
            }
        }
        n=0;
        
        //Construct the message 
        String info = newR.info.concat("The Request is forwared by " + ownHostName + " to " + neighbor[0].neighborHostName);
        offloader.offload(neighbor[0].neighborIPAddress , neighbor[0].neighborudpPortNo , info);
     }
  
    public void start () {
      /* 
       * Function to start the UDP request listening thread 
       */
        if (t == null) {
           t = new Thread (this, "listenThread");
           t.start ();
    }
   }   
}
	