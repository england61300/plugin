package com.example.core.storage;

import com.example.core.CorePlugin;
import com.example.core.storage.model.StoredPlayerData;
import com.example.core.storage.provider.SqlStorageProvider;
import com.example.core.storage.provider.StorageProvider;
import com.example.core.storage.provider.YamlStorageProvider;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;

public class StorageManager {
    private final CorePlugin plugin;
    private final Map<UUID, StoredPlayerData> cache;
    private StorageProvider provider;

    public StorageManager(CorePlugin plugin) {
        this.plugin = plugin;
        this.cache = new ConcurrentHashMap<>();
    }

    public void initialize() {
        this.provider = createProvider();
        provider.initialize();
        plugin.getLogger().info("Storage provider initialized: " + provider.getName());
    }

    public StoredPlayerData loadOrCreate(Player player) {
        return loadOrCreate(player.getUniqueId(), player.getName());
    }

    public StoredPlayerData loadOrCreate(UUID uuid, String fallbackName) {
        return cache.computeIfAbsent(uuid, id -> provider.load(id, fallbackName));
    }

    public StoredPlayerData get(UUID uuid) {
        return cache.get(uuid);
    }

    public void save(StoredPlayerData data) {
        provider.save(data);
    }

    public void savePlayer(Player player) {
        StoredPlayerData data = cache.get(player.getUniqueId());
        if (data != null) {
            provider.save(data);
        }
    }

    public void flushAll() {
        cache.values().forEach(provider::save);
    }

    public void shutdown() {
        flushAll();
        provider.shutdown();
        cache.clear();
    }

    public void reload() {
        flushAll();
        provider.shutdown();
        cache.clear();
        initialize();
    }

    public String getProviderName() {
        return provider == null ? "uninitialized" : provider.getName();
    }

    private StorageProvider createProvider() {
        String type = plugin.getConfig().getString("storage.type", "yaml").toLowerCase(Locale.ROOT);
        return switch (type) {
            case "sqlite", "mysql" -> new SqlStorageProvider(plugin, type);
            case "yaml" -> new YamlStorageProvider(plugin);
            default -> {
                plugin.getLogger().warning("Unknown storage type '" + type + "'. Falling back to YAML.");
                yield new YamlStorageProvider(plugin);
            }
        };
    }
}
