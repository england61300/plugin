package com.example.core.storage.provider;

import com.example.core.storage.model.StoredPlayerData;
import java.util.UUID;

public interface StorageProvider {
    void initialize();

    StoredPlayerData load(UUID uuid, String fallbackName);

    void save(StoredPlayerData data);

    void shutdown();

    String getName();
}
