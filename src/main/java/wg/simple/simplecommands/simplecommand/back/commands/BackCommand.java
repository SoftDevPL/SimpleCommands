package wg.simple.simplecommands.simplecommand.back.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.simplecommand.back.events.PlayerBackEvent;
import wg.simple.simplecommands.simplecommand.back.listeners.BackManager;
import wg.simple.simplecommands.simplecommand.rtp.listeners.RtpManager;
import wg.simple.simplecommands.simplecommand.tp.events.PlayerStartsTeleportEvent;


public class BackCommand implements CommandExecutor, Listener {
    private final LanguageConfig languageConfig;
    private final BackManager backManager;

    public BackCommand(SimpleCommands plugin) {
        this.languageConfig = plugin.configsManager.languageConfig;
        this.backManager = plugin.listenersManager.backManager;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location loc = backManager.getLastDeathLocation(player.getUniqueId());
            if (loc == null) {
                player.sendMessage(languageConfig.getPlayerNotDied());
                return true;
            }
            Bukkit.getServer().getPluginManager().callEvent(new PlayerStartsTeleportEvent(player,
                    () -> Bukkit.getPluginManager().callEvent(new PlayerBackEvent(player, loc))));
        } else {
            sender.sendMessage(languageConfig.getOnlyPlayerCanExecuteCommand());
        }
        return true;
    }
}
