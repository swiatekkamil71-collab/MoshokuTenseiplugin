package pl.axtra.moshoku.models;

import java.util.*;

public class MageData {
    private String playerName;
    private UUID playerUUID;
    
    // Core Stats
    private int characterLevel = 1;
    private long characterExp = 0;
    private int magicLevel = 1;
    private long magicExp = 0;
    private int combatLevel = 1;
    private long combatExp = 0;
    
    // Mana System
    private double currentMana = 100;
    private double maxMana = 100;
    private double manaRegenPerSecond = 2.0;
    
    // Magic Schools
    private Map<String, Integer> schoolLevels = new HashMap<>();
    private Map<String, Long> schoolExp = new HashMap<>();
    private Map<String, String> schoolRanks = new HashMap<>();
    private Map<String, Set<String>> unlockedSpells = new HashMap<>();
    
    // Specializations
    private String specialization = "BALANCED"; // FIRE_MAGE, WATER_MAGE, BATTLE_MAGE, etc.
    private double specializationBonus = 1.0;
    
    // Artifacts
    private List<String> artifacts = new ArrayList<>();
    
    // Titles
    private List<String> titles = new ArrayList<>();
    private String currentTitle = "Novice Mage";
    
    // Cast Speed
    private double castSpeedMultiplier = 1.0;
    
    // Skills & Cooldowns
    private Map<String, Long> spellCooldowns = new HashMap<>();
    private Map<String, Integer> skillLevels = new HashMap<>();
    
    public MageData(String playerName, UUID playerUUID) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        initializeSchools();
    }
    
    private void initializeSchools() {
        String[] schools = {
            "FIRE", "WATER", "EARTH", "WIND", "HEALING",
            "DIVINE", "LIGHTNING", "ICE", "SUMMONING", 
            "BARRIER", "GRAVITY", "ARCANE"
        };
        
        for (String school : schools) {
            schoolLevels.put(school, 1);
            schoolExp.put(school, 0L);
            schoolRanks.put(school, "BEGINNER");
            unlockedSpells.put(school, new HashSet<>());
        }
    }
    
    // Mana Management
    public void addMana(double amount) {
        currentMana = Math.min(currentMana + amount, maxMana);
    }
    
    public void removeMana(double amount) {
        currentMana = Math.max(currentMana - amount, 0);
    }
    
    public boolean canSpendMana(double amount) {
        return currentMana >= amount;
    }
    
    public void increaseMana(double amount) {
        maxMana += amount;
        currentMana = Math.min(currentMana + amount, maxMana);
    }
    
    // Experience Management
    public void addMagicExp(long amount) {
        magicExp += amount;
        long nextLevel = (long) (1000 * Math.pow(1.1, magicLevel - 1));
        if (magicExp >= nextLevel) {
            magicLevel++;
            magicExp = 0;
            maxMana *= 1.15;
            manaRegenPerSecond *= 1.05;
        }
    }
    
    public void addSchoolExp(String school, long amount) {
        if (!schoolExp.containsKey(school)) return;
        
        long current = schoolExp.get(school);
        long bonus = (long) (amount * (specialization.contains(school) ? 1.3 : 1.0));
        schoolExp.put(school, current + bonus);
        
        long nextLevel = (long) (500 * Math.pow(1.1, schoolLevels.get(school) - 1));
        if (schoolExp.get(school) >= nextLevel) {
            int level = schoolLevels.get(school) + 1;
            schoolLevels.put(school, level);
            schoolExp.put(school, 0L);
            updateRank(school);
        }
    }
    
    private void updateRank(String school) {
        int level = schoolLevels.get(school);
        String rank;
        
        if (level <= 10) rank = "BEGINNER";
        else if (level <= 25) rank = "INTERMEDIATE";
        else if (level <= 50) rank = "ADVANCED";
        else if (level <= 75) rank = "SAINT";
        else if (level <= 100) rank = "KING";
        else if (level <= 150) rank = "EMPEROR";
        else rank = "GOD";
        
        schoolRanks.put(school, rank);
    }
    
    // Getters and Setters
    public String getPlayerName() { return playerName; }
    public UUID getPlayerUUID() { return playerUUID; }
    
    public int getCharacterLevel() { return characterLevel; }
    public void setCharacterLevel(int level) { this.characterLevel = level; }
    
    public long getCharacterExp() { return characterExp; }
    public void addCharacterExp(long amount) { this.characterExp += amount; }
    
    public int getMagicLevel() { return magicLevel; }
    public long getMagicExp() { return magicExp; }
    
    public int getCombatLevel() { return combatLevel; }
    public void setCombatLevel(int level) { this.combatLevel = level; }
    
    public double getCurrentMana() { return currentMana; }
    public void setCurrentMana(double mana) { this.currentMana = Math.min(mana, maxMana); }
    
    public double getMaxMana() { return maxMana; }
    public void setMaxMana(double mana) { this.maxMana = mana; }
    
    public double getManaRegenPerSecond() { return manaRegenPerSecond; }
    public void setManaRegenPerSecond(double regen) { this.manaRegenPerSecond = regen; }
    
    public int getSchoolLevel(String school) {
        return schoolLevels.getOrDefault(school, 1);
    }
    
    public void setSchoolLevel(String school, int level) {
        schoolLevels.put(school, level);
        updateRank(school);
    }
    
    public String getSchoolRank(String school) {
        return schoolRanks.getOrDefault(school, "BEGINNER");
    }
    
    public Set<String> getUnlockedSpells(String school) {
        return unlockedSpells.getOrDefault(school, new HashSet<>());
    }
    
    public void unlockSpell(String school, String spell) {
        unlockedSpells.computeIfAbsent(school, k -> new HashSet<>()).add(spell);
    }
    
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String spec) { this.specialization = spec; }
    
    public double getSpecializationBonus() { return specializationBonus; }
    public void setSpecializationBonus(double bonus) { this.specializationBonus = bonus; }
    
    public List<String> getArtifacts() { return artifacts; }
    public void addArtifact(String artifact) { artifacts.add(artifact); }
    
    public List<String> getTitles() { return titles; }
    public String getCurrentTitle() { return currentTitle; }
    public void setCurrentTitle(String title) { this.currentTitle = title; }
    
    public double getCastSpeedMultiplier() { return castSpeedMultiplier; }
    public void setCastSpeedMultiplier(double multiplier) { this.castSpeedMultiplier = multiplier; }
    
    public Map<String, Long> getSpellCooldowns() { return spellCooldowns; }
    public void setSpellCooldown(String spell, long cooldown) {
        spellCooldowns.put(spell, System.currentTimeMillis() + cooldown);
    }
    
    public boolean isSpellReady(String spell) {
        Long cooldown = spellCooldowns.get(spell);
        return cooldown == null || System.currentTimeMillis() >= cooldown;
    }
}