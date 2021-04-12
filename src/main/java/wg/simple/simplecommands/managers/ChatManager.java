package wg.simple.simplecommands.managers;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import wg.simple.simplecommands.SimpleCommands;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ChatManager implements Listener {
    private final Map<UUID, PlayerChatAction> chatTasks = new HashMap<>();

    public void init() {
        SimpleCommands plugin = SimpleCommands.getInstance();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void removeTaskOnLeave(PlayerQuitEvent event) {
        chatTasks.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOW)
    private void executeTask(AsyncPlayerChatEvent event) {
        if (chatTasks.containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(SimpleCommands.getInstance(), () -> {
                if (chatTasks.get(event.getPlayer().getUniqueId()).action(event.getMessage(), event.getPlayer()))
                    chatTasks.remove(event.getPlayer().getUniqueId());
            });
        }
    }

    public void removeTask(UUID playerUUID) {
        chatTasks.remove(playerUUID);
    }

    public void clearTasks() {
        chatTasks.clear();
    }

    public void setTask(UUID playerUUID, PlayerChatAction action) {
        chatTasks.put(playerUUID, action);
    }
}