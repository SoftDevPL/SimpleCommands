package wg.simple.simplecommands.simplecommand.homes.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;
import wg.simple.simplecommands.simplecommand.homes.Home;

public abstract class PlayerHomeEvent extends PlayerEvent implements Cancellable {
    protected Home home;
    private boolean cancel = false;

    public PlayerHomeEvent(Player who, Home home) {
        super(who);
        this.home = home;
    }

    public Home getHome() {
        return home;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
