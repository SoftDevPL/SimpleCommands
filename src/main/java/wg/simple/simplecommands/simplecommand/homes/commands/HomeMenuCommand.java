package wg.simple.simplecommands.simplecommand.homes.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.managers.CommandsManager;
import wg.simple.simplecommands.simplecommand.homes.Home;
import wg.simple.simplecommands.simplecommand.homes.guis.HomesGui;
import wg.simple.simplecommands.simplecommand.homes.listeners.HomeManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HomeMenuCommand implements CommandExecutor, TabCompleter {
    private final LanguageConfig lg;
    private final HomeManager homeManager;

    public HomeMenuCommand(PluginCommand cmd) {
        cmd.setExecutor(this);
        cmd.setTabCompleter(this);
        this.lg = SimpleCommands.getInstance().configsManager.languageConfig;
        this.homeManager = SimpleCommands.getInstance().listenersManager.homeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(lg.getOnlyPlayerCanExecuteCommand());
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(CommandsManager.getDescription(label, command));
            return true;
        }
        OfflinePlayer player = SimpleCommands.getOfflinePlayer(args[0]);
        if (player == null) {
            sender.sendMessage(lg.getMesPlayerNotFound(args[0]));
            return true;
        }
        List<Home> homeList = this.homeManager.getPlayerHomes(player.getUniqueId());
        if (homeList == null) {
            sender.sendMessage(lg.getPlayerHasNoHomes());
            return true;
        }
        new HomesGui(null, player.getUniqueId())
                .open((Player) sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return CommandsManager.mergeTabCompleter(Arrays.stream(Bukkit.getOfflinePlayers())
                .map(OfflinePlayer::getName).collect(Collectors.toList()), args[0]);
    }
}
