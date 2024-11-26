package infrastructure.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Placeholder for Checkstyle.
 */
public class DatabaseConfig {
    private static HikariDataSource dataSource;

    /**
     * Establish a safe connection to the database.
     * @param jdbcUrl url for the remote database.
     * @param username username for the remote database.
     * @param password password for the remote database.
     * @param sslMode secure connection mode.
     */
    public static void initialize(String jdbcUrl, String username, String password, String sslMode) {
        if (dataSource == null) {
            final HikariConfig config = new HikariConfig();
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(username);
            config.setPassword(password);
            config.setMaximumPoolSize(5);
            config.addDataSourceProperty("sslmode", sslMode);

            dataSource = new HikariDataSource(config);
        }
    }

    /**
     * Placeholder for Checkstyle.
     * @return DataSource object.
     */
    public static DataSource getDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("Database not initialized. Call initialize() first.");
        }
        return dataSource;
    }

    /**
     * Closes connection with the DataSource.
     */
    public static void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}

