package wg.simple.simplecommands.managers;

import org.bukkit.entity.Player;

public interface PlayerChatAction {
    boolean action(String chatMessage, Player player);
}
