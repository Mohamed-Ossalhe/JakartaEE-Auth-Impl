package Application.Config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private static Connection connection;
    private static DBConnection instance;

    public DBConnection() {
    }

    private static void setConnection(Connection conn) {
        connection = conn;
    }

    public static DBConnection getInstance() {
        if (instance == null)
            instance = new DBConnection();
        return instance;
    }

    public Connection connect() {
        if (connection == null) {
            try {
                Properties props = new Properties();
                try (InputStream in = DBConnection.class.getResourceAsStream("/db.properties")) {
                    if (in != null)
                        props.load(in);
                    else
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Resource 'db.properties' is not found");
                }catch (NullPointerException | IOException exception) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "An Error Occurred While Loading Props", exception);
                }
                String url = props.getProperty("url");
                String dbname = props.getProperty("dbname");
                String username = props.getProperty("username");
                String password = props.getProperty("password");

                Class.forName("org.postgresql.Driver");
                setConnection(DriverManager.getConnection(url + dbname, username, password));

                if (connection != null)
                    System.out.println("Connection established successfully!");

            }catch (ClassNotFoundException | SQLException exception) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "An Error Occurred", exception);
            }
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            }catch (SQLException exception) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "An SQL Error Occurred", exception);
            }
        }
    }
}
