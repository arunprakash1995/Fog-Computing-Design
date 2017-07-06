package offloadgen;



public class offloader {
    
    private InetAddress FogIPAddress;
    private int FogPortNumber;
    private string info;
    
    
    
    public static void offload(InetAddress IotAddr , int Port ,string data) {
        
        this.FogIPAddress = InetAddress;
        this.FogPortNumber = Port;
        this.info = data;

        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket(); // make a Datagram socket
        } catch (SocketException ex) {
            System.out.println("Cannot make Datagram Socket!");
            ex.printStackTrace();
        }
        
        byte[] sendData = new byte[info.getBytes().length]; // make a Byte array of the data to be sent
        sendData = info.getBytes(); // get the bytes of the message
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, FogIPAddress, FogPortNumber); // craft the message to be sent
        try {
            clientSocket.send(sendPacket); // send the message
        } catch (IOException ex) {
            System.out.println("I/O exception happened!");
            ex.printStackTrace();
            
        }
        
    }

        
    
}
