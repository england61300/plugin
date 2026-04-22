package com.example.core.economy.command;

import com.example.core.CorePlugin;
import com.example.core.economy.EconomyService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {
    private final CorePlugin plugin;

    public BalanceCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can run this command.");
            return true;
        }

        double balance = plugin.getEconomyService().getBalance(player);
        String formatted = plugin.getEconomyService().format(balance);
        sender.sendMessage("§aBalance: §e" + formatted + " " + EconomyService.DEFAULT_CURRENCY);
        return true;
    }
}
