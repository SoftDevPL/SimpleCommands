package wg.simple.simplecommands.managers;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandsManager {

    public static List<String> mergeTabCompleter(List<String> currentTabCompleter, String[] arg, int nr) {
        if (nr >= arg.length) return new ArrayList<>(currentTabCompleter);
        else return mergeTabCompleter(currentTabCompleter, arg[nr]);
    }

    public static String getDescription(String label, Command command) {
        String[] strings = command.getUsage().split(" ", 2);
        String usage = strings.length < 2 ? "" : " " + command.getUsage().split(" ", 2)[1];
        return ChatColor.GREEN + "" + ChatColor.BOLD + "Use" + ChatColor.GRAY + " => " + ChatColor.GREEN + "" + ChatColor.BOLD + "/" + label + ChatColor.GRAY + usage;
    }

    public static List<String> mergeTabCompleter(List<String> currentTabCompleter, String arg) {
        final String lowArg = arg.toLowerCase();
        return currentTabCompleter.stream().filter(s -> s.toLowerCase().indexOf(lowArg) == 0).collect(Collectors.toList());
    }
}
