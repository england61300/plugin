package com.example.core.storage.provider;

import com.example.core.CorePlugin;
import com.example.core.storage.model.StoredPlayerData;
import java.util.UUID;

public class SqlStorageProvider implements StorageProvider {
    private final CorePlugin plugin;
    private final String dialect;

    public SqlStorageProvider(CorePlugin plugin, String dialect) {
        this.plugin = plugin;
        this.dialect = dialect;
    }

    @Override
    public void initialize() {
        plugin.getLogger().warning("Storage type '" + dialect + "' is not implemented yet. Falling back to YAML is recommended.");
    }

    @Override
    public StoredPlayerData load(UUID uuid, String fallbackName) {
        return new StoredPlayerData(uuid, fallbackName);
    }

    @Override
    public void save(StoredPlayerData data) {
        // Intentionally left as scaffold until SQL storage is implemented.
    }

    @Override
    public void shutdown() {
        // Intentionally left as scaffold until SQL storage is implemented.
    }

    @Override
    public String getName() {
        return dialect;
    }
}
