package wg.simple.simplecommands.simplecommand.spawns.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerSetHubEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final Location previousHub;
    private boolean cancel;
    private Location newHub;

    public PlayerSetHubEvent(Player who, Location previousHub, Location newHub) {
        super(who);
        this.previousHub = previousHub;
        this.newHub = newHub;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public Location getNewHub() {
        return newHub;
    }

    public void setNewHub(Location newHub) {
        this.newHub = newHub;
    }

    public Location getPreviousHub() {
        return previousHub;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
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
