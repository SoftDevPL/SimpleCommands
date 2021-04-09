package wg.simple.simplecommands.simplecommand.spawns.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.simplecommand.spawns.events.RemoveHubEvent;
import wg.simple.simplecommands.simplecommand.spawns.listeners.SpawnsManager;

public class RemoveHubCommand implements CommandExecutor {
    private final LanguageConfig languageConfig;
    private final SpawnsManager spawnsManager;

    public RemoveHubCommand(SimpleCommands plugin) {
        this.languageConfig = plugin.configsManager.languageConfig;
        this.spawnsManager = plugin.listenersManager.spawnsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Location hub = spawnsManager.getHub();
        if (hub == null) {
            sender.sendMessage(languageConfig.getHubNotExists());
            return true;
        }
        Bukkit.getPluginManager().callEvent(new RemoveHubEvent(sender, hub));
        return true;
    }
}
