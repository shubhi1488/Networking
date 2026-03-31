package GoBackNARQ;
import java.io.*;
import java.net.*;
public class Sender {
    static final int window_size=4;
    static final int total_frames=10;
    static final int timeout=3000;
    public static void main(String[] args) throws Exception{
        Socket socket=new Socket("localhost",6000);
        socket.setSoTimeout(timeout);
        DataOutputStream out=new DataOutputStream(socket.getOutputStream());
        DataInputStream in=new DataInputStream(socket.getInputStream());
        int base=0;
        int nextSeqNum=0;
        while(base<total_frames){
            //sender window-
            while(nextSeqNum<base+window_size && nextSeqNum<total_frames){
                System.out.println("sending frames:"+nextSeqNum);
                out.writeInt(nextSeqNum);
                nextSeqNum++;
            }
            try{
                int ack=in.readInt();
                System.out.println("ack received:"+ack);
                base=ack+1;
            }catch(SocketTimeoutException e){
                System.out.println("Timeout!resending from frame:"+base);
                nextSeqNum=base;
            }
        }
        System.out.println("all frames sent successfully!");
        socket.close();
    }
}
