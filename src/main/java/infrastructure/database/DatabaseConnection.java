package infrastructure.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static HikariDataSource dataSource;

    static {
        try {
            Dotenv dotenv = Dotenv.load();
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(dotenv.get("DATABASE_URL"));
            config.setUsername(dotenv.get("DATABASE_USER"));
            config.setPassword(dotenv.get("DATABASE_PASSWORD"));
            config.addDataSourceProperty("ssl", dotenv.get("POSTGRES_SSL_MODE"));
            config.setMaximumPoolSize(10);

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
