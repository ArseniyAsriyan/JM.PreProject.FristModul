package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = Util.getSQLConnection();
        String sqlCommand = "CREATE TABLE users( id INT NOT NULL AUTO_INCREMENT, name VARCHAR(50) NOT NULL, " +
                "lastname VARCHAR(50) NOT NULL, age INT NOT NULL, PRIMARY KEY (id) )";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCommand);
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
            }
        }

    }

    public void dropUsersTable() {
        Connection connection = Util.getSQLConnection();
        String sqlCommand = "DROP TABLE users";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCommand);
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;
        String sqlCommand = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try {
            connection = Util.getSQLConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = Util.getSQLConnection();
        try (Statement statement = connection.createStatement()) {
            String sqlCommand = "DELETE FROM users WHERE id";
            statement.execute(sqlCommand);
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
            }
        }
    }

    public List<User> getAllUsers() {
        Connection connection = Util.getSQLConnection();
        String sqlCommand = "SELECT * FROM users";
        List<User> listOfUsers = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlCommand)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                listOfUsers.add(user);
                connection.setAutoCommit(false);
                connection.commit();
            }
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
            }
        }
        return listOfUsers;
    }

    public void cleanUsersTable() {
        Connection connection = Util.getSQLConnection();
        String sqlCommand = "DELETE FROM users";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCommand);
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
            }
        }

    }
}
