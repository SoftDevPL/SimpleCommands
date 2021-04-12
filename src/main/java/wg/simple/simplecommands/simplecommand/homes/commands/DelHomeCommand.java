package wg.simple.simplecommands.simplecommand.homes.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.managers.CommandsManager;
import wg.simple.simplecommands.simplecommand.homes.Home;
import wg.simple.simplecommands.simplecommand.homes.events.PlayerRemoveHomeEvent;

public class DelHomeCommand extends HomeTabCompleter implements CommandExecutor, Listener, TabCompleter {
    private final LanguageConfig languageConfig;

    public DelHomeCommand(PluginCommand command) {
        super(command);
        SimpleCommands plugin = SimpleCommands.getInstance();
        command.setExecutor(this);
        this.languageConfig = plugin.configsManager.languageConfig;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(languageConfig.getOnlyPlayerCanExecuteCommand());
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(CommandsManager.getDescription(label, command));
            return true;
        }
        Player player = (Player) sender;
        Home home = homeManager.getPlayerHome(player.getUniqueId(), args[0]);
        if (home == null) {
            player.sendMessage(languageConfig.getNoSuchHome(args[0]));
            return true;
        }
        Bukkit.getServer().getPluginManager().callEvent(new PlayerRemoveHomeEvent(player, home));
        return true;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void removingMessage(PlayerRemoveHomeEvent event) {
        if (event.isCancelled()) {
            event.getPlayer().sendMessage(languageConfig.getUnableToRemoveHome());
        } else {
            event.getPlayer().sendMessage(languageConfig.getSuccessfullyRemoveHome());
        }
    }
}
