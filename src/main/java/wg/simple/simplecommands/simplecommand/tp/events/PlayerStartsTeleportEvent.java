package wg.simple.simplecommands.simplecommand.tp.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerStartsTeleportEvent extends PlayerEvent implements Cancellable {
    private final static HandlerList handlerList = new HandlerList();
    private boolean cancel = false;
    private Runnable request;

    public PlayerStartsTeleportEvent(Player who, Runnable request) {
        super(who);
        this.request = request;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public Runnable getRequest() {
        return request;
    }

    public void setRequest(Runnable request) {
        this.request = request;
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
