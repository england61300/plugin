package com.example.core.command;

import com.example.core.CorePlugin;
import java.util.Objects;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

public class CommandRegistry {
    private final CorePlugin plugin;

    public CommandRegistry(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void register(String commandName, CommandExecutor executor) {
        PluginCommand command = Objects.requireNonNull(plugin.getCommand(commandName), "Command not defined: " + commandName);
        command.setExecutor(executor);
        if (executor instanceof TabCompleter tabCompleter) {
            command.setTabCompleter(tabCompleter);
        }
    }
}
