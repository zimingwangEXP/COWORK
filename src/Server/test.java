package Server;

import CommonBase.Connection.BasicInfoTransition;
import CommonBase.Connection.Connection;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class test {
    public static  void main(String[]args) throws IOException, ClassNotFoundException {
        BasicInfoTransition bf_link=new BasicInfoTransition(new Connection(new Socket(InetAddress.getByName("localhost"),12345)));
       System.out.println(bf_link.ReceiveMessage());
        System.out.println(bf_link.ReceiveMessage());
       bf_link.ReceiveFile("D:\\receive");
    }
}
