package com.example.plugins.player.utility;

import java.util.Objects;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerUtilityPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        registerCommand("ec");
        registerCommand("craft");
        registerCommand("trash");
        registerCommand("cooldowns");
        getLogger().info("PlayerUtilityPlugin enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("PlayerUtilityPlugin disabled.");
    }

    private void registerCommand(String name) {
        PluginCommand command = Objects.requireNonNull(getCommand(name), "Missing command in plugin.yml: " + name);
        command.setExecutor(new StubCommand());
    }

    private static final class StubCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            sender.sendMessage("§eThis plugin module is scaffolded and ready for implementation.");
            return true;
        }
    }
}
