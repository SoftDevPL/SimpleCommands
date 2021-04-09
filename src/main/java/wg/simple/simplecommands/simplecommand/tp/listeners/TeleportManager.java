package wg.simple.simplecommands.simplecommand.tp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.fileManager.sql.sqlUtils.CustomMath;
import wg.simple.simplecommands.simplecommand.tp.events.PlayerStartsTeleportEvent;
import wg.simple.simplecommands.simplecommand.tp.events.PlayerTeleportRequestCancel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportManager implements Listener {

    public Map<UUID, Integer> taskRequests = new HashMap<>();
    private int teleportWaitingTime = 100;
    private SimpleCommands mainAD;
    private LanguageConfig languageConfig;

    public void init() {
        this.mainAD = SimpleCommands.getInstance();
        this.mainAD.getServer().getPluginManager().registerEvents(this, this.mainAD);
        this.languageConfig = this.mainAD.configsManager.languageConfig;
        this.teleportWaitingTime = this.mainAD.configsManager.mainConfig.getTeleportWaitTime();
    }

    public boolean hasActualRequest(UUID playerUUID) {
        return taskRequests.containsKey(playerUUID);
    }

    public boolean addRequest(Player player, Runnable runnable) {
        boolean value = false;
        UUID uuid = player.getUniqueId();
        if (taskRequests.containsKey(uuid)) {
            player.sendMessage(languageConfig.getUnableToTeleport());
            value = true;
            Bukkit.getPluginManager().callEvent(new PlayerTeleportRequestCancel(player, PlayerTeleportRequestCancel.ANOTHER_REQUEST));
        }
        int taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(this.mainAD, () -> {
            taskRequests.remove(uuid);
            runnable.run();
        }, this.teleportWaitingTime);
        taskRequests.put(uuid, taskId);
        return value;
    }


    private void cancelTask(Player player) {
        Bukkit.getScheduler().cancelTask(this.taskRequests.get(player.getUniqueId()));
        taskRequests.remove(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void addingTasks(PlayerStartsTeleportEvent event) {
        if (event.getPlayer().hasPermission(mainAD.permissions.skipTeleportTime)) {
            event.getRequest().run();
            return;
        }
        addRequest(event.getPlayer(), event.getRequest());
        event.getPlayer().sendMessage(languageConfig.getTeleportationMessage());
    }

    @EventHandler
    private void tasksCleaner(PlayerQuitEvent event) {
        if (this.taskRequests.containsKey(event.getPlayer().getUniqueId())) {
            Bukkit.getServer().getPluginManager().callEvent(new
                    PlayerTeleportRequestCancel(event.getPlayer(), PlayerTeleportRequestCancel.LEAVE_SERVER));
        }
    }

    @EventHandler
    private void cancelWhileDeath(PlayerDeathEvent event) {
        if (this.taskRequests.containsKey(event.getEntity().getUniqueId())) {
            Bukkit.getServer().getPluginManager().callEvent(new
                    PlayerTeleportRequestCancel(event.getEntity(), PlayerTeleportRequestCancel.DEATH));
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void cancelWhileMoving(PlayerMoveEvent event) {
        if (CustomMath.getPosition(event.getTo()).equals(CustomMath.getPosition(event.getFrom())))
            return;
        UUID uuid = event.getPlayer().getUniqueId();
        if (this.taskRequests.containsKey(uuid)) {
            Bukkit.getServer().getPluginManager().callEvent(new
                    PlayerTeleportRequestCancel(event.getPlayer(), PlayerTeleportRequestCancel.MOVED));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void cancelling(PlayerTeleportRequestCancel event) {
        cancelTask(event.getPlayer());
        if (event.getReason() == PlayerTeleportRequestCancel.MOVED) {
            event.getPlayer().sendMessage(languageConfig.getYouMoved());
        }
    }

}
