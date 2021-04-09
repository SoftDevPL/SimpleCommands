package wg.simple.simplecommands.simplecommand.sudo.events;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SudoEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final Player playerToExecute;
    private final CommandSender who;
    private boolean cancel;
    private String label;
    private String[] args;

    public SudoEvent(CommandSender who, Player whoWillExecute, String label, String[] args) {
        this.who = who;
        this.playerToExecute = whoWillExecute;
        this.label = label;
        this.args = args;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public CommandSender getWho() {
        return who;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public Player getPlayerToExecute() {
        return playerToExecute;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
