package pl.axtra.moshoku.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.axtra.moshoku.MoshokuTenseiPlugin;
import pl.axtra.moshoku.models.MageData;

public class SpellbookCommand implements CommandExecutor {
    private final MoshokuTenseiPlugin plugin;
    
    public SpellbookCommand(MoshokuTenseiPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        
        Player player = (Player) sender;
        MageData data = plugin.getPlayerDataManager().getMageData(player);
        
        if (data == null) {
            player.sendMessage("§cYour character data is not loaded!");
            return true;
        }
        
        // For now, open mpanel as spellbook is integrated
        player.performCommand("mpanel");
        return true;
    }
}