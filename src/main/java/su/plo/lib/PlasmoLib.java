package su.plo.lib;

import org.bukkit.plugin.java.JavaPlugin;
import su.plo.lib.mysql.MySQL;
import su.plo.lib.nats.NatsLib;

public final class PlasmoLib extends JavaPlugin {

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {
        MySQL.disconnect();
        NatsLib.disconnect();
    }
}
