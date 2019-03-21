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
            server = new ServerSocket(port); //서버소켓을 생성
             
            System.out.println("**************************************");
            System.out.println("*              채팅 서버                *" );
            System.out.println("**************************************");
            System.out.println("클라이언트의 접속을 기다립니다.");
 
            hm = new HashMap<String, PrintWriter>();
            hm2 = new HashMap<Integer, Socket>();
            int i = 1;
 
            while(true) {
                child = server.accept(); //클라이언트의 소켓을 연결받습니다.
                if(child != null) { //클라이언트 소켓과 연결시
                    sr = new TransferMessage(child, hm, hm2, i); //채팅 스레드를 생성합니다.
                    //System.out.println(child.getInetAddress().getHostAddress());
                    t = new Thread(sr); //채팅스레드를 시작합니다.
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