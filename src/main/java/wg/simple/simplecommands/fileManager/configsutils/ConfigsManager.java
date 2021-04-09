package wg.simple.simplecommands.fileManager.configsutils;


import wg.simple.simplecommands.fileManager.configsutils.configs.GuiLanguageConfig;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.fileManager.configsutils.configs.MainConfig;
import wg.simple.simplecommands.fileManager.configsutils.resourcesConfigGenerator.ConfigGenerator;

public class ConfigsManager {
    public LanguageConfig languageConfig;
    public MainConfig mainConfig;
    public GuiLanguageConfig guiLanguageConfig;
    public ConfigGenerator configGenerator;

    public ConfigsManager() {
        this.configGenerator = new ConfigGenerator();
        this.guiLanguageConfig = new GuiLanguageConfig();
        this.languageConfig = new LanguageConfig();
        this.mainConfig = new MainConfig();
    }

    public void init() {
        this.configGenerator.init();
        this.guiLanguageConfig.init();
        this.languageConfig.init();
        this.mainConfig.init();
    }
}
