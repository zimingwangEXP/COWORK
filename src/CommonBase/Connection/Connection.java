package CommonBase.Connection;
import CommonBase.Log.Log;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
//connection中可能需要对文件流进行互斥访问
//默认server地址应该使用配置文件保存到本地，方便实现修改默认ip地址的功能
public class Connection {
    protected Log log=new Log();
    protected Socket connection=null;
    protected ObjectOutputStream output=null;
    protected ObjectInputStream input=null;
    protected  Integer default_server_port=12345;
  public int  getPort() {
        return connection.getLocalPort();
    }
    public Connection(){//默认构造函数以服务器地址为目标，建立连接
        try {
            connection=new Socket(InetAddress.getByName("localhost"),default_server_port.intValue());
        }
        catch (UnknownHostException e)
        {
            log.Write("怕是电脑没联网\n");
            e.printStackTrace();
        }
        catch(IOException e)
        {
            log.Write("连接到服务器时出现了IO_Exception\n");
          e.printStackTrace();
        }
        InitStream();
    }
    public boolean WriteObject(Object any_imformation_object) throws IOException {//每写一个object，flush一遍
        if (output == null) {
            System.out.println("写Object出现问题，请检查");
            log.Write("写Object出现问题，请检查\n");
            return false;
        }
        else {
            output.writeObject(any_imformation_object);
            output.flush();
            return true;
        }
    }

    public Object ReadObject() throws IOException, ClassNotFoundException {
     return input.readObject();
    }

    public Connection(Socket connection){
        this.connection=connection;
        InitStream();
    }
    public void InitStream(){
        try {
            output=new ObjectOutputStream(connection.getOutputStream());
            input=new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            log.Write("error:未成功初始化IO流,检查网络情况\n");
            e.printStackTrace();
        }
    }
    public ObjectInputStream getInput() {
        return input;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public Socket getConnection() {
        return connection;
    }

    public Integer getDefault_server_port() {
        return default_server_port;
    }

    public void setConnection(Socket connection) {
        this.connection = connection;
    }

    public void setDefault_server_port(Integer default_server_port) {
        this.default_server_port = default_server_port;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }

    public void close() {
        try {
            output.close();
            input.close();
            connection.close();
        }
        catch (IOException e)
        {
            log.StandardWrite("close过程中抛出IO错误\n");
        }
    }
}
