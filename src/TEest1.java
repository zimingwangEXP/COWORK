import CommonBase.CSLinker.CSLinker;
import CommonBase.Connection.BasicInfoTransition;
import CommonBase.Connection.Connection;
import CommonBase.Data.ClientStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TEest1 {
    public static  void main(String[]args) throws IOException, ClassNotFoundException {
        System.out.println( CSLinker.Login("100023","123",ClientStatus.online));
          CSLinker.SendFriendRequest("100027","hello");

    }

}
