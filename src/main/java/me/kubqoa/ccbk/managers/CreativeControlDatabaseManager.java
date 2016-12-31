package me.kubqoa.ccbk.managers;

import me.kubqoa.ccbk.CreativeControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author KubqoA (Jakub Arbet)
 */
public class CreativeControlDatabaseManager {
    public static ConnectionPoolManager pool;

    public void onEnable() {
        pool = new ConnectionPoolManager();
        initTables();
    }

    private void initTables() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement(BlocksTable());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    private String BlocksTable() {
        if (CreativeControl.databaseType.equalsIgnoreCase("mysql") || CreativeControl.databaseType.equalsIgnoreCase("mariadb")) {
            return
                    "CREATE TABLE IF NOT EXISTS `" + CreativeControl.databasePrefix + "blocks` " +
                            "(" +
                            "`index` INT NOT NULL AUTO_INCREMENT, " +
                            "`x` REAL, " +
                            "`y` REAL, " +
                            "`z` REAL, " +
                            "`world` TEXT, " +
                            "PRIMARY KEY(`index`) " +
                            ");"
            ;
        } else if (CreativeControl.databaseType.equalsIgnoreCase("sqlite")) {
            return
                    "CREATE TABLE IF NOT EXISTS `" + CreativeControl.databasePrefix + "blocks` " +
                            "(" +
                            "`index` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                            "`x` REAL, " +
                            "`y` REAL, " +
                            "`z` REAL, " +
                            "`world` TEXT " +
                            ");"
            ;
        }
        return null;
    }

    public static void executeUpdate(String query, Object[] objects) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement(query);
            int i=1;
            for (Object object : objects) {
                ps.setObject(i,object);
                i++;
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public static int executeQuery(String query, Object[] objects) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int i=0;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement(query);
            i=1;
            for (Object object : objects) {
                ps.setObject(i,object);
                i++;
            }
            rs=ps.executeQuery(); i=0;
            while (rs.next()) {
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
        return i;
    }

    public void onDisable() {
        pool.closePool();
    }
}