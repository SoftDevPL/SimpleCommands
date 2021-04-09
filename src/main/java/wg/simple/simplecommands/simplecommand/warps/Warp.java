package wg.simple.simplecommands.simplecommand.warps;

import org.bukkit.Location;

import java.util.UUID;

public class Warp {
    private final UUID warpUUID;
    private Location location;
    private String name;

    public Warp(Location location, String name) {
        this.location = location;
        this.name = name;
        this.warpUUID = UUID.randomUUID();
    }

    public Warp(Location location, String name, UUID warpUUID) {
        this.warpUUID = warpUUID;
        this.location = location;
        this.name = name;
    }

    public UUID getWarpUUID() {
        return warpUUID;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
