package wg.simple.simplecommands.simplecommand.tp.listeners;

import org.bukkit.entity.Player;

public class TeleportRequest {
    Player sender;
    Player receiver;
    long expiredTime;

    public TeleportRequest(Player sender, Player receiver, long expiredTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.expiredTime = expiredTime;
    }

    public Player getSender() {
        return sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public long getExpiredTime() {
        return expiredTime;
    }
}
