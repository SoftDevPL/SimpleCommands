package wg.simple.simplecommands.simplecommand.spawns.events;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RemoveHubEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final Location removingHub;
    private final CommandSender who;
    private boolean cancel;

    public RemoveHubEvent(CommandSender who, Location removingHub) {
        this.who = who;
        this.removingHub = removingHub;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public CommandSender getWho() {
        return who;
    }

    public Location getRemovingHub() {
        return removingHub;
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
