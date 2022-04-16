/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_net;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;


/**
 *
 * @author ThinkPad
 */
public class thread  extends Thread{
      
  
   
    @Override
    public void run() {
    ClientChat c=new ClientChat();
       c.setVisible(true); 
        String name="";
        try {
          Thread.sleep(11000);
        } catch (InterruptedException ex) {
            Logger.getLogger(thread.class.getName()).log(Level.SEVERE, null, ex);
        }
              
        c.receiveMessage();
    
    }
    
    
}
