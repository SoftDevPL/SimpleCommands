package wg.simple.simplecommands.simplecommand.warps.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.sql.sqlUtils.databasescommands.AdminGuiDatabase;
import wg.simple.simplecommands.simplecommand.warps.Warp;
import wg.simple.simplecommands.simplecommand.warps.events.PlayerSetWarpEvent;
import wg.simple.simplecommands.simplecommand.warps.events.PlayerWarpEvent;
import wg.simple.simplecommands.simplecommand.warps.events.RemoveWarpEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WarpManager implements Listener {
    private final List<Warp> warpList = new ArrayList<>();
    private AdminGuiDatabase database;

    public void init() {
        SimpleCommands plugin = SimpleCommands.getInstance();
        this.database = plugin.SQLmanager.database;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        loadAllFromDatabase();
    }

    private void loadAllFromDatabase() {
        List<List<String>> all = database.getAllWarps();
        for (List<String> one : all) {
            try {
                World world = Bukkit.getWorld(UUID.fromString(one.get(AdminGuiDatabase.WORLD_WARP_UUID)));
                if (world == null) continue;
                UUID warpUUID = UUID.fromString(one.get(AdminGuiDatabase.WARP_UUID));
                String warpName = one.get(AdminGuiDatabase.WARP_NAME);
                double x = Double.parseDouble(one.get(AdminGuiDatabase.X_WARP));
                double y = Double.parseDouble(one.get(AdminGuiDatabase.Y_WARP));
                double z = Double.parseDouble(one.get(AdminGuiDatabase.Z_WARP));
                float pitch = Float.parseFloat(one.get(AdminGuiDatabase.PITCH_WARP));
                float yaw = Float.parseFloat(one.get(AdminGuiDatabase.YAW_WARP));
                Location loc = new Location(world, x, y, z, yaw, pitch);
                Warp warp = new Warp(loc, warpName, warpUUID);
                this.addWarp(warp, false);
            } catch (Exception ignored) { }
        }
    }

    public Warp getWarpById(UUID warpUUID) {
        for (Warp warp : warpList) {
            if (warp.getWarpUUID().equals(warpUUID))
                return warp;
        }
        return null;
    }

    public boolean isWarpExists(Warp warp) {
        return warpList.contains(warp);
    }

    public Warp getWarpByName(String warpName) {
        for (Warp warp : warpList) {
            if (warp.getName().equalsIgnoreCase(warpName))
                return warp;
        }
        return null;
    }

    private void addWarp(Warp warp, boolean addToDatabase) {
        if (warpList.contains(warp)) return;
        warpList.add(warp);
        if (addToDatabase) this.addToDatabase(warp);
    }

    public void addWarp(Warp warp) {
        addWarp(warp, true);
    }

    public void removeWarp(Warp warpToRemove) {
        warpList.remove(warpToRemove);
        this.removeFromDatabase(warpToRemove);
    }

    public List<Warp> getAllWarps() {
        return warpList;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void addingWarps(PlayerSetWarpEvent event) {
        addWarp(event.getWarp());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void removingWarps(RemoveWarpEvent event) {
        removeWarp(event.getRemovingWarp());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void warpingPlayers(PlayerWarpEvent event) {
        event.getPlayer().teleport(event.getTo());
    }


    private void addToDatabase(Warp warp) {
        Location loc = warp.getLocation();
        database.insertIntoWarpsTable(warp.getWarpUUID().toString(), warp.getName(), loc.getWorld().getUID().toString(),
                loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
    }

    private void removeFromDatabase(Warp warp) {
        database.deleteWarp(warp.getWarpUUID().toString());
    }
}
