package su.plo.lib.nats;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class NatsLib {
    public final static HashMap<JavaPlugin, NatsClient> clients = new HashMap<>();

    public static void init(JavaPlugin plugin) {
        clients.put(plugin, new NatsClient(plugin));
    }

    public static NatsClient get(JavaPlugin plugin) {
        return clients.get(plugin);
    }

    public static void disconnect() {
        clients.forEach(((plugin, natsClient) -> {
            natsClient.disconnect();
        }));
    }
}
