package wg.simple.simplecommands.simplecommand.warps.commands;

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
import wg.simple.simplecommands.simplecommand.warps.Warp;
import wg.simple.simplecommands.simplecommand.warps.events.PlayerSetWarpEvent;
import wg.simple.simplecommands.simplecommand.warps.listeners.WarpManager;

public class SetWarpCommand implements CommandExecutor, Listener {
    private final LanguageConfig languageConfig;
    private final WarpManager warpManager;

    public SetWarpCommand(SimpleCommands plugin) {
        this.languageConfig = plugin.configsManager.languageConfig;
        this.warpManager = plugin.listenersManager.warpManager;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 1) {
                String warpName = args[0];
                Warp warpToRemove = warpManager.getWarpByName(warpName);
                if (warpToRemove != null) {
                    sender.sendMessage(languageConfig.getWarpExists());
                    return true;
                }
                Bukkit.getPluginManager().callEvent(new PlayerSetWarpEvent(player, new Warp(player.getLocation(), warpName)));
                return true;
            }
            sender.sendMessage(CommandsManager.getDescription(label, command));
        } else {
            sender.sendMessage(languageConfig.getOnlyPlayerCanExecuteCommand());
        }
        return true;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void successfulMessage(PlayerSetWarpEvent event) {
        event.getPlayer().sendMessage(languageConfig.getWarpHasBeenPlaced());
    }
}
