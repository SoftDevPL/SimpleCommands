package wg.simple.simplecommands.managers;

import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.simplecommand.back.commands.BackCommand;
import wg.simple.simplecommands.simplecommand.homes.commands.*;
import wg.simple.simplecommands.simplecommand.msg.comands.BlockCommand;
import wg.simple.simplecommands.simplecommand.msg.comands.MsgCommand;
import wg.simple.simplecommands.simplecommand.msg.comands.UnBlockCommand;
import wg.simple.simplecommands.simplecommand.rtp.command.RtpCommand;
import wg.simple.simplecommands.simplecommand.spawns.commands.*;
import wg.simple.simplecommands.simplecommand.stockcommand.InfCommand;
import wg.simple.simplecommands.simplecommand.sudo.commands.SudoCommand;
import wg.simple.simplecommands.simplecommand.tp.commands.TpaCommand;
import wg.simple.simplecommands.simplecommand.tp.commands.TpacceptCommand;
import wg.simple.simplecommands.simplecommand.tp.commands.TpdenyCommand;
import wg.simple.simplecommands.simplecommand.warps.commands.*;

public class SimpleCommandManager {

    public void init() {
        SimpleCommands simpleCommands = SimpleCommands.getInstance();
        simpleCommands.getCommand("unblockmsg").setExecutor(new UnBlockCommand(simpleCommands));
        simpleCommands.getCommand("blockmsg").setExecutor(new BlockCommand(simpleCommands));
        simpleCommands.getCommand("msg").setExecutor(new MsgCommand(simpleCommands));
        simpleCommands.getCommand("inf").setExecutor(new InfCommand(simpleCommands));
        simpleCommands.getCommand("tpa").setExecutor(new TpaCommand(simpleCommands));
        simpleCommands.getCommand("sethome").setExecutor(new SetHomeCommand(simpleCommands));
        new DelHomeCommand(simpleCommands.getCommand("delhome"));
        new HomeListCommand(simpleCommands.getCommand("homelist"));
        new HomeCommand(simpleCommands.getCommand("home"));
        simpleCommands.getCommand("tpaccept").setExecutor(new TpacceptCommand(simpleCommands));
        simpleCommands.getCommand("tpdeny").setExecutor(new TpdenyCommand(simpleCommands));
        simpleCommands.getCommand("back").setExecutor(new BackCommand(simpleCommands));
        simpleCommands.getCommand("rtp").setExecutor(new RtpCommand(simpleCommands));
        simpleCommands.getCommand("setwarp").setExecutor(new SetWarpCommand(simpleCommands));
        new RemoveWarpCommand(simpleCommands.getCommand("removewarp"));
        new WarpCommand(simpleCommands.getCommand("warp"));
        simpleCommands.getCommand("removespawn").setExecutor(new RemoveSpawnCommand(simpleCommands));
        simpleCommands.getCommand("setspawn").setExecutor(new SetSpawnCommand(simpleCommands));
        simpleCommands.getCommand("spawn").setExecutor(new SpawnCommand(simpleCommands));
        simpleCommands.getCommand("removehub").setExecutor(new RemoveHubCommand(simpleCommands));
        simpleCommands.getCommand("sethub").setExecutor(new SetHubCommand(simpleCommands));
        simpleCommands.getCommand("hub").setExecutor(new HubCommand(simpleCommands));
        simpleCommands.getCommand("warplist").setExecutor(new WarpListCommand(simpleCommands));
        simpleCommands.getCommand("sudo").setExecutor(new SudoCommand(simpleCommands));
        new HomeMenuCommand(simpleCommands.getCommand("homemenu"));
        new WarpMenuCommand(simpleCommands.getCommand("warpmenu"));
    }
}
