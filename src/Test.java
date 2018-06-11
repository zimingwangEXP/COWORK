import CommonBase.Connection.BasicInfoTransition;
import CommonBase.Connection.Connection;
import CommonBase.Data.BasicInfo;
import CommonBase.Data.ClientStatus;
import CommonBase.Data.LoginStatus;
import CommonBase.Data.SuperInfo;

import java.io.IOException;
import java.net.Socket;

public class Test {

    public static  void main(String[]args)
    {
         //模拟客户端
        BasicInfoTransition bf_trans1=null,bf_trans2=null,bf_trans3=null;
        try {
             bf_trans1=new BasicInfoTransition(new Connection(new Socket("localhost",12345)));

            /* bf_trans1.SendMessage("regist");
             BasicInfo info1=new BasicInfo.BasicInfoBuilder("ac_automata",null).SetAge(18).
                     SetClientStatus(ClientStatus.online).SetJob("student").
                     SetMailAdress("2250606701@qq.com").SetSex(true).SetBirthday("1998-10-20").
                     SetPhone_number("110").SetPassword("5896152252").Builder();
            bf_trans1.TemplateSend(info1);
            System.out.println(bf_trans1.ReceiveMessage());*/
            bf_trans1.SendMessage("login");
            bf_trans1.SendMessage("100017");
            bf_trans1.SendMessage("5896152252");
            bf_trans1.SendStatus(ClientStatus.online);
            LoginStatus res=(LoginStatus)bf_trans1.TemplateReceive();
            if(res==LoginStatus.find) {
                BasicInfo bf = bf_trans1.ReceiveBasicInfo();
                SuperInfo sf = (SuperInfo) bf_trans1.TemplateReceive();
                bf.Show();
            }
            else
            {
                System.out.println(res);
            }
           /*  bf_trans1.
           bf_trans2=new BasicInfoTransition(new Connection(new Socket("localhost",12345)));
            bf_trans2.SendMessage("regist");
            BasicInfo info2=new BasicInfo.BasicInfoBuilder("ac_automata",null).SetAge(18).
                    SetClientStatus(ClientStatus.online).SetJob("student").
                    SetMailAdress("2250606701@qq.com").SetSex(true).SetBirthday("1998-10-20").
                    SetPhone_number("110").Builder();
            bf_trans2.TemplateSend(info2);
            System.out.println(bf_trans2.ReceiveMessage());

            bf_trans3=new BasicInfoTransition(new Connection(new Socket("localhost",12345)));
            bf_trans3.SendMessage("regist");
            BasicInfo info3=new BasicInfo.BasicInfoBuilder("ac_automata",null).SetAge(18).
                    SetClientStatus(ClientStatus.online).SetJob("student").
                    SetMailAdress("2250606701@qq.com").SetSex(true).SetBirthday("1998-10-20").
                    SetPhone_number("110").Builder();
            bf_trans3.TemplateSend(info3);
            System.out.println(bf_trans3.ReceiveMessage());*/

        } catch (IOException e) {
            System.out.println("诡异错误");
            e.printStackTrace();
        }
      /*  catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/


    }
}


