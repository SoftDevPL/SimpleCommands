package wg.simple.simplecommands.simplecommand.sudo.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;
import wg.simple.simplecommands.simplecommand.sudo.events.SudoEvent;

public class SudoListener implements Listener {
    public LanguageConfig languageConfig;

    public void init() {
        SimpleCommands mainAD = SimpleCommands.getInstance();
        this.languageConfig = mainAD.configsManager.languageConfig;
        mainAD.getServer().getPluginManager().registerEvents(this, mainAD);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void sudoExecutor(SudoEvent event) {
        StringBuilder stringBuilder = new StringBuilder(event.getLabel());
        for (String arg : event.getArgs()) {
            stringBuilder.append(" ").append(arg);
        }
        String command = stringBuilder.toString();
        if (event.getPlayerToExecute().performCommand(command)) {
            event.getWho().sendMessage(this.languageConfig.getSudoSuccessful());
        } else {
            event.getWho().sendMessage(this.languageConfig.getSudoError());
        }
    }
}
