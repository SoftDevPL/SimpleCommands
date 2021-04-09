package wg.simple.simplecommands.simplecommand.warps.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.managers.CommandsManager;
import wg.simple.simplecommands.simplecommand.warps.Warp;
import wg.simple.simplecommands.simplecommand.warps.events.RemoveWarpEvent;

public class RemoveWarpCommand extends WarpTabCompleter implements CommandExecutor, Listener {

    public RemoveWarpCommand(PluginCommand command) {
        super(command);
        SimpleCommands plugin = SimpleCommands.getInstance();
        command.setExecutor(this);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            String warpName = args[0];
            Warp warpToRemove = warpManager.getWarpByName(warpName);
            if (warpToRemove == null) {
                sender.sendMessage(languageConfig.getWarpNotExists());
                return true;
            }
            Bukkit.getPluginManager().callEvent(new RemoveWarpEvent(warpToRemove, sender));
            return true;
        }
        sender.sendMessage(CommandsManager.getDescription(label, command));
        return true;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void successfulMessage(RemoveWarpEvent event) {
        event.getExecutor().sendMessage(languageConfig.getWarpRemoved());
    }
}
