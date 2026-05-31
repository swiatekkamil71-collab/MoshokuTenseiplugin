package pl.axtra.moshoku.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import pl.axtra.moshoku.MoshokuTenseiPlugin;
import pl.axtra.moshoku.models.MageData;

import java.util.ArrayList;
import java.util.List;

public class MagePanelCommand implements CommandExecutor {
    private final MoshokuTenseiPlugin plugin;
    
    public MagePanelCommand(MoshokuTenseiPlugin plugin) {
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
        
        openMainPanel(player, data);
        return true;
    }
    
    private void openMainPanel(Player player, MageData data) {
        Inventory inv = Bukkit.createInventory(null, 54, "§6✦ Mage Panel ✦");
        
        // Character Info
        ItemStack characterInfo = createItem(
            Material.PLAYER_HEAD,
            "§e§lCharacter Information",
            "§7Level: §a" + data.getCharacterLevel(),
            "§7Magic Level: §b" + data.getMagicLevel(),
            "§7Combat Level: §c" + data.getCombatLevel(),
            "§7Title: §d" + data.getCurrentTitle()
        );
        inv.setItem(11, characterInfo);
        
        // Mana Status
        ItemStack manaStatus = createItem(
            Material.LAPIS_LAZULI,
            "§9§lMana Status",
            "§7Current: §b" + String.format("%.0f", data.getCurrentMana()),
            "§7Maximum: §3" + String.format("%.0f", data.getMaxMana()),
            "§7Regeneration: §b" + String.format("%.2f", data.getManaRegenPerSecond()) + " /s",
            "§7Percentage: §3" + String.format("%.1f%%", (data.getCurrentMana() / data.getMaxMana()) * 100)
        );
        inv.setItem(13, manaStatus);
        
        // Magic Schools
        ItemStack schools = createItem(
            Material.ENCHANTING_TABLE,
            "§5§lMagic Schools",
            "§7Fire Magic: §eLevel " + data.getSchoolLevel("FIRE"),
            "§7Water Magic: §3Level " + data.getSchoolLevel("WATER"),
            "§7Earth Magic: §6Level " + data.getSchoolLevel("EARTH"),
            "§7Wind Magic: §bLevel " + data.getSchoolLevel("WIND")
        );
        inv.setItem(15, schools);
        
        // Specialization
        ItemStack specialization = createItem(
            Material.AMETHYST_SHARD,
            "§d§lSpecialization",
            "§7Type: §5" + data.getSpecialization(),
            "§7Bonus: §d" + String.format("%.1f%%", (data.getSpecializationBonus() - 1) * 100)
        );
        inv.setItem(29, specialization);
        
        // Cast Speed
        ItemStack castSpeed = createItem(
            Material.LIGHTNING_ROD,
            "§c§lCast Speed",
            "§7Multiplier: §6" + String.format("%.2f", data.getCastSpeedMultiplier()) + "x",
            "§7Silent Casting: " + (data.getCastSpeedMultiplier() > 1.5 ? "§a✓ Enabled" : "§cLocked")
        );
        inv.setItem(31, castSpeed);
        
        // Artifacts
        ItemStack artifacts = createItem(
            Material.DIAMOND,
            "§b§lArtifacts",
            "§7Count: §b" + data.getArtifacts().size()
        );
        inv.setItem(33, artifacts);
        
        // Titles
        ItemStack titles = createItem(
            Material.ENDER_EYE,
            "§6§lTitles",
            "§7Unlocked: §e" + data.getTitles().size(),
            "§7Current: §6" + data.getCurrentTitle()
        );
        inv.setItem(35, titles);
        
        // Detailed Schools
        ItemStack detailedSchools = createItem(
            Material.BOOK,
            "§e§lDetailed Magic Schools",
            "§7Click to see all schools"
        );
        inv.setItem(40, detailedSchools);
        
        // Combat Stats
        ItemStack combatStats = createItem(
            Material.DIAMOND_SWORD,
            "§c§lCombat Statistics",
            "§7Combat EXP: §c" + data.getCombatExp(),
            "§7Fights Won: §cN/A"
        );
        inv.setItem(42, combatStats);
        
        // Statistics
        ItemStack stats = createItem(
            Material.WRITTEN_BOOK,
            "§f§lStatistics",
            "§7Spells Cast: §fN/A",
            "§7Bosses Defeated: §fN/A",
            "§7Hours Played: §fN/A"
        );
        inv.setItem(44, stats);
        
        // Credits
        ItemStack credits = createItem(
            Material.NAME_TAG,
            "§d§lCredits",
            "§7Plugin Creator: §daxtr_a",
            "§7Inspired by: §dMushoku Tensei"
        );
        inv.setItem(49, credits);
        
        player.openInventory(inv);
    }
    
    private ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            List<String> loreList = new ArrayList<>();
            for (String line : lore) {
                loreList.add(line);
            }
            meta.setLore(loreList);
            item.setItemMeta(meta);
        }
        return item;
    }
}