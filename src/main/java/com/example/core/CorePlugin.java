package com.example.core;

import com.example.core.command.AdminDebugCommand;
import com.example.core.command.CommandRegistry;
import com.example.core.command.ReloadCommand;
import com.example.core.config.ConfigManager;
import com.example.core.economy.EconomyPlaceholderService;
import com.example.core.economy.EconomyService;
import com.example.core.economy.command.AdminMoneyCommand;
import com.example.core.economy.command.BalanceCommand;
import com.example.core.gui.GuiManager;
import com.example.core.hook.HookManager;
import com.example.core.listener.PlayerDataListener;
import com.example.core.service.MessageService;
import com.example.core.service.PermissionService;
import com.example.core.storage.StorageManager;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

public final class CorePlugin extends JavaPlugin {
    private ConfigManager configManager;
    private MessageService messageService;
    private PermissionService permissionService;
    private StorageManager storageManager;
    private EconomyService economyService;
    private EconomyPlaceholderService economyPlaceholderService;
    private HookManager hookManager;
    private GuiManager guiManager;
    private CommandRegistry commandRegistry;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.configManager = new ConfigManager(this);
        this.configManager.loadAll();

        this.messageService = new MessageService(this, configManager);
        this.permissionService = new PermissionService(this);
        this.storageManager = new StorageManager(this);
        this.storageManager.initialize();
        this.economyService = new EconomyService(this);
        this.economyService.initialize();
        this.economyPlaceholderService = new EconomyPlaceholderService(economyService);
        this.hookManager = new HookManager(this);
        this.guiManager = new GuiManager(this);
        this.guiManager.initialize();
        this.commandRegistry = new CommandRegistry(this);

        registerCommands();
        getServer().getPluginManager().registerEvents(new PlayerDataListener(this), this);
        hookManager.enableBuiltInHooks();

        getLogger().info("CorePlugin enabled.");
    }

    @Override
    public void onDisable() {
        if (storageManager != null) {
            storageManager.shutdown();
        }

        if (hookManager != null) {
            hookManager.disableAllHooks();
        }

        getLogger().info("CorePlugin disabled.");
    }

    public void reloadCore() {
        configManager.loadAll();
        messageService.reload();
        permissionService.reload();
        storageManager.reload();
        getLogger().log(Level.INFO, "CorePlugin reloaded.");
    }

    private void registerCommands() {
        commandRegistry.register("coredebug", new AdminDebugCommand(this));
        commandRegistry.register("corereload", new ReloadCommand(this));
        commandRegistry.register("balance", new BalanceCommand(this));
        commandRegistry.register("money", new AdminMoneyCommand(this));
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public PermissionService getPermissionService() {
        return permissionService;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public EconomyService getEconomyService() {
        return economyService;
    }

    public EconomyPlaceholderService getEconomyPlaceholderService() {
        return economyPlaceholderService;
    }

    public HookManager getHookManager() {
        return hookManager;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }
}
