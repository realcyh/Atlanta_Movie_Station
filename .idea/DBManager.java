package CS4400Final;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBManager {
    public static final String url = "jdbc:mysql://localhost:3306/team25?serverTimezone=EST&characterEncoding=utf-8" +
            "&useSSL=false&allowPublicKeyRetrieval=true";
    public static final String name = "com.mysql.cj.jdbc.Driver";
    public static final String username = "root";
    public static final String password = "19970911";

    public  Connection connection = null;

    public DBManager(){
        try{
            Class.forName(name);
            connection = DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try{
                this.connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
