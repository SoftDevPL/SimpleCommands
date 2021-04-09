package wg.simple.simplecommands.simplecommand.warps.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.managers.CommandsManager;
import wg.simple.simplecommands.simplecommand.warps.Warp;
import wg.simple.simplecommands.simplecommand.warps.listeners.WarpManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WarpTabCompleter implements TabCompleter {
    protected final WarpManager warpManager;
    protected final LanguageConfig languageConfig;

    public WarpTabCompleter(PluginCommand command) {
        command.setTabCompleter(this);
        this.languageConfig = SimpleCommands.getInstance().configsManager.languageConfig;
        this.warpManager = SimpleCommands.getInstance().listenersManager.warpManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length != 1) return new ArrayList<>(0);
        return CommandsManager.mergeTabCompleter(this.warpManager.getAllWarps().stream()
                .map(Warp::getName).collect(Collectors.toList()), args[0]);
    }
}
