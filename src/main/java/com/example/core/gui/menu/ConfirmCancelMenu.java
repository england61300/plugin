package com.example.core.gui.menu;

import com.example.core.CorePlugin;
import com.example.core.gui.button.MenuButton;
import com.example.core.gui.util.ItemBuilder;
import java.util.function.Consumer;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ConfirmCancelMenu extends BaseMenu {
    private final String prompt;
    private final Consumer<Player> onConfirm;
    private final Consumer<Player> onCancel;

    public ConfirmCancelMenu(CorePlugin plugin, String prompt, Consumer<Player> onConfirm, Consumer<Player> onCancel) {
        super(plugin, 27, "§8Confirm");
        this.prompt = prompt;
        this.onConfirm = onConfirm;
        this.onCancel = onCancel;
    }

    @Override
    public void build(Player player, MenuContext context) {
        setButton(11, new MenuButton(
            viewer -> ItemBuilder.of(Material.GREEN_WOOL)
                .name("§aConfirm")
                .lore("§7" + prompt)
                .build(),
            (viewer, event) -> {
                viewer.closeInventory();
                onConfirm.accept(viewer);
            }
        ));

        setButton(15, new MenuButton(
            viewer -> ItemBuilder.of(Material.RED_WOOL)
                .name("§cCancel")
                .lore("§7" + prompt)
                .build(),
            (viewer, event) -> {
                viewer.closeInventory();
                onCancel.accept(viewer);
            }
        ));

        if (context.getPrevious() != null) {
            setButton(22, new MenuButton(
                viewer -> ItemBuilder.of(Material.BARRIER).name("§cBack").build(),
                (viewer, event) -> context.getPrevious().open(viewer, new MenuContext(0, null))
            ));
        }
    }
}
