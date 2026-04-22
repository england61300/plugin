package com.example.core.storage.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StoredPlayerData {
    private final UUID uuid;
    private String lastKnownName;
    private final Map<String, Double> balances;
    private final Map<String, Integer> stats;
    private final Map<String, Integer> progress;
    private final Map<String, Long> cooldowns;

    public StoredPlayerData(UUID uuid, String lastKnownName) {
        this.uuid = uuid;
        this.lastKnownName = lastKnownName;
        this.balances = new HashMap<>();
        this.stats = new HashMap<>();
        this.progress = new HashMap<>();
        this.cooldowns = new HashMap<>();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getLastKnownName() {
        return lastKnownName;
    }

    public void setLastKnownName(String lastKnownName) {
        this.lastKnownName = lastKnownName;
    }

    public double getBalance(String currency) {
        return balances.getOrDefault(currency, 0.0D);
    }

    public double setBalance(String currency, double amount) {
        double normalized = Math.max(0.0D, amount);
        balances.put(currency, normalized);
        return normalized;
    }

    public double addBalance(String currency, double amount) {
        double value = getBalance(currency) + amount;
        balances.put(currency, value);
        return value;
    }

    public Map<String, Double> getBalances() {
        return balances;
    }

    public Map<String, Integer> getStats() {
        return stats;
    }

    public Map<String, Integer> getProgress() {
        return progress;
    }

    public Map<String, Long> getCooldowns() {
        return cooldowns;
    }
}
