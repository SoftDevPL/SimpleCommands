package wg.simple.simplecommands.simplecommand.msg.comands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.managers.CommandsManager;
import wg.simple.simplecommands.simplecommand.msg.events.PlayerSentPrivateMessageEvent;

public class MsgCommand implements CommandExecutor {

    private final SimpleCommands mainAD;
    public LanguageConfig languageConfig;

    public MsgCommand(SimpleCommands mainAD) {
        this.mainAD = mainAD;
        this.languageConfig = this.mainAD.configsManager.languageConfig;
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
        Player player = Bukkit.getPlayer(args[0]);
        if (player != null) {
            Player commandSender = (Player) sender;
            if (!player.equals(sender)) {
                Bukkit.getServer().getPluginManager().callEvent(new PlayerSentPrivateMessageEvent(commandSender, player));
            } else {
                sender.sendMessage(languageConfig.getPrivateChatYouCanNotMsgYourself());
            }
        } else {
            sender.sendMessage(languageConfig.getPrivateChatPlayerNotExists(args[0]));
        }
        return true;
    }
}
