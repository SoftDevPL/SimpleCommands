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
import wg.simple.simplecommands.managers.Permissions;
import wg.simple.simplecommands.simplecommand.homes.Home;
import wg.simple.simplecommands.simplecommand.homes.events.PlayerRemoveHomeEvent;

public class DelHomeCommand extends HomeTabCompleter implements CommandExecutor, Listener, TabCompleter {
    private final LanguageConfig languageConfig;
    public Permissions permissions;

    public DelHomeCommand(PluginCommand command) {
        super(command);
        SimpleCommands plugin = SimpleCommands.getInstance();
        command.setExecutor(this);
        permissions = plugin.permissions;
        this.languageConfig = plugin.configsManager.languageConfig;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 1) {
                String homeName = args[0];
                Home home = homeManager.getPlayerHome(player.getUniqueId(), homeName);
                if (home == null) {
                    player.sendMessage(languageConfig.getNoSuchHome(homeName));
                    return true;
                }
                Bukkit.getServer().getPluginManager().callEvent(new PlayerRemoveHomeEvent(player, home));
                return true;
            }
        } else {
            sender.sendMessage(languageConfig.getOnlyPlayerCanExecuteCommand());
            return true;
        }
        sender.sendMessage(CommandsManager.getDescription(label, command));
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
