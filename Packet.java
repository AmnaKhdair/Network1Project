/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_net;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laptop
 */
 

public class Packet {
    
    static final int HEADER_LENGTH =12;
    static final int MAX_DATA_LENGTH =1000;
    
    private short seqNum;
    private int checksum;
    private char type;
    private long timer=5;
    private short portNum_receiver;
    private InetAddress ip_addr_receiver;
    private short portNum_sender;
    private InetAddress ip_addr_sender;
    private byte[] data = new byte[MAX_DATA_LENGTH]; 
    private int data_length;
    private ByteBuffer buffer = null;
    private boolean recACK=false;
  

   
   
       
    // Set
    public void setSeq(short seq) {
    this.seqNum = seq;
  }
    public void setCheck(int check){
    this.checksum= check;
  }
    public void setType(char type){
    this.type= type;
  }
    public void setTimer(long timer){
    this.timer= timer;
  }
    public void setPortRec(short portNum_rec){
    this.portNum_receiver= portNum_rec;
  }
    public void setPortSend(short portNum_send){
    this.portNum_sender= portNum_send;
  }
    public void setIpRec(InetAddress ip_rec){
    this.ip_addr_receiver= ip_rec ;
  }
    public void setIpSend(InetAddress ip_send){
    this.ip_addr_sender= ip_send ;
  }
    public void setData(byte [] data){
    this.data= data;
  }
    public void setDataLength(int dataLen){
    this.data_length= dataLen;
  }
    public void setBuffer(ByteBuffer buffer){
    this.buffer= buffer;
  }
    
   public void setRecACK(boolean recACK) {
        this.recACK = recACK;
    }
    //get
    
   public short getseq() {    
       return seqNum ; 
      }
    public int getCheck() {
       return checksum ;
      }
    public char getType() {
       return type ;
      }
    public long getTimer() {
       return timer ;
      }
    public short getPortRec(){
           return portNum_receiver;       
       }
    public short getPortSend(){
           return portNum_sender;       
       }
    public InetAddress getIPRec(){
           return ip_addr_receiver ;       
       }
    public InetAddress getIPSend(){
           return ip_addr_sender ;       
       }
    public byte[] getData(){
           return data ;       
       }
    public int  getDataLength(){
           return data_length ;       
       }
    public ByteBuffer  getBuff() {
       return buffer ;
      }
    public boolean getRecACK() {
        return recACK;
    }
    public int calc_checksum(byte []b,int n)
    { int sum=0;
        for(int j=0;j<n;j++)
        {
            sum+=b[j];
        }
        return sum;
    }
        
    public boolean addData(byte[] data, int n,short seq,char type)
            {// n is the number of bytes to add       
                // n should be more the data.length    
                data_length = n;      
                if (n > MAX_DATA_LENGTH) {    
                    System.out.println("Data too big");  
                    return false;        
                }   
                
                for (int i = 0; i < n; i++)
                {            
                    this.data[i] = data[i];     
                }       
                
              this.seqNum=seq;
              this.checksum=calc_checksum(this.data,n);
              this.type=type;
              return true;  
            } 
 
   public ByteBuffer toByteBuffer() {
   buffer = ByteBuffer.allocate(HEADER_LENGTH + MAX_DATA_LENGTH);
   buffer.clear();
   buffer.putShort(seqNum);
   buffer.putInt(checksum);
   buffer.putChar(type);
   buffer.putInt(data_length);
   buffer.put(data, 0,data_length );
   return buffer;
 }  
   public void extractPacketfromByteBuffer(ByteBuffer buf) {
 try {
 short seqNum = buf.getShort(0);
 int checksum= buf.getInt(2);
 char type = buf.getChar(6);
 int dataLen = buf.getInt(8);
 byte[] ba = new byte[dataLen];
 for (int i = 0; i < dataLen; i++) {
 byte bt = (byte) buf.get(12+i);
 ba[i] = bt;
 }
 this.seqNum=seqNum; 
 this.data = ba;
 this.checksum=checksum;
 this.type=type;
 this.data_length=dataLen;
 } catch (Exception e) {
 System.out.println(e.toString());
 }
   }
   
   
   public int injectError(int errorRate)
   {
      float errorPercentage=(float)errorRate/100;
      int maxRandom=(int)(errorPercentage*1000);
      Random rand = new Random();
      int valueRandom= rand.nextInt(MAX_DATA_LENGTH);
      if(valueRandom>=0 && valueRandom<maxRandom)
      {
          this.checksum=this.checksum+valueRandom;
          System.out.print("error");
      }
     return this.checksum;
   }
   
   public void countDown()
   {
     timer--;
    // System.out.print(""+timer);

   }
}