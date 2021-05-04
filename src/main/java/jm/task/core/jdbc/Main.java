package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;

public class Main {


    public static void main(String[] args) {
        // реализуйте алгоритм здесь


//        System.out.println(Util.getSQLConnection());
        UserService us = new UserServiceImpl();
        long startTime = System.currentTimeMillis();
        us.dropUsersTable();
        us.createUsersTable();
        try {
            us.saveUser("Arseniy", "Asriyan", (byte)31);
            us.saveUser("German", "Mentorovich", (byte)31);
            us.saveUser("Kenny", "MCKormick", (byte)12);
            us.saveUser("Valor", "Morgulis",(byte)126);
            us.saveUser("Walter", "White", (byte)47);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (User user : us.getAllUsers()) {
            System.out.println(user.getName());
            System.out.println(user.getClass().toString());
        }
        System.out.println("Время выполнения прогреммы - " + (System.currentTimeMillis() - startTime));


    }
}
