package wg.simple.simplecommands.managers;


import wg.simple.simplecommands.simplecommand.back.listeners.BackManager;
import wg.simple.simplecommands.simplecommand.homes.listeners.HomeManager;
import wg.simple.simplecommands.simplecommand.msg.listeners.MsgListener;
import wg.simple.simplecommands.simplecommand.rtp.listeners.RtpManager;
import wg.simple.simplecommands.simplecommand.spawns.listeners.SpawnsManager;
import wg.simple.simplecommands.simplecommand.stockcommand.HelpPermission;
import wg.simple.simplecommands.simplecommand.sudo.listeners.SudoListener;
import wg.simple.simplecommands.simplecommand.tp.listeners.TeleportManager;
import wg.simple.simplecommands.simplecommand.tp.listeners.TpaManager;
import wg.simple.simplecommands.simplecommand.warps.listeners.WarpManager;

public class ListenersManager {

    public ChatManager chatManager;
    public TeleportManager teleportManager;
    public HomeManager homeManager;
    public HelpPermission helpPermission;
    public TpaManager tpaManager;
    public BackManager backManager;
    public RtpManager rtpManager;
    public WarpManager warpManager;
    public SpawnsManager spawnsManager;
    public SudoListener sudoListener;
    public MsgListener msgListener;

    public ListenersManager() {
        this.chatManager = new ChatManager();
        this.spawnsManager = new SpawnsManager();
        this.warpManager = new WarpManager();
        this.teleportManager = new TeleportManager();
        this.homeManager = new HomeManager();
        this.helpPermission = new HelpPermission();
        this.tpaManager = new TpaManager();
        this.backManager = new BackManager();
        this.rtpManager = new RtpManager();
        this.msgListener = new MsgListener();
        this.sudoListener = new SudoListener();
    }

    public void init() {
        this.chatManager.init();
        this.spawnsManager.init();
        this.warpManager.init();
        this.teleportManager.init();
        this.homeManager.init();
        this.helpPermission.init();
        this.tpaManager.init();
        this.backManager.init();
        this.rtpManager.init();
        this.msgListener.init();
        this.sudoListener.init();
    }
}
