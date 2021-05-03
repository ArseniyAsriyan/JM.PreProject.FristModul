package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.Main;
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
        String SQLCommand = "CREATE TABLE users( id INT NOT NULL AUTO_INCREMENT, name VARCHAR(50) NOT NULL, " +
                "lastname VARCHAR(50) NOT NULL, age INT NOT NULL, PRIMARY KEY (id) )";

        try (Statement statement = connection.createStatement()) {
            statement.execute(SQLCommand);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        Connection connection = Util.getSQLConnection();
        String SQLCommand = "DROP TABLE users";
        try (Statement statement = connection.createStatement()){
            statement.execute(SQLCommand);
            connection.close();
        } catch (SQLException e) {
//            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection connection = Util.getSQLConnection();
        String SQLCommand = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLCommand)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex2) {
                ex2.printStackTrace();

            }
            connection.close();

        }
    }

    public void removeUserById(long id) {
        Connection connection = Util.getSQLConnection();
        try (Statement statement = connection.createStatement()) {
            String SQLCommand = "DELETE FROM users WHERE id";
            statement.execute(SQLCommand);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        Connection connection = Util.getSQLConnection();
        String SQLCommand = "SELECT * FROM users";
        List<User> listOfUsers = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLCommand);
             ResultSet resultSet = preparedStatement.executeQuery(SQLCommand)) {
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
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return listOfUsers;
    }

    public void cleanUsersTable() {
        Connection connection = Util.getSQLConnection();
        String SQLCommand = "DELETE FROM users";
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQLCommand);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
