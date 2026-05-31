package pl.axtra.moshoku.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.axtra.moshoku.MoshokuTenseiPlugin;
import pl.axtra.moshoku.models.MageData;

public class ManaRegenCommand implements CommandExecutor {
    private final MoshokuTenseiPlugin plugin;
    
    public ManaRegenCommand(MoshokuTenseiPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§cYou must be an operator!");
            return true;
        }
        
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /manaregen <player> <amount>");
            return true;
        }
        
        Player target = org.bukkit.Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cPlayer not found!");
            return true;
        }
        
        try {
            double amount = Double.parseDouble(args[1]);
            MageData data = plugin.getPlayerDataManager().getMageData(target);
            if (data != null) {
                data.setManaRegenPerSecond(amount);
                sender.sendMessage("§aSet " + target.getName() + "'s mana regen to " + amount);
            }
        } catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid number!");
        }
        
        return true;
    }
}