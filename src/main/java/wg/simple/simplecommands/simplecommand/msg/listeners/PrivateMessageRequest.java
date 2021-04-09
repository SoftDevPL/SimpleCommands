package wg.simple.simplecommands.simplecommand.msg.listeners;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.entity.Player;

@Data
@AllArgsConstructor
public class PrivateMessageRequest {

    private Player playerWhoSent;

    private Player playerToReply;
}
