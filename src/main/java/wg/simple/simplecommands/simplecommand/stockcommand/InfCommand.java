package wg.simple.simplecommands.simplecommand.stockcommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.LanguageConfig;

public class InfCommand implements CommandExecutor {

    public LanguageConfig languageConfig;

    public InfCommand(SimpleCommands mainAD) {
        languageConfig = mainAD.configsManager.languageConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 &&
                sender instanceof Player) {
            Player p = (Player) sender;
            p.sendMessage(languageConfig.getInfCommand1());
            p.sendMessage(languageConfig.getInfCommand2());
            p.sendMessage(languageConfig.getInfCommand3());
            p.sendMessage(languageConfig.getInfCommand4());
            p.sendMessage(languageConfig.getInfCommand5());
            p.sendMessage(languageConfig.getInfCommand6());
            p.sendMessage(languageConfig.getInfCommand7());
            p.sendMessage(languageConfig.getInfCommand8());
            p.sendMessage(languageConfig.getInfCommand9());
            p.sendMessage(languageConfig.getInfCommand10());
        }
        return true;
    }
}
