package wg.simple.simplecommands.simplecommand.homes;

import org.bukkit.Location;

import java.util.UUID;

public class Home {
    private final UUID ownerUUID;
    private final UUID homeUUID;
    private Location homeLocation;
    private String name;

    public Home(UUID ownerUUID, Location homeLocation, String name) {
        this(ownerUUID, homeLocation, name, UUID.randomUUID());
    }

    public Home(UUID ownerUUID, Location homeLocation, String name, UUID homeUUID) {
        this.ownerUUID = ownerUUID;
        this.homeUUID = homeUUID;
        this.homeLocation = homeLocation;
        this.name = name;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public UUID getHomeUUID() {
        return homeUUID;
    }

    public Location getHomeLocation() {
        return homeLocation.clone();
    }

    public void setHomeLocation(Location homeLocation) {
        this.homeLocation = homeLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
