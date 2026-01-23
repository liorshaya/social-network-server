package com.server.social_network_server.utils;

import com.server.social_network_server.dto.CommentDto;
import com.server.social_network_server.dto.PostDto;
import com.server.social_network_server.dto.UserSearchDto;
import com.server.social_network_server.dto.UserWithStatus;
import com.server.social_network_server.entities.Post;
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

    public boolean removeFollower(int userId , int targetUserId){
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "DELETE FROM user_follows WHERE followers_id = ? AND target_user_id = ?"
            );
            statement.setInt(1, targetUserId);
            statement.setInt(2,userId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public PostDto getPostById(int postId){
        String sql = "SELECT " +
                "p.id, p.content, p.picture_url, p.created_at, p.user_id, " +
                "u.first_name, u.last_name, u.username, u.profile_image_url, " +
                "(SELECT COUNT(*) FROM likes WHERE post_id = p.id) as like_count, " +
                "(SELECT COUNT(*) FROM comments WHERE post_id = p.id) as comment_count " +
                "FROM posts p " +
                "JOIN users u ON p.user_id = u.id " +
                "WHERE p.id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)){
            statement.setInt(1, postId);
            try (ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    PostDto postDto = new PostDto();
                    postDto.setId(rs.getInt("id"));
                    postDto.setContent(rs.getString("content"));
                    postDto.setPictureUrl(rs.getString("picture_url"));
                    postDto.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    postDto.setAuthorId(rs.getInt("user_id"));
                    postDto.setAuthorFirstName(rs.getString("first_name"));
                    postDto.setAuthorLastName(rs.getString("last_name"));
                    postDto.setAuthorUsername(rs.getString("username"));
                    postDto.setAuthorProfileImage(rs.getString("profile_image_url"));

                    postDto.setLikeCount(rs.getInt("like_count"));
                    postDto.setCommentCount(rs.getInt("comment_count"));

                    return postDto;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public PostDto addPost(Post post){
        String sql = "INSERT INTO posts (user_id, content, picture_url) VALUES (?, ?, ?)";
        try(PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1, post.getUserId());
            statement.setString(2, post.getContent());
            statement.setString(3, post.getPictureUrl());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                return null;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int newId = generatedKeys.getInt(1);
                    return getPostById(newId);

                }
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return null;
    }

    public boolean deletePost(int postId){
        try(PreparedStatement statement = this.connection.prepareStatement("DELETE FROM posts WHERE id = ?")){
            statement.setInt(1,postId);

            return statement.executeUpdate() == 1;

        } catch (SQLException e) {
            e.getStackTrace();
        }
        return false;
    }

    public List<PostDto> getPostsByUserId(int profileId, int viewerId, int page) {
        int limit = 10;
        int offset = (page - 1) * limit;

        String sql =
                "SELECT " +
                        "   p.id, p.content, p.picture_url, p.created_at, p.user_id, " +
                        "   u.first_name, u.last_name, u.username, u.profile_image_url, " +
                        "   (SELECT COUNT(*) FROM likes WHERE post_id = p.id) as like_count, " +
                        "   (SELECT COUNT(*) FROM comments WHERE post_id = p.id) as comment_count, " +
                        "EXISTS(SELECT 1 FROM likes WHERE post_id = p.id AND user_id = ?) as is_liked " +
                        "FROM posts p " +
                        "JOIN users u ON p.user_id = u.id " +
                        "WHERE p.user_id = ? " +
                        "ORDER BY p.created_at DESC " +
                        "LIMIT ? OFFSET ?";

        List<PostDto> posts = new ArrayList<>();

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, viewerId);
            statement.setInt(2, profileId);
            statement.setInt(3, limit);
            statement.setInt(4, offset);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    PostDto postDto = new PostDto();

                    postDto.setId(rs.getInt("id"));
                    postDto.setContent(rs.getString("content"));
                    postDto.setPictureUrl(rs.getString("picture_url"));
                    postDto.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    postDto.setAuthorId(rs.getInt("user_id"));
                    postDto.setAuthorFirstName(rs.getString("first_name"));
                    postDto.setAuthorLastName(rs.getString("last_name"));
                    postDto.setAuthorUsername(rs.getString("username"));
                    postDto.setAuthorProfileImage(rs.getString("profile_image_url"));
                    postDto.setLikeCount(rs.getInt("like_count"));
                    postDto.setCommentCount(rs.getInt("comment_count"));
                    postDto.setLiked(rs.getBoolean("is_liked"));

                    posts.add(postDto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }


    public boolean toggleLike(int postId, int userId) {
        String checkSql = "SELECT 1 FROM likes WHERE post_id = ? AND user_id = ?";
        String insertSql = "INSERT INTO likes (post_id, user_id) VALUES (?, ?)";
        String deleteSql = "DELETE FROM likes WHERE post_id = ? AND user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(checkSql)) {
            preparedStatement.setInt(1, postId);
            preparedStatement.setInt(2, userId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {
                        deleteStmt.setInt(1, postId);
                        deleteStmt.setInt(2, userId);
                        deleteStmt.executeUpdate();
                        return false;
                    }
                } else {
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, postId);
                        insertStmt.setInt(2, userId);
                        insertStmt.executeUpdate();
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getPostCount(int targetUserId){
        try {
            PreparedStatement statement = this.connection.prepareStatement
                    ("SELECT COUNT(*) FROM posts WHERE user_id = ?");
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

    public boolean isPostIdExist(int postId){
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "SELECT id FROM posts WHERE id = ?");
            statement.setInt(1, postId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CommentDto addComment(int userId, int postId , String content){
        String sql = "INSERT INTO comments (user_id, post_id, content) VALUES (?,?,?)";
        try(PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1, userId);
            statement.setInt(2, postId);
            statement.setString(3, content);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                return null;
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            int newCommentId = 0;
            if (generatedKeys.next()) {
                newCommentId = generatedKeys.getInt(1);
            }

            return getCommentById(newCommentId);

        }catch (SQLException e){
            e.getStackTrace();
        }
        return null;
    }

    public CommentDto getCommentById(int commentId) {
        try {
            String query = "SELECT c.*, u.first_name, u.last_name, u.profile_image_url , u.username " +
                    "FROM comments c " +
                    "JOIN users u ON c.user_id = u.id " +
                    "WHERE c.id = ?";

            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, commentId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new CommentDto(
                        rs.getInt("id"),
                        rs.getInt("post_id"),
                        rs.getString("content"),
                        rs.getString("created_at"),
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("profile_image_url"),
                        rs.getString("username")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CommentDto> getCommentByPostId(int postId){
        List<CommentDto> comments = new ArrayList<>();

        try (PreparedStatement statement = this.connection.prepareStatement("SELECT c.*, u.first_name, u.last_name, u.profile_image_url , u.username" +
                " FROM comments c " +
                "JOIN users u ON c.user_id = u.id " +
                "WHERE c.post_id = ? " +
                "ORDER BY c.created_at")){
            statement.setInt(1, postId);

            try(ResultSet rs = statement.executeQuery()){
                while (rs.next()){
                    CommentDto comment = new CommentDto(
                            rs.getInt("id"),
                            rs.getInt("post_id"),
                            rs.getString("content"),
                            rs.getString("created_at"),
                            rs.getInt("user_id"),
                            rs.getString("last_name"),
                            rs.getString("first_name"),
                            rs.getString("profile_image_url"),
                            rs.getString("username")

                    );
                    comments.add(comment);
                }
            }

        }catch (SQLException e){
            e.getStackTrace();
        }
        return comments;
    }

    public List<UserSearchDto> searchUsers(String query) {
        List<UserSearchDto> results = new ArrayList<>();

        if (query == null || query.trim().isEmpty()) {
            return results;
        }

        String sql = "SELECT id, first_name, last_name, username, profile_image_url " +
                "FROM users " +
                "WHERE username LIKE ? OR first_name LIKE ? OR last_name LIKE ? " +
                "OR CONCAT(first_name, ' ', last_name) LIKE ?" +
                "LIMIT 10";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {

            String searchPattern = "%" + query + "%";

            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);
            statement.setString(4, searchPattern);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                UserSearchDto user = new UserSearchDto(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("profile_image_url")
                );
                results.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

}
