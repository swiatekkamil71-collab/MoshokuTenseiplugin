package pl.axtra.moshoku.spells;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;
import pl.axtra.moshoku.models.Spell;

public class WaterMagic {
    
    public static class WaterBolt extends Spell {
        public WaterBolt() {
            super("Water Bolt", "WATER", "BEGINNER", 45, 700, 2500);
        }
        
        @Override
        public void cast(Player player, Vector direction) {
            Location loc = player.getEyeLocation();
            direction.normalize().multiply(0.5);
            
            ArmorStand projectile = player.getWorld().spawn(
                loc.clone().add(direction), 
                ArmorStand.class
            );
            projectile.setGravity(false);
            projectile.setInvisible(true);
            projectile.setSmall(true);
            
            player.playSound(loc, Sound.BLOCK_WATER_AMBIENT, 1, 1.5f);
            player.getWorld().spawnParticle(Particle.WATER_SPLASH, loc, 15, 0.3, 0.3, 0.3);
            
            int ticks = 0;
            var task = new Object() {
                int tick = 0;
                
                public void run() {
                    tick++;
                    if (tick > 200) {
                        projectile.remove();
                        return;
                    }
                    
                    projectile.setVelocity(direction.multiply(1.5));
                    player.getWorld().spawnParticle(Particle.WATER_SPLASH, projectile.getLocation(), 3);
                }
            };
        }
        
        @Override
        public void setupLevel(int level) {
            manaCost = 45 + (level * 3);
            castTime = (long) (700 - (level * 15));
        }
    }
    
    public static class IceSpike extends Spell {
        public IceSpike() {
            super("Ice Spike", "WATER", "INTERMEDIATE", 100, 900, 4000);
        }
        
        @Override
        public void cast(Player player, Vector direction) {
            Location center = player.getEyeLocation().add(direction.normalize().multiply(3));
            
            player.getWorld().spawnParticle(Particle.ITEM_SNOWBALL, center, 30, 1, 1, 1, 0.5);
            player.playSound(center, Sound.BLOCK_GLASS_BREAK, 1, 1.5f);
            
            for (Entity entity : player.getWorld().getNearbyEntities(center, 5, 5, 5)) {
                if (entity instanceof LivingEntity && !entity.equals(player)) {
                    ((LivingEntity) entity).damage(18 + (level * 2));
                    entity.setFreezeTicks(60 + (level * 10));
                }
            }
        }
        
        @Override
        public void setupLevel(int level) {
            manaCost = 100 + (level * 8);
            castTime = (long) (900 - (level * 25));
        }
    }
    
    public static class TidalWave extends Spell {
        public TidalWave() {
            super("Tidal Wave", "WATER", "ADVANCED", 250, 1800, 10000);
        }
        
        @Override
        public void cast(Player player, Vector direction) {
            Location wave = player.getLocation().add(0, 1, 0);
            
            for (int x = -8; x <= 8; x++) {
                for (int y = 0; y < 3; y++) {
                    Location waveLoc = wave.clone().add(x, y, 0);
                    player.getWorld().spawnParticle(Particle.WATER_SPLASH, waveLoc, 3);
                }
            }
            
            player.playSound(wave, Sound.ENTITY_DOLPHIN_SPLASH, 1, 0.8f);
            
            for (Entity entity : player.getWorld().getNearbyEntities(wave, 10, 5, 5)) {
                if (entity instanceof LivingEntity && !entity.equals(player)) {
                    ((LivingEntity) entity).damage(30 + (level * 4));
                    Vector knockback = entity.getLocation().toVector().subtract(wave.toVector()).normalize().multiply(1.5);
                    entity.setVelocity(knockback.setY(0.5));
                }
            }
        }
        
        @Override
        public void setupLevel(int level) {
            manaCost = 250 + (level * 25);
            castTime = (long) (1800 - (level * 40));
        }
    }
    
    public static class DeepFreezeOcean extends Spell {
        public DeepFreezeOcean() {
            super("Deep Freeze Ocean", "WATER", "SAINT", 500, 2200, 15000);
        }
        
        @Override
        public void cast(Player player, Vector direction) {
            Location center = player.getEyeLocation().add(direction.normalize().multiply(8));
            
            player.playSound(center, Sound.BLOCK_ICE_BREAK, 1, 0.5f);
            
            for (int i = 0; i < 100; i++) {
                double angle = Math.random() * Math.PI * 2;
                double radius = Math.random() * 12;
                Location loc = center.clone().add(
                    Math.cos(angle) * radius,
                    Math.random() * 8,
                    Math.sin(angle) * radius
                );
                player.getWorld().spawnParticle(Particle.ITEM_SNOWBALL, loc, 2);
            }
            
            for (Entity entity : player.getWorld().getNearbyEntities(center, 15, 10, 15)) {
                if (entity instanceof LivingEntity && !entity.equals(player)) {
                    ((LivingEntity) entity).damage(60 + (level * 6));
                    entity.setFreezeTicks(150 + (level * 20));
                }
            }
        }
        
        @Override
        public void setupLevel(int level) {
            manaCost = 500 + (level * 60);
            castTime = (long) (2200 - (level * 50));
        }
    }
    
    public static class TsunamiSlash extends Spell {
        public TsunamiSlash() {
            super("Tsunami Slash", "WATER", "KING", 1000, 2800, 25000);
        }
        
        @Override
        public void cast(Player player, Vector direction) {
            Location loc = player.getLocation().add(0, 2, 0);
            
            player.playSound(loc, Sound.ENTITY_DOLPHIN_DEATH, 1, 0.3f);
            
            for (int i = 0; i < 200; i++) {
                double angle = Math.random() * Math.PI * 2;
                double distance = 3 + Math.random() * 15;
                Location dropLoc = loc.clone().add(
                    Math.cos(angle) * distance,
                    Math.random() * 5,
                    Math.sin(angle) * distance
                );
                player.getWorld().spawnParticle(Particle.WATER_SPLASH, dropLoc, 5);
            }
            
            for (Entity entity : player.getWorld().getNearbyEntities(loc, 20, 15, 20)) {
                if (entity instanceof LivingEntity && !entity.equals(player)) {
                    ((LivingEntity) entity).damage(100 + (level * 12));
                }
            }
        }
        
        @Override
        public void setupLevel(int level) {
            manaCost = 1000 + (level * 120);
            castTime = (long) (2800 - (level * 60));
        }
    }
}