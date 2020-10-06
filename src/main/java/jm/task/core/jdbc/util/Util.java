package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static Connection conn;

    private Util() {}

    public static Connection getConnection() throws SQLException {
        if (null == conn || conn.isClosed()) {
            try {
                Properties props = getProps();
                conn = DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.username"), props.getProperty("db.password"));
            } catch (SQLException e) {
                throw new SQLException("Not connecting to database", e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    private static Properties getProps() throws IOException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get(Util.class.getResource("/database.properties").toURI()))) {
            props.load(in);
            return props;
        } catch (IOException | URISyntaxException e) {
            throw new IOException("Config file not found", e);
        }
    }

    public static void close() throws SQLException {
        try {
            if (null != conn) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new SQLException("Close database connection error", e);
        }
    }
}
