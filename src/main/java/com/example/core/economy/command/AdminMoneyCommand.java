package com.example.core.economy.command;

import com.example.core.CorePlugin;
import java.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AdminMoneyCommand implements CommandExecutor {
    private final CorePlugin plugin;

    public AdminMoneyCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!plugin.getPermissionService().has(sender, "economy.admin")) {
            plugin.getMessageService().send(sender, "messages.no-permission");
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage("§cUsage: /money <add|remove|set> <player> <amount> [currency]");
            return true;
        }

        String action = args[0].toLowerCase(Locale.ROOT);
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        if (target.getUniqueId() == null || target.getName() == null) {
            sender.sendMessage("§cUnknown player.");
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[2]);
        } catch (NumberFormatException exception) {
            sender.sendMessage("§cAmount must be a number.");
            return true;
        }

        String currency = args.length > 3 ? args[3] : "coins";
        String actor = sender.getName();

        switch (action) {
            case "add" -> {
                boolean ok = plugin.getEconomyService().add(target.getUniqueId(), target.getName(), currency, amount, "admin_add", actor);
                sender.sendMessage(ok ? "§aAdded money successfully." : "§cCould not add money.");
            }
            case "remove" -> {
                boolean ok = plugin.getEconomyService().remove(target.getUniqueId(), target.getName(), currency, amount, "admin_remove", actor);
                sender.sendMessage(ok ? "§aRemoved money successfully." : "§cCould not remove money (insufficient funds or invalid input).");
            }
            case "set" -> {
                plugin.getEconomyService().set(target.getUniqueId(), target.getName(), currency, amount, "admin_set", actor);
                sender.sendMessage("§aSet money successfully.");
            }
            default -> sender.sendMessage("§cUsage: /money <add|remove|set> <player> <amount> [currency]");
        }

        return true;
    }
}
