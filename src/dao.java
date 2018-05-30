//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.ArrayList;

import Data.*;

import static Data.BasicInfo.*;


public class dao {
    static String username = "root";
    static String pwd = "123456";
    static String url = "jdbc:mysql://localhost:3306/icsy2";
    static String driver = "com.mysql.jdbc.Driver";
    static Connection con;
    static Statement statement;

    public dao() {
    }

    public static void main(String[] args) {
        new dao();
    }
    private static Date convertToDate(String str) {
        String[] li = str.split(" ");
        if(li.length != 2) {
            System.out.println("convertToDate 出错，空格错误");
            return new Date();
        } else {
            String[] para = li[0].split("-");
            String[] para1 = li[1].split(":");
            return (new GregorianCalendar((new Integer(para[0])).intValue(), (new Integer(para[1])).intValue(), (new Integer(para[2])).intValue(), (new Integer(para1[0])).intValue(), (new Integer(para1[1])).intValue(), (new Integer(para[2])).intValue())).getTime();
        }
    }
    private void start() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException var3) {
            System.out.println("error in loading driver.");
        }

        try {
            con = DriverManager.getConnection(url, username, pwd);
            statement = con.createStatement();
        } catch (SQLException var2) {
            System.out.println("error in connect database.");
        }

    }
    private void close() {
        try {
            statement.close();
        } catch (SQLException var3) {
            System.out.println("error in close statement.");
        }

        try {
            con.close();
        } catch (SQLException var2) {
            System.out.println("error in close connection.");
        }

    }
    public BasicInfo getBasicInfoById(String id) {
        this.start();
        LinkedList li = new LinkedList();

        BasicInfoBuilder b = null;
        try {
            ResultSet e = statement.executeQuery("select * from basicinfo where id =" + id + ";");

            while (e.next()) {
                b = new BasicInfoBuilder(e.getString(1), e.getString(2));
                b.SetSex(e.getBoolean(3));
                b.SetMailAdress(e.getString(4));
                b.SetQuestion(e.getString(5));
                b.SetSolution(e.getString(6));
                b.SetJob(e.getString(7));
                b.SetSignatrue(e.getString(8));
                b.SetPassword(e.getString(9));
                b.SetAge(e.getInt(10));
                b.SetClientStatus(e.getString(11));
            }
        } catch (SQLException var7) {
            System.out.println("error in getUser(" + id + ")");
        } finally {
            this.close();
        }

        return new BasicInfo(b);
    }
    public Boolean addBasicInfo(BasicInfo u) {
        this.start();
        Boolean flag = Boolean.valueOf(true);

        try {
            statement.execute("insert into basicinfo(uname,id,sex,email,question,solution,job,signature,pwd,age,ClientStatus) values(\'" + u.getNick_name() + "\',\'" + u.getId() + "\',\'" + u.getSex() + "\',\'" + u.getMail_address() + "\',\'" + u.getQuestion() + "\',\'" + u.getSolution() + "\',\'" + u.getJob() + "\',\'" + u.getSignature() + "\',\'" + u.getPassword() + "\',\'" + u.getAge() + "\',\'" + u.getStatus() + "\');");
        } catch (SQLException var7) {
            System.out.println("error in add Basic Info:" + u);
            flag = Boolean.valueOf(false);
        } finally {
            this.close();
        }
        return flag;
    }
    public Boolean deleteBasicInfo(String id) {
        this.start();
        Boolean flag = Boolean.valueOf(true);

        try {
            statement.execute("delete from basicinfo where id =\'" + id + "\';");
        } catch (SQLException var7) {
            System.out.println("error in delete basic info:id=" + id);
            flag = Boolean.valueOf(false);
        } finally {
            this.close();
        }

        return flag;
    }
    public Boolean updateBasicInfo(BasicInfo u) {
        this.start();
        Boolean flag = Boolean.valueOf(true);

        try {
            statement.execute("update basicinfo set uname=\'" + u.getNick_name() + "\',sex=\'" + u.getSex() + "\',email=\'" + u.getMail_address() + "\',question =\'" + u.getQuestion() + "\',solution =\'" + u.getSolution() + "\',job =\'" + u.getJob() + "\',signature =\'" + u.getSignature() + "\',pwd =\'" + u.getPassword() + "\',age=\'" + u.getAge() + "\',ClientStatus=\'" + u.getStatus() + "\' where id =\'" + u.getId() + "\';");
        } catch (SQLException var7) {
            System.out.println("error in update:" + u);
            flag = Boolean.valueOf(false);
        } finally {
            this.close();
        }

        return flag;
    }
    private int NumberOfID() {
        int uid=0;
        try {
            String sqlString="select max(id) from basicinfo";
            ResultSet rs=statement.executeQuery(sqlString);
            if(rs.next()){
                int maxid=rs.getInt(2);
                if(maxid>uid)uid=maxid;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return uid;
    }
    private int NumberOfOnline() {
        String str="online";
        int ans = 0;
        try {
            ResultSet rs=statement.executeQuery( "select count(11) from basicinfo where ClientStatus =" + str + ";");
            if(rs.next()){
                ans=rs.getInt(1);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ans;
    }
    public String getStatus(String id) {
        this.start();
        String str=null;

        try {
            ResultSet e = statement.executeQuery("select * from basicinfo where id =" + id + ";");

            while (e.next()) {
              str = e.getString(11);
            }
        } catch (SQLException var7) {
            System.out.println("error in getStatus(" + id + ")");
        } finally {
            this.close();
        }
        return str;
    }
    public Boolean SetStatus(String id,String status) {
        this.start();
        Boolean flag = Boolean.valueOf(true);

        try {
            statement.execute("insert into basicinfo(ClientStatus) values(\'" + status + "\');");
        } catch (SQLException var7) {
            System.out.println("error in set status:" + id);
            flag = Boolean.valueOf(false);
        } finally {
            this.close();
        }
        return flag;
    }
    public Boolean updatePwd(BasicInfo u) {
        this.start();
        Boolean flag = Boolean.valueOf(true);

        try {
            statement.execute("update user set pwd=\'" + u.getNick_name() + "\' where id =\'" + u.getId() + "\';");
        } catch (SQLException var7) {
            System.out.println("error in updatepwd:" + u);
            flag = Boolean.valueOf(false);
        } finally {
            this.close();
        }

        return flag;
    }
    public ArrayList<failrelation> getBlack(String id) {
        this.start();
        ArrayList<failrelation> li = new ArrayList<failrelation>();
        try {
            ResultSet re = statement.executeQuery("select * from failrelation where id1 = \'" + id + "\';");

            while(re.next()) {
                li.add(new failrelation(re.getString(1), re.getString(2)));
            }
        } catch (SQLException var8) {
            System.out.println("error in getBlacklist:id=" + id);
        } finally {
            this.close();
        }
        return li;
    }
    public ArrayList<UserSnapShot> getBlack_list(String id) {
        this.start();
        ArrayList<failrelation> li = new ArrayList<failrelation>();
        ArrayList<UserSnapShot> black=new ArrayList<UserSnapShot>();
        li=getBlack(id);
        try {
            for(int i = 0;i < li.size();i++) {
                failrelation f=new failrelation();
                f=li.get(i);
                ResultSet re = statement.executeQuery("select * from basicinfo where id = \'" + f.getId2() + "\';");

                while (re.next()) {
                    black.add(new UserSnapShot(re.getString(2),re.getString(1),re.getString(11),re.getString(8),"blacklist"));
                }
            }
        } catch (SQLException var8) {
            System.out.println("error in getBlacklist:id=" + id);
        } finally {
            this.close();
        }
        return black;
    }
    public Boolean addBlacklist(failrelation r) {
        this.start();
        Boolean flag = Boolean.valueOf(true);
        try {
            statement.execute("insert into failrelation(id1,id2) values(\'" + r.getId1() + "\',\'" + r.getId2() + "\');");
        } catch (SQLException var7) {
            System.out.println("error in addBlacklist(" + r + ").");
            flag = Boolean.valueOf(false);
        } finally {
            this.close();
        }

        return flag;
    }
    public Boolean deleteBlacklist(failrelation r) {
        this.start();
        Boolean flag = Boolean.valueOf(true);

        try {
            statement.execute("delete from failrelation where (id1=\'" + r.getId1() + "\'&&id2 =\'" + r.getId2() + "\')||(id1=\'" + r.getId2() + "\'&&id2=\'" + r.getId1() + "\');");
        } catch (SQLException var7) {
            System.out.println("error in deleteFailRelation(" + r + ").");
            flag = Boolean.valueOf(false);
        } finally {
            this.close();
        }
        return flag;
    }
    public ArrayList<relation> getFriend(String id){
        start();
        ArrayList<relation> li = new ArrayList<relation>();
        try {
            statement = con.createStatement();
            ResultSet re = statement.executeQuery("select * from relation where id1 ="+id+"||id2 ="+id+";");
            while(re.next()){
                li.add(new relation(re.getString(1),re.getString(2),re.getString(3)));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("error in getRelation("+id+")");
        }
        finally{close();}
        return li;
    }
    public Boolean addFriendlist(relation r) {
        this.start();
        Boolean flag = Boolean.valueOf(true);

        try {
            String e = r.getId1();
            String id2 = r.getId2();
            String groupid=r.getgroupid();
            if((new Integer(e)).intValue() > (new Integer(id2)).intValue()) {
                String id = e;
                e = id2;
                id2 = id;
            }
            statement.execute("insert into relation(id1,id2,Groupid) values(\'" + e + "\',\'" + id2 + "\',\'" + groupid + "\');");
        } catch (SQLException var9) {
            System.out.println("error in addRelation:" + r);
            flag = Boolean.valueOf(false);
        } finally {
            this.close();
        }

        return flag;
    }
    public Boolean deleteFriendlist(relation r) {
        this.start();
        Boolean flag = Boolean.valueOf(true);

        try {
            String e = r.getId1();
            String id2 = r.getId2();
            if((new Integer(e)).intValue() > (new Integer(id2)).intValue()) {
                String id = e;
                e = id2;
                id2 = id;
            }

            statement.execute("delete from relation where id1 = \'" + e + "\'&&id2 =\'" + id2 + "\';");
        } catch (SQLException var9) {
            System.out.println("error in deleteRelation(" + r + ").");
            flag = Boolean.valueOf(false);
        } finally {
            this.close();
        }

        return flag;
    }
    public ArrayList<UserSnapShot> getFriend_list(String id) {
        this.start();
        ArrayList<relation> li = new ArrayList<relation>();
        ArrayList<UserSnapShot> Friend=new ArrayList<UserSnapShot>();
        li = getFriend(id);
        try {
            for(int i = 0;i < li.size();i++) {
                relation f=new relation();
                f=li.get(i);
                ResultSet re = statement.executeQuery("select * from basicinfo where id = \'" + f.getId2() + "\';");

                while (re.next()) {
                    Friend.add(new UserSnapShot(re.getString(2),re.getString(1),re.getString(11),re.getString(8),f.getgroupid()));
                }
            }
        } catch (SQLException var8) {
            System.out.println("error in get Friend list:id=" + id);
        } finally {
            this.close();
        }
        return Friend;
    }
    public Boolean addmessage(message msg) {
        this.start();
        Boolean flag = Boolean.valueOf(true);

        try {
            statement.execute("insert into message(id1,id2,time,content) values(\'" + msg.getId1() + "\',\'" + msg.getId2() + "\',\'" + msg.getDate().toLocaleString() + "\',\'" + msg.getContent() + "\');");
        } catch (SQLException var7) {
            var7.printStackTrace();
            System.out.println("error in add message(" + msg + ").");
            flag = Boolean.valueOf(false);
        } finally {
            this.close();
        }

        return flag;
    }
    public List<message> getfailmessage(String id) {
        this.start();
        LinkedList li = new LinkedList();

        try {
            ResultSet re = statement.executeQuery("select * from failmessage where id2 = \'" + id + "\';");

            while(re.next()) {
                Date e = convertToDate(re.getString(3));
                li.add(new message(re.getString(1), re.getString(2), e, re.getString(4)));
            }
        } catch (SQLException var8) {
            System.out.println("error in get fail message(" + id + ").");
        } finally {
            this.close();
        }

        return li;
    }
    public Boolean addfailmessage(message msg) {
        this.start();
        Boolean flag = Boolean.valueOf(true);

        try {
            statement.execute("insert into failmessage(id1,id2,time,content) values(\'" + msg.getId1() + "\',\'" + msg.getId2() + "\',\'" + msg.getDate().toLocaleString() + "\',\'" + msg.getContent() + "\');");
        } catch (SQLException var7) {
            System.out.println("error in add fail message(" + msg + ").");
            flag = Boolean.valueOf(false);
        } finally {
            this.close();
        }

        return flag;
    }
    public Boolean addRemark(Remark msg) {
        this.start();
        Boolean flag = Boolean.valueOf(true);

        try {
            statement.execute("insert into remark(id1,id2,content) values(\'" + msg.getFrom() + "\',\'" + msg.getTo() + "\',\'" + msg.getContent() + "\');");
        } catch (SQLException var7) {
            System.out.println("error in add remark(" + msg + ").");
            flag = Boolean.valueOf(false);
        } finally {
            this.close();
        }

        return flag;
    }
    public ArrayList<Remark> getRemark_list(String id) /*是否有参数ID?*/ {
        this.start();
        ArrayList<Remark> li = new ArrayList<Remark>();

        try {
            ResultSet e = statement.executeQuery("select * from remark where id1 =" + id + ";");

            while(e.next()) {
                li.add(new Remark(e.getString(1), e.getString(2), e.getString(3)));
            }
        } catch (SQLException var6) {
            System.out.println("error in getRemark(" + id + ")");
        }

        return li;
    }
    public Boolean deletefailmessage(message msg) {
        this.start();
        Boolean flag = Boolean.valueOf(true);

        try {
            statement.execute("delete from failmessage where id1 = \'" + msg.getId1() + "\'&&id2 =\'" + msg.getId2() + "\';");
        } catch (SQLException var7) {
            System.out.println("error in delete fail message(" + msg + ").");
            flag = Boolean.valueOf(false);
        } finally {
            this.close();
        }

        return flag;
    }
    public List<message> getMessage() {
        this.start();
        LinkedList li = new LinkedList();

        try {
            ResultSet re = statement.executeQuery("select * from message;");

            while(re.next()) {
                li.add(new message(re.getString(1), re.getString(2), re.getDate(3), re.getString(4)));
            }
        } catch (SQLException var7) {
            System.out.println("error in getMessage().");
        } finally {
            this.close();
        }

        return li;
    }
    public List<message> getMessage(String id) {
        this.start();
        LinkedList li = new LinkedList();

        try {
            ResultSet e = statement.executeQuery("select * from message where id1 =" + id + ";");

            while(e.next()) {
                String str = e.getString(3);
                Date date = (new GregorianCalendar((new Integer(str.substring(0, 4))).intValue(), (new Integer(str.substring(5, 7))).intValue(), (new Integer(str.substring(8, 10))).intValue(), (new Integer(str.substring(11, 13))).intValue(), (new Integer(str.substring(14, 16))).intValue(), (new Integer(str.substring(17, 19))).intValue())).getTime();
                li.add(new message(e.getString(1), e.getString(2), date, e.getString(4)));
            }
        } catch (SQLException var6) {
            System.out.println("error in getMessage(" + id + ")");
        }

        return li;
    }
    public List<message> getMessage(String id1, String id2) {
        this.start();
        LinkedList li = new LinkedList();

        try {
            ResultSet re = statement.executeQuery("select * from message where (d1=1&&id1=\'" + id1 + "\'&&id2=\'" + id2 + "\')||(d2=1&&id1=\'" + id2 + "\'&&id2=\'" + id1 + "\') order by time ASC;");

            while(re.next()) {
                li.add(new message(re.getString(1), re.getString(2), re.getDate(3), re.getString(4)));
            }
        } catch (SQLException var9) {
            var9.printStackTrace();
            System.out.println("error in getMessage(" + id1 + "," + id2 + ").");
        } finally {
            this.close();
        }

        return li;
    }
    public Boolean deleteMessage(String id1, String id2) {
        this.start();
        Boolean flag = Boolean.valueOf(true);

        try {
            statement.execute("update message set d1 = 0 where id1 =\'" + id1 + "\'&&id2=\'" + id2 + "\';");
            statement.execute("update message set d2 = 0 where id2 =\'" + id1 + "\'&&id1=\'" + id2 + "\';");
        } catch (SQLException var8) {
            System.out.println("error in deleteMessage(" + id1 + "," + id2 + ").");
            flag = Boolean.valueOf(false);
        } finally {
            this.close();
        }

        return flag;
    }
    public int getGood(String id) {
        int ans = 0;
        try {
            ResultSet rs=statement.executeQuery( "select count(1) from basicinfo where id1 =" + id + ";");
            if(rs.next()){
                ans=rs.getInt(1);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ans;
    }
    public Boolean addonegood(good r) {
        this.start();
        Boolean flag = Boolean.valueOf(true);
        try {
            statement.execute("insert into good(id1,id2) values(\'" + r.getId1() + "\',\'" + r.getId2() + "\');");
        } catch (SQLException var7) {
            System.out.println("error in add one good(" + r + ").");
            flag = Boolean.valueOf(false);
        } finally {
            this.close();
        }

        return flag;
    }
    public Boolean deleteonegood(good r) {
        this.start();
        Boolean flag = Boolean.valueOf(true);

        try {
            statement.execute("delete from good where (id1=\'" + r.getId1() + "\'&&id2 =\'" + r.getId2() + "\')||(id1=\'" + r.getId2() + "\'&&id2=\'" + r.getId1() + "\');");
        } catch (SQLException var7) {
            System.out.println("error in delete one good(" + r + ").");
            flag = Boolean.valueOf(false);
        } finally {
            this.close();
        }
        return flag;
    }
    public void md() {
        this.start();

        try {
            ResultSet e = statement.executeQuery("select id,pwd from user;");
            Vector id = new Vector();
            Vector pwd = new Vector();

            while(e.next()) {
                id.add(e.getString(1));
                pwd.add(e.getString(2));
            }

            e.close();

            while(!id.isEmpty()) {
                statement.execute("update user set pwd=\'" + this.encryp((String)pwd.remove(0)) + "\' where id=\'" + (String)id.remove(0) + "\';");
            }
        } catch (SQLException var7) {
            var7.printStackTrace();
        } finally {
            this.close();
        }

    }
    public String encryp(String pwd) {
        Object message = null;
        byte[] message1 = pwd.getBytes();
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
        }

        byte[] encrypwd = md.digest(message1);
        BigInteger bigInteger = new BigInteger(1, encrypwd);
        return bigInteger.toString(16);
    }
}
