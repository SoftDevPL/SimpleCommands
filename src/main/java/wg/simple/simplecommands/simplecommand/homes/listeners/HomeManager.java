package wg.simple.simplecommands.simplecommand.homes.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.fileManager.sql.sqlUtils.databasescommands.AdminGuiDatabase;
import wg.simple.simplecommands.managers.Permissions;
import wg.simple.simplecommands.simplecommand.homes.Home;
import wg.simple.simplecommands.simplecommand.homes.events.PlayerRemoveHomeEvent;
import wg.simple.simplecommands.simplecommand.homes.events.PlayerSetHomeEvent;
import wg.simple.simplecommands.simplecommand.homes.events.PlayerTeleportsToHomeEvent;

import java.util.*;
import java.util.stream.Collectors;

public class HomeManager implements Listener {
    private final Map<UUID, List<Home>> homeMap = new HashMap<>();
    private int premiumHomeLimit;
    private int normalHomeLimit;
    private AdminGuiDatabase database;
    private Permissions permissions;
    private LanguageConfig languageConfig;

    public void init() {
        SimpleCommands plugin = SimpleCommands.getInstance();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.database = plugin.SQLmanager.database;
        this.permissions = plugin.permissions;
        this.normalHomeLimit = plugin.configsManager.mainConfig.getHomeNormalLimit();
        this.premiumHomeLimit = plugin.configsManager.mainConfig.getHomePremiumLimit();
        languageConfig = plugin.configsManager.languageConfig;
        System.out.println(database.getAllHomes());
        deleteAllNotExistingWorlds();
        setupHomes();
    }

    private void setupHomes() {
        for (Map.Entry<UUID, Home> entry: database.getAllHomes().entries()) {
            this.addHome(entry.getValue());
        }
    }

    private List<UUID> returnRetailedList(List<UUID> mainList, List<UUID> listToRetail) {
        return listToRetail.stream().filter(item -> !mainList.contains(item)).collect(Collectors.toList());
    }

    private void deleteAllNotExistingWorlds() {
        List<UUID> homesWorldsUUIDS = new ArrayList<>();

        for (Map.Entry<UUID, Home> entry: database.getAllHomes().entries()) {
            if (entry.getValue().getHomeLocation().getWorld() == null) {
                database.deleteHubByWorldUUID(entry.getKey().toString());
            } else {
                homesWorldsUUIDS.add(entry.getKey());
            }
        }

        for (UUID uuid : returnRetailedList(Bukkit.getWorlds().stream().map(World::getUID).collect(Collectors.toList()), homesWorldsUUIDS)) {
            this.database.deleteHomeByWorldUUID(uuid.toString());
        }
    }

    public boolean isHomeRegister(Home home) {
        return homeMap.containsValue(home);
    }

    private void addHome(Home home) {
        UUID owner = home.getOwnerUUID();
        if (homeMap.containsKey(owner)) {
            homeMap.get(owner).add(home);
        } else {
            List<Home> listToAdd = new ArrayList<>();
            listToAdd.add(home);
            homeMap.put(owner, listToAdd);
        }
    }

    private void removeHome(Home home) {
        List<Home> homeList = homeMap.get(home.getOwnerUUID());
        if (homeList == null) return;
        homeList.remove(home);
        if (homeList.isEmpty()) {
            homeMap.remove(home.getOwnerUUID());
        }
    }

    public List<Home> getPlayerHomes(UUID playerUUID) {
        List<Home> homes = homeMap.get(playerUUID);
        return homes == null ? new ArrayList<>() : homes;
    }

    public boolean hasAlreadyHome(UUID playerUUID, String homeName) {
        List<Home> homes = homeMap.get(playerUUID);
        if (homes == null) return false;
        for (Home home : homes) {
            if (home.getName().equalsIgnoreCase(homeName))
                return true;
        }
        return false;
    }

    public Home getPlayerHome(UUID playerUUID, String homeName) {
        List<Home> homes = getPlayerHomes(playerUUID);
        if (homes == null || homes.isEmpty()) return null;
        for (Home home : homes) {
            if (home.getName().equalsIgnoreCase(homeName)) {
                return home;
            }
        }
        return null;
    }

    public int getPlayerHomeCount(UUID playerUUID) {
        List<Home> homes = getPlayerHomes(playerUUID);
        if (homes == null) return 0;
        return homes.size();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void addingHomes(PlayerSetHomeEvent event) {
        addHome(event.getHome());
        this.saveToDatabase(event.getHome());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void checkingForHomeLimit(PlayerSetHomeEvent event) {
        Player player = event.getPlayer();
        boolean block = false;
        if (player.hasPermission(permissions.unlimitedHomeCountPerm)) {
            return;
        }

        int actualHomeCount = getPlayerHomeCount(event.getHome().getOwnerUUID());

        if (player.hasPermission(permissions.specialHomeCountPerm)) {
            if (actualHomeCount >= premiumHomeLimit)
                block = true;
        } else if (player.hasPermission(permissions.normalHomeCountPerm)) {
            if (actualHomeCount >= normalHomeLimit)
                block = true;
        }
        if (block) {
            event.setCancelled(true);
            player.sendMessage(String.valueOf(actualHomeCount));
            player.sendMessage(languageConfig.getHomeLimit());
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void removingHomes(PlayerRemoveHomeEvent event) {
        this.removeHome(event.getHome());
        removeFromDatabase(event.getHome().getHomeUUID());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void teleportingPlayers(PlayerTeleportsToHomeEvent event) {
        event.getPlayer().teleport(event.getHome().getHomeLocation());
    }


    public void saveToDatabase(Home home) {
        Location loc = home.getHomeLocation();
        database.insertIntoHomesTable(home.getOwnerUUID().toString(), home.getHomeUUID().toString(), loc.getWorld().getUID().toString(),
                home.getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
    }

    public void removeFromDatabase(UUID homeUUID) {
        database.deleteHome(homeUUID.toString());
    }

}
