package wg.simple.simplecommands.simplecommand.msg.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerUnBlockPrivateMessageEvent extends PlayerEvent {

    private final static HandlerList handlerList = new HandlerList();
    private Player playerToUnblock;

    public PlayerUnBlockPrivateMessageEvent(Player who, Player playerToUnblock) {
        super(who);
        this.playerToUnblock = playerToUnblock;
    }

    public Player getPlayerToUnblock() { return playerToUnblock;}

    public void setPlayerToUnblock(Player playerToUnblock) { this.playerToUnblock = playerToUnblock;}

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
