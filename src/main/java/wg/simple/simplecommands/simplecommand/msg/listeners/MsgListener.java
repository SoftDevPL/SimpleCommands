package wg.simple.simplecommands.simplecommand.msg.listeners;

import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.fileManager.sql.sqlUtils.databasescommands.AdminGuiDatabase;
import wg.simple.simplecommands.managers.ChatManager;
import wg.simple.simplecommands.managers.Permissions;
import wg.simple.simplecommands.simplecommand.msg.events.PlayerBlockPrivateMassagerEvent;
import wg.simple.simplecommands.simplecommand.msg.events.PlayerSentPrivateMessageEvent;
import wg.simple.simplecommands.simplecommand.msg.events.PlayerUnBlockPrivateMessageEvent;

import java.util.*;

public class MsgListener implements Listener {

    public final Map<UUID, PrivateMessageRequest> requestMap = new HashMap<>();
    private final Map<UUID, Boolean> sentMessages = new HashMap<>();
    public LanguageConfig languageConfig;
    private List<PrivateMessageRequest> messageRequests = new ArrayList<>();
    @Getter
    private SimpleCommands mainAD;
    private Permissions permissions;
    private ChatManager chatManager;
    private AdminGuiDatabase adminGuiDatabase;

    public void init() {
        this.mainAD = SimpleCommands.getInstance();
        this.chatManager = this.mainAD.listenersManager.chatManager;
        this.adminGuiDatabase = this.mainAD.SQLmanager.database;
        this.messageRequests = this.adminGuiDatabase.getBlockedPlayers();
        this.permissions = this.mainAD.permissions;
        this.mainAD.getServer().getPluginManager().registerEvents(this, this.mainAD);
        this.languageConfig = this.mainAD.configsManager.languageConfig;
    }

    private void updateBlockedPlayerList() {
        this.messageRequests.clear();
        this.messageRequests = this.adminGuiDatabase.getBlockedPlayers();
    }

    private void displayMessagesToAdmin(Player player, Player playerToReply, String message) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission(permissions.seePrivateChat) && !players.equals(player) && !players.equals(playerToReply)) {
                players.sendMessage(languageConfig.getPrivateChatAdminCanSeePrivateMessagesPlayerSentToPlayer(player.getName(), playerToReply.getName(), message));
            }
        }
    }

    private void displayBlocksToAdmin(Player player, Player blockedPlayer) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission(permissions.seePrivateChat) && !players.equals(player) && !players.equals(blockedPlayer)) {
                players.sendMessage(languageConfig.getPrivateChatAdminCanSeePrivateMessagesPlayerBlockedByPlayer(player.getName(), blockedPlayer.getName()));
            }
        }
    }

    private void displayUnblocksToAdmin(Player player, Player blockedPlayer) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission(permissions.seePrivateChat) && !players.equals(player) && !players.equals(blockedPlayer)) {
                players.sendMessage(languageConfig.getPrivateChatAdminCanSeePrivateMessagesPlayerUnBlockedByPlayer(player.getName(), blockedPlayer.getName()));
            }
        }
    }

    @EventHandler
    private void playerUnBlockReplying(PlayerUnBlockPrivateMessageEvent event) {
        PrivateMessageRequest messageRequest = new PrivateMessageRequest(event.getPlayer(), event.getPlayerToUnblock());
        if (messageRequests.contains(messageRequest)) {
            displayUnblocksToAdmin(event.getPlayer(), event.getPlayerToUnblock());
            this.adminGuiDatabase.deleteBlockedPlayer(event.getPlayerToUnblock().getUniqueId());
            event.getPlayerToUnblock().sendMessage(languageConfig.getPrivateChatUnblockCommandYouHaveBeenUnBlocked(event.getPlayer().getName()));
            event.getPlayer().sendMessage(languageConfig.getPrivateChatUnblockCommandSuccessfullyUnblocked(event.getPlayerToUnblock().getName()));
            updateBlockedPlayerList();
        } else {
            event.getPlayer().sendMessage(languageConfig.getPrivateChatYouCanNotUnblockTwice());
        }
    }

    @EventHandler
    private void playerBlockReplying(PlayerBlockPrivateMassagerEvent event) {
        PrivateMessageRequest messageRequest = new PrivateMessageRequest(event.getPlayer(), event.getBlockedPlayer());
        if (!messageRequests.contains(messageRequest)) {
            displayBlocksToAdmin(event.getPlayer(), event.getBlockedPlayer());
            this.adminGuiDatabase.insertIntoBlockedMessagesTable(event.getPlayer().getUniqueId(), event.getBlockedPlayer().getUniqueId());
            event.getPlayer().sendMessage(languageConfig.getPrivateChatBlockCommandYouBlocked(event.getBlockedPlayer().getName()));
            event.getBlockedPlayer().sendMessage(languageConfig.getPrivateChatBlockCommandYouAreBlockedBy(event.getPlayer().getName()));
            this.requestMap.remove(event.getPlayer().getUniqueId());
            updateBlockedPlayerList();
        } else {
            event.getBlockedPlayer().sendMessage(languageConfig.getPrivateChatBlockCommandYouAreBlocked());
        }
    }

    private void prepareMessageForBlockedPlayer(Player player, Player receiver, String message) {
        PrivateMessageRequest messageRequest = new PrivateMessageRequest(player, receiver);
        if (messageRequests.contains(messageRequest)) {
            receiver.sendMessage(languageConfig.getPrivateChatMsgCommandMessageForBlockedPlayerPlayerSentMessage(receiver.getName(), message) + "\n");
            receiver.sendMessage(languageConfig.getPrivateChatMsgCommandMessageForBlockedPlayerMessageToBlockPlayer());
        }
    }

    private void prepareMessageForNonBlockedPlayer(Player player, Player playerToReply, String message) {
        PrivateMessageRequest messageRequest = new PrivateMessageRequest(playerToReply, player);
        if (!messageRequests.contains(messageRequest)) {
            if (!requestMap.containsKey(playerToReply.getUniqueId())) {
                PrivateMessageRequest privateMessageRequest = new PrivateMessageRequest(player, playerToReply);
                requestMap.put(playerToReply.getUniqueId(), privateMessageRequest);
            }
            player.sendMessage(languageConfig.getPrivateChatMessageHasBeenSent());
            playerToReply.sendMessage(languageConfig.getPrivateChatMsgCommandMessageForNonBlockedPlayerPlayerSentMessage(player.getName(), message) + "\n");

            if (!sentMessages.containsKey(playerToReply.getUniqueId())) {
                sentMessages.put(playerToReply.getUniqueId(), true);
                TextComponent block = new TextComponent(SimpleCommands.convertColors(languageConfig.getPrivateChatInteractiveMessageMainMessage()));
                block.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(languageConfig.getPrivateChatInteractiveMessageHover()).create()));
                block.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/blockmsg"));
                playerToReply.spigot().sendMessage(new ComponentBuilder(block).create());
            }
        }
    }

    @EventHandler
    private void playerSentPrivateMessage(PlayerSentPrivateMessageEvent event) {
        PrivateMessageRequest messageRequest = new PrivateMessageRequest(event.getPlayerToReply(), event.getPlayer());
        if (!messageRequests.contains(messageRequest)) {
            event.getPlayer().sendMessage(languageConfig.getPrivateChatEnterMessageInChat());
            chatManager.setTask(event.getPlayer().getUniqueId(), (chatMessage, chatPlayer) -> {
                PrivateMessageRequest messageRequest2 = new PrivateMessageRequest(event.getPlayer(), event.getPlayerToReply());
                if (messageRequests.contains(messageRequest2)) {
                    prepareMessageForBlockedPlayer(event.getPlayer(), event.getPlayerToReply(), chatMessage);
                } else {
                    prepareMessageForNonBlockedPlayer(event.getPlayer(), event.getPlayerToReply(), chatMessage);
                }
                displayMessagesToAdmin(event.getPlayer(), event.getPlayerToReply(), chatMessage);
                return true;
            });
        } else {
            event.getPlayer().sendMessage(languageConfig.getPrivateChatBlockCommandYouAreBlocked());
            event.setCancelled(true);
        }
    }
}
