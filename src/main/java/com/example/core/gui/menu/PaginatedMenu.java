package com.example.core.gui.menu;

import com.example.core.CorePlugin;
import com.example.core.gui.button.MenuButton;
import com.example.core.gui.util.ItemBuilder;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class PaginatedMenu<T> extends BaseMenu {
    private final int[] contentSlots;

    protected PaginatedMenu(CorePlugin plugin, int size, String title, int[] contentSlots) {
        super(plugin, size, title);
        this.contentSlots = contentSlots;
    }

    @Override
    public void build(Player player, MenuContext context) {
        List<T> entries = new ArrayList<>(getEntries(player));
        int maxPage = Math.max(0, (int) Math.ceil((double) entries.size() / contentSlots.length) - 1);
        int page = Math.min(context.getPage(), maxPage);
        context.setPage(page);

        int start = page * contentSlots.length;
        int end = Math.min(start + contentSlots.length, entries.size());

        for (int i = start; i < end; i++) {
            int slot = contentSlots[i - start];
            T entry = entries.get(i);
            setButton(slot, getEntryButton(player, entry, i));
        }

        addNavigation(player, context, maxPage);
        decorate(player, context);
    }

    protected abstract List<T> getEntries(Player player);

    protected abstract MenuButton getEntryButton(Player player, T entry, int absoluteIndex);

    protected void decorate(Player player, MenuContext context) {
        // extension point
    }

    private void addNavigation(Player player, MenuContext context, int maxPage) {
        if (context.getPage() > 0) {
            setButton(45, new MenuButton(
                viewer -> navItem(Material.ARROW, "§ePrevious Page"),
                (viewer, event) -> {
                    context.setPage(context.getPage() - 1);
                    refresh(viewer, context);
                }
            ));
        }

        if (context.getPage() < maxPage) {
            setButton(53, new MenuButton(
                viewer -> navItem(Material.ARROW, "§eNext Page"),
                (viewer, event) -> {
                    context.setPage(context.getPage() + 1);
                    refresh(viewer, context);
                }
            ));
        }

        if (context.getPrevious() != null) {
            setButton(49, new MenuButton(
                viewer -> navItem(Material.BARRIER, "§cBack"),
                (viewer, event) -> context.getPrevious().open(viewer, new MenuContext(0, null))
            ));
        }
    }

    private ItemStack navItem(Material material, String name) {
        return ItemBuilder.of(material).name(name).build();
    }
}
