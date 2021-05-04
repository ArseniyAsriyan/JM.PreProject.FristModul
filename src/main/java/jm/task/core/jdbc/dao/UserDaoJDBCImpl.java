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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        Connection connection = Util.getSQLConnection();
        String sqlCommand = "DROP TABLE users";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCommand);
            connection.close();
        } catch (SQLException e) {
//            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection connection = Util.getSQLConnection();
        String sqlCommand = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.close();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getSQLConnection();
             Statement statement = connection.createStatement()) {
            String sqlCommand = "DELETE FROM users WHERE id";
            statement.execute(sqlCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        Connection connection = Util.getSQLConnection();
        String sqlCommand = "SELECT * FROM users";
        List<User> listOfUsers = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
             ResultSet resultSet = preparedStatement.executeQuery(sqlCommand)) {
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
        String sqlCommand = "DELETE FROM users";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCommand);
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
