package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Data.failrelation;
import Data.relation;

public class test {

    public static void main(String[] args) throws SQLException {

       // System.out.println(new dao().getUser("120"));
        //new dao().getMessage("120");
//		List<message> li = new dao().getMessage("120");
//		for(message m:li){
//			System.out.println(m);
//		}
         ArrayList<failrelation> li = new dao().getBlack("123");
         for(int i = 0; i <li.size();i++){
             failrelation r = new failrelation();
             r=li.get(i);
           System.out.println(r.getId2());
        }
    }

}
