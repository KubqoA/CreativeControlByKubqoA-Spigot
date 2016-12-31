package me.kubqoa.ccbk.managers;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.kubqoa.ccbk.CreativeControl;
import me.kubqoa.ccbk.CreativeControlManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static me.kubqoa.ccbk.CreativeControl.config;
import static me.kubqoa.ccbk.CreativeControl.dataSource;

/**
 * @author KubqoA (Jakub Arbet)
 */
public class ConnectionPoolManager {

    public ConnectionPoolManager() {
        initPool();
    }

    private void initPool() {
        if (CreativeControl.databaseType.equalsIgnoreCase("sqlite")) {
            connectToSQLite();
            CreativeControlManager.log("OK!");
        } else if (CreativeControl.databaseType.equalsIgnoreCase("mariadb")) {
            connectToMariaDB();
            CreativeControlManager.log("OK!");
        } else if (CreativeControl.databaseType.equalsIgnoreCase("mysql")) {
            connectToMySQL();
            CreativeControlManager.log("OK!");
        } else {
            CreativeControlManager.log("Invalid value in config.yml for db-type.");
        }
    }

    private void connectToSQLite() {
        HikariConfig config = new HikariConfig();
        config.setPoolName("CreativeControlSQLitePool");
        config.setDriverClassName("org.sqlite.JDBC");
        config.setJdbcUrl("jdbc:sqlite:plugins/CreativeControl/creativecontrol.db");
        config.setMaxLifetime(60000);
        config.setIdleTimeout(45000);
        config.setMaximumPoolSize(10);
        config.setConnectionTestQuery("SELECT 1");

        dataSource = new HikariDataSource(config);
    }

    private void connectToMariaDB() {
        String host = config.getString("db-host");
        String port = config.getString("db-port");
        String username = config.getString("db-user");
        String password = config.getString("db-password");
        String databaseName = config.getString("db-database");

        HikariConfig config = new HikariConfig();
        config.setPoolName("CreativeControlMariaDBPool");
        config.setDriverClassName("org.mariadb.jdbc.MySQLDataSource");
        config.setJdbcUrl("jdbc:mysql://"+host+":"+port+"/"+databaseName);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    private void connectToMySQL() {
        String host = config.getString("db-host");
        String port = config.getString("db-port");
        String username = config.getString("db-user");
        String password = config.getString("db-password");
        String databaseName = config.getString("db-database");

        HikariConfig config = new HikariConfig();
        config.setPoolName("CreativeControlMySQLPool");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://"+host+":"+port+"/"+databaseName);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close(Connection conn, PreparedStatement ps, ResultSet res) {
        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
        if (res != null) try { res.close(); } catch (SQLException ignored) {}
    }

    public void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
