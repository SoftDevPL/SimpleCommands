package wg.simple.simplecommands.simplecommand.msg.comands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.managers.CommandsManager;
import wg.simple.simplecommands.simplecommand.msg.events.PlayerBlockPrivateMassagerEvent;
import wg.simple.simplecommands.simplecommand.msg.listeners.MsgListener;
import wg.simple.simplecommands.simplecommand.msg.listeners.PrivateMessageRequest;

public class BlockCommand implements CommandExecutor {

    @Getter
    private final SimpleCommands mainAD;
    private final MsgListener msgListener;
    public LanguageConfig languageConfig;

    public BlockCommand(SimpleCommands mainAD) {
        this.mainAD = mainAD;
        this.msgListener = this.mainAD.listenersManager.msgListener;
        this.languageConfig = this.mainAD.configsManager.languageConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(languageConfig.getOnlyPlayerCanExecuteCommand());
            return true;
        }
        Player commandSender = (Player) sender;
        if (args.length == 0) {
            if (!msgListener.requestMap.containsKey(commandSender.getUniqueId())) {
                sender.sendMessage(CommandsManager.getDescription(label, command));
            } else {
                PrivateMessageRequest messageRequest = msgListener.requestMap.get(commandSender.getUniqueId());
                this.mainAD.getServer().getPluginManager().callEvent(new PlayerBlockPrivateMassagerEvent(messageRequest.getPlayerToReply(), messageRequest.getPlayerWhoSent()));
            }
            return true;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (player != null) {
            if (!player.equals(commandSender)) {
                this.mainAD.getServer().getPluginManager().callEvent(new PlayerBlockPrivateMassagerEvent(player, commandSender));
            } else {
                commandSender.sendMessage(languageConfig.getPrivateChatYouCanNotBlockYourself());
            }
        } else {
            sender.sendMessage(languageConfig.getPrivateChatPlayerNotExists(args[0]));
        }
        return true;
    }
}
