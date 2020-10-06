package jm.task.core.jdbc.util;

import javax.sql.PooledConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static Util instance;

    //TODO: throws SQLException, IOException
    public static Connection getConnection() throws SQLException, IOException {
        if (instance == null) {
            instance = new Util();
        }

        try {
            Properties props = Util.getProps();
            return DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.username"), props.getProperty("db.password"));
        } catch (SQLException | IOException e) {
            throw e;
        }
    }

    //TODO: Exceptions
    private static Properties getProps() throws IOException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get(Util.class.getResource("/database.properties").toURI()))) {
            props.load(in);
            return props;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new IOException("Config file not found", e);
        }
    }

    //TODO: Обработка всех исключений, связанных с работой с базой данных должна находиться в dao
    public static void close(Connection connection)
    {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
