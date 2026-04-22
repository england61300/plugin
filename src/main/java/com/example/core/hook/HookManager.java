package com.example.core.hook;

import com.example.core.CorePlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class HookManager {
    private final CorePlugin plugin;
    private final Map<String, Runnable> disableActions;

    public HookManager(CorePlugin plugin) {
        this.plugin = plugin;
        this.disableActions = new HashMap<>();
    }

    public void enableBuiltInHooks() {
        registerHook("placeholderapi", action -> {
            if (plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                plugin.getLogger().info("Hooked into PlaceholderAPI. Available placeholders: %core_balance%, %core_balance_<currency>%");
                action.accept(() -> plugin.getLogger().info("PlaceholderAPI hook disabled."));
            }
        });
    }

    public void registerHook(String key, Consumer<Consumer<Runnable>> initializer) {
        initializer.accept(disableAction -> disableActions.put(key, disableAction));
    }

    public void disableAllHooks() {
        disableActions.values().forEach(Runnable::run);
        disableActions.clear();
    }
}
