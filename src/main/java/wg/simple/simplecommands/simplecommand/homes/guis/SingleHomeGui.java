package wg.simple.simplecommands.simplecommand.homes.guis;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.GuiLanguageConfig;
import wg.simple.simplecommands.managers.guiengine.basics.BasicGui;
import wg.simple.simplecommands.managers.guiengine.basics.Colors;
import wg.simple.simplecommands.simplecommand.homes.Home;
import wg.simple.simplecommands.simplecommand.homes.events.PlayerRemoveHomeEvent;
import wg.simple.simplecommands.simplecommand.homes.listeners.HomeManager;

public class SingleHomeGui extends BasicGui {
    @Getter
    private final Home home;
    private final GuiLanguageConfig glc;
    private final HomeManager homeManager;

    public SingleHomeGui(Home home, BasicGui previousGui) {
        super(3, home.getName(), previousGui);
        this.homeManager = SimpleCommands.getInstance().listenersManager.homeManager;
        this.home = home;
        this.glc = SimpleCommands.getInstance().configsManager.guiLanguageConfig;
        this.initPassiveItems();
    }

    private void initPassiveItems() {
        if (previousGui != null) {
            this.setItem(7, 1, BasicGui.createBackItem(glc.getAdminGuiBack()),
                    this::backOrClose);
        }
        this.setItem(8, 1, BasicGui.createExitItem(glc.getAdminGuiClose()),
                HumanEntity::closeInventory);
        this.setItem(5, 1, BasicGui.createItem(Material.WOOL,
                glc.getSingleHomeGuiRemoveHomeItem(), Colors.RED), player -> {
            Bukkit.getPluginManager().callEvent(new PlayerRemoveHomeEvent(player, home));
            if (!homeManager.isHomeRegister(home)) backOrClose(player);
        });
        this.autoFill(BasicGui.createBackground(Colors.ORANGE));
    }

    private void initItems() {
        this.setItem(3, 1, createLocationItem(), p -> {
            p.teleport(home.getHomeLocation());
            p.closeInventory();
        });
    }

    private ItemStack createNameItem() {
        OfflinePlayer owner = Bukkit.getOfflinePlayer(home.getOwnerUUID());
        return BasicGui.createSegmentedItem(Material.PAPER,
                glc.getSingleHomeGuiHomeNameItemName(home.getName()) + "\n" +
                        glc.getSingleHomeGuiHomeNameItemOwner(owner.getName()));
    }

    private ItemStack createLocationItem() {
        Location loc = home.getHomeLocation();
        return BasicGui.createSegmentedItem(Material.COMPASS, glc.getSingleHomeGuiLocationItemName()
                + "\n" + glc.getSingleHomeGuiLocationItemX(String.valueOf((float) loc.getX())) + "\n"
                + glc.getSingleHomeGuiLocationItemY(String.valueOf((float) loc.getY())) + "\n"
                + glc.getSingleHomeGuiLocationItemZ(String.valueOf((float) loc.getZ())) + "\n"
                + glc.getSingleHomeGuiLocationItemClickToTeleport());
    }

    @Override
    public void open(Player opener) {
        initItems();
        super.open(opener);
    }
}
