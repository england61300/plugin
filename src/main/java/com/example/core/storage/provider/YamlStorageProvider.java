package com.example.core.storage.provider;

import com.example.core.CorePlugin;
import com.example.core.economy.EconomyService;
import com.example.core.storage.model.StoredPlayerData;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlStorageProvider implements StorageProvider {
    private final CorePlugin plugin;
    private File dataFolder;

    public YamlStorageProvider(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void initialize() {
        this.dataFolder = new File(plugin.getDataFolder(), "playerdata");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    @Override
    public StoredPlayerData load(UUID uuid, String fallbackName) {
        File file = new File(dataFolder, uuid + ".yml");
        if (!file.exists()) {
            return new StoredPlayerData(uuid, fallbackName);
        }

        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        StoredPlayerData data = new StoredPlayerData(uuid, yaml.getString("identity.last-known-name", fallbackName));

        mapDoubleSection(yaml.getConfigurationSection("economy.balances"), data.getBalances());
        if (data.getBalances().isEmpty()) {
            double legacyBalance = yaml.getDouble("economy.balance", 0.0D);
            data.setBalance(EconomyService.DEFAULT_CURRENCY, legacyBalance);
        }

        mapIntegerSection(yaml.getConfigurationSection("stats"), data.getStats());
        mapIntegerSection(yaml.getConfigurationSection("progress"), data.getProgress());
        mapLongSection(yaml.getConfigurationSection("cooldowns"), data.getCooldowns());
        return data;
    }

    @Override
    public void save(StoredPlayerData data) {
        File file = new File(dataFolder, data.getUuid() + ".yml");
        YamlConfiguration yaml = new YamlConfiguration();

        yaml.set("identity.last-known-name", data.getLastKnownName());
        yaml.set("economy.balances", data.getBalances());
        yaml.set("stats", data.getStats());
        yaml.set("progress", data.getProgress());
        yaml.set("cooldowns", data.getCooldowns());

        try {
            yaml.save(file);
        } catch (IOException exception) {
            plugin.getLogger().warning("Failed saving player data for " + data.getUuid() + ": " + exception.getMessage());
        }
    }

    @Override
    public void shutdown() {
        // no-op for YAML provider
    }

    @Override
    public String getName() {
        return "yaml";
    }

    private void mapIntegerSection(ConfigurationSection section, Map<String, Integer> target) {
        if (section == null) {
            return;
        }

        for (String key : section.getKeys(false)) {
            target.put(key, section.getInt(key));
        }
    }

    private void mapLongSection(ConfigurationSection section, Map<String, Long> target) {
        if (section == null) {
            return;
        }

        for (String key : section.getKeys(false)) {
            target.put(key, section.getLong(key));
        }
    }

    private void mapDoubleSection(ConfigurationSection section, Map<String, Double> target) {
        if (section == null) {
            return;
        }

        for (String key : section.getKeys(false)) {
            target.put(key, section.getDouble(key));
        }
    }
}
