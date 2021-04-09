package wg.simple.simplecommands.simplecommand.warps.guis;


import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.GuiLanguageConfig;
import wg.simple.simplecommands.managers.guiengine.basics.BasicGui;
import wg.simple.simplecommands.managers.guiengine.basics.ListGui;
import wg.simple.simplecommands.simplecommand.warps.Warp;
import wg.simple.simplecommands.simplecommand.warps.listeners.WarpManager;

import java.util.Comparator;
import java.util.List;

public class WarpsGui extends ListGui<Warp> {

    private final WarpManager warpManager;
    private GuiLanguageConfig glc;

    public WarpsGui(BasicGui previousGui) {
        this(previousGui, SimpleCommands.getInstance().configsManager.guiLanguageConfig.getWarpsGuiPageName());
        this.glc = SimpleCommands.getInstance().configsManager.guiLanguageConfig;
    }

    public WarpsGui(BasicGui previousGui, String title) {
        super(previousGui, title);
        warpManager = SimpleCommands.getInstance().listenersManager.warpManager;
        this.setAction(this::defaultAction);
        this.setRefreshFunction(this::refreshFunction);
    }

    private List<Warp> refreshFunction() {
        List<Warp> sortedWarps = warpManager.getAllWarps();
        sortedWarps.sort(Comparator.comparing(Warp::getName));
        return warpManager.getAllWarps();
    }

    private void defaultAction(Warp warp) {
        new SingleWarpGui(warp, this).open(this.getLastClicker());
    }

    @Override
    public ItemStack getDescriptionItem(Warp warp) {
        return BasicGui.createSegmentedItem(Material.COMPASS, glc.getWarpsGuiItemName(warp.getName()));
    }
}
