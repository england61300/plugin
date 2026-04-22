package com.example.core.listener;

import com.example.core.CorePlugin;
import com.example.core.storage.model.StoredPlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDataListener implements Listener {
    private final CorePlugin plugin;

    public PlayerDataListener(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        StoredPlayerData data = plugin.getStorageManager().loadOrCreate(event.getPlayer());
        data.setLastKnownName(event.getPlayer().getName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        StoredPlayerData data = plugin.getStorageManager().loadOrCreate(event.getPlayer());
        data.setLastKnownName(event.getPlayer().getName());
        plugin.getStorageManager().save(data);
    }
}
