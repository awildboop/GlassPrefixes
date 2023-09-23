package com.awildboop.glassprefixes.managers;

import com.awildboop.glassprefixes.GlassPrefixes;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DatabaseManager {
    private final GlassPrefixes plugin;
    private HikariDataSource dataSource;
    public DatabaseManager(GlassPrefixes plugin) { this.plugin = plugin; }



    public void connect() {
        final String host = this.plugin.getConfig().getString("database.sql.host");
        final String port = this.plugin.getConfig().getString("database.sql.port");
        final String database = this.plugin.getConfig().getString("database.sql.database");
        final String username = this.plugin.getConfig().getString("database.sql.username");
        final String password = this.plugin.getConfig().getString("database.sql.password");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mariadb://" + host + ':' + port + '/' + database); // Address of your running MySQL database
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        dataSource = new HikariDataSource(config);

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement sql = conn.prepareStatement("CREATE TABLE IF NOT EXISTS `prefixes` ( `uuid` CHAR(36), `prefix` VARCHAR(50), KEY `players` (`uuid`) USING BTREE);");
            sql.execute();
        } catch (Exception e) {
            this.plugin.getLogger().warning("Encountered error creating/checking database tables!");
        }
    }

    public HikariDataSource getDataSource() {
        return this.dataSource;
    }
}
