package wg.simple.simplecommands.simplecommand.warps.events;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import wg.simple.simplecommands.simplecommand.warps.Warp;

public class RemoveWarpEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final Warp removingWarp;
    private final CommandSender executor;
    private boolean cancel;

    public RemoveWarpEvent(Warp removingWarp, CommandSender executor) {
        this.removingWarp = removingWarp;
        this.executor = executor;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public CommandSender getExecutor() {
        return executor;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public Warp getRemovingWarp() {
        return removingWarp;
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
