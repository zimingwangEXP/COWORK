/*package Client.application;

import Client.application.UserInfoBuffer;
import CommonBase.Connection.BasicInfoTransition;
import CommonBase.Connection.Connection;
import CommonBase.Data.BasicInfo;
import CommonBase.Data.TempSavedInfo;
import CommonBase.Data.UserSnapShot;

import java.net.Socket;
import java.util.ArrayList;

public class temp {
    public static void main(String[]args){
        //1.更新用户基本信息
            //更新信息到info——buffer
            UserInfoBuffer.bf_trans_to_server.SendBasicInfo(UserInfoBuffer.bf);
           //更新前端

       //2.设置状态
        //更新信息到info——buffer
         UserInfoBuffer.bf_trans_to_server.SendStatus(UserInfoBuffer.bf.getStatus());
         //更新前端
        //3.查找指定好友
          //获取id信息
          UserInfoBuffer.bf_trans_to_server.SendMessage("");
          UserInfoBuffer.bf_trans_to_server.SendMessage("id");
         BasicInfo bf_info=UserInfoBuffer.bf_trans_to_server.ReceiveBasicInfo();
         //将信息显示在界面上
        //4.添加好友
          //获取对应的好友id
         UserInfoBuffer.bf_trans_to_server.IdToIp("id");
        String res=UserInfoBuffer.bf_trans_to_server.ReceiveMessage();
        if(res.equals("not_online")) {
            UserInfoBuffer.bf_trans_to_server.SendMessage("add_friend");
            UserInfoBuffer.bf_trans_to_server.TemplateSend(new TempSavedInfo(from,to,"add_friend","验证消息"));
            String an=UserInfoBuffer.bf_trans_to_server.ReceiveMessage();
            if(an.equals("fail"))
            {
                //显示已经被对方加入黑名单
            }
            else
            {
                //服务器好友验证发送
            }
        }
        else if(res.equals("no_exist"))
        {

        }
        else {
            String ip=UserInfoBuffer.bf_trans_to_server.ReceiveMessage();
            BasicInfoTransition direct_connect=new BasicInfoTransition(new Connection(new Socket(ip,12345)));
            direct_connect.TemplateSend(new TempSavedInfo(from,to,"add_friend","验证消息"));
        }
        //已经发出，请等待。。。。。。
            UserInfoBuffer.bf_trans_to_server.ReceiveMessage();//清空下一个
//5.查看他人资料
        //获取id
        UserInfoBuffer.bf_trans_to_server.SendMessage("get_basic_info_by_id");
        UserInfoBuffer.bf_trans_to_server.SendMessage("id");
        BasicInfo info=UserInfoBuffer.bf_trans_to_server.ReceiveBasicInfo();
        //根据info更新相关界面

        //修改好友组
        //获取用户的superinfo
        UserInfoBuffer.bf_trans_to_server.SendSuperInfo();

        //好友组中查找特定好友
        //不是我的任务

        //修改黑名单
        //更新superinfo
        UserInfoBuffer.bf_trans_to_server.SendMessage("update_blacklist");
        UserInfoBuffer.bf_trans_to_server.TemplateSend(ArrayList<UserSnapShot>);
       //
        /*
        接受：
            同意或拒绝好友请求
             接受服务器的更新信息
         */
/*
删号
 */

         /*
           发送消息及文件：
            // //获取对应的好友id
         UserInfoBuffer.bf_trans_to_server.IdToIp("id");
        String[2]!!!!!! res=UserInfoBuffer.bf_trans_to_server.ReceiveMessage();
        if(res.equals("not_online")) {
           UserInfoBuffer.bf_trans_to_server.SendMessage("temptext tempfile");
           //消息考虑逐条发送或批量发送
           //发送对应的TempSave
           //文件则还需传文件
        }
        else if(res.equals("no_exist"))
        {

        }
        else
         {
            String ip=UserInfoBuffer.bf_trans_to_server.ReceiveMessage();
            BasicInfoTransition direct_connect=new BasicInfoTransition(new Connection(new Socket(ip,12345)));
            //如何结束输入？
            //定时检测或一次连接一次输入
              direct_connect.SendMessage("text" "file");
        调用不同函数发送
        }
        */
/*
    }
}
*/