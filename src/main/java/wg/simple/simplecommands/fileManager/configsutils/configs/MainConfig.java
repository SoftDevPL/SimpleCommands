package wg.simple.simplecommands.fileManager.configsutils.configs;


import wg.simple.simplecommands.fileManager.configsutils.resourcesConfigGenerator.ConfigAccessor;

public class MainConfig extends ConfigAccessor {

    public String path = "Configurations" + ".";
    private int homeNormalLimit;
    private int homePremiumLimit;
    private boolean teleportingToSpawn;
    private boolean teleportingToHub;
    private boolean help;
    private boolean version;
    private boolean teleportWhenJoinFirst;
    private boolean alwaysTeleportToHubWhenJoin;
    private boolean plugins;
    private int teleportWaitTime;
    private int requestExpiryTime;
    private int teleportationRadius;

    public void init() {
        super.init("Config");
        this.teleportationRadius = getIntPath(path + "world.rtpTeleportationRadius");
        this.alwaysTeleportToHubWhenJoin = getBooleanPath(path + "world.alwaysTeleportToHubWhenJoin");
        this.teleportWhenJoinFirst = getBooleanPath(path + "world.teleportToHubIfJoinFirtTime");
        this.teleportingToHub = getBooleanPath(path + "world.teleportingToHubAfterDeath");
        this.teleportingToSpawn = getBooleanPath(path + "world.teleportingToSpawnAfterDeath");
        this.homeNormalLimit = getIntPath(path + "homes.normalHomeLimit");
        this.homePremiumLimit = getIntPath(path + "homes.premiumHomeLimit");
        this.plugins = getBooleanPath(path + "blockedCommands.plugins");
        this.version = getBooleanPath(path + "blockedCommands.version");
        this.help = getBooleanPath(path + "blockedCommands.help");
        this.teleportWaitTime = getIntPath(path + "tp.teleportWaitTime");
        this.requestExpiryTime = getIntPath(path + "tp.teleportExpiryTime");
    }

    public int getTeleportationRadius() {
        return teleportationRadius;
    }

    public int getTeleportWaitTime() {
        return teleportWaitTime;
    }

    public int getRequestExpiryTime() {
        return requestExpiryTime;
    }

    public boolean isTeleportWhenJoinFirst() {
        return teleportWhenJoinFirst;
    }

    public boolean isAlwaysTeleportToHubWhenJoin() {
        return alwaysTeleportToHubWhenJoin;
    }

    public boolean isTeleportingToSpawn() {
        return teleportingToSpawn;
    }

    public boolean isTeleportingToHub() {
        return teleportingToHub;
    }

    public int getHomeNormalLimit() {
        return homeNormalLimit;
    }

    public int getHomePremiumLimit() {
        return homePremiumLimit;
    }

    public boolean isHelp() {
        return help;
    }

    public boolean isVersion() {
        return version;
    }

    public boolean isPlugins() {
        return plugins;
    }
}
