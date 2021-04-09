package wg.simple.simplecommands.simplecommand.back.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerBackEvent extends PlayerEvent implements Cancellable {
    private final static HandlerList handlerList = new HandlerList();
    private boolean cancel;
    private Location backLocation;

    public PlayerBackEvent(Player who, Location backLocation) {
        super(who);
        this.backLocation = backLocation;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Location getBackLocation() {
        return backLocation.clone();
    }

    public void setBackLocation(Location backLocation) {
        this.backLocation = backLocation;
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
