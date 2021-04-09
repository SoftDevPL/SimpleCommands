package wg.simple.simplecommands.simplecommand.warps.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import wg.simple.simplecommands.simplecommand.warps.Warp;

public class PlayerSetWarpEvent extends PlayerEvent implements Cancellable {
    private final static HandlerList handlerList = new HandlerList();
    private boolean cancel;
    private Warp warp;

    public PlayerSetWarpEvent(Player who, Warp warp) {
        super(who);
        this.warp = warp;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Warp getWarp() {
        return warp;
    }

    public void setWarp(Warp warp) {
        this.warp = warp;
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
