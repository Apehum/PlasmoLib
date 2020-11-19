package su.plo.lib;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import su.plo.lib.mysql.MySQL;
import su.plo.lib.nats.NatsLib;

@Plugin(
        id = "plasmolib",
        name = "su.plo.lib.PlasmoLibSponge"
)
public class PlasmoLibSponge {

    @Listener
    public void onReload(GameReloadEvent e) {
        MySQL.disconnect();
        NatsLib.disconnect();
    }

    @Listener
    public void onStop(GameStoppingServerEvent e) {
        MySQL.disconnect();
        NatsLib.disconnect();
    }
}
