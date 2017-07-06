CS 6390 - Advanced Computer Networks
Fall 2016
Team : Arun Prakash Themothy Prabu Vincent (axt161330) and Jaypreethi Palanisamy(jxp164030)


The Project was developed in Eclipse in MacOS

You run the IoTNodeRequestGenerator with the following command (for a Unix machine):
java -jar “/Location/of/the/FogNOde“ Location/of/the/config.txt

The format of the config file is as follows:

{line1}: “IP address” of the machine where FogNode runs.all the nodes are on the same  machine hence we have used 127.0.0.1
{line2}: “Listening Port” of the Fog Node to listen to incoming TCP update message
{line3}: “Listening Port” of the Fog Node to listen to incoming UDP request 
{line4}: “Interval” between sending periodic update packets to neighbors (in ms)
{line5}: Max Response time of the fog node
{line6}: “Number of fog nodes”. This is used in the program to know how many more lines must be read. If “Number of fog nodes”=x, then 3x lines are expected to be read from the file (IP and port).
{line7}: “IP address” of the first fog node
{line8}: “TCP port number” of the first fog node
{line9}: “UDP port number” of the first fog node
{line10}: “IP address” of the second fog node
{line11}: “TCP port number” of the second fog node
{line12}: “UDP port number” of the second fog node
{line13}: “IP address” of the third fog node
{line14}: “TCP port number” of the third fog node
{line15}: “UDP port number” of the third fog node


Terminal Command to Run the six fog nodes and IOT Generator : 

java -jar /Users/ArunPrakash/Desktop/FogNode/IOTRequestGenerator.jar /Users/ArunPrakash/Desktop/FogNode/IOT_config.txt > Desktop/IOT-log.txt 2>&1 & java -jar /Users/ArunPrakash/Desktop/FogNode/FogNodeGen.jar /Users/ArunPrakash/Desktop/FogNode/Fog_config1.txt > Desktop/fog1-log.txt 2>&1 & java -jar /Users/ArunPrakash/Desktop/FogNode/FogNodeGen.jar /Users/ArunPrakash/Desktop/FogNode/Fog_config2.txt > Desktop/fog2-log.txt 2>&1 & java -jar /Users/ArunPrakash/Desktop/FogNode/FogNodeGen.jar /Users/ArunPrakash/Desktop/FogNode/Fog_config3.txt > Desktop/fog3-log.txt 2>&1 & java -jar /Users/ArunPrakash/Desktop/FogNode/FogNodeGen.jar /Users/ArunPrakash/Desktop/FogNode/Fog_config4.txt > Desktop/fog4-log.txt 2>&1 & java -jar /Users/ArunPrakash/Desktop/FogNode/FogNodeGen.jar /Users/ArunPrakash/Desktop/FogNode/Fog_config5.txt > Desktop/fog5-log.txt 2>&1 & java -jar /Users/ArunPrakash/Desktop/FogNode/FogNodeGen.jar /Users/ArunPrakash/Desktop/FogNode/Fog_config6.txt > Desktop/fog6-log.txt 2>&1
