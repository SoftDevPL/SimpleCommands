package wg.simple.simplecommands.simplecommand.warps.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;
import wg.simple.simplecommands.simplecommand.warps.Warp;

public class PlayerWarpEvent extends PlayerMoveEvent {
    private final Warp warp;

    public PlayerWarpEvent(Player player, Location from, Location to, Warp warp) {
        super(player, from, to);
        this.warp = warp;
    }

    public static HandlerList getHandlerList() {
        return PlayerMoveEvent.getHandlerList();
    }

    @Override
    public HandlerList getHandlers() {
        return super.getHandlers();
    }

    public Warp getWarp() {
        return warp;
    }
}
