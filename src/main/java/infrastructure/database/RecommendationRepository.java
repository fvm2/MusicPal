package infrastructure.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.Recommendation;

/**
 * Handles database operations for music recommendations.
 * Supports both AI-generated and friend recommendations.
 */
public class RecommendationRepository implements IRepository<Recommendation> {
    private static final String TABLE_NAME = "recommendations";

    @Override
    public Optional<Recommendation> findById(int recommendationId) {
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE recommendation_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recommendationId);
            final ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToRecommendation(resultSet));
            }
            return Optional.empty();
        }
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
    }

    /**
     * Retrieves all recommendations for a specific user.
     * @param userId User's ID
     * @return List of recommendations
     */
    public List<Recommendation> findByUserId(int userId) {
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ? ORDER BY recommendation_id DESC";
        final List<Recommendation> recommendations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            final ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                recommendations.add(mapResultSetToRecommendation(resultSet));
            }
            return recommendations;
        }
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
    }

    /**
     * Retrieves all friend recommendations between two users.
     * @param fromUserId Recommending user's ID
     * @param toUserId Receiving user's ID
     * @return List of recommendations
     */
    public List<Recommendation> findFriendRecommendations(int fromUserId, int toUserId) {
        final String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE user_id = ? AND by = ? ORDER BY recommendation_id DESC";
        final List<Recommendation> recommendations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, toUserId);
            stmt.setInt(2, fromUserId);
            final ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                recommendations.add(mapResultSetToRecommendation(resultSet));
            }
            return recommendations;
        } 
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
    }

    private Recommendation mapResultSetToRecommendation(ResultSet resultSet) throws SQLException {
        final Recommendation recommendation = new Recommendation();
        recommendation.setRecommendationId(resultSet.getInt("recommendation_id"));
        recommendation.setUserId(resultSet.getInt("user_id"));
        recommendation.setContent(resultSet.getString("content"));
        recommendation.setType(resultSet.getString("type"));
        recommendation.setBy(resultSet.getInt("by"));
        recommendation.setLiked(resultSet.getObject("liked", Boolean.class));
        return recommendation;
    }

    @Override
    public void save(Recommendation recommendation) {
        final String sql = "INSERT INTO " + TABLE_NAME +
                " (user_id, content, type, by, liked) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setStatementParameters(stmt, recommendation);

            final int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating recommendation failed, no rows affected.");
            }

            final ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                recommendation.setRecommendationId(generatedKeys.getInt(1));
            }
        }
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
    }

    private void setStatementParameters(PreparedStatement stmt, Recommendation recommendation)
            throws SQLException {
        stmt.setInt(1, recommendation.getUserId());
        stmt.setString(2, recommendation.getContent());
        stmt.setString(3, recommendation.getType());
        stmt.setInt(4, recommendation.getBy());
        if (recommendation.getLiked() == null) {
            stmt.setNull(5, Types.BOOLEAN);
        }
        else {
            stmt.setBoolean(5, recommendation.getLiked());
        }
    }

    @Override
    public void update(Recommendation recommendation) {
        final String sql = "UPDATE " + TABLE_NAME +
                " SET content = ?, type = ?, by = ?, liked = ? " +
                "WHERE recommendation_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, recommendation.getContent());
            stmt.setString(2, recommendation.getType());
            stmt.setInt(3, recommendation.getBy());
            if (recommendation.getLiked() == null) {
                stmt.setNull(4, Types.BOOLEAN);
            }
            else {
                stmt.setBoolean(4, recommendation.getLiked());
            }
            stmt.setInt(5, recommendation.getRecommendationId());

            stmt.executeUpdate();
        }
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(int recommendationId) {
        final String sql = "DELETE FROM " + TABLE_NAME + " WHERE recommendation_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recommendationId);
            stmt.executeUpdate();
        }
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
    }
}
