package wg.simple.simplecommands.simplecommand.tp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.simplecommand.tp.events.TpDenyEvent;
import wg.simple.simplecommands.simplecommand.tp.listeners.TeleportRequest;
import wg.simple.simplecommands.simplecommand.tp.listeners.TpaManager;

import java.util.Map;
import java.util.UUID;

public class TpdenyCommand implements CommandExecutor {

    private final LanguageConfig languageConfig;
    private final TpaManager tpaManager;

    public TpdenyCommand(SimpleCommands mainAD) {
        languageConfig = mainAD.configsManager.languageConfig;
        this.tpaManager = mainAD.listenersManager.tpaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(languageConfig.getOnlyPlayerCanExecuteCommand());
            return true;
        }
        Player player = (Player) sender;
        Map<UUID, TeleportRequest> actualRequests = tpaManager.getAllRequests();
        if (!actualRequests.containsKey(player.getUniqueId())) {
            player.sendMessage(languageConfig.getNoRequests());
            return true;
        }
        Player playerToTeleport = actualRequests.get(player.getUniqueId()).getSender();
        Bukkit.getServer().getPluginManager().callEvent(new TpDenyEvent(playerToTeleport, player));
        return true;
    }
}
