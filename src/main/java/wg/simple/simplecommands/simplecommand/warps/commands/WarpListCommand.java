package wg.simple.simplecommands.simplecommand.warps.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.simplecommand.warps.Warp;
import wg.simple.simplecommands.simplecommand.warps.listeners.WarpManager;

import java.util.List;

public class WarpListCommand implements CommandExecutor {
    private final WarpManager warpManager;
    private final LanguageConfig languageConfig;

    public WarpListCommand(SimpleCommands plugin) {
        this.warpManager = plugin.listenersManager.warpManager;
        this.languageConfig = plugin.configsManager.languageConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<Warp> warpList = warpManager.getAllWarps();
        sender.sendMessage(getDescriptionString(warpList));
        return true;
    }

    private String getDescriptionString(List<Warp> warpList) {
        if (warpList.isEmpty()) return this.languageConfig.getWarpListNoWarps();
        StringBuilder warps = new StringBuilder(languageConfig.getWarpListWarpNamesHeader() + ChatColor.RESET + "\n");
        for (Warp warp : warpList) {
            Location loc = warp.getLocation();
            warps.append(languageConfig.getWarpListWarpNames(
                    warp.getLocation().getWorld().getName(),
                    warp.getName(),
                    (float) loc.getX(),
                    (float) loc.getY(),
                    (float) loc.getZ()));
            warps.append("\n");
        }
        return warps.toString();
    }
}
