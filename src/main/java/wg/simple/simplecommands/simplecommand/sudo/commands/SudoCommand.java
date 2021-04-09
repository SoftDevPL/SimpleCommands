package wg.simple.simplecommands.simplecommand.sudo.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.managers.CommandsManager;
import wg.simple.simplecommands.managers.Permissions;
import wg.simple.simplecommands.simplecommand.sudo.events.SudoEvent;

public class SudoCommand implements CommandExecutor {
    public final Permissions permissions;
    private final LanguageConfig languageConfig;

    public SudoCommand(SimpleCommands plugin) {
        this.languageConfig = plugin.configsManager.languageConfig;
        this.permissions = plugin.permissions;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 2) {
            Player playerToExecute = Bukkit.getPlayer(args[0]);
            if (playerToExecute == null) {
                sender.sendMessage(languageConfig.getPlayerIsOffline());
                return true;
            }
            String sudoLabel = args[1];
            String[] sudoArgs = new String[args.length - 2];
            System.arraycopy(args, 2, sudoArgs, 0, sudoArgs.length);
            Bukkit.getPluginManager().callEvent(new SudoEvent(sender, playerToExecute, sudoLabel, sudoArgs));
            return true;
        }
        sender.sendMessage(CommandsManager.getDescription(label, command));
        return true;
    }
}
