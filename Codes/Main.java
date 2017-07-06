package fogNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Arun Prakash
 *
 */
public class Main {
    
    
    /**
     * The main function
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        
        String FogHostName;
        InetAddress FogIPAddress;
        int FogPortNumber;
        req_listener L;
        int maxRespT;
        request[] req;
        request[] cloudReq;
        int qDelay=0;
        NeighborUpdate[] neighbor;
        TCP_Listener l;
        
        L.listen(FogPortNumber);
        request newReq=new request(L.seqNumber,L.msgtype,L.forwardLimit,L.iotHostName,L.FogPortNumber,L.info);
        l.listen(FogTCPListeningPort);
        neighbour temp = new neighbor(l.FogIPAddress,l.FogHostName,l.processTime);
        flag=0;
        for( int j=0;j<neighbor.length();++j)
        {
            if(neighbor[j].neighborIPAddress == temp.neighborIPAddress)
            {
                neighbor[j]=temp;
                flag=1;
            }
        }
        if(flag == 0)
        {
            neighbor[j] = temp;
        }
        sendPeriodicUpdate intervalUpdateSender = new sendPeriodicUpdate(FogHostName, NeighborName , NeighPort ,qDelay ,interval);
        Thread intervalUpdateSender = new Thread(intervalUpdateSender);
        intervalUpdateSenderThread.start();
        
        queing_delay();
        if(qDelay+L.processTime < maxRespT){
            process();
        }
        else{
            L.forwardLimit--;
            if(L.forwardLimit=0)
            {
               forward_cloud();
            }
            else
            {
               foward_neighbor();
            }
        }
            
        
        
        public void queing_delay(){
            for(int i=0;i<req.length();++req)
                qDelay=qDelay+req[i].processTime;
        }
        
        
        public void process(){
            int i=0;
            i=req.length();
            req[i]=L;
            String S;
            
            S=S.concat("The Request is Processed by " + String.valuof(FogPortNumber))
            for(i=0;i<req.length();++i)
            {
                wait(req[i].processTime);
                req[i].info=req[i].info.concat(S);
                ResponseSender response=new ResponseSender(req[i].iotIPAddress,req[i].iotPortNumber,req[i].info);
                Thread response = new Thread(respone);
                response.start();
            }
            
            
        }
        
        void forward_cloud()
        {
            int i=0;
            i=cloudReq.length();
            cloudReq[i]=L;
            String S;
            
            S=S.concat("The Request is Processed by Cloud");
            for(i=0;i<cloudReq.length();++i){
                wait(cloudReq[i].processTime);
                cloudReq[i].info = cloudReq[i].info.concat(s);
                CloudResponseSender cldresponse = new CloudResponseSender(cloudReq[i].iotIPAddress,cloudReq[i].iotPortNumber,cloudReq[i].info);
                Thread cldresponse = new Thread(cldresponse);
                cldresponse.start();
                
            }
            
        void forward_neighbor(){
            int n = neighbor.length;
            int k;
            for (int m = n; m >= 0; m--) {
                 for (int i = 0; i < n - 1; i++) {
                       k = i + 1;
                       if (neighbor[i].processTime > neighbor[k],processTime) {
                       NeighborUpdate temp;
                       temp = neighbor[i];
                       neighbor[i] = neighbor[k];
                       neighbor[k] = temp;
           
                     }
                  }
                
             }
            n=0;
            String info = info.concat("The Request is forwared by " + FogHostName " to " + neighbor.neighborHostName);
            offloader.offload(neighbor[0].neighborIPAddress , neighbor[0].neighborPortNo , info);
            
        }
    
            
}
        
        
        
        
        
        
