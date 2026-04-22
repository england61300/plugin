package com.example.core.config;

import com.example.core.CorePlugin;
import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
    private final CorePlugin plugin;
    private File langFile;
    private FileConfiguration langConfig;

    public ConfigManager(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void loadAll() {
        plugin.reloadConfig();
        loadLang();
    }

    private void loadLang() {
        String langName = plugin.getConfig().getString("language-file", "lang.yml");
        langFile = new File(plugin.getDataFolder(), langName);

        if (!langFile.exists()) {
            plugin.saveResource("lang.yml", false);
        }

        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }

    public void saveLang() {
        if (langConfig == null || langFile == null) {
            return;
        }

        try {
            langConfig.save(langFile);
        } catch (IOException exception) {
            plugin.getLogger().warning("Unable to save lang file: " + exception.getMessage());
        }
    }

    public FileConfiguration getLangConfig() {
        return langConfig;
    }
}
