package es.angeldam.boardgamemanager.dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {
    private static final String FILE = "connection.xml";
    private static Connection con;
    private static ConnectionBD instance;

    private ConnectionBD() {
        ConnectionProperties properties = XMLManager.readXML(new ConnectionProperties(), FILE);
        try{
            con = DriverManager.getConnection(properties.getURL(), properties.getUser(), properties.getPassword());
        }catch(SQLException e){
            e.printStackTrace();
            con=null;
        }
    }

    public static Connection getConnection() {
        if(instance ==null){
            instance = new ConnectionBD();
        }
        return con;
    }
}