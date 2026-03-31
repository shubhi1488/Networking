package SelectiveRepeat;
import java.io.*;
import java.net.*;
import java.util.*;
public class Sender {
    static final int window_size=4;
    static final int total_frames=10;
    static final int timeout=3000;
    public static void main(String[] args) throws Exception{
        Socket socket=new Socket("localhost",7000);
        socket.setSoTimeout(timeout);
        DataOutputStream out=new DataOutputStream(socket.getOutputStream());
        DataInputStream in=new DataInputStream(socket.getInputStream());
        boolean[] acked=new boolean[total_frames];
        int base=0;
        while(base<total_frames){
            //send frames in window
            for(int i=base;i<base+window_size && i<total_frames;i++){
                if(!acked[i]){
                    System.out.println("sending frame:"+i);
                    out.writeInt(i);
                }
            }
            try{
                int ack=in.readInt();
                System.out.println("ack received:"+ack);
                acked[ack]=true;
                //slide window
                while(base<total_frames && acked[base]){
                    base++;
                }

            }catch (SocketTimeoutException e){
                System.out.println("Timeout! resending unacknowldeged frames...");
            }
        }
        System.out.println("all frames sent successfully!");
        socket.close();
    }
}
