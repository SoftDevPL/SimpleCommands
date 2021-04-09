package wg.simple.simplecommands.simplecommand.msg.comands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.managers.CommandsManager;
import wg.simple.simplecommands.simplecommand.msg.events.PlayerUnBlockPrivateMessageEvent;

public class UnBlockCommand implements CommandExecutor {

    private final LanguageConfig languageConfig;
    private final SimpleCommands mainAD;

    public UnBlockCommand(SimpleCommands mainAD) {
        this.mainAD = mainAD;
        this.languageConfig = mainAD.configsManager.languageConfig;
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
        Player commandSender = (Player) sender;
        Player playerToUnblock = Bukkit.getPlayer(args[0]);
        this.mainAD.getServer().getPluginManager().callEvent(new PlayerUnBlockPrivateMessageEvent(commandSender, playerToUnblock));
        return true;
    }
}
