package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        Connection connection = null;
        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(255), " +
                        "lastName VARCHAR(255), " +
                        "age TINYINT)");
                connection.commit();
            }
        } catch (SQLException e) {
            rollbackQuietly(connection);
            e.printStackTrace();
        } finally {
            closeQuietly(connection);
        }
    }

    @Override
    public void dropUsersTable() {
        Connection connection = null;
        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DROP TABLE IF EXISTS users");
                connection.commit();
            }
        } catch (SQLException e) {
            rollbackQuietly(connection);
            e.printStackTrace();
        } finally {
            closeQuietly(connection);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;
        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
                ps.setString(1, name);
                ps.setString(2, lastName);
                ps.setByte(3, age);
                ps.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            rollbackQuietly(connection);
            e.printStackTrace();
        } finally {
            closeQuietly(connection);
        }
    }

    @Override
    public void removeUserById(long id) {
        Connection connection = null;
        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM users WHERE id = ?")) {
                ps.setLong(1, id);
                ps.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            rollbackQuietly(connection);
            e.printStackTrace();
        } finally {
            closeQuietly(connection);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery("SELECT * FROM users")) {

                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setName(rs.getString("name"));
                    user.setLastName(rs.getString("lastName"));
                    user.setAge(rs.getByte("age"));
                    users.add(user);
                }
                connection.commit();
            }
        } catch (SQLException e) {
            rollbackQuietly(connection);
            e.printStackTrace();
        } finally {
            closeQuietly(connection);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Connection connection = null;
        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("TRUNCATE TABLE users");
                connection.commit();
            }
        } catch (SQLException e) {
            rollbackQuietly(connection);
            e.printStackTrace();
        } finally {
            closeQuietly(connection);
        }
    }

    private void rollbackQuietly(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeQuietly(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}