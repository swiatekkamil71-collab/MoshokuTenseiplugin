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

public class AdminCommand implements CommandExecutor {
    private final MoshokuTenseiPlugin plugin;
    
    public AdminCommand(MoshokuTenseiPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§cYou must be an operator to use this command!");
            return true;
        }
        
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        
        Player player = (Player) sender;
        openAdminPanel(player);
        return true;
    }
    
    private void openAdminPanel(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "§c✦ Admin Panel - Magic System ✦");
        
        // Player Management
        ItemStack playerMgmt = createItem(
            Material.PLAYER_HEAD,
            "§e§lPlayer Management",
            "§7Manage player stats"
        );
        inv.setItem(10, playerMgmt);
        
        // Magic Level Control
        ItemStack magicControl = createItem(
            Material.ENCHANTED_BOOK,
            "§5§lMagic Level Control",
            "§7Set magic levels"
        );
        inv.setItem(12, magicControl);
        
        // Mana Management
        ItemStack manaMgmt = createItem(
            Material.LAPIS_LAZULI,
            "§9§lMana Management",
            "§7Set max mana",
            "§7Set current mana",
            "§7Set mana regen"
        );
        inv.setItem(14, manaMgmt);
        
        // School Levels
        ItemStack schoolLevels = createItem(
            Material.ENCHANTING_TABLE,
            "§5§lSchool Levels",
            "§7Fire: Click to modify",
            "§7Water: Click to modify",
            "§7Earth: Click to modify",
            "§7Wind: Click to modify"
        );
        inv.setItem(16, schoolLevels);
        
        // Spell Unlocking
        ItemStack spellUnlock = createItem(
            Material.RECOVERY_COMPASS,
            "§c§lSpell Unlocking",
            "§7Unlock spells for players"
        );
        inv.setItem(19, spellUnlock);
        
        // Specialization Setting
        ItemStack specSet = createItem(
            Material.AMETHYST_SHARD,
            "§d§lSpecialization",
            "§7Set player specialization"
        );
        inv.setItem(21, specSet);
        
        // Artifact Management
        ItemStack artifactMgmt = createItem(
            Material.DIAMOND,
            "§b§lArtifacts",
            "§7Give artifacts to players"
        );
        inv.setItem(23, artifactMgmt);
        
        // Title Management
        ItemStack titleMgmt = createItem(
            Material.ENDER_EYE,
            "§6§lTitles",
            "§7Assign titles to players"
        );
        inv.setItem(25, titleMgmt);
        
        // Infinite Mana Toggle
        ItemStack infiniteMana = createItem(
            Material.BLUE_CONCRETE,
            "§3§lInfinite Mana",
            "§7Enable/Disable infinite mana"
        );
        inv.setItem(28, infiniteMana);
        
        // Cooldown Remover
        ItemStack cooldownRemover = createItem(
            Material.BARRIER,
            "§c§lRemove Cooldowns",
            "§7Clear all spell cooldowns"
        );
        inv.setItem(30, cooldownRemover);
        
        // Cast Speed Multiplier
        ItemStack castSpeedMult = createItem(
            Material.LIGHTNING_ROD,
            "§e§lCast Speed Multiplier",
            "§7Modify cast speed"
        );
        inv.setItem(32, castSpeedMult);
        
        // Reset Character
        ItemStack resetChar = createItem(
            Material.RED_CONCRETE,
            "§4§lReset Character",
            "§cWARNING: This cannot be undone!",
            "§7Reset all player progress"
        );
        inv.setItem(34, resetChar);
        
        // Combat Level Control
        ItemStack combatControl = createItem(
            Material.DIAMOND_SWORD,
            "§c§lCombat Level",
            "§7Set combat level"
        );
        inv.setItem(37, combatControl);
        
        // Boss Spawning
        ItemStack bossSpawn = createItem(
            Material.WITHER_SKELETON_SKULL,
            "§4§lBoss Spawning",
            "§7Spawn bosses for testing"
        );
        inv.setItem(39, bossSpawn);
        
        // Experience Control
        ItemStack expControl = createItem(
            Material.EXPERIENCE_BOTTLE,
            "§2§lExperience Control",
            "§7Give magic EXP",
            "§7Give school EXP",
            "§7Give combat EXP"
        );
        inv.setItem(41, expControl);
        
        // Teleport to Players
        ItemStack teleport = createItem(
            Material.ENDER_PEARL,
            "§5§lTeleport to Players",
            "§7Fast admin teleport"
        );
        inv.setItem(43, teleport);
        
        // Server Settings
        ItemStack settings = createItem(
            Material.REDSTONE,
            "§8§lServer Settings",
            "§7Plugin configuration"
        );
        inv.setItem(45, settings);
        
        // Information
        ItemStack info = createItem(
            Material.WRITABLE_BOOK,
            "§f§lPlugin Information",
            "§7Version: 1.0.0",
            "§7Author: axtr_a",
            "§7Status: §aRunning"
        );
        inv.setItem(50, info);
        
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