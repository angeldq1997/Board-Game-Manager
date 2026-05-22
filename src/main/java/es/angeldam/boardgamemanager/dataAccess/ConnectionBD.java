package es.angeldam.boardgamemanager.dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class that stores the connection data and properties to give the app the ability to read from the database
 */
public class ConnectionBD {
    private static final String FILE = "connection.xml";
    private static Connection con;
    private static ConnectionBD instance;

    /**
     * Method to get the properties of the database (URL, user, password) it creates a variable that stores the information of the connection
     */
    private ConnectionBD() {
        ConnectionProperties properties = XMLManager.readXML(new ConnectionProperties(), FILE);
        try{
            con = DriverManager.getConnection(properties.getURL(), properties.getUser(), properties.getPassword());
        }catch(SQLException e){
            e.printStackTrace();
            con=null;
        }
    }

    /**
     * Method that follows the singleton pattern to get the connection to the database
     * @return the connection to the database if is null it creates a new object of this class, which reads the properties from a connection.xml
     */
    public static Connection getConnection() {
        if(instance ==null){
            instance = new ConnectionBD();
        }
        return con;
    }
}