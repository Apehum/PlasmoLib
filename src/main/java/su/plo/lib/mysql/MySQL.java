package su.plo.lib.mysql;

import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class MySQL {
    private static final HashMap<JavaPlugin, HikariDataSource> hikariSources = new HashMap<>();

    public static void init(JavaPlugin plugin) {
        FileConfiguration cfg = plugin.getConfig();

        HikariDataSource hikari = new HikariDataSource();
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", cfg.getString("mysql.host"));
        hikari.addDataSourceProperty("port", cfg.getString("mysql.port"));
        hikari.addDataSourceProperty("databaseName", cfg.getString("mysql.db"));
        hikari.addDataSourceProperty("user", cfg.getString("mysql.user"));
        hikari.addDataSourceProperty("password", cfg.getString("mysql.password"));
        hikari.addDataSourceProperty("useSSL", "false");
        hikari.addDataSourceProperty("characterEncoding","utf8");
        hikari.addDataSourceProperty("useUnicode","true");
        hikari.setMinimumIdle(2);

        hikariSources.put(plugin, hikari);
    }

    public static Connection getConnection(JavaPlugin plugin) throws SQLException {
        return hikariSources.get(plugin).getConnection();
    }

    public static void disconnect() {
        hikariSources.forEach(((plugin, hikariDataSource) -> {
            try {
                hikariDataSource.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));
    }
}
