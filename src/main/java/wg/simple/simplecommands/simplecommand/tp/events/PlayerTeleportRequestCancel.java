package wg.simple.simplecommands.simplecommand.tp.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerTeleportRequestCancel extends PlayerEvent {
    public static final int MOVED = 0;
    public static final int DEATH = 1;
    public static final int LEAVE_SERVER = 2;
    public static final int ANOTHER_REQUEST = 3;
    private static final HandlerList handlerList = new HandlerList();
    private final int reason;

    public PlayerTeleportRequestCancel(Player who, int reason) {
        super(who);
        this.reason = reason;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public int getReason() {
        return reason;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
