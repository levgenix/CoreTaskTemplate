package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

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
    private static Session session;

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

    public static Session getSession() throws HibernateException {
        if (null == session) {
            try {
                StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
                serviceRegistryBuilder
                        .applySetting("hibernate.use_sql_comments", "false")
                        .applySetting("hibernate.show_sql", "true")
                        .applySetting("hibernate.hbm2ddl.auto", "update"); //create-drop
                MetadataSources metadataSources = new MetadataSources(serviceRegistryBuilder.build());
                metadataSources.addAnnotatedClass(User.class);
                MetadataBuilder metadataBuilder = metadataSources.getMetadataBuilder(); //TODO: Добавить файлы отображения hbm.xml metadataSources.addFile(...);
                session = metadataBuilder.build().buildSessionFactory().openSession(); //TODO: sessionFactory.getCurrentSession();
            } catch (HibernateException e) {
                throw new HibernateException("Not connecting to database", e);
            }
        }
        return session;
    }
}
