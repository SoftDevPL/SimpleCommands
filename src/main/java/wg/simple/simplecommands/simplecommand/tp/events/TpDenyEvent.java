package wg.simple.simplecommands.simplecommand.tp.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class TpDenyEvent extends TpEvent {
    private static final HandlerList handlerList = new HandlerList();
    private final Player playerWhoDenied;

    public TpDenyEvent(Player sender, Player playerWhoDenied) {
        super(sender);
        this.playerWhoDenied = playerWhoDenied;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Player getPlayerWhoDenied() {
        return playerWhoDenied;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
