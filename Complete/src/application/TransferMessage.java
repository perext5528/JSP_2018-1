package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

@SuppressWarnings("serial")
public class TransferMessage implements Runnable, java.io.Serializable

{
    Socket child;
    BufferedReader br;
    PrintWriter oos;
    int num;
    
    
    String user_id;
    double x;
    HashMap<String, PrintWriter> hm;
    HashMap<Integer, Socket> hm2;
    InetAddress ip;
    String msg;
 
    public TransferMessage(Socket s, HashMap<String, PrintWriter> h, HashMap<Integer, Socket> h2, int num) {
       child = s;
        hm = h;
        hm2 = h2;
        this.num = num;
        
        try {
           
            br = new BufferedReader(new InputStreamReader(child.getInputStream()));
            oos = new PrintWriter(child.getOutputStream());
            user_id = br.readLine();
            ip = child.getInetAddress();
           
            System.out.println(ip + "로부터 " + user_id + "님이 접속하였습니다.");
            broadcast(user_id + "님이 접속하셨습니다.");

 
            synchronized(hm) {    
                hm.put(user_id, oos);
            }
            /*synchronized(hm2) {    
                hm2.put(num, child);
            }*/
            
            System.out.println(hm.size());

        }
 
        catch(Exception e) {
            e.printStackTrace();
        }
 
    }
 
    public void run() {
        String receiveData;
 
        try
        {
           while((receiveData = br.readLine()) != null)
           {
             receiveData = br.readLine();
             if( receiveData.equals("/quit") ) {
                    synchronized(hm) {
                        hm.remove(user_id);
                    }
         
                    break;
                }
 
                else if(receiveData.indexOf("/to") >= 0) {
                    //sendMsg(receiveData);
                }
                else
                {
                    System.out.println(user_id + " >> " + receiveData);
                    broadcast(user_id + " >> " + receiveData);
                }
           }
           
        }
 
        catch (Exception e ) {
            e.printStackTrace();
        }
 
        finally {
            synchronized( hm ) {
                hm.remove( user_id );
            }
 
            broadcast(user_id + "님이 퇴장했습니다.");
            System.out.println(user_id + "님이 퇴장했습니다.");
 
            try
            {
                if( child != null ) {
                    br.close();
                    oos.close();
                    child.close();
                }
            }
 
            catch ( Exception e) {}
        }
    }
 
 
 
    public void broadcast(String message){
        synchronized( hm ) {
            try{
                for( PrintWriter oos : hm.values()){
                  oos.println(message);
                  oos.flush();
                  /*if(hm.keySet().toString().equals("[user1]"))
                  {
                     System.out.println("eq");
                  }
                  if(hm.keySet().toString().equals("[user2]"))
                  {
                     
                  }*/
                  /*for(int i=0;i<client1_x.length;i++)
                  {
                     oos.println(client1_x[i]);
                     oos.flush();
                     oos.println(client1_y[i]);
                     oos.flush();
                  }*/
                }
            }catch(Exception e){ }
        }
    }
    
    public void broadcast2(){
        synchronized(hm) {
            try{
                for( PrintWriter pw : hm.values()){
                    if(hm.keySet().toString().equals("[user1]"))
                    {
                       System.out.println("asdasd");
                    }
                    if(hm.keySet().toString().equals("[user2]"))
                    {
                       System.out.println("aa");
                    }
                    /*for(int i=0;i<client1_x.length;i++)
                    {
                       oos.println(client1_x[i]);
                       oos.flush();
                       oos.println(client1_y[i]);
                       oos.flush();
                    }*/
                }
            }catch(Exception e){ }
        }
    }
 
 
    /*public void sendMsg(String message){
        int begin = message.indexOf(" ") + 1;
        int end   = message.indexOf(" ", begin);
 
        if(end != -1){
            String id = message.substring(begin, end);
            String msg = message.substring(end+1);
            PrintWriter oos = hm.get(id);
 
            try{
                if(oos != null){
                    oos.println( user_id + "님이 다음과 같은 귓속말을 보내셨습니다. : " + msg );
                    oos.flush();
                }
 
            }catch(Exception e)
            { 
                 
            }
        }
    }*/
 
}