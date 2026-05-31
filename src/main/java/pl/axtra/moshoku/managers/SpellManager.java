package pl.axtra.moshoku.managers;

import pl.axtra.moshoku.MoshokuTenseiPlugin;
import pl.axtra.moshoku.models.Spell;
import pl.axtra.moshoku.spells.FireMagic;
import pl.axtra.moshoku.spells.WaterMagic;

import java.util.*;

public class SpellManager {
    private final MoshokuTenseiPlugin plugin;
    private final Map<String, Spell> spells = new HashMap<>();
    
    public SpellManager(MoshokuTenseiPlugin plugin) {
        this.plugin = plugin;
        registerSpells();
    }
    
    private void registerSpells() {
        // Fire spells
        registerSpell("fireball", new FireMagic.Fireball());
        registerSpell("flame_burst", new FireMagic.FlammeBurst());
        registerSpell("flame_wall", new FireMagic.FlameWall());
        registerSpell("inferno_storm", new FireMagic.InfernoStorm());
        registerSpell("volcanic_eruption", new FireMagic.VolcanicEruption());
        registerSpell("meteor_rain", new FireMagic.MeteorRain());
        registerSpell("emperor_inferno", new FireMagic.EmperorInferno());
        registerSpell("god_flame_cataclysm", new FireMagic.GodFlameCataclysm());
        
        // Water spells
        registerSpell("water_bolt", new WaterMagic.WaterBolt());
        registerSpell("ice_spike", new WaterMagic.IceSpike());
        registerSpell("tidal_wave", new WaterMagic.TidalWave());
        registerSpell("deep_freeze_ocean", new WaterMagic.DeepFreezeOcean());
        registerSpell("tsunami_slash", new WaterMagic.TsunamiSlash());
    }
    
    public void registerSpell(String id, Spell spell) {
        spells.put(id.toLowerCase(), spell);
    }
    
    public Spell getSpell(String id) {
        return spells.get(id.toLowerCase());
    }
    
    public Collection<Spell> getAllSpells() {
        return spells.values();
    }
    
    public Map<String, Spell> getSpellsBySchool(String school) {
        Map<String, Spell> schoolSpells = new HashMap<>();
        for (Map.Entry<String, Spell> entry : spells.entrySet()) {
            if (entry.getValue().getSchool().equals(school)) {
                schoolSpells.put(entry.getKey(), entry.getValue());
            }
        }
        return schoolSpells;
    }
}