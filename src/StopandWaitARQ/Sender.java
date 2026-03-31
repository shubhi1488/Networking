package StopandWaitARQ;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.io.*;
public class Sender {
    static final int timeout=2000;
    public static void main(String[] args) throws Exception{
        Socket socket=new Socket("localhost",5000);
        DataOutputStream out=new DataOutputStream(socket.getOutputStream());
        socket.setSoTimeout(timeout);
        int frame=0;
        int totalframes=5;
        for(int i=0;i<totalframes;){
            System.out.println("\nSending frame:"+frame);
            out.writeInt(frame);
            try{
                DataInputStream in=new DataInputStream(socket.getInputStream());
                int ack=in.readInt();
                System.out.println("ACK received:"+ack);
                if(ack==frame){
                    frame=1-frame;
                    i++;
                }
            }catch(SocketTimeoutException e){
                System.out.println("Timeout!Resending frame:"+frame);
            }
        }
        socket.close();
    }
}
