package wg.simple.simplecommands.simplecommand.spawns.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.simplecommand.spawns.events.PlayerRemoveSpawnEvent;
import wg.simple.simplecommands.simplecommand.spawns.listeners.SpawnsManager;

public class RemoveSpawnCommand implements CommandExecutor {
    private final LanguageConfig languageConfig;
    private final SpawnsManager spawnsManager;

    public RemoveSpawnCommand(SimpleCommands plugin) {
        this.languageConfig = plugin.configsManager.languageConfig;
        this.spawnsManager = plugin.listenersManager.spawnsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location spawn = spawnsManager.getSpawn(player.getLocation().getWorld().getUID());
            if (spawn == null) {
                player.sendMessage(languageConfig.getSpawnNotExists());
                return true;
            }
            Bukkit.getPluginManager().callEvent(new PlayerRemoveSpawnEvent(player, spawn));
        } else {
            sender.sendMessage(languageConfig.getOnlyPlayerCanExecuteCommand());
        }
        return true;
    }
}
