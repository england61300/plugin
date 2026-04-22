package com.example.core.gui;

import com.example.core.CorePlugin;
import com.example.core.gui.button.MenuButton;
import com.example.core.gui.menu.BaseMenu;
import com.example.core.gui.menu.MenuContext;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GuiManager implements Listener {
    private final CorePlugin plugin;
    private final Map<UUID, BaseMenu> openMenus;
    private final Map<UUID, MenuContext> contexts;

    public GuiManager(CorePlugin plugin) {
        this.plugin = plugin;
        this.openMenus = new ConcurrentHashMap<>();
        this.contexts = new ConcurrentHashMap<>();
    }

    public void initialize() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void trackOpenMenu(Player player, BaseMenu menu, MenuContext context) {
        openMenus.put(player.getUniqueId(), menu);
        contexts.put(player.getUniqueId(), context);
    }

    public void open(Player player, BaseMenu menu) {
        menu.open(player, new MenuContext(0, null));
    }

    public void refresh(Player player) {
        UUID uuid = player.getUniqueId();
        BaseMenu menu = openMenus.get(uuid);
        MenuContext context = contexts.get(uuid);

        if (menu != null && context != null) {
            menu.refresh(player, context);
        }
    }

    public void closeTracking(Player player) {
        UUID uuid = player.getUniqueId();
        openMenus.remove(uuid);
        contexts.remove(uuid);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        BaseMenu menu = openMenus.get(player.getUniqueId());
        if (menu == null) {
            return;
        }

        if (event.getClickedInventory() == null || event.getClickedInventory() != player.getOpenInventory().getTopInventory()) {
            return;
        }

        event.setCancelled(true);
        int slot = event.getSlot();
        MenuButton button = menu.getButton(slot);
        if (button != null) {
            button.click(player, event);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            closeTracking(player);
        }
    }
}
