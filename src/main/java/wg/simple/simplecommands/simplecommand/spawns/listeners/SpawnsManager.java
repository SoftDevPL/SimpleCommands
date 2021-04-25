package wg.simple.simplecommands.simplecommand.spawns.listeners;

import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.omg.CORBA.MARSHAL;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.fileManager.sql.sqlUtils.databasescommands.AdminGuiDatabase;
import wg.simple.simplecommands.simplecommand.spawns.events.*;

import java.util.*;
import java.util.stream.Collectors;

public class SpawnsManager implements Listener {
    private final Map<UUID, Location> spawnsMap = new HashMap<>();
    public LanguageConfig languageConfig;
    private boolean teleportToSpawnAfterDeath;
    private boolean teleportToHubAfterDeath;
    private boolean teleportToHubWhenJoinFirstTime;
    private boolean alwaysTeleportToHubWhenJoin;
    private AdminGuiDatabase database;
    private Location hub = null;

    public void init() {
        SimpleCommands plugin = SimpleCommands.getInstance();
        this.languageConfig = plugin.configsManager.languageConfig;
        this.teleportToSpawnAfterDeath = plugin.configsManager.mainConfig.isTeleportingToSpawn();
        this.teleportToHubAfterDeath = plugin.configsManager.mainConfig.isTeleportingToHub();
        this.database = plugin.SQLmanager.database;
        this.teleportToHubWhenJoinFirstTime = plugin.configsManager.mainConfig.isTeleportWhenJoinFirst();
        this.alwaysTeleportToHubWhenJoin = plugin.configsManager.mainConfig.isAlwaysTeleportToHubWhenJoin();
        deleteAllNotExistingWorlds();
        setupHub();
        setupSpawns();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private void setupHub() {
        Map<UUID, Location> locations = this.database.getHub();
        if (locations.isEmpty()) return;
        for (Map.Entry<UUID, Location> entry: database.getHub().entrySet()) {
            this.hub = entry.getValue();
        }
    }

    private void setupSpawns() {
        Multimap<UUID, Location> locations = this.database.getSpawns();
        if (locations.isEmpty()) return;
        for (Map.Entry<UUID, Location> entry: database.getHub().entrySet()) {
            spawnsMap.put(entry.getKey(), entry.getValue());
        }
    }

    private List<UUID> returnRetailedList(List<UUID> mainList, List<UUID> listToRetail) {
        return listToRetail.stream().filter(item -> !mainList.contains(item)).collect(Collectors.toList());
    }

    private void deleteAllNotExistingWorlds() {
        List<UUID> hubsWorldsUUIDS = new ArrayList<>();
        List<UUID> spawnsWorldsUUIDS = new ArrayList<>();

        for (Map.Entry<UUID, Location> entry: database.getHub().entrySet()) {
            if (entry.getValue().getWorld() == null) {
                database.deleteHubByWorldUUID(entry.getKey().toString());
            } else {
                hubsWorldsUUIDS.add(entry.getKey());
            }
        }

        for (Map.Entry<UUID, Location> entry: database.getSpawns().entries()) {
            if (entry.getValue().getWorld() == null) {
                database.deleteSpawnByWorldUUID(entry.getKey().toString());
            } else {
                spawnsWorldsUUIDS.add(entry.getKey());
            }
        }

        for (UUID uuid : returnRetailedList(Bukkit.getWorlds().stream().map(World::getUID).collect(Collectors.toList()), hubsWorldsUUIDS)) {
            this.database.deleteHubByWorldUUID(uuid.toString());
        }

        for (UUID uuid : returnRetailedList(Bukkit.getWorlds().stream().map(World::getUID).collect(Collectors.toList()), spawnsWorldsUUIDS)) {
            this.database.deleteSpawnByWorldUUID(uuid.toString());
        }
    }

    public Map<UUID, Location> getAllSpawns() {
        return spawnsMap;
    }

    public void removeHub() {
        this.hub = null;
        database.deleteHub();
    }

    private void addSpawn(Location loc, boolean addToDatabase) {
        if (spawnsMap.containsKey(loc.getWorld().getUID())) {
            removeSpawnFromDatabase(loc.getWorld().getUID());
        }
        spawnsMap.put(loc.getWorld().getUID(), loc);
        if (addToDatabase) this.saveToDatabase(loc);
    }

    private void addSpawn(Location loc) {
        this.addSpawn(loc, true);
    }

    private boolean removeSpawn(UUID worldUUID) {
        Location loc = spawnsMap.remove(worldUUID);
        if (loc == null) return false;
        removeSpawnFromDatabase(worldUUID);
        return true;
    }

    public Location getSpawn(UUID worldUUID) {
        return spawnsMap.get(worldUUID);
    }

    public Location getHub() {
        return hub;
    }

    public void setHub(Location loc) {
        if (this.hub != null) {
            database.deleteHubByWorldUUID(loc.getWorld().getUID().toString());
        }
        this.hub = loc;
        database.insertIntoHubTable(loc.getWorld().getUID().toString(), loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
    }

    public boolean hasSpawn(UUID worldUUID) {
        return spawnsMap.containsKey(worldUUID);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void addingSpawns(PlayerSetSpawnEvent event) {
        if (hasSpawn(event.getNewSpawn().getWorld().getUID())) {
            event.getPlayer().sendMessage(languageConfig.getSpawnChanged());
        } else {
            event.getPlayer().sendMessage(languageConfig.getSpawnCreated());
        }
        addSpawn(event.getNewSpawn());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void removingSpawns(PlayerRemoveSpawnEvent event) {
        if (removeSpawn(event.getSpawn().getWorld().getUID())) {
            event.getPlayer().sendMessage(languageConfig.getSpawnRemoved());
        } else event.getPlayer().sendMessage(languageConfig.getSpawnNotExists());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void teleportingPlayersToSpawn(PlayerTeleportSpawnEvent event) {
        event.getPlayer().teleport(event.getTo());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void changingHub(PlayerSetHubEvent event) {
        if (this.hub == null)
            event.getPlayer().sendMessage(languageConfig.getHubCreated());
        else
            event.getPlayer().sendMessage(languageConfig.getHubChanged());
        setHub(event.getNewHub());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void teleportingToHub(PlayerTeleportHubEvent event) {
        event.getPlayer().teleport(event.getTo());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void removingHub(RemoveHubEvent event) {
        if (this.hub == null) event.getWho().sendMessage(languageConfig.getHubNotExists());
        else {
            event.getWho().sendMessage(languageConfig.getHubRemoved());
            removeHub();
        }
    }

    @EventHandler
    public void teleportAfterDeath(PlayerRespawnEvent event) {
        if (teleportToHubAfterDeath && this.hub != null) {
            event.setRespawnLocation(this.hub);
        } else if (teleportToSpawnAfterDeath) {
            Location spawn = this.getSpawn(event.getPlayer().getLocation().getWorld().getUID());
            if (spawn == null) return;
            event.setRespawnLocation(spawn);
        }
    }

    public void saveToDatabase(Location spawn) {
        database.insertIntoSpawnsTable(spawn.getWorld().getUID().toString(), spawn.getX(), spawn.getY(), spawn.getZ(),
                spawn.getPitch(), spawn.getYaw());
    }

    public void removeSpawnFromDatabase(UUID worldUUID) {
        database.deleteSpawn(worldUUID.toString());
    }

    @EventHandler
    public void teleportingToHub(PlayerJoinEvent event) {
        if (this.getHub() == null) return;
        if (alwaysTeleportToHubWhenJoin) {
            event.getPlayer().teleport(this.getHub());
        } else if (teleportToHubWhenJoinFirstTime) {
            if (!event.getPlayer().hasPlayedBefore()) {
                event.getPlayer().teleport(this.getHub());
            }
        }
    }
}
