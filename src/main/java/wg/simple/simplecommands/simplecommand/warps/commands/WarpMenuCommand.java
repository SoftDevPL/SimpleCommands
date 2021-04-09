package wg.simple.simplecommands.simplecommand.warps.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import wg.simple.simplecommands.simplecommand.warps.Warp;
import wg.simple.simplecommands.simplecommand.warps.guis.SingleWarpGui;
import wg.simple.simplecommands.simplecommand.warps.guis.WarpsGui;

public class WarpMenuCommand extends WarpTabCompleter implements CommandExecutor {
    public WarpMenuCommand(PluginCommand command) {
        super(command);
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(languageConfig.getOnlyPlayerCanExecuteCommand());
            return true;
        }
        if (args.length == 0) {
            if (warpManager.getAllWarps().isEmpty()) {
                sender.sendMessage(languageConfig.getWarpListNoWarps());
                return true;
            }
            new WarpsGui(null).open((Player) sender);
            return true;
        } else {
            Warp warp = warpManager.getWarpByName(args[0]);
            if (warp == null) {
                sender.sendMessage(languageConfig.getWarpNotExists());
                return true;
            }
            new SingleWarpGui(warp, null).open((Player) sender);
        }
        return true;
    }
}
