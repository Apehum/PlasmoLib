package su.plo.lib.nats;

import io.nats.client.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NatsClient {

    private Connection connection;
    private final String subject;

    public NatsClient(JavaPlugin plugin) {
        FileConfiguration config = plugin.getConfig();
        this.subject = config.getString("nats.subject");
        try {
            Options options = new Options.Builder().
                    server(String.format("nats://%s:%d",
                            config.getString("nats.ip"),
                            config.getInt("nats.port"))).
                    token(config.getString("nats.secret").toCharArray()).
                    build();
            this.connection = Nats.connect(options);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getSubject(String name) {
        return this.subject + "." + name;
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() {
        if(this.connection == null) {
            return;
        }

        try {
            this.connection.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void publish(String name, byte[] bytes) {
        if(this.connection == null) {
            return;
        }

        this.connection.publish(this.getSubject(name), bytes);
    }

    public void reply(String name, String to, byte[] bytes) {
        if(this.connection == null) {
            return;
        }

        this.connection.publish(this.getSubject(name), to, bytes);
    }
}
