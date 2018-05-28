package Connection;

import Data.BasicInfo;
import Data.ClientStatus;
import Data.LoginStatus;
import Data.SuperInfo;
import Log.Log;
import  Data.UserSnapShot;
import com.sun.xml.internal.bind.api.impl.NameConverter;

import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;

public class BasicInfoTransition
{
    private static DecimalFormat fm = null;
    Log log=new Log();
    protected  Connection link=null;
    protected  int  trans_size=1024*1024;
    byte[] bytes=null;
    public BasicInfoTransition(Connection link)
    {
        this.link=link;
        bytes=new byte[trans_size];
    }
    public BasicInfoTransition(Connection link,int buffer_size)
    {
        this.link = link;
        trans_size = buffer_size;
        bytes = new byte[trans_size];
    }
    public void SendBasicInfo(BasicInfo info)
    {
        try {
            link.WriteObject("basic_info_block");
            link.WriteObject(info);
        } catch (IOException e) {
            log.StandardWrite("发送基础信息块时抛出了异常\n");
            e.printStackTrace();
        }
    }
    public void SendSuperInfo(SuperInfo info) {
        try {
            link.WriteObject("super_info_block");
            link.WriteObject(info);
        } catch (IOException e) {
            log.StandardWrite("发送高级信息块时抛出了异常\n");
            e.printStackTrace();
        }
    }
    public void SendStatus(String id,ClientStatus status){
        try {
            link.WriteObject("user_status");
            link.WriteObject(id);
            link.WriteObject(status);
        }
        catch (Exception e){
            log.Write("error\n");
        }
    }
    public BasicInfo  GetBasicInfoById(String id){
        BasicInfo info=null;
        try {
            link.WriteObject("get_basic_info_by_id");
             String temp=(String)link.ReadObject();
             if(temp.equals("basic_info_block"))
             {
                 info=(BasicInfo)link.ReadObject();
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }
    public void AddFriend(UserSnapShot friend_request){
        try {
        link.WriteObject("add_friend");
        link.WriteObject(friend_request);
        } catch (IOException e) {
            log.StandardWrite("error");
            e.printStackTrace();
        }
    }
   public void SendLoginInfo(String username, String password, ClientStatus status){
        try {
            link.WriteObject("login");
            link.WriteObject(username);
            link.WriteObject(password);
            link.WriteObject(status);
        }
        catch(IOException e)
        {
            log.Write("客户端发送Login信息时出错\n");
        }

   }
   public void  SendRegistInfo(BasicInfo info){

       try {
        link.WriteObject("regist");
           link.WriteObject(info);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   public String[] IdToIp(String id){//将往返的过程全部实现,根据id获取ip，String[0]是相关客户端ip,String[1]为其绑定的端口号（存在问题）
       String[] rnt=new String[2];
        try {
            link.WriteObject("id_to_ip");
            link.WriteObject(id);
            rnt[0]=(String)link.ReadObject();
            rnt[1]=(String)link.ReadObject();
        }
        catch (Exception e)
        {
            log.Write("error\n");
        }
        return rnt;
   }
  public LoginStatus  ClientReceiveLoginInfo(BasicInfo basic_info, SuperInfo super_info ){
       LoginStatus result=null;
      try {
          result=(LoginStatus) (link.ReadObject());
          if(result==LoginStatus.find)
          {

            basic_info=(BasicInfo) link.ReadObject();
            super_info=(SuperInfo)link.ReadObject();
          }
          else{
              basic_info=null;
              super_info=null;
          }
      } catch (IOException e) {
          log.Write("error\n");
          e.printStackTrace();
      } catch (ClassNotFoundException e) {
          log.Write("error\n");
          e.printStackTrace();
      }
      return result;
  }
    public Object TemplateReceive(){
        Object something=null;
        try {
            something=link.ReadObject();
        }
        catch(Exception e) {
            log.Write("标准模板读写时出错\n");
        }
        return something;
    }
    public void TemplateSend(Object something){
        try {
            link.WriteObject(something);
        } catch (IOException e) {
            log.Write("标准模板读写时出错\n");
            e.printStackTrace();
        }
    }

    public BasicInfo ReceiveBasicInfo(){
        BasicInfo rnt=null;
        try {
            rnt=(BasicInfo)link.ReadObject();
        } catch (IOException e) {
            log.StandardWrite("error:读基础信息块时抛出IOException\n");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            log.StandardWrite("error:读基础信息块时抛出ClassNotFoundException\n");
            e.printStackTrace();
        }
        return rnt;
    }
   public void SendMessage(String text) throws IOException
   {
        link.WriteObject("chat_text");
         link.WriteObject(text);
   }
   public String ReceiveMessage() throws IOException, ClassNotFoundException {
         return (String)link.ReadObject();
    }
    public ClientStatus ReceiveStatus() throws IOException, ClassNotFoundException {
        return (ClientStatus)link.ReadObject();
    }
    public void SendFile(File file)
    {
        if(!file.exists())
        {
           log.StandardWrite("需发送的文件不存在");
        }
        else
        {
            try {
                FileInputStream bridge = new FileInputStream(file);
                int length, progress = 0;
                link.output.writeUTF(file.getName());
                link.output.flush();
                link.output.writeLong(file.length());
                link.output.flush();
                System.out.println("======== 开始传输文件 ========");
                while ((length = bridge.read(bytes, 0, bytes.length)) != -1) {
                    link.output.write(bytes, 0, length);
                    link.output.flush();
                    progress += length;
                    System.out.print("| " + ((double) 100 * progress / file.length()) + "% |");
                }
                System.out.println();
                System.out.println("======== 文件传输成功 ========");
            }
            catch (IOException e)
            {
                log.StandardWrite("error:在写文件时抛出了IOException\n");
            }
        }
    }
    private String getFormatFileSize(long length)
    {
        double size = ((double) length) / (1 << 30);
        if(size >= 1) {
            return fm.format(size) + "GB";
        }
        size = ((double) length) / (1 << 20);
        if(size >= 1) {
            return fm.format(size) + "MB";
        }
        size = ((double) length) / (1 << 10);
        if(size >= 1) {
            return fm.format(size) + "KB";
        }
        return length + "B";
    }
    public void ReceiveFile(String name)
    {
        File dic=new File(name);
        if(dic.isDirectory()&&dic.exists())
        {
            try {
                int length;
                String filename=link.input.readUTF();
                long size=link.input.readLong();
                FileOutputStream bridge = new FileOutputStream(dic.getAbsolutePath()+File.separatorChar+filename);
                while ((length = link.input.read(bytes, 0, bytes.length)) != -1) {
                    bridge.write(bytes, 0,length);
                    bridge.flush();
                }
                System.out.println("======== 文件接收成功 [File Name：" + dic.getName() + "] [Size：" + getFormatFileSize(dic.length()) + "] ========");
            }
            catch (IOException e)
            {
                log.Write("error:读取文件时抛出了异常\n");
            }
        }
        else
        {
            log.StandardWrite("error:接受文件时选择的文件地址有问题\n");
        }
    }
}