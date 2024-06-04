package Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class  ConnectionToDatabase {


    private String spURL;
    private final String spUsername;
    private final String spPassword;
    private final String URl;
    private final String port;
    private final String spName;

    public ConnectionToDatabase() {
        this.URl = "127.0.0.1";
        this.port = "3306";
        this.spName = "TechnicalCenter";
        this.spUsername = "root";
        this.spPassword = "15987533578951";
    }
    public Connection connectToDB() {
        try {
            spURL = "jdbc:mysql://" + this.URl + ":" + this.port + "/" + this.spName + "?verifyServerCertificate=false";
            Properties properties = new Properties();
            properties.setProperty("user", spUsername);
            properties.setProperty("password", spPassword);
            properties.setProperty("useSSl", "false");
            properties.setProperty("autoReconnect", "true");
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(spURL, properties);

        } catch (ClassNotFoundException | SQLException notFoundException) {
            System.out.println(notFoundException.getMessage());
            return null;
        }
    }
}
