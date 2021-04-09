package wg.simple.simplecommands.simplecommand.tp.listeners;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.simplecommand.tp.events.PlayerStartsTeleportEvent;
import wg.simple.simplecommands.simplecommand.tp.events.TpDenyEvent;
import wg.simple.simplecommands.simplecommand.tp.events.TpaEvent;
import wg.simple.simplecommands.simplecommand.tp.events.TpacceptEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpaManager implements Listener {
    private final int schedulerDelay = 20;
    private final Map<UUID, TeleportRequest> requestsMap = new HashMap<>();
    public LanguageConfig languageConfig;
    private int tokenExpiryTime = 15000;
    private int teleportWaitTime = 100;
    private SimpleCommands mainAD;

    public void init() {
        this.mainAD = SimpleCommands.getInstance();
        languageConfig = mainAD.configsManager.languageConfig;
        this.mainAD.getServer().getPluginManager().registerEvents(this, this.mainAD);
        this.teleportWaitTime = this.mainAD.configsManager.mainConfig.getTeleportWaitTime();
        this.tokenExpiryTime = this.mainAD.configsManager.mainConfig.getRequestExpiryTime();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this.mainAD, () -> {

            for (HashMap.Entry<UUID, TeleportRequest> entry : requestsMap.entrySet()) {
                if (entry.getValue().getExpiredTime() <= System.currentTimeMillis()) {
                    requestsMap.remove(entry.getValue().getReceiver().getUniqueId());
                }
            }

        }, schedulerDelay, schedulerDelay);
    }

    private void addRequest(Player sender, Player playerToTeleport) {
        TeleportRequest request = new TeleportRequest(sender, playerToTeleport, System.currentTimeMillis() + this.tokenExpiryTime);
        requestsMap.put(playerToTeleport.getUniqueId(), request);
    }

    public boolean hasActiveRequest(UUID playerUUID) {
        return requestsMap.containsKey(playerUUID);
    }

    public Map<UUID, TeleportRequest> getAllRequests() {
        return requestsMap;
    }

    @EventHandler
    public void removingRequestWhenLeave(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        for (HashMap.Entry<UUID, TeleportRequest> entry : requestsMap.entrySet()) {
            if (entry.getKey().equals(uuid)) {
                requestsMap.remove(uuid);
                continue;
            }
            if (entry.getValue().getSender().getUniqueId().equals(uuid)) {
                requestsMap.remove(entry.getKey());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void addingRequest(TpaEvent event) {
        if (event.getPlayerToTeleport().getUniqueId().equals(event.getSender().getUniqueId())) {
            event.getPlayerToTeleport().sendMessage(languageConfig.getYouCantTeleportToYourSelf());
            return;
        }
        if (requestsMap.containsKey(event.getPlayerToTeleport().getUniqueId())) {
            event.getSender().sendMessage(languageConfig.getPlayerHasAlreadyHadTeleportationRequest());
            return;
        }
        addRequest(event.getSender(), event.getPlayerToTeleport());
        event.getSender().sendMessage(languageConfig.getRequestSent());

        TextComponent accept = new TextComponent(languageConfig.getAcceptMessage());
        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/tpaccept").create()));
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));

        TextComponent deny = new TextComponent(languageConfig.getDenyMessage());
        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/tpdeny").create()));
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny"));

        event.getPlayerToTeleport().sendMessage(languageConfig.getPlayerWantsToTeleport(event.getSender().getName()));
        event.getPlayerToTeleport().spigot().sendMessage(new ComponentBuilder(accept).append("\n").append(deny).create());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void acceptingRequests(TpacceptEvent event) {
        event.getPlayerWhoAccept().sendMessage(languageConfig.getRequestAccepted());
        event.getSender().sendMessage(languageConfig.getPlayerAcceptedRequest());
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.mainAD, ()
                -> Bukkit.getPluginManager().callEvent(new PlayerStartsTeleportEvent(event.getSender(), () -> {
            event.getSender().teleport(event.getPlayerWhoAccept());
            event.getSender().sendMessage(languageConfig.getTeleportedByRequest());
            event.getPlayerWhoAccept().sendMessage(languageConfig.getPlayerHasBeenTeleported());

        })), this.teleportWaitTime);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void denyRequests(TpDenyEvent event) {
        requestsMap.remove(event.getPlayerWhoDenied().getUniqueId());
        event.getPlayerWhoDenied().sendMessage(languageConfig.getTeleportedByRequestDenied());
        event.getSender().sendMessage(languageConfig.getPlayerDeniedYourRequest());
    }
}
