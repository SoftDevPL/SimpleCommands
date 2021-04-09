package wg.simple.simplecommands.managers.guiengine.basics;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.managers.guiengine.interfaces.Action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GuiListener implements Listener {
    private static final int clickCooldown = 100;
    private final Set<BasicGui> activeGuis = new HashSet<>();
    private boolean locked = false;

    public static int getClickCooldown() {
        return clickCooldown;
    }

    public void init() {
        SimpleCommands plugin = SimpleCommands.getInstance();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public synchronized void addGui(BasicGui gui, Player opener) {
        if (locked)
            Bukkit.getScheduler().scheduleSyncDelayedTask(SimpleCommands.getInstance(), () -> {
                opener.openInventory(gui.getGui());
                activeGuis.add(gui);
            });
        else {
            opener.openInventory(gui.getGui());
            activeGuis.add(gui);
        }
    }

    public void disable() {
        activeGuis.forEach(gui -> {
            List<HumanEntity> guiViewers = gui.getGui().getViewers();
            for (HumanEntity guiViewer : guiViewers) {
                guiViewer.closeInventory();
            }

        });
    }

    @EventHandler
    void InventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player) || e.getRawSlot() < 0) return;
        this.lock();

        List<BasicGui> filteredGuis = activeGuis.stream().filter(gui ->
                e.getInventory().equals(gui.getGui()))
                .collect(Collectors.toList());
        this.unlock();
        if (!filteredGuis.isEmpty()) e.setCancelled(true);
        filteredGuis.stream().filter(gui -> gui.getLastClick() + clickCooldown < System.currentTimeMillis())
                .forEach(
                        gui -> {
                            Action action;
                            if (e.getClick() == ClickType.SHIFT_RIGHT
                                    || e.getClick() == ClickType.RIGHT) {
                                action = gui.getRightClickActions().get(e.getRawSlot());
                            } else {
                                action = gui.getActions().get(e.getRawSlot());
                            }

                            if (!gui.advancedClickHandler(e, action)) return;

                            if (action == null) return;
                            action.action((Player) e.getWhoClicked());
                            gui.setLastClick(System.currentTimeMillis());
                        });
    }

    private void lock() {
        this.locked = true;
    }

    private void unlock() {
        this.locked = false;
    }

    @EventHandler
    void guiClearer(InventoryCloseEvent e) {
        this.lock();
        activeGuis.removeIf(gui -> {
            boolean equals = gui.getGui().equals(e.getInventory());
            if (equals) {
                gui.onClose();
                gui.setClosed();
            }
            return equals;
        });
        this.unlock();
    }

}
