package wg.simple.simplecommands.simplecommand.homes.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.managers.CommandsManager;
import wg.simple.simplecommands.managers.Permissions;
import wg.simple.simplecommands.simplecommand.homes.Home;
import wg.simple.simplecommands.simplecommand.homes.events.PlayerTeleportsToHomeEvent;
import wg.simple.simplecommands.simplecommand.tp.events.PlayerStartsTeleportEvent;


public class HomeCommand extends HomeTabCompleter implements CommandExecutor, Listener {
    private final LanguageConfig languageConfig;
    public Permissions permissions;

    public HomeCommand(PluginCommand command) {
        super(command);
        command.setExecutor(this);
        SimpleCommands plugin = SimpleCommands.getInstance();
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
                    player.sendMessage(languageConfig.getPlayerDontHaveThatHome());
                    return true;
                }
                Bukkit.getServer().getPluginManager().callEvent(new PlayerStartsTeleportEvent(player,
                        () -> Bukkit.getServer().getPluginManager().callEvent(new PlayerTeleportsToHomeEvent(player, home))));
                return true;
            }
            sender.sendMessage(CommandsManager.getDescription(label, command));
        } else {
            sender.sendMessage(languageConfig.getOnlyPlayerCanExecuteCommand());
        }
        return true;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void teleportMessage(PlayerTeleportsToHomeEvent event) {
        if (event.isCancelled()) {
            event.getPlayer().sendMessage(languageConfig.getUnableToTeleport());
        } else {
            event.getPlayer().sendMessage(languageConfig.getSuccessfullyTeleport());
        }
    }
}
