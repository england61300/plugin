package com.example.core.gui.button;

import java.util.function.BiConsumer;
import java.util.function.Function;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MenuButton {
    private final Function<Player, ItemStack> itemFactory;
    private final BiConsumer<Player, InventoryClickEvent> clickHandler;

    public MenuButton(Function<Player, ItemStack> itemFactory, BiConsumer<Player, InventoryClickEvent> clickHandler) {
        this.itemFactory = itemFactory;
        this.clickHandler = clickHandler;
    }

    public ItemStack createItem(Player player) {
        return itemFactory.apply(player);
    }

    public void click(Player player, InventoryClickEvent event) {
        clickHandler.accept(player, event);
    }
}
