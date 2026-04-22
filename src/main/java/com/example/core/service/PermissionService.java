package com.example.core.service;

import com.example.core.CorePlugin;
import org.bukkit.command.CommandSender;

public class PermissionService {
    private final CorePlugin plugin;

    public PermissionService(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void reload() {
        // Uses plugin config directly; no extra state to refresh.
    }

    public boolean has(CommandSender sender, String key) {
        return sender.hasPermission(resolveNode(key));
    }

    public String resolveNode(String key) {
        return plugin.getConfig().getString("permissions." + key, key);
    }
}
