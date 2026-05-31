package pl.axtra.moshoku.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.axtra.moshoku.MoshokuTenseiPlugin;
import pl.axtra.moshoku.models.MageData;

public class PlayerJoinListener implements Listener {
    private final MoshokuTenseiPlugin plugin;
    
    public PlayerJoinListener(MoshokuTenseiPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        MageData data = plugin.getPlayerDataManager().getOrCreateMageData(player);
        
        player.sendMessage("§6═════════════════════════════════════");
        player.sendMessage("§6Mushoku Tensei Magic System");
        player.sendMessage("§6Credits: axtr_a");
        player.sendMessage("§6Use /mpanel to open your character panel");
        player.sendMessage("§6═════════════════════════════════════");
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getPlayerDataManager().unloadPlayer(player);
    }
}