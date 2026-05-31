package pl.axtra.moshoku;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.axtra.moshoku.commands.AdminCommand;
import pl.axtra.moshoku.commands.ManaRegenCommand;
import pl.axtra.moshoku.commands.MagePanelCommand;
import pl.axtra.moshoku.commands.SpellbookCommand;
import pl.axtra.moshoku.listeners.PlayerJoinListener;
import pl.axtra.moshoku.listeners.ManaRegenListener;
import pl.axtra.moshoku.managers.PlayerDataManager;
import pl.axtra.moshoku.managers.ManaManager;
import pl.axtra.moshoku.managers.SpellManager;

public class MoshokuTenseiPlugin extends JavaPlugin {
    private static MoshokuTenseiPlugin instance;
    private PlayerDataManager playerDataManager;
    private ManaManager manaManager;
    private SpellManager spellManager;

    @Override
    public void onEnable() {
        instance = this;
        
        getLogger().info("═════════════════════════════════════");
        getLogger().info("Mushoku Tensei Magic System v1.0.0");
        getLogger().info("Credits: axtr_a");
        getLogger().info("═════════════════════════════════════");
        
        // Initialize managers
        playerDataManager = new PlayerDataManager(this);
        manaManager = new ManaManager(this);
        spellManager = new SpellManager(this);
        
        // Register listeners
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ManaRegenListener(this), this);
        
        // Register commands
        getCommand("mpanel").setExecutor(new MagePanelCommand(this));
        getCommand("madmin").setExecutor(new AdminCommand(this));
        getCommand("spellbook").setExecutor(new SpellbookCommand(this));
        getCommand("manaregen").setExecutor(new ManaRegenCommand(this));
        
        getLogger().info("✓ Plugin loaded successfully!");
        getLogger().info("✓ Magic System initialized!");
    }

    @Override
    public void onDisable() {
        playerDataManager.saveAllPlayers();
        getLogger().info("✓ Plugin disabled!");
    }

    public static MoshokuTenseiPlugin getInstance() {
        return instance;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public ManaManager getManaManager() {
        return manaManager;
    }

    public SpellManager getSpellManager() {
        return spellManager;
    }
}