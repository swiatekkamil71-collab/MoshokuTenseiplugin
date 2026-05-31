package pl.axtra.moshoku.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.axtra.moshoku.MoshokuTenseiPlugin;
import pl.axtra.moshoku.models.MageData;

public class ManaManager {
    private final MoshokuTenseiPlugin plugin;
    
    public ManaManager(MoshokuTenseiPlugin plugin) {
        this.plugin = plugin;
        startManaRegeneration();
    }
    
    private void startManaRegeneration() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    MageData data = plugin.getPlayerDataManager().getMageData(player);
                    if (data != null) {
                        double regen = data.getManaRegenPerSecond() / 20.0; // 20 ticks per second
                        data.addMana(regen);
                        updateManaBar(player, data);
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }
    
    public void updateManaBar(Player player, MageData data) {
        double percentage = data.getCurrentMana() / data.getMaxMana();
        int manaBlocks = (int) (percentage * 20);
        
        StringBuilder manaBar = new StringBuilder();
        manaBar.append("§b");
        for (int i = 0; i < manaBlocks; i++) manaBar.append("█");
        manaBar.append("§7");
        for (int i = 0; i < 20 - manaBlocks; i++) manaBar.append("█");
        
        String actionBar = String.format("%s [%.0f/%.0f]", 
            manaBar.toString(), 
            data.getCurrentMana(), 
            data.getMaxMana()
        );
        
        player.sendActionBar(actionBar);
    }
    
    public boolean canCastSpell(Player player, double manaCost) {
        MageData data = plugin.getPlayerDataManager().getMageData(player);
        return data != null && data.canSpendMana(manaCost);
    }
    
    public void spendMana(Player player, double amount) {
        MageData data = plugin.getPlayerDataManager().getMageData(player);
        if (data != null) {
            data.removeMana(amount);
        }
    }
}