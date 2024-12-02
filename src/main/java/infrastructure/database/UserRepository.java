package infrastructure.database;

import infrastructure.database.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;

import entity.User;

public class UserRepository implements infrastructure.database.IRepository<User>{
    private static final String TABLE_NAME = "users";

    private List<Integer> stringToIntList(String friendsStr) {
        final List<Integer> result;
        if (friendsStr == null || friendsStr.isEmpty()) {
            result = new ArrayList<>();
        }
        else {
            result = Arrays.stream(friendsStr.split(","))
                    .map(Integer::parseInt)
                    .toList();
        }
        return result;
    }

    private String intListToString(List<Integer> friends) {
        if (friends.isEmpty()) {
            return "";
        }
        return String.join(",", friends.stream().map(String::valueOf).toList());
    }

    @Override
    public Optional<User> findById(int id) {
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection conn = infrastructure.database.DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            final ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                final User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setEmail(rs.getString("email"));
                user.setCountry(rs.getString("country"));
                user.setPassword(rs.getString("password"));
                user.setThread(rs.getString("thread"));
                user.setFriends(stringToIntList(rs.getString("friends")));
                return Optional.of(user);
            }
            return Optional.empty();
        }
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
    }

    public Optional<User> findByEmail(String email) {
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";
        try (Connection conn = infrastructure.database.DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            final ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                final User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setEmail(email);
                user.setCountry(rs.getString("country"));
                user.setPassword(rs.getString("password"));
                user.setThread(rs.getString("thread"));
                user.setFriends(stringToIntList(rs.getString("friends")));
                return Optional.of(user);
            }
            return Optional.empty();
        }
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void save(User user) {
        final String sql = "INSERT INTO " + TABLE_NAME +
                " (name, surname, email, country, password, thread, friends) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = infrastructure.database.DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getCountry());
            stmt.setString(5, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            stmt.setString(6, user.getThread());
            stmt.setString(7, intListToString(user.getFriends()));

            final int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            final ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        }
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void update(User user) {
        final String sql = "UPDATE " + TABLE_NAME +
                " SET name = ?, surname = ?, country = ?, thread = ?, friends = ? " +
                "WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getCountry());
            stmt.setString(4, user.getThread());
            stmt.setString(5, intListToString(user.getFriends()));
            stmt.setInt(6, user.getId());

            stmt.executeUpdate();
        }
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
    }

    public void updatePassword(int userId, String newPassword) {
        final String sql = "UPDATE " + TABLE_NAME + " SET password = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            stmt.setInt(2, userId);

            stmt.executeUpdate();
        }
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(int id) {
        final String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
    }

    public boolean verifyPassword(String email, String password) {
        final String sql = "SELECT password FROM " + TABLE_NAME + " WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            final ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                final String hashedPassword = rs.getString("password");
                return BCrypt.checkpw(password, hashedPassword);
            }
            return false;
        }
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
    }
}
