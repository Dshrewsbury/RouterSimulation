/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datacommfinal;

import java.util.ArrayList;


/**
 * Class that simulates a router. 
 * Able to create  subnets, delete subnets,
 * and look up the route of specific IP addresses.
 * 
 * @author Elissa Shinseki, Dan Shrewsbury
 */
public class Router
{
    /**
     * Constructor for the Router class. Initializes the ArrayList of sub-nets
     * each containing an ArrayList of IProute objects. IProute objects have
     * both a string array containing the IP address and an integer carrying 
     * its route.
     */
    public Router()
    {
        _subnets = new ArrayList<ArrayList<IProute>>(NUM_OF_SUBNETS);
        
        // Fills sub-nets array with ArrayLists
        for (int i = 0; i < NUM_OF_SUBNETS; i++)
        {
            _subnets.add(i, new ArrayList<IProute>());
        }
    
    }
    
    
    /**
     * Parses the routing command and executes the instruction.
     * 
     * @param command - String
     * @return returnNum - int
     */
    public int parseCmd(String command)
    {
        // Parses the command for its individual components
        // cmd[0]: Prefix
        // cmd[2]: IP address
        // cmd[3]: net mask
        // cmd[5]: route number
        String[] cmd  = command.split("[ /,=]+");
        int returnNum = 0;
        int netNum;
        
        // Performs a lookup on a net address
        if (cmd[0].equals("LOOKUP"))
        {
            returnNum = lookup(cmd[2]);
        }
        else
        {
            netNum = NUM_OF_SUBNETS - Integer.parseInt(cmd[3]) - 1;  
            
            if (cmd[2].equals("0"))
            {
                cmd[2] = "0.0.0.0";
            }
            
            // Adds net
            if (cmd[0].equals("ADD"))
            {
                returnNum = addNet(cmd, netNum);
            }
            
            // Deletes net
            if (cmd[0].equals("DEL"))
            {
                returnNum = delNet(cmd, netNum);
            }
        }
              
        // Returns result
        return returnNum;
    }
    
    
    /**
     * Adds a network to the subnet matrix. 
     * 
     * @author - Elissa
     * @param net - String[]
     * @param netNum - int
     * @return result - int
     *         1: if addition was successful
     *         0: if addition was unsuccessful
     */
    private int addNet(String[] net, int netNum)
    {       
     
        // See if the net is in the subnet
        int result = findNet(net[2], net[5], _subnets.get(netNum));
        
        // If not found in memory, add new net. Result is 1. 
        if (result == -1)
        {
            IProute address = new IProute(net[2], net[5]);
            _subnets.get(netNum).add(address);
            result = 1;
        } 
        // If found in memory, do nothing. Result is 0.
        else
        {
            result = 0;
        }   
        
        return result;
    }
    
    
    /**
     * Deletes a network from the subnet matrix. 
     * 
     * @param net - String[]
     * @param netNum - int
     * @return result - int
     *         1: if deletion was successful
     *         0: if deletion was unsuccessful
     */
    private int delNet(String[] net, int netNum)
    {
        // See if the net is in the subnet array
        int result = findNet(net[2], net[5], _subnets.get(netNum));
        
        // If not found in memory, do nothing. Result is 0. 
        if (result == -1)
        {            
            result = 0;
        } 
        // If found in memory, delete the entry. Result is 1.
        else
        {
            _subnets.get(netNum).remove(result);
            result = 1;
        }
        
        return result;
    }
    
    
    /**
     * Searches for a net within an array using its IP address. 
     * Returns the location of the net within the array if it is found.
     * Returns -1 if it is not found.
     * 
     * @param IPaddress- String
     * @param routeNumber - String
     * @param subnetList - ArrayList<IProute>
     * @return int
     */
    private int findNet(String IPaddress, String routeNumber, ArrayList<IProute> subnetList)
    {
        int result = 0;    
        int i = 0;
        int location = 0;
        boolean found = false;   
        int routeNum = 0;
        
        if (routeNumber.equals("NULL"))
        {
            routeNum = 0;
        }
        else
        {
            routeNum = Integer.parseInt(routeNumber);
        }
        
        while (!found && i < subnetList.size())
        {
            IProute route = subnetList.get(i);
            
            if (route._IPstring.equals(IPaddress) && route._route == routeNum)
            {
                found = true;
                location = i;
            }
            
            i++;
        }
        
        if (found)
        {
            result = location;
        }
        else
        {
            result = -1;
        } 
        
        return result;
    }
    
    
    
    /**
     * Finds the Max Range of an 8 bit section of an IP address
     * 
     * @param netMask - int
     * @param ipSecLocation - int
     * @param ipSectionStr - String
     * @return newIPsection - int
     */
     private int findMaxRange(int netMask, int ipSecLocation, String ipSectionStr)
    {
        
        int newIPsection = 0;
        int ipSection = Integer.parseInt(ipSectionStr);
        String ipToEdit = Integer.toBinaryString(ipSection);
        ipToEdit = ("00000000" + ipToEdit).substring(ipToEdit.length());
        
        char [] arrayOfBits = ipToEdit.toCharArray();              
        int bitFlipSpot = netMask - ipSecLocation;
        
        // Loop through the section of the IP and flip the necessary bits to '1'
        for(int j = bitFlipSpot; j < 8; j++, bitFlipSpot++)
        {
           arrayOfBits[bitFlipSpot] = '1';
        }    
                    
        String maxIP = new String(arrayOfBits); 
    
        newIPsection = Integer.parseInt(maxIP, 2);
            
        return newIPsection;
    }
    
    
    
     /**
     * Converts an IP address to decimal to be compared when finding the range
     * 
     * @param ipAddress - String
     * @return result - long
     */
    private static long ipToLong(String ipAddress) 
    {
    
        
        String[] ipAddressInArray = ipAddress.split("\\.");
 
        long result = 0;
        
        // Takes the first section of an IP address and raises it to the highest power, then adds each other section with one less power
        // The power could be any number > 1 but is 255 in order to reduce the chances of an error by being a huge number.
        // The result is a massive number which is then used to compare an IP address with the minimum and maximum range of a net which are also converted to long
        for (int i = 0; i < ipAddressInArray.length; i++) 
        {
         
           int power = 3 - i;
           int ip = Integer.parseInt(ipAddressInArray[i]);
           result += ip * Math.pow(255, power);
         
        }
         
            return result;
    }
    
    
    
   /**
     * Looks through all stored Nets to find the most exact(highest netmask) match for a specific IP address
     * 
     * @param ipAddress - String
     * @return route - int
     */
    
    private int lookup(String ipCheck) 
    {
        int route; //return variable
        int maxIPsection = 0;
        int match = 0;
        int i;
        int netMask = 32;
        int j = 0;
        
        //Lowest IP in a net
        long ipLowest;
        
        // Highest IP in a net
        long ipHighest;
        
        // IP we want to see if is in a certain net
        long ipToTest;
        
        String MaxIPrange = "";
          
        // Loops through all 33 netMasks looking for the most specific match, exiting as soon as it finds one             
        for (i = 0; i < _subnets.size() && match == 0; i++, netMask--) 
        {
            
          // Loop through every net within a certain netMask looking for a match 
          for(j = 0; j < _subnets.get(i).size() && match == 0; j++)  
          {  
             
             // If netMask is between 24 and 32, then find the max range for the fourth section of the IP address
             if(netMask <= 32 && netMask > 24)
                 {                       
                    maxIPsection= findMaxRange(netMask, 24, _subnets.get(i).get(j)._IPaddress[3]);
                    MaxIPrange = _subnets.get(i).get(j)._IPaddress[0] + "." + _subnets.get(i).get(j)._IPaddress[1] + "." + _subnets.get(i).get(j)._IPaddress[2] + "." + maxIPsection;     
                 }       
                 else if(netMask <= 24 && netMask > 16) 
                 {      
                    // If netMask is between 24 and 16, then find the max range for the third section of the IP address  
                    maxIPsection= findMaxRange(netMask, 16, _subnets.get(i).get(j)._IPaddress[2]);
                    MaxIPrange = _subnets.get(i).get(j)._IPaddress[0] + "." + _subnets.get(i).get(j)._IPaddress[1] + "." + maxIPsection + ".255";  
                
                 }
                else if(netMask <= 16 && netMask > 8)
                {       
                    // If netMask is between 16 and 8, then find the max range for the second section of the IP address  
                    maxIPsection= findMaxRange(netMask, 8, _subnets.get(i).get(j)._IPaddress[1]);
                    MaxIPrange = _subnets.get(i).get(j)._IPaddress[0] + "." + maxIPsection + ".255.255";  
                } 
                else if(netMask <= 8)
                {
                   // If netMask is between 8 and 0, then find the max range for the first section of the IP address   
                   maxIPsection= findMaxRange(netMask, 0, _subnets.get(i).get(j)._IPaddress[0]);
                   MaxIPrange = maxIPsection + ".255.255.255";
                }
               
           
          
                
              // Find the Range
              String fullIP = _subnets.get(i).get(j)._IPaddress[0] + "." + _subnets.get(i).get(j)._IPaddress[1] + "." + _subnets.get(i).get(j)._IPaddress[2] + "." + _subnets.get(i).get(j)._IPaddress[3];
              ipLowest = ipToLong(fullIP);
              ipHighest = ipToLong(MaxIPrange);
              ipToTest = ipToLong(ipCheck);
        
              if(ipToTest >= ipLowest && ipToTest <= ipHighest)
              {
                // Then its within the range and get out of the loop
                match = 1;    
              }
            
        
          }     
        
        }     

        if(match == 1)
        {
            // It's a match so return route number
            route = _subnets.get(i-1).get(j-1)._route;
        }
        else
        {
            route = -99;
        }
        
        
        return route;  
    }
    
    
       
    
    
    /**
     * Class that holds route number and IP address blocks.
     */
    private class IProute
    {
        /**
         * Constructor for the IProute class
         * 
         * @param IP - string containing IP address
         * @param route - integer route number
         */
        public IProute(String IP, String route)
        { 
            // Saves IP as a string
            _IPstring = IP;
            
            // Splits IP address string into four discrete components
            _IPaddress = IP.split("[.]+");
                  
           // Saves route number. If the route is NULL, sets route number to 0. 
            if (route.equals("NULL"))
            {
                _route = 0;
            }
            // Otherwise, parses the route number from the string
            else
            {
                _route = Integer.parseInt(route);
            }
            
        }
        
        private String _IPstring;
        private String[] _IPaddress;
        private int _route = 0;
    }

    
    private static final int NUM_OF_SUBNETS = 33;
    private final ArrayList<ArrayList<IProute>> _subnets; 
    
}