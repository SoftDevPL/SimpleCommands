package wg.simple.simplecommands.simplecommand.warps.listeners;

import org.bukkit.Location;
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
    private List<Warp> warpList = new ArrayList<>();
    private AdminGuiDatabase database;

    public void init() {
        SimpleCommands plugin = SimpleCommands.getInstance();
        this.database = plugin.SQLmanager.database;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        deleteNotExistingWarps();
    }

    private void deleteNotExistingWarps() {
        warpList = database.getAllWarps();
        for (Warp warp : warpList) {
            if (warp.getLocation().getWorld() == null) {
                this.database.deleteWarp(warp.getWarpUUID().toString());
            }
        }
        warpList.clear();
        warpList = database.getAllWarps();
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

    public void addWarp(Warp warp) {
        if (!isWarpExists(warp)) {
            warpList.add(warp);
            this.addToDatabase(warp);
        }
    }

    private void addToDatabase(Warp warp) {
        Location loc = warp.getLocation();
        database.insertIntoWarpsTable(
                warp.getWarpUUID().toString(),
                warp.getName(),
                loc.getWorld().getUID().toString(),
                loc.getX(),
                loc.getY(),
                loc.getZ(),
                loc.getPitch(),
                loc.getYaw());
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

    private void removeFromDatabase(Warp warp) {
        database.deleteWarp(warp.getWarpUUID().toString());
    }
}
