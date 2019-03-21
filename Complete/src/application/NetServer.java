package application;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
 
@SuppressWarnings("serial")
public class NetServer implements java.io.Serializable
{
 
    int port = 4000;
    ServerSocket server = null;
    Socket child = null;
 
    HashMap<String, PrintWriter> hm;
    HashMap<Integer, Socket> hm2;
 
    @SuppressWarnings("unlikely-arg-type")
   public NetServer() {
 
       TransferMessage sr;
        Thread t;
 
        try {
            server = new ServerSocket(port); //���������� ����
             
            System.out.println("**************************************");
            System.out.println("*              ä�� ����                *" );
            System.out.println("**************************************");
            System.out.println("Ŭ���̾�Ʈ�� ������ ��ٸ��ϴ�.");
 
            hm = new HashMap<String, PrintWriter>();
            hm2 = new HashMap<Integer, Socket>();
            int i = 1;
 
            while(true) {
                child = server.accept(); //Ŭ���̾�Ʈ�� ������ ����޽��ϴ�.
                if(child != null) { //Ŭ���̾�Ʈ ���ϰ� �����
                    sr = new TransferMessage(child, hm, hm2, i); //ä�� �����带 �����մϴ�.
                    //System.out.println(child.getInetAddress().getHostAddress());
                    t = new Thread(sr); //ä�ý����带 �����մϴ�.
                    t.start();
                }
                i++;
            }
        }
        catch ( Exception e )   {
            e.printStackTrace();
        }
    }
 
 
 
    public static void main(String[] args)
 
    {
        new NetServer();
    }
 
}