package com.example.core.service;

import com.example.core.CorePlugin;
import com.example.core.config.ConfigManager;
import com.example.core.util.CoreUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageService {
    private final CorePlugin plugin;
    private final ConfigManager configManager;
    private FileConfiguration lang;

    public MessageService(CorePlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.lang = configManager.getLangConfig();
    }

    public void reload() {
        this.lang = configManager.getLangConfig();
    }

    public String get(String path) {
        if (lang == null) {
            return path;
        }

        return CoreUtil.color(lang.getString(path, path));
    }

    public void send(CommandSender sender, String path) {
        sender.sendMessage(get(path));
    }

    public CorePlugin getPlugin() {
        return plugin;
    }
}
