package wg.simple.simplecommands.simplecommand.warps.guis;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.GuiLanguageConfig;
import wg.simple.simplecommands.managers.guiengine.basics.BasicGui;
import wg.simple.simplecommands.managers.guiengine.basics.Colors;
import wg.simple.simplecommands.simplecommand.warps.Warp;
import wg.simple.simplecommands.simplecommand.warps.events.RemoveWarpEvent;
import wg.simple.simplecommands.simplecommand.warps.listeners.WarpManager;

public class SingleWarpGui extends BasicGui {
    private final GuiLanguageConfig glc;
    private final WarpManager warpManager;
    @Getter
    private final Warp warp;

    public SingleWarpGui(Warp warp, BasicGui previousGui) {
        super(3, warp.getName(), previousGui);
        glc = SimpleCommands.getInstance().configsManager.guiLanguageConfig;
        warpManager = SimpleCommands.getInstance().listenersManager.warpManager;
        this.warp = warp;
        initPassiveItems();
    }


    private void initPassiveItems() {
        if (previousGui != null) {
            this.setItem(7, 1, BasicGui.createBackItem(glc.getAdminGuiBack()),
                    this::backOrClose);
        }
        this.setItem(8, 1, BasicGui.createExitItem(glc.getAdminGuiClose()),
                HumanEntity::closeInventory);
        this.setItem(5, 1, BasicGui.createItem(Material.WOOL,
                glc.getSingleWarpGuiRemoveWarpItem(), Colors.RED), player -> {
            Bukkit.getPluginManager().callEvent(new RemoveWarpEvent(warp, player));
            if (!warpManager.isWarpExists(warp)) backOrClose(player);
        });
        this.autoFill(BasicGui.createBackground(Colors.GREEN));
    }

    private void initItems() {
        this.setItem(3, 1, createLocationItem(), p -> {
            p.teleport(warp.getLocation());
            p.closeInventory();
        });
        this.setItem(1, 1, createNameItem(), null);
    }

    private ItemStack createNameItem() {
        return BasicGui.createSegmentedItem(Material.PAPER, glc.getSingleWarpGuiWarpNameItem(warp.getName()));
    }

    private ItemStack createLocationItem() {
        Location loc = warp.getLocation();
        return BasicGui.createSegmentedItem(Material.COMPASS, glc.getSingleWarpGuiLocationItemName()
                + "\n" + glc.getSingleWarpGuiLocationItemX(String.valueOf((float) loc.getX())) + "\n"
                + glc.getSingleWarpGuiLocationItemY(String.valueOf((float) loc.getY())) + "\n"
                + glc.getSingleWarpGuiLocationItemZ(String.valueOf((float) loc.getZ())) + "\n"
                + glc.getSingleWarpGuiLocationItemClickToTeleport());
    }

    @Override
    public void open(Player opener) {
        initItems();
        super.open(opener);
    }
}
