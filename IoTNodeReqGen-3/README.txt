CS 6390 - Advanced Computer Networks
Fall 2016
Author: Ashkan Yousefpour

In order to run this JAR file, you need to have Java installed on your machine (JVM and JRE)

You run the IoTNodeRequestGenerator with the following command (for a Unix machine):
java -jar “/Location/of/the/IoTNodeReqGen/dist/IoTNodeReqGen.jar" Location/of/the/config.txt

The format of the config file is as follows:

{line1}: “IP address” of the machine where IoTNodeRequestGenerator runs. Note that this is necessary in the cases where the machine has both private and public IP addresses and you want to use to public IP address to be able to receive requests coming from outside network. If you all the nodes on your machine you can write 127.0.0.1
{line2}: “Listening Port” of the IoTNodeRequestGenerator to listen to incoming UDP response
{line3}: “Interval” between sending packets to destinations (in ms)
{line4}: “Forward limit”
{line5}: “Number of fog nodes”. This is used in the program to know how many more lines must be read. If “Number of fog nodes”=x, then 2x lines are expected to be read from the file (IP and port).
{line6}: “IP address” of the first fog node
{line7}: “UDP port number” of the first fog node
{line8}: “IP address” of the second fog node
{line9}: “UDP port number” of the second fog node
{line10}: “IP address” of the third fog node
{line11}: “UDP port number” of the third fog node
…


Note that all the hostnames in the config file must be resolvable and accessible by your computer. E.g. if a local host name in UTD is used, (like dcXX.utdallas.edu), make sure you are in the UTD network and can access the local host name.