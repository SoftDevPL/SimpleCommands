package wg.simple.simplecommands.simplecommand.back.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.fileManager.sql.sqlUtils.databasescommands.AdminGuiDatabase;
import wg.simple.simplecommands.simplecommand.back.events.PlayerBackEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BackManager implements Listener {
    private final Map<UUID, Location> backMap = new HashMap<>();
    private AdminGuiDatabase database;
    private LanguageConfig languageConfig;

    public void init() {
        SimpleCommands plugin = SimpleCommands.getInstance();
        this.database = plugin.SQLmanager.database;
        this.languageConfig = plugin.configsManager.languageConfig;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        loadAllFromDatabase();
    }


    private void addBack(UUID playerUUID, Location backLocation, boolean addToDatabase) {
        if (addToDatabase) {
            removeBack(playerUUID);
            this.addToDatabase(playerUUID, backLocation);
        }
        backMap.put(playerUUID, backLocation);
    }

    public void addBack(UUID playerUUID, Location backLocation) {
        addBack(playerUUID, backLocation, true);
    }

    public void removeBack(UUID playerUUID) {
        if (backMap.remove(playerUUID) != null) {
            removeFromDatabase(playerUUID);
        }
    }

    public void addToDatabase(UUID playerUUID, Location loc) {
        database.insertIntoBackTable(playerUUID.toString(), loc.getWorld().getUID().toString(),
                loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
    }

    public void loadAllFromDatabase() {
        List<List<String>> all = database.getAllBacks();
        for (List<String> back : all) {
            World world = Bukkit.getWorld(UUID.fromString(back.get(AdminGuiDatabase.WORLD_BACK_UUID)));
            UUID playerUUID = UUID.fromString(back.get(AdminGuiDatabase.PLAYER_UUID_BACK));
            double x = Double.parseDouble(back.get(AdminGuiDatabase.X_BACK));
            double y = Double.parseDouble(back.get(AdminGuiDatabase.Y_BACK));
            double z = Double.parseDouble(back.get(AdminGuiDatabase.Z_BACK));
            float pitch = Float.parseFloat(back.get(AdminGuiDatabase.PITCH_BACK));
            float yaw = Float.parseFloat(back.get(AdminGuiDatabase.YAW_BACK));
            addBack(playerUUID, new Location(world, x, y, z, yaw, pitch), false);
        }
    }

    public void removeFromDatabase(UUID playerUUID) {
        database.deleteBack(playerUUID.toString());
    }

    public Location getLastDeathLocation(UUID playerUUID) {
        return backMap.get(playerUUID);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void addingBack(PlayerDeathEvent event) {
        if (event.getEntity().getHealth() > 0) return;
        addBack(event.getEntity().getUniqueId(), event.getEntity().getLocation(), true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void playerTeleporting(PlayerBackEvent event) {
        event.getPlayer().teleport(event.getBackLocation());
        safeTeleportPlayer(event.getPlayer());
        event.getPlayer().sendMessage(languageConfig.getSuccessfullyTeleport());
    }

    private void removeAllNegativePotionEffects(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.setFireTicks(0);
    }

    private void safeTeleportPlayer(Player player) {
        player.setInvulnerable(true);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SimpleCommands.getInstance(), () -> {
            player.setInvulnerable(false);
            removeAllNegativePotionEffects(player);
        }, 100);
    }

}
