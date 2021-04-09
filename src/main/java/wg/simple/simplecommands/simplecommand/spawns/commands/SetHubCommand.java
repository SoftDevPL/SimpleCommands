package wg.simple.simplecommands.simplecommand.spawns.commands;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.simplecommand.spawns.events.PlayerSetHubEvent;
import wg.simple.simplecommands.simplecommand.spawns.listeners.SpawnsManager;

public class SetHubCommand implements CommandExecutor {
    private final LanguageConfig languageConfig;
    private final SpawnsManager spawnsManager;

    public SetHubCommand(SimpleCommands plugin) {
        this.languageConfig = plugin.configsManager.languageConfig;
        this.spawnsManager = plugin.listenersManager.spawnsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location hub = spawnsManager.getHub();
            Bukkit.getPluginManager().callEvent(new PlayerSetHubEvent(player, hub, player.getLocation()));
        } else {
            sender.sendMessage(languageConfig.getOnlyPlayerCanExecuteCommand());
        }
        return true;
    }
}
