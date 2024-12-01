package infrastructure.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import entity.Preference;

/**
 * Handles database operations for user music preferences.
 * Each operation establishes its own connection, making it independent of application state.
 */
public class PreferenceRepository implements IRepository<Preference> {
    private static final String TABLE_NAME = "preferences";

    /**
     * Converts database string representation of lists to Java List.
     * @param dbString Comma-separated string from database
     * @return List of strings
     */
    private List<String> stringToList(String dbString) {
        final List<String> result;
        if (dbString == null || dbString.isEmpty()) {
            result = new ArrayList<>();
        }
        else {
            result = Arrays.asList(dbString.split(","));
        }
        return result;
    }

    /**
     * Converts Java List to database-friendly string.
     * @param list List to convert
     * @return Comma-separated string
     */
    private String listToString(List<String> list) {
        String result = "";
        if (!list.isEmpty()) {
            result = String.join(",", list);
        }
        return result;
    }

    @Override
    public Optional<Preference> findById(int preferenceId) {
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE preference_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, preferenceId);
            final ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToPreference(rs));
            }
            return Optional.empty();
        }
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
    }

    /**
     * Finds preferences by user ID.
     * @param userId User's ID
     * @return Optional containing user's preferences if found
     */
    public Optional<Preference> findByUserId(int userId) {
        final Optional<Preference> result;
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            final ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                result = Optional.of(mapResultSetToPreference(rs));
            }
            else {
                result = Optional.empty();
            }
        }
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
        return result;
    }

    private Preference mapResultSetToPreference(ResultSet resultSet) throws SQLException {
        final Preference preference = new Preference();
        preference.setPreferenceId(resultSet.getInt("preference_id"));
        preference.setUserId(resultSet.getInt("user_id"));
        preference.setSongs(stringToList(resultSet.getString("songs")));
        preference.setGenres(stringToList(resultSet.getString("genres")));
        preference.setArtists(stringToList(resultSet.getString("artists")));
        preference.setAlbums(stringToList(resultSet.getString("albums")));
        return preference;
    }

    @Override
    public void save(Preference preference) {
        final String sql = "INSERT INTO " + TABLE_NAME
                + " (user_id, songs, genres, artists, albums) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setStatementParameters(stmt, preference);

            final int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating preference failed, no rows affected.");
            }

            final ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                preference.setPreferenceId(generatedKeys.getInt(1));
            }
        }
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void update(Preference preference) {
        final String sql = "UPDATE " + TABLE_NAME +
                " SET songs = ?, genres = ?, artists = ?, albums = ? " +
                "WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, listToString(preference.getSongs()));
            stmt.setString(2, listToString(preference.getGenres()));
            stmt.setString(3, listToString(preference.getArtists()));
            stmt.setString(4, listToString(preference.getAlbums()));
            stmt.setInt(5, preference.getUserId());

            stmt.executeUpdate();
        }
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
    }

    private void setStatementParameters(PreparedStatement stmt, Preference preference)
            throws SQLException {
        stmt.setInt(1, preference.getUserId());
        stmt.setString(2, listToString(preference.getSongs()));
        stmt.setString(3, listToString(preference.getGenres()));
        stmt.setString(4, listToString(preference.getArtists()));
        stmt.setString(5, listToString(preference.getAlbums()));
    }

    @Override
    public void delete(int preferenceId) {
        final String sql = "DELETE FROM " + TABLE_NAME + " WHERE preference_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, preferenceId);
            stmt.executeUpdate();
        }
        catch (SQLException exception) {
            throw new RuntimeException("Database error: " + exception.getMessage(), exception);
        }
    }
}
