package wg.simple.simplecommands.simplecommand.msg.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerSentPrivateMessageEvent extends PlayerEvent implements Cancellable {

    private final static HandlerList handlerList = new HandlerList();
    private boolean cancel;
    private Player playerToReply;

    public PlayerSentPrivateMessageEvent(Player who, Player playerToReply) {
        super(who);
        this.playerToReply = playerToReply;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Player getPlayerToReply() {
        return playerToReply;
    }

    public void setPlayerToReply(Player playerToReply) {
        this.playerToReply = playerToReply;
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
