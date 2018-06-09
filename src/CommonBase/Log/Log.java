package CommonBase.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Log {
    private File file=new File("/net_log.txt");
    private PrintWriter pw=null;
    private Scanner   sc=null;
    protected void test() {
        if(file.isDirectory())
        {
            System.out.println("参数应该是一个文件地址,而非目录");
        }
        else  if(file.isFile())
        {
            if(!file.exists())
            {//默认创建一个文件
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    System.out.println("创建不存在的文件时发生了I/O错误,请检查文件权限");
                    e.printStackTrace();
                }
            }
        }
        else
        {
            System.out.println("输入既不是文件也不是目录，请按照路径的正确输入法输入");
        }
    }
    public void ModifyLogAdrress(String address){
        file=new File(address);
        test();
    }
    public Log(){
        test();
    }
    public Log(String path){
        file=new File(path);
        test();
    }
    public void StandardWrite(String info) {
       Write(info);
       System.out.print(info);
    }
    public void Write(String text){//写入文件时请严格按照 error:(specified imformation)
        //考虑对写入增加安全检查
        try {
            pw=new PrintWriter(file);
        } catch (FileNotFoundException e) {
            System.out.println("发生了诡异的错误，请认真检查Log类的代码逻辑");
            e.printStackTrace();
        }
        pw.append(text);
        pw.close();
    }
    public String Read(){//仅用于过滤对应级别的日志信息
        try {
            sc=new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("发生了诡异的错误，请认真检查Log类的代码逻辑");
            e.printStackTrace();
        }
        if(sc.hasNextLine())
            return sc.nextLine();
        else {
            sc.close();
            return null;
        }
    }


}
