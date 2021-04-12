package wg.simple.simplecommands.simplecommand.stockcommand;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.fileManager.configsutils.configs.MainConfig;
import wg.simple.simplecommands.managers.Permissions;

import java.sql.Struct;
import java.util.Arrays;
import java.util.stream.Stream;

public class HelpPermission implements Listener {

    public MainConfig mainConfig;
    public Permissions permissions;
    public LanguageConfig languageConfig;
    private String[] stockCommandList = {};
    private Boolean isHelp;
    private Boolean isVersion;
    private Boolean isPlugins;

    public void init() {
        SimpleCommands mainAD = SimpleCommands.getInstance();
        permissions = mainAD.permissions;
        mainAD.getServer().getPluginManager().registerEvents(this, mainAD);
        mainConfig = mainAD.configsManager.mainConfig;
        this.isHelp = mainConfig.isHelp();
        this.isVersion = mainConfig.isVersion();
        this.isPlugins = mainConfig.isPlugins();
        languageConfig = mainAD.configsManager.languageConfig;
        initStockCommandsList();
    }

    private void initStockCommandsList() {
        String[] helpCommandsList = isHelp ? new String[]{"/help", "/?"} : new String[]{};
        String[] versionCommandsList = isVersion ? new String[]{"/version", "/ver"} : new String[]{};
        String[] pluginsCommandList = isPlugins ? new String[]{"/plugins", "/pl"} : new String[]{};
        stockCommandList = Stream.concat(Arrays.stream(stockCommandList), Arrays.stream(helpCommandsList)).toArray(String[]::new);
        stockCommandList = Stream.concat(Arrays.stream(stockCommandList), Arrays.stream(versionCommandsList)).toArray(String[]::new);
        stockCommandList = Stream.concat(Arrays.stream(stockCommandList), Arrays.stream(pluginsCommandList)).toArray(String[]::new);
    }

    @EventHandler
    void BlockStockCommands(PlayerCommandPreprocessEvent event) {
        if (!event.getPlayer().hasPermission(permissions.helpPermission)) {
            for (String badWord : stockCommandList) {
                if (event.getMessage().toLowerCase().contains(badWord)) {
                    event.getPlayer().sendMessage(languageConfig.getMinecraftCommandsNoPerm());
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
