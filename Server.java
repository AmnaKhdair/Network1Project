
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;


/**
 *
 * @author ThinkPad
 */
public class Server{
    
   public static ArrayList <String> list =new ArrayList<String>();
  
      public static void main(String argv[]) 
    {  
        
      try{

       ServerSocket listen_S= new ServerSocket(6666); 
    
      while(true) { 
  
            Socket socket = listen_S.accept(); 
 
           BufferedReader input =  new BufferedReader(new InputStreamReader(socket.getInputStream())); 
              String clientport="",clientIP,stateClient, msg; 
            msg = input.readLine();
              
            String[] msgSperate=msg.split(":");
              
              clientport=msgSperate[1];
              InetAddress IP; 
              IP=socket.getInetAddress();
              clientIP=IP.toString();
              clientIP=clientIP.substring(1);
             int x,w=0;
              x=Integer.parseInt(msgSperate[0]);
           if(x==0)
            list.add(clientIP+","+clientport);
               
            if(x==9999999)
            {
               
               for(int i=0;i<list.size();i++)
               if(list.get(i).equals(clientIP+","+clientport))
                       w=i;
               list.remove(w);
               
            }
            
            DataOutputStream  Dout =  new DataOutputStream(socket.getOutputStream());  
           
              int size=list.size();    
           String size2=Integer.toString(size);
           Dout.writeBytes(size2+'\n');    
           for(int i=0;i<list.size();i++)
            Dout.writeBytes(list.get(i)+'\n'); 
          
           
          
        } 
}
        catch( Exception ex){
          System.out.print("Hadle Exception");
      }
      }
        }
      
    
    

