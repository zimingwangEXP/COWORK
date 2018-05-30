package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Data.failrelation;
import Data.relation;

public class test {

    public static void main(String[] args) throws SQLException {

      
         ArrayList<failrelation> li = new dao().getBlack("123");
         for(int i = 0; i <li.size();i++){
             failrelation r = new failrelation();
             r=li.get(i);
           System.out.println(r.getId2());
        }
    }

}
