package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;

public class Main {


    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        long startTime = System.currentTimeMillis();
        UserService us = new UserServiceImpl();


        us.dropUsersTable();
        us.createUsersTable();

        us.saveUser("Arseniy", "Asriyan", (byte)31);
        us.saveUser("German", "Mentorovich", (byte)31);
        us.saveUser("Kenny", "MCKormick", (byte)12);
        us.saveUser("Valor", "Morgulis",(byte)126);
        us.saveUser("Walter", "White", (byte)47);

        System.out.println("По указанным критериям проходят: ");
        for (User user : us.getAllUsers()) {
            byte age = user.getAge();
            if (age > 18 && age < 60) {
                System.out.println(user.getName() + " " + user.getLastName());
            }
        }
        System.out.println("Время выполнения прогреммы - " + (System.currentTimeMillis() - startTime));


    }
}
