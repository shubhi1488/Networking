package GoBackNARQ;
import java.io.*;
import java.net.*;
import java.util.Random;
public class Receiver {
    public static void main(String[] args) throws Exception{
        ServerSocket server=new ServerSocket(6000);
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
                    System.out.println("frame:"+frame+"received correctly");
                    if(frame==expected){
                        out.writeInt(frame);
                        expected++;
                    }
                    break;
                case 1:
                    System.out.println("frame:"+frame+"received but ack lost");
                    if(frame==expected){
                        expected++;
                    }
                    break;
                case 2:
                    System.out.println("frame:"+frame+"lost");
                    break;
                case 3:
                    System.out.println("frame:"+frame+"received, ack is delayed");
                    if(frame==expected){
                        Thread.sleep(4000);
                        out.writeInt(frame);
                        expected++;
                    }
                    break;
            }
        }
    }
}
