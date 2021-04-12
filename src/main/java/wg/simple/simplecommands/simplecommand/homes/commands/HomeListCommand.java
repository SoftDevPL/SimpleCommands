package wg.simple.simplecommands.simplecommand.homes.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.simplecommand.homes.Home;
import wg.simple.simplecommands.simplecommand.homes.listeners.HomeManager;

import java.util.List;

public class HomeListCommand implements CommandExecutor {
    private final LanguageConfig languageConfig;
    private final HomeManager homeManager;

    public HomeListCommand(PluginCommand command) {
        SimpleCommands plugin = SimpleCommands.getInstance();
        command.setExecutor(this);
        this.languageConfig = plugin.configsManager.languageConfig;
        this.homeManager = plugin.listenersManager.homeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(languageConfig.getOnlyPlayerCanExecuteCommand());
            return true;
        }
        Player player = (Player) sender;
        List<Home> homeList = homeManager.getPlayerHomes(player.getUniqueId());
        player.sendMessage(getHomesDescriptionString(homeList));
        return true;
    }

    private String getHomesDescriptionString(List<Home> homeList) {
        StringBuilder homeString = new StringBuilder(languageConfig.getYourHomes() + ChatColor.RESET);
        if (homeList == null || homeList.isEmpty())
            return languageConfig.getYouhaveNoHomes();
        for (Home home : homeList) {
            homeString.append("\n").append(languageConfig.getHomeDir(home));
        }
        return homeString.toString();
    }

}
