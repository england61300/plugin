package com.example.core.command;

import com.example.core.CorePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private final CorePlugin plugin;

    public ReloadCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!plugin.getPermissionService().has(sender, "admin.reload")) {
            plugin.getMessageService().send(sender, "messages.no-permission");
            return true;
        }

        plugin.reloadCore();
        plugin.getMessageService().send(sender, "messages.reloaded");
        return true;
    }
}
