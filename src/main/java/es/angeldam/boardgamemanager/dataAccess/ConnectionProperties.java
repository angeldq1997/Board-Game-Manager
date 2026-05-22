package es.angeldam.boardgamemanager.dataAccess;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "connection")
/**
 * Class that stores the URL for the driver JDBC (Java Database Connectivity) which allows to share data between mysql and Java.
 * This class is used when is read the connection.xml
 */
public class ConnectionProperties implements Serializable {
    private String server;
    private String port;
    private String dataBase;
    private String user;
    private String password;

    /**
     * Empty constructor
     */
    public ConnectionProperties() {
    }

    /**
     * Full-equip constructor of the class
     * @param server a string that stores the IP of the connection URL
     * @param port a string that stores the port of the connection URL
     * @param dataBase a string that stores the name of the database for the connection to it
     * @param user a string that stores the username of the connection
     * @param password a string that stores the password of the connection. It should be encrypted, but we get it in plain text right now!
     */
    public ConnectionProperties(String server, String port, String dataBase, String user, String password) {
        this.server = server;
        this.port = port;
        this.dataBase = dataBase;
        this.user = user;
        this.password = password;
    }

    /**
     * A getter that obtains the user
     * @return the user that will connect to the database
     */
    public String getUser() {
        return user;
    }

    /**
     * A getter that obtains the password of the URL. Is stored on plain text for now!
     * @return the password that uses the user for the database
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method that appends the URL in one line, taking the server, port and database name
     * @return a string with the driver fused with the server, port and database
     */
    public String getURL() {
        return "jdbc:mysql://" + server + ":" + port + "/" + dataBase;
    }
}