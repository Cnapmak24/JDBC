package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private String sql;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        sql = "CREATE TABLE USER(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45),  lastName VARCHAR(45), age TINYINT(3))";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Таблица уже создана");
        }
    }

    public void dropUsersTable() {
        sql = "DROP TABLE USER";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Данной таблицы не существует");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        sql = "insert into USER (name, lastname, age) values(?, ?, ?)";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        sql = "delete from USER where id=?";
        try (PreparedStatement preparedstatement = Util.getConnection().prepareStatement(sql)) {
            preparedstatement.setLong(1, id);
            preparedstatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        sql = "select * from USER";
        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                result.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void cleanUsersTable() {
        sql = "delete from USER";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
