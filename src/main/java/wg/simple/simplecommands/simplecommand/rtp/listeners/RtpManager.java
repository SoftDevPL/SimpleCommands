package wg.simple.simplecommands.simplecommand.rtp.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.simplecommand.rtp.events.PlayerRtpEvent;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RtpManager implements Listener {
    public static final int teleportTries = 10;
    private static final Set<Material> dangerousBlocks = new HashSet<>();

    static {
        dangerousBlocks.add(Material.LAVA);
        dangerousBlocks.add(Material.WATER);
        dangerousBlocks.add(Material.STATIONARY_LAVA);
        dangerousBlocks.add(Material.STATIONARY_WATER);
        dangerousBlocks.add(Material.CACTUS);
    }

    private LanguageConfig languageConfig;

    public static Location getRandomPosition(Location referenceLocation, int maxRadius) {
        World world = referenceLocation.getWorld();
        Random random = new Random();
        int counter = teleportTries;
        Block highestBlock;
        while (counter-- > 0) {
            highestBlock = world.getHighestBlockAt(referenceLocation.getBlockX() + random.nextInt(2 * maxRadius) - maxRadius,
                    referenceLocation.getBlockZ() + random.nextInt(2 * maxRadius) - maxRadius);
            if (!dangerousBlocks.contains(highestBlock.getType()) && !dangerousBlocks.contains(highestBlock.getRelative(BlockFace.DOWN).getType())) {
                return highestBlock.getLocation().add(0.5, 0, 0.5);
            }
        }
        return null;
    }

    public void init() {
        SimpleCommands plugin = SimpleCommands.getInstance();
        this.languageConfig = plugin.configsManager.languageConfig;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void teleporting(PlayerRtpEvent event) {
        event.getPlayer().teleport(event.getTo());
        event.getPlayer().sendMessage(languageConfig.getSuccessfullyTeleport());
    }

}
