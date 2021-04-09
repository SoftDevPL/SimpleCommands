package wg.simple.simplecommands.fileManager.sql.sqlUtils;


import wg.simple.simplecommands.SimpleCommands;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static wg.simple.simplecommands.SimpleCommands.*;

public class CustomSQLInterface {

    public String databaseName;
    public String databaseUrl;
    protected SimpleCommands plugin;
    protected boolean ok = true;
    File database;

    public static void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void init(String filename) {
        this.init(filename, true);
    }

    protected void init(String filename, boolean create) {
        this.plugin = SimpleCommands.getInstance();
        this.databaseName = filename + ".db";
        this.databaseUrl = "jdbc:sqlite:" + "plugins/" + this.plugin.getDataFolder().getName() + "/" + this.databaseName;
        database = new File(this.plugin.getDataFolder(), this.databaseName);
        if (create) init();
        else {
            if (!this.plugin.getDataFolder().exists()) {
                ok = false;
                return;
            }
            database = new File(this.plugin.getDataFolder(), this.databaseName);
            if (!database.exists()) {
                ok = false;
            }
        }
    }

    private void init() {
        CheckIfDatabaseExists();
    }

    boolean isOk() {
        return this.ok;
    }

    public void CheckIfDatabaseExists() {
        MakeFolderIfNotExists();
        if (!database.exists()) {
            createDatabase();
        }
    }

    protected boolean MakeFolderIfNotExists() {
        if (!this.plugin.getDataFolder().exists()) {
            return this.plugin.getDataFolder().mkdir();
        }
        return true;
    }

    public void createDatabase() {
        createNewDatabase();
    }

    public Connection connect() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            //
        }
        try {
            conn = DriverManager.getConnection(this.databaseUrl);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createNewDatabase() {
        this.databaseUrl = "jdbc:sqlite:" + "plugins/" + this.plugin.getDataFolder().getName() + "/" + this.databaseName;

        try (Connection conn = DriverManager.getConnection(this.databaseUrl)) {
            SimpleCommands.getInstance().mesLogger.info("");
            SimpleCommands.getInstance().mesLogger.info("==> Database Initialization <==");
            if (conn != null) {
                SimpleCommands.getInstance().mesLogger.info(ANSI_CYAN + this.databaseName + ANSI_GREEN + " -> database has been created!" + ANSI_RESET);
            } else {
                SimpleCommands.getInstance().mesLogger.info(ANSI_CYAN + this.databaseName + ANSI_GREEN + " -> database has been loaded successfully!" + ANSI_RESET);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
