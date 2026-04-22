package com.example.core.command;

import com.example.core.CorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AdminDebugCommand implements CommandExecutor {
    private final CorePlugin plugin;

    public AdminDebugCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!plugin.getPermissionService().has(sender, "admin.debug")) {
            plugin.getMessageService().send(sender, "messages.no-permission");
            return true;
        }

        sender.sendMessage("§e[CoreDebug] TPS (1m): " + Bukkit.getTPS()[0]);
        sender.sendMessage("§e[CoreDebug] Online Players: " + Bukkit.getOnlinePlayers().size());
        sender.sendMessage("§e[CoreDebug] Loaded worlds: " + Bukkit.getWorlds().size());
        sender.sendMessage("§e[CoreDebug] Storage provider: " + plugin.getStorageManager().getProviderName());
        return true;
    }
}
