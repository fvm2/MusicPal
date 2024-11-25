package infrastructure.persistence;

import domain.Recommendation;
import domain.User;
import domain.UserPreferences;
import domain.ports.IUserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PostgresUserRepository implements IUserRepository {
    private final DataSource dataSource;

    public PostgresUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Queries to initialize the database directly from Java and not ssh connection to the remote PSQL db in Render.
    public void initializeDatabase() {
        String createUserTable = """
        CREATE TABLE IF NOT EXISTS users (
            user_id VARCHAR(36) PRIMARY KEY,
            name VARCHAR(100) NOT NULL,
            email VARCHAR(100) UNIQUE NOT NULL,
            password_hash VARCHAR(60) NOT NULL,
            country VARCHAR(100),
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )""";

        String createPreferencesTable = """
        CREATE TABLE IF NOT EXISTS user_preferences (
            preference_id SERIAL PRIMARY KEY,
            user_id VARCHAR(36) REFERENCES users(user_id) ON DELETE CASCADE,
            song VARCHAR(200),
            artist VARCHAR(200),
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )""";

        String createRecommendationsTable = """
        CREATE TABLE IF NOT EXISTS recommendations (
            recommendation_id VARCHAR(36) PRIMARY KEY,
            user_id VARCHAR(36) REFERENCES users(user_id) ON DELETE CASCADE,
            content VARCHAR(200) NOT NULL,
            artist VARCHAR(200),
            type VARCHAR(50) NOT NULL,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )""";

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try (Statement stmt = conn.createStatement()) {
                System.out.println("Creating users table...");
                stmt.execute(createUserTable);

                System.out.println("Creating preferences table...");
                stmt.execute(createPreferencesTable);

                System.out.println("Creating recommendations table...");
                stmt.execute(createRecommendationsTable);

                conn.commit();
                System.out.println("All tables created successfully.");
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Error creating database tables: " + e.getMessage(), e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database initialization failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void save(User user) {
        // Note: This is the correct syntax to avoid SQL injections.
        String sql = "INSERT INTO users (user_id, name, email, password_hash, country) VALUES (?, ?, ?, ?, ?)";
        String preferenceSql = "INSERT INTO user_preferences (user_id, song, artist) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             PreparedStatement prefStmt = conn.prepareStatement(preferenceSql)) {

            conn.setAutoCommit(false);
            try {
                // Save user
                stmt.setString(1, user.getUserId());
                stmt.setString(2, user.getName());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getPasswordHash());
                stmt.setString(5, user.getCountry());
                stmt.executeUpdate();

                // Save preferences
                UserPreferences prefs = user.getPreferences();
                for (String songWithArtist : prefs.getFavoriteSongs()) {
                    String[] parts = songWithArtist.split(" - ");
                    prefStmt.setString(1, user.getUserId());
                    prefStmt.setString(2, parts[0]);
                    prefStmt.setString(3, parts[1]);
                    prefStmt.executeUpdate();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user: " + e.getMessage());
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = """
            SELECT u.*, p.song, p.artist 
            FROM users u 
            LEFT JOIN user_preferences p ON u.user_id = p.user_id 
            WHERE u.email = ?
            """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            User user = new User(
                    rs.getString("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password_hash"),
                    rs.getString("country")
            );

            UserPreferences prefs = new UserPreferences();
            do {
                String song = rs.getString("song");
                String artist = rs.getString("artist");
                if (song != null && artist != null) {
                    prefs.addSongWithArtist(song, artist);
                }
            } while (rs.next());

            user.updatePreferences(prefs);
            return Optional.of(user);

        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by email: " + e.getMessage());
        }
    }

    @Override
    public void saveRecommendationHistory(String userId, List<Recommendation> recommendations) {
        String sql = "INSERT INTO recommendations (recommendation_id, user_id, content, artist, type) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);
            try {
                for (Recommendation rec : recommendations) {
                    stmt.setString(1, UUID.randomUUID().toString());
                    stmt.setString(2, userId);
                    stmt.setString(3, rec.getContent());
                    stmt.setString(4, rec.getArtist());
                    stmt.setString(5, rec.getType());
                    stmt.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving recommendations: " + e.getMessage());
        }
    }

    @Override
    public List<Recommendation> getRecommendationHistory(String userId) {
        String sql = """
            SELECT recommendation_id, content, artist, type 
            FROM recommendations 
            WHERE user_id = ? 
            ORDER BY created_at DESC
            """;

        List<Recommendation> recommendations = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                recommendations.add(new Recommendation(
                        Integer.parseInt(rs.getString("recommendation_id")),
                        rs.getString("content"),
                        rs.getString("artist"),
                        rs.getString("type")
                ));
            }

            return recommendations;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting recommendation history: " + e.getMessage());
        }
    }

    @Override
    public Optional<User> findById(String id) {
        String sql = """
            SELECT u.*, p.song, p.artist 
            FROM users u 
            LEFT JOIN user_preferences p ON u.user_id = p.user_id 
            WHERE u.user_id = ?
            """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            User user = User.createFromDatabase(
                    rs.getString("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password_hash"),
                    rs.getString("country")
            );

            UserPreferences prefs = new UserPreferences();
            do {
                String song = rs.getString("song");
                String artist = rs.getString("artist");
                if (song != null && artist != null) {
                    prefs.addSongWithArtist(song, artist);
                }
            } while (rs.next());

            user.updatePreferences(prefs);
            return Optional.of(user);

        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by id: " + e.getMessage());
        }
    }

    @Override
    public void update(User user) {
        String sql = """
            UPDATE users 
            SET name = ?, email = ?, password_hash = ?, country = ? 
            WHERE user_id = ?
            """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPasswordHash());
            stmt.setString(4, user.getCountry());
            stmt.setString(5, user.getUserId());

            stmt.executeUpdate();

            // Update preferences
            updatePreferences(user);

        } catch (SQLException e) {
            throw new RuntimeException("Error updating user: " + e.getMessage());
        }
    }

    private void updatePreferences(User user) throws SQLException {
        // First delete existing preferences
        String deleteSql = "DELETE FROM user_preferences WHERE user_id = ?";
        // Then insert new preferences
        String insertSql = "INSERT INTO user_preferences (user_id, song, artist) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Delete old preferences
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                    deleteStmt.setString(1, user.getUserId());
                    deleteStmt.executeUpdate();
                }

                // Insert new preferences
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    for (String songWithArtist : user.getPreferences().getFavoriteSongs()) {
                        String[] parts = songWithArtist.split(" - ");
                        insertStmt.setString(1, user.getUserId());
                        insertStmt.setString(2, parts[0]);
                        insertStmt.setString(3, parts[1]);
                        insertStmt.executeUpdate();
                    }
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage());
        }
    }
}
