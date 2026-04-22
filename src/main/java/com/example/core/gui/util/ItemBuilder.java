package com.example.core.gui.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;

    private ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material);
    }

    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder name(String displayName) {
        meta.setDisplayName(displayName);
        return this;
    }

    public ItemBuilder lore(String... lines) {
        meta.setLore(new ArrayList<>(Arrays.asList(lines)));
        return this;
    }

    public ItemBuilder lore(List<String> lines) {
        meta.setLore(new ArrayList<>(lines));
        return this;
    }

    public ItemBuilder flags(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}
