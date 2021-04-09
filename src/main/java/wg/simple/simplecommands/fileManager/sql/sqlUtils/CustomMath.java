package wg.simple.simplecommands.fileManager.sql.sqlUtils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class CustomMath {

    public static Vector getPosition(Location loc) {
        return new Vector(loc.getX(), loc.getY(), loc.getZ());
    }

    public static long calcMilliseconds(long secInMili, long minInMili, long hourInMili, long dayInMili, long weekInMili, long monthInMili, long yearInMili) {
        return (secInMili * 1000L) + (minInMili * 60000L) + (hourInMili * 3600000L) + (dayInMili * 86400000L) + (weekInMili * 604800017L) + (monthInMili * 2629800000L) + (yearInMili * 31557600000L);
    }
}
