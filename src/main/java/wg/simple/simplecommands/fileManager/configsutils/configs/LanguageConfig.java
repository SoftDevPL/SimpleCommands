package wg.simple.simplecommands.fileManager.configsutils.configs;


import wg.simple.simplecommands.fileManager.configsutils.resourcesConfigGenerator.ConfigAccessor;
import wg.simple.simplecommands.fileManager.configsutils.resourcesConfigGenerator.LiteralType;

public class LanguageConfig extends ConfigAccessor {

    private String playerHasNoHomes;
    private String mesPlayerNotFound;
    private String onlyPlayerCanExecuteCommand;
    private String playerIsOffline;
    private String alreadyHaveHomeNamed;
    private String setHomeSuccess;
    private String noSuchHome;
    private String successfullyRemoveHome;
    private String unableToRemoveHome;
    private String successfullyTeleport;
    private String unableToTeleport;
    private String minecraftCommandsNoPerm;
    private String infCommand1;
    private String infCommand2;
    private String infCommand3;
    private String infCommand4;
    private String infCommand5;
    private String infCommand6;
    private String infCommand7;
    private String infCommand8;
    private String infCommand9;
    private String infCommand10;
    private String yourHomes;
    private String playerNotDied;
    private String homeDir;
    private String youhaveNoHomes;
    private String playerDontHaveThatHome;
    private String noRequests;
    private String homeLimit;
    private String warpRemoved;
    private String warpHasBeenPlaced;
    private String warpExists;
    private String warpNotExists;
    private String warpTeleported;
    private String hubCreated;
    private String hubNotExists;
    private String rtpFaildtofindSafeLocation;
    private String spawnNotExists;
    private String spawnCreated;
    private String hubRemoved;
    private String hubChanged;
    private String spawnChanged;
    private String youMoved;
    private String teleportationMessage;
    private String spawnRemoved;
    private String youCantTeleportToYourSelf;
    private String requestSent;
    private String acceptMessage;
    private String denyMessage;
    private String requestAccepted;
    private String playerHasAlreadyHadTeleportationRequest;
    private String playerAcceptedRequest;
    private String teleportedByRequest;
    private String playerWantsToTeleport;
    private String playerHasBeenTeleported;
    private String teleportedByRequestDenied;
    private String playerDeniedYourRequest;
    private String warpBadArgs;
    private String warpListNoWarps;
    private String warpListWarpNamesHeader;
    private String warpListWarpNames;
    private String sudoSuccessful;
    private String sudoError;
    private String privateChatAdminCanSeePrivateMessagesPlayerBlockedByPlayer;
    private String privateChatAdminCanSeePrivateMessagesPlayerUnBlockedByPlayer;
    private String privateChatAdminCanSeePrivateMessagesPlayerSentToPlayer;
    private String privateChatInteractiveMessageMainMessage;
    private String privateChatInteractiveMessageHover;
    private String privateChatMessageHasBeenSent;
    private String privateChatEnterMessageInChat;
    private String privateChatMsgCommandMessageForBlockedPlayerPlayerSentMessage;
    private String privateChatMsgCommandMessageForBlockedPlayerMessageToBlockPlayer;
    private String privateChatMsgCommandMessageForNonBlockedPlayerPlayerSentMessage;
    private String privateChatBlockCommandYouBlocked;
    private String privateChatBlockCommandYouAreBlockedBy;
    private String privateChatBlockCommandYouAreBlocked;
    private String privateChatUnblockCommandYouHaveBeenUnBlocked;
    private String privateChatUnblockCommandSuccessfullyUnblocked;
    private String privateChatPlayerNotExists;
    private String privateChatYouCanNotBlockYourself;
    private String privateChatYouCanNotMsgYourself;
    private String privateChatYouCanNotUnblockTwice;

    public void init() {
        super.init("LanguageConfig");

        this.privateChatYouCanNotUnblockTwice = this.getStringPath("PrivateChat.youCanNotUnblockTwice");
        this.privateChatPlayerNotExists = this.getStringPath("PrivateChat.playerNotExists");
        this.privateChatYouCanNotBlockYourself = this.getStringPath("PrivateChat.youCanNotBlockYourself");
        this.privateChatYouCanNotMsgYourself = this.getStringPath("PrivateChat.youCanNotMsgYourself");
        this.privateChatAdminCanSeePrivateMessagesPlayerBlockedByPlayer = this.getStringPath("PrivateChat.adminCanSeePrivateMessages.playerBlockedByPlayer");
        this.privateChatAdminCanSeePrivateMessagesPlayerUnBlockedByPlayer = this.getStringPath("PrivateChat.adminCanSeePrivateMessages.playerUnBlockedByPlayer");
        this.privateChatAdminCanSeePrivateMessagesPlayerSentToPlayer = this.getStringPath("PrivateChat.adminCanSeePrivateMessages.playerSentToPlayer");
        this.privateChatInteractiveMessageMainMessage = this.getStringPath("PrivateChat.InteractiveMessage.mainMessage");
        this.privateChatInteractiveMessageHover = this.getStringPath("PrivateChat.InteractiveMessage.hover");
        this.privateChatMessageHasBeenSent = this.getStringPath("PrivateChat.messageHasBeenSent");
        this.privateChatEnterMessageInChat = this.getStringPath("PrivateChat.enterMessageInChat");
        this.privateChatMsgCommandMessageForBlockedPlayerPlayerSentMessage = this.getStringPath("PrivateChat.msgCommand.messageForBlockedPlayer.playerSentMessage");
        this.privateChatMsgCommandMessageForBlockedPlayerMessageToBlockPlayer = this.getStringPath("PrivateChat.msgCommand.messageForBlockedPlayer.messageToBlockPlayer");
        this.privateChatMsgCommandMessageForNonBlockedPlayerPlayerSentMessage = this.getStringPath("PrivateChat.msgCommand.messageForNonBlockedPlayer.playerSentMessage");
        this.privateChatBlockCommandYouBlocked = this.getStringPath("PrivateChat.blockCommand.youBlocked");
        this.privateChatBlockCommandYouAreBlockedBy = this.getStringPath("PrivateChat.blockCommand.youAreBlockedBy");
        this.privateChatBlockCommandYouAreBlocked = this.getStringPath("PrivateChat.blockCommand.youAreBlocked");
        this.privateChatUnblockCommandYouHaveBeenUnBlocked = this.getStringPath("PrivateChat.unblockCommand.youHaveBeenUnBlocked");
        this.privateChatUnblockCommandSuccessfullyUnblocked = this.getStringPath("PrivateChat.unblockCommand.successfullyUnblocked");
        this.playerHasNoHomes = this.getStringPath("General.commands.homes.playerHasNoHomes");
        this.sudoSuccessful = this.getStringPath("General.commands.sudoSuccessful");
        this.sudoError = this.getStringPath("General.commands.sudoError");
        this.warpListNoWarps = this.getStringPath("General.commands.warps.warpListNoWarps");
        this.warpListWarpNamesHeader = this.getStringPath("General.commands.warps.warpListWarpNamesHeader");
        this.warpListWarpNames = this.getStringPath("General.commands.warps.warpListWarpNames");
        this.warpBadArgs = this.getStringPath("General.commands.warps.warpBadArgs");
        this.teleportedByRequestDenied = this.getStringPath("General.commands.tpa.teleportedByRequestDenied");
        this.playerDeniedYourRequest = this.getStringPath("General.commands.tpa.playerDeniedYourRequest");
        this.playerHasBeenTeleported = this.getStringPath("General.commands.tpa.playerHasBeenTeleported");
        this.teleportedByRequest = this.getStringPath("General.commands.tpa.teleportedByRequest");
        this.playerWantsToTeleport = this.getStringPath("General.commands.tpa.playerWantsToTeleport");
        this.playerHasAlreadyHadTeleportationRequest = this.getStringPath("General.commands.tpa.playerHasAlreadyHadTeleportationRequest");
        this.youCantTeleportToYourSelf = this.getStringPath("General.commands.tpa.youCantTeleportToYourSelf");
        this.requestSent = this.getStringPath("General.commands.tpa.requestSent");
        this.acceptMessage = this.getStringPath("General.commands.tpa.hoverMessages.acceptMessage");
        this.denyMessage = this.getStringPath("General.commands.tpa.hoverMessages.denyMessage");
        this.requestAccepted = this.getStringPath("General.commands.tpa.requestAccepted");
        this.playerAcceptedRequest = this.getStringPath("General.commands.tpa.playerAcceptedRequest");
        this.youMoved = this.getStringPath("General.commands.adminGui.youMoved");
        this.teleportationMessage = this.getStringPath("General.commands.adminGui.teleportationMessage");
        this.spawnRemoved = this.getStringPath("General.commands.spawns.spawnRemoved");
        this.spawnChanged = this.getStringPath("General.commands.spawns.spawnChanged");
        this.hubRemoved = this.getStringPath("General.commands.hubs.hubRemoved");
        this.hubChanged = this.getStringPath("General.commands.hubs.hubChanged");
        this.spawnCreated = this.getStringPath("General.commands.spawns.spawnCreated");
        this.spawnNotExists = this.getStringPath("General.commands.spawns.spawnNotExists");
        this.hubCreated = this.getStringPath("General.commands.hubs.hubCreated");
        this.hubNotExists = this.getStringPath("General.commands.hubs.hubNotExists");
        this.warpTeleported = this.getStringPath("General.commands.warps.warpTeleported");
        this.warpHasBeenPlaced = this.getStringPath("General.commands.warps.warpHasBeenPlaced");
        this.warpExists = this.getStringPath("General.commands.warps.warpExists");
        this.warpRemoved = this.getStringPath("General.commands.warps.warpRemoved");
        this.warpNotExists = this.getStringPath("General.commands.warps.warpNotExists");
        this.homeLimit = this.getStringPath("General.commands.homes.homeLimit");
        this.noRequests = this.getStringPath("General.commands.tpa.noRequests");
        this.homeDir = this.getStringPath("General.commands.homes.homeDir");
        this.playerNotDied = this.getStringPath("General.commands.backCom.playerNotDied");
        this.unableToTeleport = this.getStringPath("General.commands.homes.unableToTeleport");
        this.successfullyTeleport = this.getStringPath("General.commands.homes.successfullyTeleported");
        this.successfullyRemoveHome = this.getStringPath("General.commands.homes.homeRemovedSuccessfully");
        this.unableToRemoveHome = this.getStringPath("General.commands.homes.homeRemovedFailed");
        this.noSuchHome = this.getStringPath("General.commands.homes.noSuchHome");
        this.setHomeSuccess = this.getStringPath("General.commands.homes.setHomeSuccess");
        this.alreadyHaveHomeNamed = this.getStringPath("General.commands.homes.alreadyHaveHome");
        this.mesPlayerNotFound = this.getStringPath("General.commands.playerNotFound");
        this.onlyPlayerCanExecuteCommand = this.getStringPath("General.commands.permissions.onlyPlayer");
        this.playerIsOffline = this.getStringPath("General.commands.adminGui.playerIsOffline");
        this.minecraftCommandsNoPerm = this.getStringPath("General.commands.adminGui.minecraftCommandsNoPerm");
        this.infCommand1 = this.getStringPath("General.commands.adminGui.infCommand.1");
        this.infCommand2 = this.getStringPath("General.commands.adminGui.infCommand.2");
        this.infCommand3 = this.getStringPath("General.commands.adminGui.infCommand.3");
        this.infCommand4 = this.getStringPath("General.commands.adminGui.infCommand.4");
        this.infCommand5 = this.getStringPath("General.commands.adminGui.infCommand.5");
        this.infCommand6 = this.getStringPath("General.commands.adminGui.infCommand.6");
        this.infCommand7 = this.getStringPath("General.commands.adminGui.infCommand.7");
        this.infCommand8 = this.getStringPath("General.commands.adminGui.infCommand.8");
        this.infCommand9 = this.getStringPath("General.commands.adminGui.infCommand.9");
        this.infCommand10 = this.getStringPath("General.commands.adminGui.infCommand.10");
        this.playerDontHaveThatHome = this.getStringPath("General.commands.homes.playerDontHaveThatHome");
        this.yourHomes = this.getStringPath("General.commands.homes.yourHomes");
        this.youhaveNoHomes = this.getStringPath("General.commands.homes.youhaveNoHomes");
        this.rtpFaildtofindSafeLocation = this.getStringPath("General.commands.rtp.rtpFaildtofindSafeLocation");
    }

    public String getPrivateChatYouCanNotUnblockTwice() {
        return privateChatYouCanNotUnblockTwice;
    }

    public String getPrivateChatPlayerNotExists(String player) {
        return privateChatPlayerNotExists.replaceAll(LiteralType.PLAYER_NAME, player);
    }

    public String getPrivateChatYouCanNotBlockYourself() {
        return privateChatYouCanNotBlockYourself;
    }

    public String getPrivateChatYouCanNotMsgYourself() {
        return privateChatYouCanNotMsgYourself;
    }

    public String getPrivateChatAdminCanSeePrivateMessagesPlayerBlockedByPlayer(String player1, String player2) {
        return privateChatAdminCanSeePrivateMessagesPlayerBlockedByPlayer.replaceAll(LiteralType.PLAYER_NAME_1, player1).replaceAll(LiteralType.PLAYER_NAME_2, player2);
    }

    public String getPrivateChatAdminCanSeePrivateMessagesPlayerUnBlockedByPlayer(String player1, String player2) {
        return privateChatAdminCanSeePrivateMessagesPlayerUnBlockedByPlayer.replaceAll(LiteralType.PLAYER_NAME_1, player1).replaceAll(LiteralType.PLAYER_NAME_2, player2);
    }

    public String getPrivateChatAdminCanSeePrivateMessagesPlayerSentToPlayer(String player1, String player2, String message) {
        return privateChatAdminCanSeePrivateMessagesPlayerSentToPlayer.replaceAll(LiteralType.PLAYER_NAME_1, player1).replaceAll(LiteralType.PLAYER_NAME_2, player2).replaceAll(LiteralType.MESSAGE, message);
    }

    public String getPrivateChatInteractiveMessageMainMessage() {
        return privateChatInteractiveMessageMainMessage;
    }

    public String getPrivateChatInteractiveMessageHover() {
        return privateChatInteractiveMessageHover;
    }

    public String getPrivateChatMessageHasBeenSent() {
        return privateChatMessageHasBeenSent;
    }

    public String getPrivateChatEnterMessageInChat() {
        return privateChatEnterMessageInChat;
    }

    public String getPrivateChatMsgCommandMessageForBlockedPlayerPlayerSentMessage(String player1, String message) {
        return privateChatMsgCommandMessageForBlockedPlayerPlayerSentMessage.replaceAll(LiteralType.PLAYER_NAME, player1).replaceAll(LiteralType.MESSAGE, message);
    }

    public String getPrivateChatMsgCommandMessageForBlockedPlayerMessageToBlockPlayer() {
        return privateChatMsgCommandMessageForBlockedPlayerMessageToBlockPlayer;
    }

    public String getPrivateChatMsgCommandMessageForNonBlockedPlayerPlayerSentMessage(String player1, String message) {
        return privateChatMsgCommandMessageForNonBlockedPlayerPlayerSentMessage.replaceAll(LiteralType.PLAYER_NAME, player1).replaceAll(LiteralType.MESSAGE, message);
    }

    public String getPrivateChatBlockCommandYouBlocked(String player) {
        return privateChatBlockCommandYouBlocked.replaceAll(LiteralType.PLAYER_NAME, player);
    }

    public String getPrivateChatBlockCommandYouAreBlockedBy(String player) {
        return privateChatBlockCommandYouAreBlockedBy.replaceAll(LiteralType.PLAYER_NAME, player);
    }

    public String getPrivateChatBlockCommandYouAreBlocked() {
        return privateChatBlockCommandYouAreBlocked;
    }

    public String getPrivateChatUnblockCommandYouHaveBeenUnBlocked(String player) {
        return privateChatUnblockCommandYouHaveBeenUnBlocked.replaceAll(LiteralType.PLAYER_NAME, player);
    }

    public String getPrivateChatUnblockCommandSuccessfullyUnblocked(String player) {
        return privateChatUnblockCommandSuccessfullyUnblocked.replaceAll(LiteralType.PLAYER_NAME, player);
    }

    public String getPlayerHasNoHomes() {
        return playerHasNoHomes;
    }

    public String getWarpListNoWarps() {
        return warpListNoWarps;
    }

    public String getWarpListWarpNamesHeader() {
        return warpListWarpNamesHeader;
    }

    public String getWarpListWarpNames(String worldName, String warpName, float x, float y, float z) {
        String mes1 = this.warpListWarpNames.replaceAll(LiteralType.WORLD_NAME, worldName);
        String mes2 = mes1.replaceAll(LiteralType.X, String.valueOf(x));
        String mes3 = mes2.replaceAll(LiteralType.Y, String.valueOf(y));
        String mes4 = mes3.replaceAll(LiteralType.WARP_NAME, warpName);
        return mes4.replaceAll(LiteralType.Z, String.valueOf(z));
    }

    public String getSudoSuccessful() {
        return sudoSuccessful;
    }

    public String getSudoError() {
        return sudoError;
    }

    public String getWarpBadArgs() {
        return warpBadArgs;
    }

    public String getTeleportedByRequestDenied() {
        return teleportedByRequestDenied;
    }

    public String getPlayerDeniedYourRequest() {
        return playerDeniedYourRequest;
    }


    public String getPlayerHasBeenTeleported() {
        return playerHasBeenTeleported;
    }

    public String getTeleportedByRequest() {
        return teleportedByRequest;
    }

    public String getPlayerWantsToTeleport(String player) {
        return playerWantsToTeleport.replaceAll(LiteralType.PLAYER_NAME, player);
    }

    public String getPlayerHasAlreadyHadTeleportationRequest() {
        return playerHasAlreadyHadTeleportationRequest;
    }

    public String getYouCantTeleportToYourSelf() {
        return youCantTeleportToYourSelf;
    }

    public String getRequestSent() {
        return requestSent;
    }

    public String getAcceptMessage() {
        return acceptMessage;
    }

    public String getDenyMessage() {
        return denyMessage;
    }

    public String getRequestAccepted() {
        return requestAccepted;
    }

    public String getPlayerAcceptedRequest() {
        return playerAcceptedRequest;
    }

    public String getYouMoved() {
        return youMoved;
    }

    public String getTeleportationMessage() {
        return teleportationMessage;
    }

    public String getSpawnChanged() {
        return spawnChanged;
    }

    public String getSpawnRemoved() {
        return spawnRemoved;
    }

    public String getHubRemoved() {
        return hubRemoved;
    }

    public String getHubChanged() {
        return hubChanged;
    }

    public String getSpawnCreated() {
        return spawnCreated;
    }

    public String getSpawnNotExists() {
        return spawnNotExists;
    }

    public String getHubCreated() {
        return hubCreated;
    }

    public String getHubNotExists() {
        return hubNotExists;
    }

    public String getWarpTeleported() {
        return warpTeleported;
    }

    public String getWarpHasBeenPlaced() {
        return warpHasBeenPlaced;
    }

    public String getWarpExists() {
        return warpExists;
    }

    public String getWarpRemoved() {
        return warpRemoved;
    }

    public String getHomeLimit() {
        return homeLimit;
    }

    public String getWarpNotExists() {
        return warpNotExists;
    }

    public String getNoRequests() {
        return noRequests;
    }

    public String getRtpFaildtofindSafeLocation() {
        return rtpFaildtofindSafeLocation;
    }

    public String getHomeDir(String homeName) {
        return this.homeDir.replaceAll(LiteralType.HOME_NAME, homeName);
    }

    public String getYouhaveNoHomes() {
        return youhaveNoHomes;
    }

    public String getYourHomes() {
        return yourHomes;
    }

    public String getPlayerDontHaveThatHome() {
        return playerDontHaveThatHome;
    }

    public String getPlayerNotDied() {
        return playerNotDied;
    }

    public String getInfCommand1() {
        return infCommand1;
    }

    public String getInfCommand2() {
        return infCommand2;
    }

    public String getInfCommand3() {
        return infCommand3;
    }

    public String getInfCommand4() {
        return infCommand4;
    }

    public String getInfCommand5() {
        return infCommand5;
    }

    public String getInfCommand6() {
        return infCommand6;
    }

    public String getInfCommand7() {
        return infCommand7;
    }

    public String getInfCommand8() {
        return infCommand8;
    }

    public String getInfCommand9() {
        return infCommand9;
    }

    public String getInfCommand10() {
        return infCommand10;
    }

    public String getMinecraftCommandsNoPerm() {
        return minecraftCommandsNoPerm;
    }

    public String getSuccessfullyTeleport() {
        return successfullyTeleport;
    }

    public String getUnableToTeleport() {
        return unableToTeleport;
    }

    public String getSuccessfullyRemoveHome() {
        return successfullyRemoveHome;
    }

    public String getUnableToRemoveHome() {
        return unableToRemoveHome;
    }

    public String getNoSuchHome(String homeName) {
        return noSuchHome.replaceAll(LiteralType.HOME_NAME, homeName);
    }

    public String getSetHomeSuccess() {
        return setHomeSuccess;
    }

    public String getAlreadyHaveHomeNamed(String homeName) {
        return alreadyHaveHomeNamed.replaceAll(LiteralType.HOME_NAME, homeName);
    }

    public String getPlayerIsOffline() {
        return playerIsOffline;
    }

    public String getOnlyPlayerCanExecuteCommand() {
        return onlyPlayerCanExecuteCommand;
    }

    public String getMesPlayerNotFound(String notFoundPlayerName) {
        return this.mesPlayerNotFound.replaceAll(LiteralType.PLAYER_NAME, notFoundPlayerName);
    }
}
