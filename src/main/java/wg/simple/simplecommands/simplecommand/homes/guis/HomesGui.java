package wg.simple.simplecommands.simplecommand.homes.guis;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.fileManager.configsutils.configs.GuiLanguageConfig;
import wg.simple.simplecommands.managers.guiengine.basics.BasicGui;
import wg.simple.simplecommands.managers.guiengine.basics.ListGui;
import wg.simple.simplecommands.simplecommand.homes.Home;
import wg.simple.simplecommands.simplecommand.homes.listeners.HomeManager;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class HomesGui extends ListGui<Home> {

    private final HomeManager homeManager;
    private final GuiLanguageConfig glc;
    @Setter
    @Getter
    private UUID playerUUID;

    public HomesGui(BasicGui previousGui) {
        this(previousGui, null);
    }

    public HomesGui(BasicGui previousGui, UUID playerUUID) {
        super(previousGui, SimpleCommands.getInstance().configsManager.guiLanguageConfig.getHomeGuiGuiPageName(Bukkit.getOfflinePlayer(playerUUID).getName()));
        this.glc = SimpleCommands.getInstance().configsManager.guiLanguageConfig;
        this.playerUUID = playerUUID;
        this.homeManager = SimpleCommands.getInstance().listenersManager.homeManager;
        this.setRefreshFunction(this::refreshFunction);
        this.setAction(this::defaultAction);
    }

    private List<Home> refreshFunction() {
        List<Home> sortedList = homeManager.getPlayerHomes(playerUUID);
        sortedList.sort(Comparator.comparing(Home::getName));
        return sortedList;
    }

    private void defaultAction(Home home) {
        new SingleHomeGui(home, this).open(this.getLastClicker());
    }

    @Override
    public ItemStack getDescriptionItem(Home home) {
        return BasicGui.createSegmentedItem(Material.PAINTING, glc.getHomeGuiName(home.getName()));
    }

}
