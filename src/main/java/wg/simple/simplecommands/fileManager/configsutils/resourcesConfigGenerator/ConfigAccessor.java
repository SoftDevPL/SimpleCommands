package wg.simple.simplecommands.fileManager.configsutils.resourcesConfigGenerator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import wg.simple.simplecommands.SimpleCommands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigAccessor {
    protected SimpleCommands plugin;
    private YamlConfiguration yml;
    private File file;

    public void init(String filename) {
        this.initConfig(filename);
    }

    private void initConfig(String filename) {
        this.plugin = SimpleCommands.getInstance();
        file = new File(this.plugin.getDataFolder(), "/configs/" + filename + ".yml");
        if (file.exists()) {
            yml = YamlConfiguration.loadConfiguration(file);
        }
    }

    public String getStringPath(String path) {
        if (yml == null) {
            return String.format(
                    "Sorry but |=> %s <=| or path: |=> %s <=| doesn't exists, Check if you have a literal mistake."
                    , file.getName()
                    , path);
        }
        String string = yml.getString(path);
        if (string == null) {
            Bukkit.getConsoleSender().sendMessage(SimpleCommands.convertColors(
                    "&cSome config is incorrect, please check this path &f==> " + path + " <== &cor remove that config to return to defaults."));
            return "unknown";
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public boolean getBooleanPath(String path) {
        if (yml == null) {
            return false;
        }
        return yml.getBoolean(path);
    }

    public int getIntPath(String path) {
        if (yml == null) {
            return 0;
        }
        return yml.getInt(path);
    }

    public List<String> getStringListPath(String path) {
        if (yml == null) {
            return new ArrayList<>();
        }
        return yml.getStringList(path);
    }
}
