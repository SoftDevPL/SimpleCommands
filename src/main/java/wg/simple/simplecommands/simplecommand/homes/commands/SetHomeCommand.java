package wg.simple.simplecommands.simplecommand.homes.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.managers.CommandsManager;
import wg.simple.simplecommands.simplecommand.homes.Home;
import wg.simple.simplecommands.simplecommand.homes.events.PlayerSetHomeEvent;
import wg.simple.simplecommands.simplecommand.homes.listeners.HomeManager;

public class SetHomeCommand implements CommandExecutor, Listener {
    private final LanguageConfig languageConfig;
    private final HomeManager homeManager;

    public SetHomeCommand(SimpleCommands plugin) {
        this.languageConfig = plugin.configsManager.languageConfig;
        this.homeManager = plugin.listenersManager.homeManager;
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
        if (homeManager.hasAlreadyHome(player.getUniqueId(), args[0])) {
            player.sendMessage(languageConfig.getAlreadyHaveHomeNamed(args[0]));
            return true;
        }
        Bukkit.getServer().getPluginManager().callEvent(new PlayerSetHomeEvent(player,
                new Home(player.getUniqueId(), player.getLocation(), args[0])));
        return true;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void setHomeMessage(PlayerSetHomeEvent event) {
        event.getPlayer().sendMessage(languageConfig.getSetHomeSuccess());
    }
}
