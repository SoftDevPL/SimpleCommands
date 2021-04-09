package wg.simple.simplecommands.fileManager.sql.sqlUtils;


import wg.simple.simplecommands.fileManager.sql.sqlUtils.databasescommands.AdminGuiDatabase;

public class SQLManager {
    public AdminGuiDatabase database;

    public SQLManager() {
        this.database = new AdminGuiDatabase();
    }

    public void init() {
        this.database.init();
        this.database.connect();
    }
}
