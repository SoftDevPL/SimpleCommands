package wg.simple.simplecommands.simplecommand.spawns.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerTeleportSpawnEvent extends PlayerMoveEvent {

    public PlayerTeleportSpawnEvent(Player player, Location from, Location to) {
        super(player, from, to);
    }

    public static HandlerList getHandlerList() {
        return PlayerMoveEvent.getHandlerList();
    }

    @Override
    public HandlerList getHandlers() {
        return super.getHandlers();
    }
}
