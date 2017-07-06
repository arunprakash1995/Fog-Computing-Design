package Neighbor_info;

import java.net.InetAddress;

class NeighborUpdate{

    public InetAddress neighborIPAddress;
    public String neighborHostName;
    public int neighborPortNo;
    public int processTime;
    
    NeighborUpdate ( InetAddress neighIPAddr , String neighHN , int processT ){
        neighborIPAddress = neighIPAddr ;
        neighborHostName = neighHN ;
        processTime = processT ;
    }
    
}