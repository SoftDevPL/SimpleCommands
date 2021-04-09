package wg.simple.simplecommands.simplecommand.tp.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class TpacceptEvent extends TpEvent {

    private final static HandlerList handlerList = new HandlerList();

    private final Player playerWhoAccept;

    public TpacceptEvent(Player sender, Player playerWhoAccept) {
        super(sender);
        this.playerWhoAccept = playerWhoAccept;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Player getPlayerWhoAccept() {
        return playerWhoAccept;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
