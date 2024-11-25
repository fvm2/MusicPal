package infrastructure.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class DatabaseConfig {
    private static HikariDataSource dataSource;

    public static void initialize(String jdbcUrl, String username, String password, String sslMode) {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(username);
            config.setPassword(password);
            config.setMaximumPoolSize(5);
            config.addDataSourceProperty("sslmode", sslMode);

            dataSource = new HikariDataSource(config);
        }
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("Database not initialized. Call initialize() first.");
        }
        return dataSource;
    }

    public static void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}