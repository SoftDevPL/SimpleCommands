package wg.simple.simplecommands.simplecommand.tp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.managers.CommandsManager;
import wg.simple.simplecommands.simplecommand.tp.events.TpaEvent;

public class TpaCommand implements CommandExecutor {

    public LanguageConfig languageConfig;

    public TpaCommand(SimpleCommands mainAD) {
        languageConfig = mainAD.configsManager.languageConfig;
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
        Player playerToTeleport = Bukkit.getPlayer(args[0]);
        if (playerToTeleport == null) {
            player.sendMessage(languageConfig.getMesPlayerNotFound(args[0]));
            return true;
        }
        Bukkit.getServer().getPluginManager().callEvent(new TpaEvent(player, playerToTeleport));
        return true;
    }
}
