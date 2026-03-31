package SelectiveRepeat;
import java.io.*;
import java.net.*;
import java.util.*;
public class Receiver {
    public static void main(String[] args) throws Exception{
        ServerSocket server=new ServerSocket(7000);
        Socket socket=server.accept();
        DataInputStream in=new DataInputStream(socket.getInputStream());
        DataOutputStream out=new DataOutputStream(socket.getOutputStream());
        Random rand=new Random();
        int window_size=4;
        Set<Integer> received=new HashSet<>();
        while(true){
            int frame=in.readInt();
            int event=rand.nextInt(4);
            switch(event){
                case 0:
                    System.out.println("frame:"+frame+"received correctly");
                    received.add(frame);
                    out.writeInt(frame);
                    break;
                case 1:
                    System.out.println("frame:"+frame+"received but ack lost");
                    received.add(frame);
                    break;
                case 2:
                    System.out.println("frame:"+frame+"lost");
                    break;
                case 3:
                    System.out.println("frame:"+frame+"recieved, ack delayed");
                    received.add(frame);
                    Thread.sleep(4000);
                    out.writeInt(frame);
                    break;
            }
        }
    }
}
