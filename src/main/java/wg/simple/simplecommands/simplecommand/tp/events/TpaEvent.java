package wg.simple.simplecommands.simplecommand.tp.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class TpaEvent extends TpEvent {

    private final static HandlerList handlerList = new HandlerList();

    private Player playerToTeleport;

    public TpaEvent(Player sender, Player playerToTeleport) {
        super(sender);
        this.playerToTeleport = playerToTeleport;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Player getPlayerToTeleport() {
        return playerToTeleport;
    }

    public void setPlayerToTeleport(Player playerToTeleport) {
        this.playerToTeleport = playerToTeleport;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
