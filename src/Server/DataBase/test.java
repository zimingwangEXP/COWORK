package Server.DataBase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import CommonBase.Data.BasicInfo;
import Server.ServerData.message;
import Server.ServerData.failrelation;
import Server.ServerData.message;
import Server.ServerData.relation;

public class test {

    public static void main(String[] args) throws SQLException {
        dao test= new dao();
        BasicInfo bs1=new BasicInfo.BasicInfoBuilder("hhh","151561").Builder();
        BasicInfo bs2=new BasicInfo.BasicInfoBuilder("545","4141").Builder();
        BasicInfo bs3=new BasicInfo.BasicInfoBuilder("1651","1551").Builder();
        test.addBasicInfo(bs1);
        test.addBasicInfo(bs2);
        test.addBasicInfo(bs3);
        System.out.println(test.NumberOfID());
    }

}
