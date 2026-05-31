package pl.axtra.moshoku.models;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public abstract class Spell {
    protected String name;
    protected String school;
    protected String rank;
    protected double manaCost;
    protected long castTime; // in milliseconds
    protected long cooldown; // in milliseconds
    protected int level;
    
    public Spell(String name, String school, String rank, double manaCost, long castTime, long cooldown) {
        this.name = name;
        this.school = school;
        this.rank = rank;
        this.manaCost = manaCost;
        this.castTime = castTime;
        this.cooldown = cooldown;
        this.level = 1;
    }
    
    public abstract void cast(Player player, Vector direction);
    public abstract void setupLevel(int level);
    
    // Getters
    public String getName() { return name; }
    public String getSchool() { return school; }
    public String getRank() { return rank; }
    public double getManaCost() { return manaCost; }
    public long getCastTime() { return castTime; }
    public long getCooldown() { return cooldown; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; setupLevel(level); }
    
    @Override
    public String toString() {
        return name + " [" + rank + "]"; 
    }
}