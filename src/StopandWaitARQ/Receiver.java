package StopandWaitARQ;
import java.io.*;
import java.net.*;
import java.util.*;
public class Receiver {
    public static void main(String[] args) throws Exception{
        ServerSocket server=new ServerSocket(5000);
        Socket socket=server.accept();
        DataInputStream in=new DataInputStream(socket.getInputStream());
        DataOutputStream out=new DataOutputStream(socket.getOutputStream());
        Random rand=new Random();
        int expected=0;
        while(true){
            int frame=in.readInt();
            int event=rand.nextInt(4);
            switch(event){
                case 0:
                    System.out.println("Frame:"+frame+"received correctly");
                    out.writeInt(frame);
                    expected=1-expected;
                    break;
                case 1:
                    //ACK lost
                    System.out.println("frame:"+frame+"received but ACK lost");
                    break;
                case 2:
                    //frame lost
                    System.out.println("frame:"+frame+"lost");
                    break;
                case 3:
                    System.out.println("frame:"+frame+"recieved but ack delayed");
                    Thread.sleep(3000);
                    out.writeInt(frame);
                    expected=1-expected;
                    break;
            }
        }
    }
}
