package pl.axtra.moshoku.managers;

import org.bukkit.entity.Player;
import pl.axtra.moshoku.MoshokuTenseiPlugin;
import pl.axtra.moshoku.models.MageData;

import java.io.*;
import java.util.*;

public class PlayerDataManager {
    private final MoshokuTenseiPlugin plugin;
    private final Map<UUID, MageData> playerData = new HashMap<>();
    private final File dataFolder;
    
    public PlayerDataManager(MoshokuTenseiPlugin plugin) {
        this.plugin = plugin;
        this.dataFolder = new File(plugin.getDataFolder(), "playerdata");
        
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }
    
    public MageData getOrCreateMageData(Player player) {
        UUID uuid = player.getUniqueId();
        
        if (playerData.containsKey(uuid)) {
            return playerData.get(uuid);
        }
        
        MageData data = loadPlayerData(uuid, player.getName());
        if (data == null) {
            data = new MageData(player.getName(), uuid);
        }
        
        playerData.put(uuid, data);
        return data;
    }
    
    public void savePlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        MageData data = playerData.get(uuid);
        
        if (data == null) return;
        
        File playerFile = new File(dataFolder, uuid + ".dat");
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(playerFile))) {
            oos.writeObject(data);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save player data for " + player.getName());
            e.printStackTrace();
        }
    }
    
    public MageData loadPlayerData(UUID uuid, String playerName) {
        File playerFile = new File(dataFolder, uuid + ".dat");
        
        if (!playerFile.exists()) {
            return null;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(playerFile))) {
            return (MageData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            plugin.getLogger().warning("Failed to load player data for " + playerName);
            return null;
        }
    }
    
    public void saveAllPlayers() {
        for (UUID uuid : playerData.keySet()) {
            Player player = plugin.getServer().getPlayer(uuid);
            if (player != null) {
                savePlayerData(player);
            }
        }
    }
    
    public MageData getMageData(Player player) {
        return playerData.get(player.getUniqueId());
    }
    
    public void unloadPlayer(Player player) {
        savePlayerData(player);
        playerData.remove(player.getUniqueId());
    }
}