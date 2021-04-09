package wg.simple.simplecommands.simplecommand.spawns.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerSetSpawnEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final Location previousSpawn;
    private boolean cancel;
    private Location newSpawn;

    public PlayerSetSpawnEvent(Player who, Location previousSpawn, Location newSpawn) {
        super(who);
        this.previousSpawn = previousSpawn;
        this.newSpawn = newSpawn;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Location getPreviousSpawn() {
        return previousSpawn;
    }

    public Location getNewSpawn() {
        return newSpawn;
    }

    public void setNewSpawn(Location newSpawn) {
        this.newSpawn = newSpawn;
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
