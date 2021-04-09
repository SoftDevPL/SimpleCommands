package wg.simple.simplecommands.simplecommand.msg.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerBlockPrivateMassagerEvent extends PlayerEvent implements Cancellable {

    private final static HandlerList handlerList = new HandlerList();
    private Player blockedPlayer;
    private boolean cancel = false;

    public PlayerBlockPrivateMassagerEvent(Player who, Player blockedPlayer) {
        super(who);
        this.blockedPlayer = blockedPlayer;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Player getBlockedPlayer() {
        return this.blockedPlayer;
    }

    public void setBlockedPlayer(Player blockedPlayer) {
        this.blockedPlayer = player;
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
