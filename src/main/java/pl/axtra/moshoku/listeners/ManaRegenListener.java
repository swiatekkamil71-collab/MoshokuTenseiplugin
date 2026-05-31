package pl.axtra.moshoku.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.axtra.moshoku.MoshokuTenseiPlugin;
import pl.axtra.moshoku.models.MageData;

public class ManaRegenListener implements Listener {
    private final MoshokuTenseiPlugin plugin;
    
    public ManaRegenListener(MoshokuTenseiPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        MageData data = plugin.getPlayerDataManager().getMageData(player);
        
        if (data != null) {
            plugin.getManaManager().updateManaBar(player, data);
        }
    }
}