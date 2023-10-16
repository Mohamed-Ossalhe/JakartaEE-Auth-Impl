package Application.DAO;

import Application.Config.DBConnection;
import Application.Entities.User;
import Application.Interfaces.Model;
import Application.Utils.PasswordHasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao implements Model<User> {
    private final DBConnection connection = DBConnection.getInstance();
    private final String table = "users";

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.connect().prepareStatement("SELECT * FROM " + table);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setMobile(resultSet.getString("mobile"));
                users.add(user);
            }
        }catch (SQLException exception) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "An SQL Error Occurred", exception);
        }finally {
//            connection.closeConnection();
        }
        return users;
    }

    @Override
    public User store(User object) {
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.connect().prepareStatement("INSERT INTO " + table + " (name, email, password, mobile) VALUES (?, ?, ?, ?) RETURNING name, email, mobile");
            preparedStatement.setString(1, object.getName());
            preparedStatement.setString(2, object.getEmail());
            preparedStatement.setString(3, PasswordHasher.PasswordHash(object.getPassword()));
            preparedStatement.setString(4, object.getMobile());
            if (preparedStatement.executeQuery() != null) {
                ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setMobile(resultSet.getString("mobile"));
                }
            }
        }catch (SQLException exception) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "An SQL Error Occurred", exception);
        }finally {
//            connection.closeConnection();
        }

        return user;
    }

    @Override
    public User findOne(User object) {
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.connect().prepareStatement("SELECT * FROM " + table + " WHERE email = ?");
            preparedStatement.setString(1, object.getEmail());
            if (preparedStatement.executeQuery() != null) {
                ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setMobile(resultSet.getString("mobile"));
                    return user;
                }
            }
        }catch (SQLException exception) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "An SQL Error Occurred", exception);
        }finally {
//            connection.closeConnection();
        }
        return null;
    }

    @Override
    public User update(User object) {
        return null;
    }

    @Override
    public boolean destroy(User object) {
        return false;
    }
}
