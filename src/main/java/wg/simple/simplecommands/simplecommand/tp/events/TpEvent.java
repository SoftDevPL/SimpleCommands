package wg.simple.simplecommands.simplecommand.tp.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class TpEvent extends Event implements Cancellable {
    private final Player sender;
    private boolean cancel = false;

    public TpEvent(Player sender) {
        this.sender = sender;
    }

    public Player getSender() {
        return sender;
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
