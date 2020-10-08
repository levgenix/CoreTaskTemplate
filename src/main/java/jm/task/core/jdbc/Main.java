package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        UserService userService = new UserServiceImpl();
//
//        userService.createUsersTable();
//        userService.saveUser("Name", "Last name", (byte) 45);
//        userService.saveUser("Герман", "Севостьянов", (byte) 32);
//        userService.saveUser("Василий", "Иванов", (byte) 88);
//        userService.saveUser("Михаил", "Петров", (byte) 28);
//        userService.getAllUsers();
//        userService.cleanUsersTable();
//        userService.dropUsersTable();


        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
                serviceRegistryBuilder
                        .applySetting("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                        .applySetting("hibernate.connection.url", "jdbc:mysql://localhost/phpmyadmin?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT&allowPublicKeyRetrieval=true&useSSL=false&zeroDateTimeBehavior=convertToNull")
                        .applySetting("hibernate.connection.username", "root")
                        .applySetting("hibernate.connection.use_sql_comments", "root")
                        .applySetting("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect")
                        .applySetting("hibernate.use_sql_comments", "true")
                        //.applySetting("hibernate.hbm2ddl.auto", "create-drop");
                        .applySetting("hibernate.hbm2ddl.auto", "update");
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(
                User.class
        );
        // Добавить файлы отображения hbm.xml
        // metadataSources.addFile(...);
        MetadataBuilder metadataBuilder = metadataSources.getMetadataBuilder();

        Metadata metadata = metadataBuilder.build();
        //assertEquals(metadata.getEntityBindings().size(), 1);
        SessionFactory sessionFactory = metadata.buildSessionFactory();

        //UserTransaction tx = TM.getUserTransaction();
        //Session session = sessionFactory.getCurrentSession();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = new User("Василий", "Уткин", (byte) 22);
        session.persist(user);
        //session.save(user);
        session.getTransaction().commit();
        session.close();

/*
        Configuration configuration = new Configuration();
//        configuration.addAnnotatedClass(User.class);
        configuration.configure();

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
//        final ServiceRegistry registry = new StandardServiceRegistryBuilder()
////                .configure()
////                .configure("/hibernate.properties")
//                .build();

        final ServiceRegistry registry = builder.build();
        try (SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory()) {
//        try (SessionFactory sessionFactory = configuration.buildSessionFactory(registry.build())) {

            //TODO: Saving entities
            Session session = sessionFactory.openSession();
//            session.beginTransaction();
//            session.save(new User("Ярослав", "Мудрый", (byte) 21));
//            session.getTransaction().commit();
            session.close();

            //TODO: Obtaining a list of entities
//            session = sessionFactory.openSession();
//            session.beginTransaction();
//            List result = session.createQuery("from User").list();
//            for (User user : (List<User>) result) {
//                System.out.println(user);
//            }
//            session.getTransaction().commit();
//            session.close();


        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
*/

    }
}
