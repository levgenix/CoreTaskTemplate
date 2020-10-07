package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

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

//        Configuration cfg = new Configuration().addResource("hibernate.properties");
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                //.applySettings(cfg.getProperties())
                .configure()
                //.configure("/hibernate.properties")
                //.configure(Main.class.getResource("/hibernate.props"))
                .build();

        try (SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory()) {

            //TODO: Saving entities
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(new User("Ярослав", "Мудрый", (byte) 21));
            session.getTransaction().commit();
            session.close();

            //TODO: Obtaining a list of entities


            session = sessionFactory.openSession();
            session.beginTransaction();
            List result = session.createQuery("from Event").list();
            for (User user : (List<User>) result) {
                System.out.println(user);
            }
            session.getTransaction().commit();
            session.close();


        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }


    }
}
