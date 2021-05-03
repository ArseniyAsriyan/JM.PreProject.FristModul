package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static SessionFactory sessionFactory;

    public static Connection getSQLConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (SQLException e) {
            System.err.println("Не удалось установить соединение с базой данных");
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
//        sessionFactory = null;


        try {
            Configuration configuration = new Configuration();
            Properties properties = new Properties();
            //Environment - Предоставляет доступ к информации о конфигурации, передаваемой в объект Properties
            properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.put(Environment.URL, URL);
            properties.put(Environment.USER, USERNAME);
            properties.put(Environment.PASS, PASSWORD);
            //для связи Hibernate с БД
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

            //SHOW_SQL Включить запись сгенерированного SQL в консоль
            properties.put(Environment.SHOW_SQL, "true");

            //CURRENT_SESSION_CONTEXT_CLASS Определение контекста для SessionFactory.getCurrentSession()обработки.
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            //HBM2DDL_AUTO Автоматический экспорт / обновление схемы с помощью инструмента hbm2ddl.
            properties.put(Environment.HBM2DDL_AUTO, "update");

            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return sessionFactory;
    }

}
