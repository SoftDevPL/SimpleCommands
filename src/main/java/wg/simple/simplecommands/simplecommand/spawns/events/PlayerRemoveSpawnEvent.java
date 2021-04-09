package wg.simple.simplecommands.simplecommand.spawns.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerRemoveSpawnEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final Location spawn;
    private boolean cancel;

    public PlayerRemoveSpawnEvent(Player who, Location spawn) {
        super(who);
        this.spawn = spawn;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Location getSpawn() {
        return spawn;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
