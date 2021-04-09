package wg.simple.simplecommands.simplecommand.homes.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import wg.simple.simplecommands.simplecommand.homes.Home;

public class PlayerTeleportsToHomeEvent extends PlayerHomeEvent {
    private static final HandlerList handlerList = new HandlerList();

    public PlayerTeleportsToHomeEvent(Player who, Home home) {
        super(who, home);
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
