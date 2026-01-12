package com.server.social_network_server.utils;

import com.server.social_network_server.dto.UserWithStatus;
import com.server.social_network_server.entities.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DbUtils {
    private Connection connection;

    @PostConstruct
    private void init(){
        try {
            String host = "localhost";
            String username = "root";
            String password = "12345678";
            String schema = "social_network_db";
            int port = 3306;
            String url = "jdbc:mysql://" + host + ":" + port + "/" + schema;
            this.connection = DriverManager.getConnection(url,username,password);
            System.out.println("DB connected!");
        } catch (SQLException e) {
            System.out.println("Failed to create DB connection");
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();

        try {
            PreparedStatement statement = this.connection.prepareStatement
                    ("SELECT id, first_name, last_name, username, profile_image_url FROM users");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String profileImage = resultSet.getString("profile_image_url");
                User user = new User(id,firstName, lastName, username, profileImage);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public User getUserById(int targetUserId){
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT id, first_name, last_name, username, profile_image_url, description, city, country, created_at FROM users WHERE id = ?");
            statement.setInt(1, targetUserId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String profileImage = resultSet.getString("profile_image_url");
                String description = resultSet.getString("description");
                String city = resultSet.getString("city");
                String country = resultSet.getString("country");
                LocalDateTime createdAt = resultSet.getObject("created_at", LocalDateTime.class);

                return new User(id, firstName, lastName, username, profileImage, description, city, country, createdAt);
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByUsername(String targetUserId){
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT id, first_name, last_name, username, profile_image_url, description, city, country, created_at FROM users WHERE username = ?");
            statement.setString(1, targetUserId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String profileImage = resultSet.getString("profile_image_url");
                String description = resultSet.getString("description");
                String city = resultSet.getString("city");
                String country = resultSet.getString("country");
                LocalDateTime createdAt = resultSet.getObject("created_at", LocalDateTime.class);

                return new User(id, firstName, lastName, username, profileImage, description, city, country, createdAt);
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser(User user){
        try {
            PreparedStatement statement = this.connection.prepareStatement
                    ("INSERT INTO users (first_name, last_name, username, password_hash) VALUE (?,?,?,?)");
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getPassword());
            statement.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isUserIdExist(int userId){
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT id FROM users WHERE id = ?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isUsernameExists(String username){
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT username FROM users WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User loginCheck(String username, String password){
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT id, first_name, last_name, username, profile_image_url, created_at, city, country, description FROM users WHERE username = ?" +
                            "AND password_hash = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String dbUsername = resultSet.getString("username");
                String profileImage = resultSet.getString("profile_image_url");
                LocalDateTime createdAt = resultSet.getObject("created_at", LocalDateTime.class);
                String city = resultSet.getString("city");
                String country = resultSet.getString("country");
                String description = resultSet.getString("description");


                return new User(id, firstName, lastName, dbUsername, profileImage, description, city, country,createdAt );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isUserFollowUser(int followerId, int targetUserId){
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT * FROM user_follows WHERE followers_id = ? " +
                            "AND target_user_id = ? ");
            statement.setInt(1, followerId);
            statement.setInt(2, targetUserId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean followUser(int followerId, int targetUserId){
        if (!isUserFollowUser(followerId, targetUserId)){
            try {
                PreparedStatement statement = this.connection.prepareStatement(
                        "INSERT INTO user_follows (followers_id, target_user_id)" +
                                "VALUES (?, ?)");
                statement.setInt(1, followerId);
                statement.setInt(2, targetUserId);
                return statement.executeUpdate() == 1;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public boolean removeFollowUser(int followerId , int targetUserId){
        if(isUserFollowUser(followerId,targetUserId)){
            try {
                PreparedStatement statement = this.connection.prepareStatement(
                        "DELETE FROM user_follows WHERE followers_id = ? " +
                                "AND target_user_id = ?");
                statement.setInt(1, followerId);
                statement.setInt(2, targetUserId);
                return statement.executeUpdate() == 1;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return false;
    }

    // מחזיר יוזרים שאני עוקב אחריהם!
    public List<UserWithStatus> getFollowingUsers(int currentUserId, int targetUserId){
        try {
            List<UserWithStatus> followingUserList = new ArrayList<>();

            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT id, first_name, last_name, username, profile_image_url FROM users WHERE id IN" +
                            "(SELECT target_user_id FROM user_follows WHERE followers_id = ?)" );
            statement.setInt(1, targetUserId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String profileImage = resultSet.getString("profile_image_url");

                User user = new User(id, firstName, lastName, username, profileImage);
                boolean isFollow = isUserFollowUser(currentUserId, id);

                followingUserList.add(new UserWithStatus(user, isFollow));
            }
            return followingUserList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // מחזיר יוזרים שעוקבים אחריי!
    public List<UserWithStatus> getFollowersUsers(int currentUserId,int targetUserId){
        try {
            List<UserWithStatus> followingUserList = new ArrayList<>();

            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT id, first_name, last_name, username, profile_image_url FROM users WHERE id IN" +
                            "(SELECT followers_id FROM user_follows WHERE target_user_id = ?)" );
            statement.setInt(1, targetUserId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String profileImage = resultSet.getString("profile_image_url");

                User user = new User(id, firstName, lastName, username, profileImage);
                boolean isFollow = isUserFollowUser(currentUserId, id);

                followingUserList.add(new UserWithStatus(user, isFollow));
            }
            return followingUserList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // כמה עוקבים אחריי
    public int getFollowersCount(int targetUserId){
        try {
            PreparedStatement statement = this.connection.prepareStatement
                    ("SELECT COUNT(*) FROM user_follows WHERE target_user_id = ?");
            statement.setInt(1, targetUserId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    // אחרי כמה אני עוקב
    public int getFollowingCount(int targetUserId){
        try {
            PreparedStatement statement = this.connection.prepareStatement
                    ("SELECT COUNT(*) FROM user_follows WHERE followers_id = ?");
            statement.setInt(1, targetUserId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public User editUser(User user){
        try (
            PreparedStatement statement = this.connection.prepareStatement(
                    "UPDATE users SET first_name = ? , last_name = ? , city = ? , country = ? , description = ? , profile_image_url = ? " +
                            "WHERE id = ?")) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getCity());
            statement.setString(4, user.getCountry());
            statement.setString(5, user.getDescription());
            statement.setString(6, user.getPictureUrl());
            statement.setInt(7, user.getId());
            if (statement.executeUpdate() == 1){
                return getUserById(user.getId());
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }





}
