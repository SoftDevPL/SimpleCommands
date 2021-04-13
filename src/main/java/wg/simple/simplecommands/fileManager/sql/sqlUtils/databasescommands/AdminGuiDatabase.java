package wg.simple.simplecommands.fileManager.sql.sqlUtils.databasescommands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import wg.simple.simplecommands.fileManager.sql.sqlUtils.CustomSQLInterface;
import wg.simple.simplecommands.simplecommand.homes.Home;
import wg.simple.simplecommands.simplecommand.msg.listeners.PrivateMessageRequest;
import wg.simple.simplecommands.simplecommand.warps.Warp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminGuiDatabase extends CustomSQLInterface {

    /**
     * Warpy
     */
    public static final Integer WARP_UUID = 0;
    /**
     * backs
     */
    public static final Integer PLAYER_UUID_BACK = 0;
    public static final Integer WORLD_BACK_UUID = 1;
    public static final Integer X_BACK = 2;
    public static final Integer Y_BACK = 3;
    public static final Integer Z_BACK = 4;
    public static final Integer PITCH_BACK = 5;
    public static final Integer YAW_BACK = 6;
    /**
     * Homes
     */
    public static final Integer PLAYER_HOME_UUID = 0;
    public static final Integer HOME_UUID = 1;
    public static final Integer WORLD_HOME_UUID = 2;
    public static final Integer HOME_NAME = 3;
    public static final Integer X = 4;
    public static final Integer Y = 5;
    public static final Integer Z = 6;
    public static final Integer PITCH = 7;
    public static final Integer YAW = 8;
    public static final Integer WARP_NAME = 1;
    public static final Integer WORLD_WARP_UUID = 2;
    public static final Integer X_WARP = 3;
    public static final Integer Y_WARP = 4;
    public static final Integer Z_WARP = 5;
    public static final Integer PITCH_WARP = 6;
    public static final Integer YAW_WARP = 7;
    /**
     * spawn
     */
    public static final Integer WORLD_SPAWN_UUID = 0;
    public static final Integer X_SPAWN = 1;
    public static final Integer Y_SPAWN = 2;
    public static final Integer Z_SPAWN = 3;
    public static final Integer PITCH_SPAWN = 4;
    public static final Integer YAW_SPAWN = 5;
    /**
     * hub
     */
    public static final Integer WORLD_HUB_UUID = 0;
    public static final Integer X_HUB = 1;
    public static final Integer Y_HUB = 2;
    public static final Integer Z_HUB = 3;
    public static final Integer PITCH_HUB = 4;
    public static final Integer YAW_HUB = 5;
    /**
     * Warps
     */
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

    public List<String> getHub() {
        String sql = "SELECT * FROM " + hubTable;
        return new Worker<List<String>>().getSomething(rs -> {
            List<String> hub = new ArrayList<>();
            while (rs.next()) {
                hub.add(WORLD_HUB_UUID, rs.getString(this.worldUUID));
                hub.add(X_HUB, rs.getString(this.x));
                hub.add(Y_HUB, rs.getString(this.y));
                hub.add(Z_HUB, rs.getString(this.z));
                hub.add(PITCH_HUB, rs.getString(this.pitch));
                hub.add(YAW_HUB, rs.getString(this.yaw));
            }
            return hub;
        }, sql);
    }

    public List<List<String>> getAllSpawns() {
        String sql = "SELECT * FROM " + this.spawnsTable;
        return new Worker<List<List<String>>>().getSomething(rs -> {
            List<List<String>> spawnsTable = new ArrayList<>();
            while (rs.next()) {
                List<String> spawn = new ArrayList<>();
                spawn.add(WORLD_SPAWN_UUID, rs.getString(this.worldUUID));
                spawn.add(X_SPAWN, rs.getString(this.x));
                spawn.add(Y_SPAWN, rs.getString(this.y));
                spawn.add(Z_SPAWN, rs.getString(this.z));
                spawn.add(PITCH_SPAWN, rs.getString(this.pitch));
                spawn.add(YAW_SPAWN, rs.getString(this.yaw));
                spawnsTable.add(spawn);
            }
            return spawnsTable;
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

    public List<List<String>> getAllBacks() {
        String sql = "SELECT * FROM " + this.playerBackTable;
        return new Worker<List<List<String>>>().getSomething(rs -> {
            List<List<String>> playerBacks = new ArrayList<>();
            while (rs.next()) {
                List<String> back = new ArrayList<>();
                back.add(PLAYER_UUID_BACK, rs.getString(this.playerUUID));
                back.add(WORLD_BACK_UUID, rs.getString(this.worldUUID));
                back.add(X_BACK, rs.getString(this.x));
                back.add(Y_BACK, rs.getString(this.y));
                back.add(Z_BACK, rs.getString(this.z));
                back.add(PITCH_BACK, rs.getString(this.pitch));
                back.add(YAW_BACK, rs.getString(this.yaw));
                playerBacks.add(back);
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

//    public List<List<String>> getAllHomes() {
//        String sql = "SELECT * FROM " + this.homesTable;
//        return new Worker<List<List<String>>>().getSomething(rs -> {
//            List<List<String>> playerHomes = new ArrayList<>();
//            while (rs.next()) {
//                List<String> home = new ArrayList<>();
//                home.add(PLAYER_HOME_UUID, rs.getString(this.playerUUID));
//                home.add(HOME_UUID, rs.getString(this.homeUUID));
//                home.add(WORLD_HOME_UUID, rs.getString(this.worldUUID));
//                home.add(HOME_NAME, rs.getString(this.homeName));
//                home.add(X, rs.getString(this.x));
//                home.add(Y, rs.getString(this.y));
//                home.add(Z, rs.getString(this.z));
//                home.add(PITCH, rs.getString(this.pitch));
//                home.add(YAW, rs.getString(this.yaw));
//                playerHomes.add(home);
//            }
//            return playerHomes;
//        }, sql);
//    }

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

    public void deleteWarp(String warpUUID) {
        String sql = "DELETE FROM " + this.playersWarpTable + " WHERE " + this.warpUUID + " = " + "\"" + warpUUID + "\"";
        delete(sql);
    }

    public void deleteBack(String playerUUID) {
        String sql = "DELETE FROM " + this.playerBackTable + " WHERE " + this.playerUUID + " = " + "\"" + playerUUID + "\"";
        delete(sql);
    }

    public void deleteHome(String homeUUID) {
        String sql = "DELETE FROM " + this.homesTable + " WHERE " + this.homeUUID + " = " + "\"" + homeUUID + "\"";
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
