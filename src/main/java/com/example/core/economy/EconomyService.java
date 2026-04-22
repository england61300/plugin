package com.example.core.economy;

import com.example.core.CorePlugin;
import com.example.core.economy.model.TransactionType;
import com.example.core.storage.model.StoredPlayerData;
import java.util.Locale;
import java.util.UUID;
import org.bukkit.entity.Player;

public class EconomyService {
    public static final String DEFAULT_CURRENCY = "coins";

    private final CorePlugin plugin;
    private final TransactionLogger transactionLogger;

    public EconomyService(CorePlugin plugin) {
        this.plugin = plugin;
        this.transactionLogger = new TransactionLogger(plugin);
    }

    public void initialize() {
        transactionLogger.initialize();
    }

    public double getBalance(Player player) {
        return getBalance(player.getUniqueId(), player.getName(), DEFAULT_CURRENCY);
    }

    public double getBalance(UUID uuid, String name, String currency) {
        StoredPlayerData data = plugin.getStorageManager().loadOrCreate(uuid, name);
        return data.getBalance(currency.toLowerCase(Locale.ROOT));
    }

    public boolean add(Player player, double amount, String reason, String actor) {
        return add(player.getUniqueId(), player.getName(), DEFAULT_CURRENCY, amount, reason, actor);
    }

    public boolean add(UUID uuid, String name, String currency, double amount, String reason, String actor) {
        if (amount < 0) {
            return false;
        }

        StoredPlayerData data = plugin.getStorageManager().loadOrCreate(uuid, name);
        double after = data.addBalance(currency.toLowerCase(Locale.ROOT), amount);
        plugin.getStorageManager().save(data);
        transactionLogger.log(uuid, name, TransactionType.ADD, currency, amount, after, reason, actor);
        return true;
    }

    public boolean remove(UUID uuid, String name, String currency, double amount, String reason, String actor) {
        if (amount < 0) {
            return false;
        }

        StoredPlayerData data = plugin.getStorageManager().loadOrCreate(uuid, name);
        String currencyKey = currency.toLowerCase(Locale.ROOT);
        double current = data.getBalance(currencyKey);
        if (current < amount) {
            return false;
        }

        double after = data.setBalance(currencyKey, current - amount);
        plugin.getStorageManager().save(data);
        transactionLogger.log(uuid, name, TransactionType.REMOVE, currency, amount, after, reason, actor);
        return true;
    }

    public void set(UUID uuid, String name, String currency, double amount, String reason, String actor) {
        StoredPlayerData data = plugin.getStorageManager().loadOrCreate(uuid, name);
        double after = data.setBalance(currency.toLowerCase(Locale.ROOT), Math.max(0.0D, amount));
        plugin.getStorageManager().save(data);
        transactionLogger.log(uuid, name, TransactionType.SET, currency, amount, after, reason, actor);
    }

    public String format(double amount) {
        return String.format(Locale.US, "%,.2f", amount);
    }
}
