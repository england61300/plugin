package com.example.core.gui.menu;

import com.example.core.CorePlugin;
import com.example.core.gui.GuiManager;
import com.example.core.gui.button.MenuButton;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class BaseMenu {
    protected final CorePlugin plugin;
    private final int size;
    private final String title;
    private final Map<Integer, MenuButton> buttons;

    protected BaseMenu(CorePlugin plugin, int size, String title) {
        this.plugin = plugin;
        this.size = size;
        this.title = title;
        this.buttons = new HashMap<>();
    }

    public void open(Player player, MenuContext context) {
        Inventory inventory = Bukkit.createInventory(player, size, title);
        buttons.clear();
        build(player, context);

        for (Map.Entry<Integer, MenuButton> entry : buttons.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().createItem(player));
        }

        GuiManager manager = plugin.getGuiManager();
        manager.trackOpenMenu(player, this, context);
        player.openInventory(inventory);
    }

    public void refresh(Player player, MenuContext context) {
        Inventory top = player.getOpenInventory().getTopInventory();
        if (top.getSize() != size) {
            open(player, context);
            return;
        }

        top.clear();
        buttons.clear();
        build(player, context);
        for (Map.Entry<Integer, MenuButton> entry : buttons.entrySet()) {
            top.setItem(entry.getKey(), entry.getValue().createItem(player));
        }
    }

    public abstract void build(Player player, MenuContext context);

    public void setButton(int slot, MenuButton button) {
        buttons.put(slot, button);
    }

    public MenuButton getButton(int slot) {
        return buttons.get(slot);
    }
}
