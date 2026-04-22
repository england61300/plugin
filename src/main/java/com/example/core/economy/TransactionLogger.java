package com.example.core.economy;

import com.example.core.CorePlugin;
import com.example.core.economy.model.TransactionType;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

public class TransactionLogger {
    private final CorePlugin plugin;
    private final File transactionFile;

    public TransactionLogger(CorePlugin plugin) {
        this.plugin = plugin;
        this.transactionFile = new File(new File(plugin.getDataFolder(), "logs"), "economy-transactions.log");
    }

    public void initialize() {
        File parent = transactionFile.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        if (!transactionFile.exists()) {
            try {
                transactionFile.createNewFile();
            } catch (IOException exception) {
                plugin.getLogger().warning("Could not create transaction log file: " + exception.getMessage());
            }
        }
    }

    public void log(UUID playerId, String playerName, TransactionType type, String currency, double amount, double after, String reason, String actor) {
        String line = String.format(
            Locale.US,
            "%s | player=%s(%s) | type=%s | currency=%s | amount=%.2f | after=%.2f | actor=%s | reason=%s%n",
            Instant.now(),
            playerName,
            playerId,
            type,
            currency,
            amount,
            after,
            actor,
            reason
        );

        try {
            Files.writeString(transactionFile.toPath(), line, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException exception) {
            plugin.getLogger().warning("Could not append transaction log: " + exception.getMessage());
        }
    }
}
