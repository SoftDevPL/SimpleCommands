package wg.simple.simplecommands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import wg.simple.simplecommands.fileManager.configsutils.ConfigsManager;
import wg.simple.simplecommands.fileManager.sql.sqlUtils.SQLManager;
import wg.simple.simplecommands.managers.ListenersManager;
import wg.simple.simplecommands.managers.Permissions;
import wg.simple.simplecommands.managers.SimpleCommandManager;
import wg.simple.simplecommands.managers.guiengine.basics.GuiListener;

import java.util.UUID;
import java.util.logging.Logger;

public final class SimpleCommands extends JavaPlugin {

    public static final String ANSI_RESET = "\u001b[0m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_CYAN = "\u001b[36m";
    private static final Logger log = Logger.getLogger("Minecraft");
    static private SimpleCommands instance;
    @Getter
    public final Logger mesLogger = Logger.getLogger("");
    private final GuiListener guiListener = new GuiListener();
    public ListenersManager listenersManager;
    public ConfigsManager configsManager;
    public SimpleCommandManager simpleCommandManager;
    public SQLManager SQLmanager;
    public Permissions permissions;

    public static String convertColors(String st) {
        return ChatColor.translateAlternateColorCodes('&', st);
    }

    public static OfflinePlayer getOfflinePlayer(String name) {
        OfflinePlayer[] offlinePlayers = Bukkit.getServer().getOfflinePlayers();
        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            if (offlinePlayer.getName().equalsIgnoreCase(name)) {
                return offlinePlayer;
            }
        }
        return null;
    }

    public static OfflinePlayer getOfflinePlayer(UUID playerUUID) {
        return Bukkit.getOfflinePlayer(playerUUID);
    }

    public static SimpleCommands getInstance() {
        if (instance == null) {
            log.info(ANSI_RED + "SimpleCommands.java -> Instance not exist" + ANSI_RESET);
        }
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        guiListener.init();
        createClasses();
        initClasses();
    }

    @Override
    public void onDisable() {
        this.guiListener.disable();
        if (instance == this) instance = null;
    }

    public void createClasses() {
        configsManager = new ConfigsManager();
        permissions = new Permissions();
        listenersManager = new ListenersManager();
        simpleCommandManager = new SimpleCommandManager();
        SQLmanager = new SQLManager();
    }

    public void initClasses() {
        configsManager.init();
        SQLmanager.init();
        listenersManager.init();
        simpleCommandManager.init();
    }

    public GuiListener getGuiListener() {
        return guiListener;
    }
}
