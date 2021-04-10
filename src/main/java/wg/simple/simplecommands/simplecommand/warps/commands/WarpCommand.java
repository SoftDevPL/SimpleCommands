package wg.simple.simplecommands.simplecommand.warps.commands;

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
import wg.simple.simplecommands.managers.CommandsManager;
import wg.simple.simplecommands.simplecommand.tp.events.PlayerStartsTeleportEvent;
import wg.simple.simplecommands.simplecommand.warps.Warp;
import wg.simple.simplecommands.simplecommand.warps.events.PlayerWarpEvent;

public class WarpCommand extends WarpTabCompleter implements CommandExecutor, Listener {

    public WarpCommand(PluginCommand command) {
        super(command);
        command.setExecutor(this);
        SimpleCommands plugin = SimpleCommands.getInstance();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 1) {
                String warpName = args[0];
                Warp warpToTeleport = warpManager.getWarpByName(warpName);
                if (warpToTeleport == null) {
                    player.sendMessage(languageConfig.getWarpNotExists());
                    return true;
                }
                Bukkit.getPluginManager().callEvent(new PlayerStartsTeleportEvent(player, () -> Bukkit.getPluginManager().callEvent(
                        new PlayerWarpEvent(player, player.getLocation(), warpToTeleport.getLocation(), warpToTeleport))));
            } else {
                player.sendMessage(languageConfig.getWarpBadArgs());
                sender.sendMessage(CommandsManager.getDescription(label, command));
            }
        } else {
            sender.sendMessage(languageConfig.getOnlyPlayerCanExecuteCommand());
        }
        return true;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void successfulMessage(PlayerWarpEvent event) {
        event.getPlayer().sendMessage(languageConfig.getWarpTeleported());
    }
}
