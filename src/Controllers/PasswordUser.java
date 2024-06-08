package Controllers;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PasswordUser {
    private String spURL;
        private String spUsername;
        private String spPassword;
        private final String URl;
        private final String port;
        private final String spName;

        public PasswordUser() {
            this.URl = "127.0.0.1";
            this.port = "3306";
            this.spName = "technicalcenter";
        }

        public void setCredentials(String username, String password) {
            this.spUsername = username;
            this.spPassword = password;
        }

        public Connection connectToDB() {
            if (spUsername == null || spPassword == null) {
                System.out.println("Username or password is null");
                return null;
            }

            try {
                spURL = "jdbc:mysql://" + this.URl + ":" + this.port + "/" + this.spName + "?verifyServerCertificate=false";
                Properties properties = new Properties();
                properties.setProperty("user", spUsername);
                properties.setProperty("password", spPassword);
                properties.setProperty("useSSL", "false");
                properties.setProperty("autoReconnect", "true");
                Class.forName("com.mysql.cj.jdbc.Driver");
                return DriverManager.getConnection(spURL, properties);
            } catch (ClassNotFoundException | SQLException notFoundException) {
                System.out.println("Connection failed: " + notFoundException.getMessage());
                return null;
            }
        }
    }
