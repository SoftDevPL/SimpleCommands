package wg.simple.simplecommands.simplecommand.rtp.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.fileManager.configsutils.configs.MainConfig;
import wg.simple.simplecommands.simplecommand.rtp.events.PlayerRtpEvent;
import wg.simple.simplecommands.simplecommand.rtp.listeners.RtpManager;
import wg.simple.simplecommands.simplecommand.tp.events.PlayerStartsTeleportEvent;


public class RtpCommand implements CommandExecutor {
    public static Integer defaultTeleportRadius;
    private final LanguageConfig languageConfig;

    public RtpCommand(SimpleCommands plugin) {
        initTeleportRadius(plugin.configsManager.mainConfig);
        this.languageConfig = plugin.configsManager.languageConfig;
    }

    private void initTeleportRadius(MainConfig mainConfig) {
        defaultTeleportRadius = mainConfig.getTeleportationRadius();
        if (defaultTeleportRadius == 0) {
            defaultTeleportRadius = 10;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(languageConfig.getOnlyPlayerCanExecuteCommand());
            return true;
        }
        Player player = (Player) sender;
        Location locToTeleport = RtpManager.getRandomPosition(player.getLocation(), defaultTeleportRadius);
        Location previousLocation = player.getLocation();
        if (locToTeleport == null) {
            player.sendMessage(languageConfig.getRtpFaildtofindSafeLocation());
            return true;
        }
        Bukkit.getPluginManager().callEvent(new PlayerStartsTeleportEvent(player, () -> Bukkit.getPluginManager().callEvent(new PlayerRtpEvent(player, previousLocation, locToTeleport))));
        return true;
    }
}
