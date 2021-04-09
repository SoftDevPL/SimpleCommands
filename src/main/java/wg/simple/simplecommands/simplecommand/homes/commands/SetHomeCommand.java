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
import wg.simple.simplecommands.managers.Permissions;
import wg.simple.simplecommands.simplecommand.homes.Home;
import wg.simple.simplecommands.simplecommand.homes.events.PlayerSetHomeEvent;
import wg.simple.simplecommands.simplecommand.homes.listeners.HomeManager;

public class SetHomeCommand implements CommandExecutor, Listener {
    private final LanguageConfig languageConfig;
    private final HomeManager homeManager;
    public Permissions permissions;

    public SetHomeCommand(SimpleCommands plugin) {
        permissions = plugin.permissions;
        this.languageConfig = plugin.configsManager.languageConfig;
        this.homeManager = plugin.listenersManager.homeManager;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 1) {
                String homeName = args[0];
                if (homeManager.hasAlreadyHome(player.getUniqueId(), homeName)) {
                    player.sendMessage(languageConfig.getAlreadyHaveHomeNamed(homeName));
                    return true;
                }
                Bukkit.getServer().getPluginManager().callEvent(new PlayerSetHomeEvent(player,
                        new Home(player.getUniqueId(), player.getLocation(), homeName)));
                return true;
            }
            sender.sendMessage(CommandsManager.getDescription(label, command));
        } else {
            sender.sendMessage(languageConfig.getOnlyPlayerCanExecuteCommand());
        }
        return true;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void setHomeMessage(PlayerSetHomeEvent event) {
        event.getPlayer().sendMessage(languageConfig.getSetHomeSuccess());
    }
}
