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
import wg.simple.simplecommands.simplecommand.homes.Home;
import wg.simple.simplecommands.simplecommand.homes.events.PlayerTeleportsToHomeEvent;
import wg.simple.simplecommands.simplecommand.tp.events.PlayerStartsTeleportEvent;

public class HomeCommand extends HomeTabCompleter implements CommandExecutor, Listener {
    private final LanguageConfig languageConfig;

    public HomeCommand(PluginCommand command) {
        super(command);
        command.setExecutor(this);
        SimpleCommands plugin = SimpleCommands.getInstance();
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
            player.sendMessage(languageConfig.getPlayerDontHaveThatHome());
            return true;
        }
        Bukkit.getServer().getPluginManager().callEvent(new PlayerStartsTeleportEvent(player,
                () -> Bukkit.getServer().getPluginManager().callEvent(new PlayerTeleportsToHomeEvent(player, home))));
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
