package com.example.core.economy;

import java.util.Locale;
import org.bukkit.entity.Player;

public class EconomyPlaceholderService {
    private final EconomyService economyService;

    public EconomyPlaceholderService(EconomyService economyService) {
        this.economyService = economyService;
    }

    public String resolve(Player player, String identifier) {
        if (identifier == null) {
            return "";
        }

        String normalized = identifier.toLowerCase(Locale.ROOT);
        if (normalized.equals("balance") || normalized.equals("money")) {
            return economyService.format(economyService.getBalance(player));
        }

        if (normalized.startsWith("balance_")) {
            String currency = normalized.substring("balance_".length());
            return economyService.format(
                economyService.getBalance(player.getUniqueId(), player.getName(), currency)
            );
        }

        return "";
    }
}
