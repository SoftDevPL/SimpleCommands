package wg.simple.simplecommands.fileManager.sql.sqlUtils.databasescommands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import wg.simple.simplecommands.fileManager.sql.sqlUtils.CustomSQLInterface;
import wg.simple.simplecommands.simplecommand.homes.Home;
import wg.simple.simplecommands.simplecommand.msg.listeners.PrivateMessageRequest;
import wg.simple.simplecommands.simplecommand.warps.Warp;

import java.sql.*;
import java.util.*;

public class AdminGuiDatabase extends CustomSQLInterface {

    private final String playerBackTable = "playersBack";
    private final String playersWarpTable = "warps";
    /**
     * Spawns
     */
    private final String spawnsTable = "spawnsTable";
    /**
     * hub
     */
    private final String hubTable = "hubTable";
    /**
     * players_table
     */
    String playerUUID = "playerUUID";
    /**
     * landsTableName
     */
    String worldUUID = "worldUUID";
    /**
     * Warp utils
     */
    String warpUUID = "warpUUID";
    String warpName = "warpName";
    /**
     * homes Table
     */
    String homesTable = "homesTable";
    String homeUUID = "homeUUID";
    String homeName = "homeName";
    String x = "x";
    String y = "y";
    String z = "z";
    String pitch = "pitch";
    String yaw = "yaw";
    /**
     * blockedPrivateMessages
     */
    String blockedPrivateMessagesTable = "blocked_private_messages_table";
    String playerWhoBlocked = "playerWhoBlocked";
    String blockedPlayer = "blockedPlayer";

    public void init() {
        super.init("SimpleCommands");
        CheckIfDatabaseExists();
        createHomesTables(homesTable, playerUUID, homeUUID, worldUUID, homeName, x, y, z, pitch, yaw);
        createBackTable(playerBackTable, playerUUID, worldUUID, x, y, z, pitch, yaw);
        createWarpTable(playersWarpTable, warpUUID, warpName, worldUUID, x, y, z, pitch, yaw);
        createSpawnTable(spawnsTable, worldUUID, x, y, z, pitch, yaw);
        createHubTable(hubTable, worldUUID, x, y, z, pitch, yaw);
        createPrivateMessagesTable(blockedPrivateMessagesTable, playerWhoBlocked, blockedPlayer);
    }

    public void createPrivateMessagesTable(String blockedPrivateMessagesTable, String playerWhoBlocked, String blockedPlayer) {
        String saleable = "CREATE TABLE IF NOT EXISTS " + blockedPrivateMessagesTable + " (" + playerWhoBlocked + " wibblewibble NOT NULL, " + blockedPlayer + " wibblewibble NOT NULL " + ");";
        createTable(saleable, this.databaseUrl);
    }

    private void createHubTable(String tableName, String worldUUID, String x, String y, String z, String pitch, String yaw) {
        String saleable = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + worldUUID + " wibblewibble NOT NULL, " + x + " REAL NOT NULL, " + y
                + " REAL NOT NULL, " + z + " REAL NOT NULL, " + pitch + " REAL NOT NULL, " + yaw + " REAL NOT NULL );";
        createTable(saleable, this.databaseUrl);
    }

    private void createSpawnTable(String tableName, String worldUUID, String x, String y, String z, String pitch, String yaw) {
        String saleable = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + worldUUID + " wibblewibble NOT NULL, " + x + " REAL NOT NULL, " + y
                + " REAL NOT NULL, " + z + " REAL NOT NULL, " + pitch + " REAL NOT NULL, " + yaw + " REAL NOT NULL );";
        createTable(saleable, this.databaseUrl);
    }

    private void createWarpTable(String tableName, String warpUUID, String warpName, String worldUUID, String x, String y, String z, String pitch, String yaw) {
        String saleable = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + warpUUID + " wibblewibble NOT NULL, " + warpName + " TEXT NOT NULL, " + worldUUID
                + " wibblewibble NOT NULL, " + x + " REAL NOT NULL, " + y
                + " REAL NOT NULL, " + z + " REAL NOT NULL, " + pitch + " REAL NOT NULL, " + yaw + " REAL NOT NULL );";
        createTable(saleable, this.databaseUrl);
    }

    private void createBackTable(String tableName, String playerUUID, String worldUUID, String x, String y, String z, String pitch, String yaw) {
        String saleable = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + playerUUID + " wibblewibble NOT NULL, " + worldUUID
                + " wibblewibble NOT NULL, " + x + " REAL NOT NULL, " + y
                + " REAL NOT NULL, " + z + " REAL NOT NULL, " + pitch + " REAL NOT NULL, " + yaw + " REAL NOT NULL );";
        createTable(saleable, this.databaseUrl);
    }

    private void createHomesTables(String tableName, String playerUUID, String homeUUID, String worldUUID, String homeName, String x, String y, String z, String pitch, String yaw) {
        String saleable = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + playerUUID + " wibblewibble NOT NULL, "
                + homeUUID + " wibblewibble NOT NULL, " + worldUUID
                + " wibblewibble NOT NULL, " + homeName + " TEXT NOT NULL, " + x + " REAL NOT NULL, " + y
                + " REAL NOT NULL, " + z + " REAL NOT NULL, " + pitch + " REAL NOT NULL, " + yaw + " REAL NOT NULL );";
        createTable(saleable, this.databaseUrl);
    }

    public void insertIntoBlockedMessagesTable(UUID playerWhoBlocked, UUID blockedPlayer) {
        String sql = "INSERT INTO  " + blockedPrivateMessagesTable + " (" + this.playerWhoBlocked + ", " + this.blockedPlayer + ") VALUES(?,?)";
        insertSomething(pstmt -> {
            pstmt.setString(1, playerWhoBlocked.toString());
            pstmt.setString(2, blockedPlayer.toString());
        }, sql);
    }

    public void insertIntoHubTable(String worldUUID, double x, double y, double z, float pitch, float yaw) {
        String sql = "INSERT INTO " + hubTable + " (" + this.worldUUID + ", " + this.x + ", " + this.y + ", " + this.z + ", " + this.pitch + ", " + this.yaw + ") VALUES(?,?,?,?,?,?)";
        insertSomething(pstmt -> {
            pstmt.setString(1, worldUUID);
            pstmt.setDouble(2, x);
            pstmt.setDouble(3, y);
            pstmt.setDouble(4, z);
            pstmt.setFloat(5, pitch);
            pstmt.setFloat(6, yaw);
        }, sql);
    }

    public void insertIntoSpawnsTable(String worldUUID, double x, double y, double z, float pitch, float yaw) {
        String sql = "INSERT INTO " + spawnsTable + " (" + this.worldUUID + ", " + this.x + ", " + this.y + ", " + this.z + ", " + this.pitch + ", " + this.yaw + ") VALUES(?,?,?,?,?,?)";
        insertSomething(pstmt -> {
            pstmt.setString(1, worldUUID);
            pstmt.setDouble(2, x);
            pstmt.setDouble(3, y);
            pstmt.setDouble(4, z);
            pstmt.setFloat(5, pitch);
            pstmt.setFloat(6, yaw);
        }, sql);
    }

    public void insertIntoWarpsTable(String warpUUID, String warpName, String worldUUID, double x, double y, double z, float pitch, float yaw) {
        String sql = "INSERT INTO " + playersWarpTable + " (" + this.warpUUID + ", " + this.warpName + ", " + this.worldUUID + ", " + this.x + ", " + this.y + ", " + this.z + ", " + this.pitch + ", " + this.yaw + ") VALUES(?,?,?,?,?,?,?,?)";
        insertSomething(pstmt -> {
            pstmt.setString(1, warpUUID);
            pstmt.setString(2, warpName);
            pstmt.setString(3, worldUUID);
            pstmt.setDouble(4, x);
            pstmt.setDouble(5, y);
            pstmt.setDouble(6, z);
            pstmt.setFloat(7, pitch);
            pstmt.setFloat(8, yaw);
        }, sql);
    }

    public void insertIntoBackTable(String playerUUID, String worldUUID, double x, double y, double z, float pitch, float yaw) {
        String sql = "INSERT INTO " + playerBackTable + " (" + this.playerUUID + ", " + this.worldUUID + ", " + this.x + ", " + this.y + ", " + this.z + ", " + this.pitch + ", " + this.yaw + ") VALUES(?,?,?,?,?,?,?)";
        insertSomething(pstmt -> {
            pstmt.setString(1, playerUUID);
            pstmt.setString(2, worldUUID);
            pstmt.setDouble(3, x);
            pstmt.setDouble(4, y);
            pstmt.setDouble(5, z);
            pstmt.setFloat(6, pitch);
            pstmt.setFloat(7, yaw);
        }, sql);
    }

    public void insertIntoHomesTable(String playerUUID, String homeUUID, String worldUUID, String homeName, double x, double y, double z, float pitch, float yaw) {
        String sql = "INSERT INTO " + this.homesTable + " (" + this.playerUUID + ", " + this.homeUUID + ", " + this.worldUUID + ", " + this.homeName + ", " + this.x + ", " + this.y + ", " + this.z + ", " + this.pitch + ", " + this.yaw + ") VALUES(?,?,?,?,?,?,?,?,?)";
        insertSomething(pstmt -> {
            pstmt.setString(1, playerUUID);
            pstmt.setString(2, homeUUID);
            pstmt.setString(3, worldUUID);
            pstmt.setString(4, homeName);
            pstmt.setDouble(5, x);
            pstmt.setDouble(6, y);
            pstmt.setDouble(7, z);
            pstmt.setFloat(8, pitch);
            pstmt.setFloat(9, yaw);
        }, sql);
    }

    public List<PrivateMessageRequest> getBlockedPlayers() {
        String sql = "SELECT * FROM " + blockedPrivateMessagesTable;
        return new Worker<List<PrivateMessageRequest>>().getSomething(
                rs -> {
                    List<PrivateMessageRequest> requests = new ArrayList<>();
                    while (rs.next()) {
                        UUID blockedPlayerUUID = UUID.fromString(rs.getString(blockedPlayer));
                        UUID playerWhoBlockedUUID = UUID.fromString(rs.getString(playerWhoBlocked));
                        PrivateMessageRequest privateMessageRequest = new PrivateMessageRequest(
                                Bukkit.getPlayer(playerWhoBlockedUUID),
                                Bukkit.getPlayer(blockedPlayerUUID)

                        );
                        requests.add(privateMessageRequest);
                    }
                    return requests;
                }, sql);
    }

    public List<Location> getHub() {
        String sql = "SELECT * FROM " + hubTable;
        return new Worker<List<Location>>().getSomething(rs -> {
           List<Location> locations = new ArrayList<>();
            while (rs.next()) {
                World world = Bukkit.getServer().getWorld(UUID.fromString(rs.getString(this.worldUUID)));
                Location location = new Location(
                        world,
                        rs.getDouble(this.x),
                        rs.getDouble(this.y),
                        rs.getDouble(this.z),
                        rs.getFloat(this.yaw),
                        rs.getFloat(this.pitch));
                locations.add(location);
            }
            return locations;
        }, sql);
    }

    public List<Location> getSpawns() {
        String sql = "SELECT * FROM " + spawnsTable;
        return new Worker<List<Location>>().getSomething(rs -> {
            List<Location> locations = new ArrayList<>();
            while (rs.next()) {
                World world = Bukkit.getServer().getWorld(UUID.fromString(rs.getString(this.worldUUID)));
                Location location = new Location(
                        world,
                        rs.getDouble(this.x),
                        rs.getDouble(this.y),
                        rs.getDouble(this.z),
                        rs.getFloat(this.yaw),
                        rs.getFloat(this.pitch));
                locations.add(location);
            }
            return locations;
        }, sql);
    }

    public List<Warp> getAllWarps() {
        String sql = "SELECT * FROM " + this.playersWarpTable;
        return new Worker<List<Warp>>().getSomething(rs -> {
            List<Warp> warpList = new ArrayList<>();
            while (rs.next()) {
                World world = Bukkit.getServer().getWorld(UUID.fromString(rs.getString(this.worldUUID)));
                Location location = new Location(
                        world,
                        rs.getDouble(this.x),
                        rs.getDouble(this.y),
                        rs.getDouble(this.z),
                        rs.getFloat(this.yaw),
                        rs.getFloat(this.pitch));
                Warp warp = new Warp(
                        location,
                        rs.getString(this.warpName),
                        UUID.fromString(rs.getString(this.warpUUID))
                );
                warpList.add(warp);
            }
            return warpList;
        }, sql);
    }

    public Map<UUID, Location> getAllBacks() {
        String sql = "SELECT * FROM " + this.playerBackTable;
        return new Worker<Map<UUID, Location>>().getSomething(rs -> {
            Map<UUID, Location> playerBacks = new HashMap<>();
            while (rs.next()) {
                World world = Bukkit.getServer().getWorld(UUID.fromString(rs.getString(this.worldUUID)));
                Location location = new Location(
                        world,
                        rs.getDouble(this.x),
                        rs.getDouble(this.y),
                        rs.getDouble(this.z),
                        rs.getFloat(this.yaw),
                        rs.getFloat(this.pitch));
                playerBacks.put(UUID.fromString(rs.getString(this.playerUUID)), location);
            }
            return playerBacks;
        }, sql);
    }

    public List<Home> getAllHomes() {
        String sql = "SELECT * FROM " + this.homesTable;
        return new Worker<List<Home>>().getSomething(rs -> {
            List<Home> playerHomes = new ArrayList<>();
            while (rs.next()) {
                World world = Bukkit.getServer().getWorld(UUID.fromString(rs.getString(this.worldUUID)));
                Location homeLocation = new Location(
                        world,
                        rs.getDouble(this.x),
                        rs.getDouble(this.y),
                        rs.getDouble(this.z),
                        rs.getFloat(this.yaw),
                        rs.getFloat(this.pitch));
               Home home = new Home(
                       UUID.fromString(rs.getString(this.playerUUID)),
                       homeLocation,
                       rs.getString(this.homeName),
                       UUID.fromString(rs.getString(this.homeUUID))

               );
                playerHomes.add(home);
            }
            return playerHomes;
        }, sql);
    }

    public void deleteBlockedPlayer(UUID blockedPlayer) {
        String sql = "DELETE FROM " + this.blockedPrivateMessagesTable + " WHERE " + this.blockedPlayer + " = " + "\"" + blockedPlayer.toString() + "\"";
        delete(sql);
    }

    public void deleteHub() {
        String sql = "DELETE FROM " + this.hubTable;
        delete(sql);
    }

    public void deleteSpawn(String spawnUUID) {
        String sql = "DELETE FROM " + this.spawnsTable + " WHERE " + this.worldUUID + " = " + "\"" + spawnUUID + "\"";
        delete(sql);
    }

    public void deleteSpawnByWorldUUID(String worldUUID) {
        String sql = "DELETE FROM " + this.spawnsTable + " WHERE " + this.worldUUID + " = " + "\"" + worldUUID + "\"";
        delete(sql);
    }


    public void deleteWarp(String warpUUID) {
        String sql = "DELETE FROM " + this.playersWarpTable + " WHERE " + this.warpUUID + " = " + "\"" + warpUUID + "\"";
        delete(sql);
    }

    public void deleteWarpByWorldUUID(String worldUUID) {
        String sql = "DELETE FROM " + this.playersWarpTable + " WHERE " + this.worldUUID + " = " + "\"" + worldUUID + "\"";
        delete(sql);
    }

    public void deleteBack(String playerUUID) {
        String sql = "DELETE FROM " + this.playerBackTable + " WHERE " + this.playerUUID + " = " + "\"" + playerUUID + "\"";
        delete(sql);
    }

    public void deleteBackByWorldUUID(String worldUUID) {
        String sql = "DELETE FROM " + this.playerBackTable + " WHERE " + this.worldUUID + " = " + "\"" + worldUUID + "\"";
        delete(sql);
    }

    public void deleteHome(String homeUUID) {
        String sql = "DELETE FROM " + this.homesTable + " WHERE " + this.homeUUID + " = " + "\"" + homeUUID + "\"";
        delete(sql);
    }

    public void deleteHomeByWorldUUID(String worldUUID) {
        String sql = "DELETE FROM " + this.homesTable + " WHERE " + this.worldUUID + " = " + "\"" + worldUUID + "\"";
        delete(sql);
    }

    public void deleteHubByWorldUUID(String worldUUID) {
        String sql = "DELETE FROM " + this.hubTable + " WHERE " + this.worldUUID + " = " + "\"" + worldUUID + "\"";
        delete(sql);
    }

    public void delete(String query) {
        try (Connection conn = AdminGuiDatabase.this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertSomething(DatabaseInsertion operation, String query) {
        try (Connection conn = AdminGuiDatabase.this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            operation.insert(pstmt);
            pstmt.executeUpdate();
            close(pstmt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTable(String query, String databaseUrl) {
        try (Connection conn = DriverManager.getConnection(databaseUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public interface DatabaseOperation<T> {
        T operate(ResultSet rs) throws SQLException;
    }


    public interface DatabaseInsertion<T> {
        void insert(PreparedStatement pstmt) throws SQLException;
    }

    public class Worker<T> {
        public T getSomething(DatabaseOperation<T> operation, String query) {
            T temp = null;
            try (Connection conn = AdminGuiDatabase.this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                ResultSet rs = pstmt.executeQuery();
                temp = operation.operate(rs);
                close(pstmt);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return temp;
        }
    }

}
