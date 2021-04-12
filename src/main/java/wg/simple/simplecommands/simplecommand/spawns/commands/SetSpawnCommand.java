package wg.simple.simplecommands.simplecommand.spawns.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.simplecommand.spawns.events.PlayerSetSpawnEvent;
import wg.simple.simplecommands.simplecommand.spawns.listeners.SpawnsManager;


public class SetSpawnCommand implements CommandExecutor {
    private final LanguageConfig languageConfig;
    private final SpawnsManager spawnsManager;

    public SetSpawnCommand(SimpleCommands plugin) {
        this.languageConfig = plugin.configsManager.languageConfig;
        this.spawnsManager = plugin.listenersManager.spawnsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(languageConfig.getOnlyPlayerCanExecuteCommand());
            return true;
        }
        Player player = (Player) sender;
        Location previousSpawn = spawnsManager.getSpawn(player.getLocation().getWorld().getUID());
        Bukkit.getPluginManager().callEvent(new PlayerSetSpawnEvent(player, previousSpawn, player.getLocation()));
        return true;
    }
}
