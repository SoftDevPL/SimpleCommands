package wg.simple.simplecommands.simplecommand.homes.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.managers.CommandsManager;
import wg.simple.simplecommands.simplecommand.homes.Home;
import wg.simple.simplecommands.simplecommand.homes.listeners.HomeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeTabCompleter implements TabCompleter {
    protected HomeManager homeManager;

    public HomeTabCompleter(PluginCommand command) {
        command.setTabCompleter(this);
        this.homeManager = SimpleCommands.getInstance().listenersManager.homeManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            if (args.length != 1) return new ArrayList<>(0);
            List<Home> homes = this.homeManager.getPlayerHomes(((Player) sender).getUniqueId());
            if (homes == null) return new ArrayList<>();
            return CommandsManager.mergeTabCompleter(
                    homes.stream().map(Home::getName).collect(Collectors.toList()), args[0]);
        }
        return null;
    }
}