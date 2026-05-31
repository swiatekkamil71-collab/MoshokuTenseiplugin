package pl.axtra.moshoku.spells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;
import pl.axtra.moshoku.models.Spell;
import pl.axtra.moshoku.MoshokuTenseiPlugin;

public class FireMagic {
    
    public static class Fireball extends Spell {
        public Fireball() {
            super("Fireball", "FIRE", "BEGINNER", 50, 800, 3000);
        }
        
        @Override
        public void cast(Player player, Vector direction) {
            Location loc = player.getEyeLocation();
            direction.normalize().multiply(0.5);
            
            Fireball fireball = player.getWorld().spawn(loc.clone().add(direction), Fireball.class);
            fireball.setVelocity(direction.multiply(2));
            fireball.setExplosionPower(2);
            
            player.playSound(loc, Sound.ENTITY_BLAZE_SHOOT, 1, 1);
            player.getWorld().spawnParticle(Particle.FLAME, loc, 20, 0.5, 0.5, 0.5, 0.1);
        }
        
        @Override
        public void setupLevel(int level) {
            manaCost = 50 + (level * 5);
            castTime = (long) (800 - (level * 20));
        }
    }
    
    public static class FlammeBurst extends Spell {
        public FlammeBurst() {
            super("Flame Burst", "FIRE", "INTERMEDIATE", 120, 1000, 5000);
        }
        
        @Override
        public void cast(Player player, Vector direction) {
            Location center = player.getEyeLocation().add(direction.normalize().multiply(3));
            
            player.getWorld().spawnParticle(Particle.FLAME, center, 100, 2, 2, 2, 0.2);
            player.getWorld().spawnParticle(Particle.SMOKE, center, 50, 2.5, 2.5, 2.5);
            player.playSound(center, Sound.ENTITY_BLAZE_DEATH, 1, 0.5f);
            
            for (Entity entity : player.getWorld().getNearbyEntities(center, 5, 5, 5)) {
                if (entity instanceof LivingEntity && !entity.equals(player)) {
                    ((LivingEntity) entity).damage(15 + (level * 2), player);
                    entity.setFireTicks(60 + (level * 10));
                }
            }
        }
        
        @Override
        public void setupLevel(int level) {
            manaCost = 120 + (level * 10);
            castTime = (long) (1000 - (level * 30));
        }
    }
    
    public static class FlameWall extends Spell {
        public FlameWall() {
            super("Flame Wall", "FIRE", "ADVANCED", 200, 1500, 8000);
        }
        
        @Override
        public void cast(Player player, Vector direction) {
            Location center = player.getLocation().add(0, 1, 0);
            
            // Create wall
            for (int i = -3; i <= 3; i++) {
                for (int j = 0; j < 4; j++) {
                    Location wallLoc = center.clone().add(i, j, 0);
                    player.getWorld().spawnParticle(Particle.FLAME, wallLoc, 5);
                }
            }
            
            player.playSound(center, Sound.BLOCK_FIRE_AMBIENT, 1, 1);
            
            // Damage nearby entities
            for (Entity entity : player.getWorld().getNearbyEntities(center, 8, 5, 2)) {
                if (entity instanceof LivingEntity && !entity.equals(player)) {
                    ((LivingEntity) entity).damage(25 + (level * 3));
                }
            }
        }
        
        @Override
        public void setupLevel(int level) {
            manaCost = 200 + (level * 20);
            castTime = (long) (1500 - (level * 40));
        }
    }
    
    public static class InfernoStorm extends Spell {
        public InfernoStorm() {
            super("Inferno Storm", "FIRE", "SAINT", 400, 2000, 12000);
        }
        
        @Override
        public void cast(Player player, Vector direction) {
            Location center = player.getEyeLocation().add(direction.normalize().multiply(8));
            
            player.playSound(center, Sound.ENTITY_WARDEN_ROAR, 1, 0.5f);
            
            // Massive particle effect
            for (int i = 0; i < 50; i++) {
                double angle = Math.random() * Math.PI * 2;
                double radius = Math.random() * 10;
                Location loc = center.clone().add(
                    Math.cos(angle) * radius,
                    Math.random() * 5,
                    Math.sin(angle) * radius
                );
                player.getWorld().spawnParticle(Particle.FLAME, loc, 1);
            }
            
            // Damage entities in large radius
            for (Entity entity : player.getWorld().getNearbyEntities(center, 15, 10, 15)) {
                if (entity instanceof LivingEntity && !entity.equals(player)) {
                    ((LivingEntity) entity).damage(50 + (level * 5));
                    entity.setFireTicks(100 + (level * 20));
                }
            }
        }
        
        @Override
        public void setupLevel(int level) {
            manaCost = 400 + (level * 50);
            castTime = (long) (2000 - (level * 50));
        }
    }
    
    public static class VolcanicEruption extends Spell {
        public VolcanicEruption() {
            super("Volcanic Eruption", "FIRE", "KING", 800, 2500, 20000);
        }
        
        @Override
        public void cast(Player player, Vector direction) {
            Location center = player.getLocation().add(0, -1, 0);
            
            player.playSound(center, Sound.ENTITY_WARDEN_ROAR, 2, 0.3f);
            
            // Massive eruption effect
            for (int y = 0; y < 20; y++) {
                for (int i = 0; i < 30; i++) {
                    double angle = Math.random() * Math.PI * 2;
                    double radius = Math.random() * 15;
                    Location loc = center.clone().add(
                        Math.cos(angle) * radius,
                        y,
                        Math.sin(angle) * radius
                    );
                    player.getWorld().spawnParticle(Particle.LAVA, loc, 2);
                    player.getWorld().spawnParticle(Particle.FLAME, loc, 3);
                }
            }
            
            for (Entity entity : player.getWorld().getNearbyEntities(center, 20, 20, 20)) {
                if (entity instanceof LivingEntity && !entity.equals(player)) {
                    ((LivingEntity) entity).damage(80 + (level * 10));
                    entity.setFireTicks(150 + (level * 30));
                }
            }
        }
        
        @Override
        public void setupLevel(int level) {
            manaCost = 800 + (level * 100);
            castTime = (long) (2500 - (level * 60));
        }
    }
    
    public static class MeteorRain extends Spell {
        public MeteorRain() {
            super("Meteor Rain", "FIRE", "EMPEROR", 1500, 3000, 30000);
        }
        
        @Override
        public void cast(Player player, Vector direction) {
            Location center = player.getLocation().add(0, 30, 0);
            
            player.playSound(center, Sound.ENTITY_WARDEN_ROAR, 2, 0.1f);
            
            // Meteor shower
            for (int i = 0; i < 20; i++) {
                double x = (Math.random() - 0.5) * 40;
                double z = (Math.random() - 0.5) * 40;
                Location meteorLoc = center.clone().add(x, 0, z);
                
                Fireball meteor = player.getWorld().spawn(meteorLoc, Fireball.class);
                meteor.setVelocity(new Vector(0, -2, 0));
                meteor.setExplosionPower(4);
            }
            
            for (Entity entity : player.getWorld().getNearbyEntities(center, 30, 30, 30)) {
                if (entity instanceof LivingEntity && !entity.equals(player)) {
                    ((LivingEntity) entity).damage(120 + (level * 20));
                }
            }
        }
        
        @Override
        public void setupLevel(int level) {
            manaCost = 1500 + (level * 200);
            castTime = (long) (3000 - (level * 70));
        }
    }
    
    public static class EmperorInferno extends Spell {
        public EmperorInferno() {
            super("Emperor Inferno", "FIRE", "GOD", 3000, 4000, 60000);
        }
        
        @Override
        public void cast(Player player, Vector direction) {
            Location center = player.getEyeLocation().add(direction.normalize().multiply(15));
            
            player.playSound(center, Sound.ENTITY_WARDEN_ROAR, 2, 0);
            player.playSound(center, Sound.ENTITY_DRAGON_DEATH, 2, 0.5f);
            
            // Epic particle show
            for (int i = 0; i < 200; i++) {
                double angle = Math.random() * Math.PI * 2;
                double radius = Math.random() * 25;
                double height = Math.random() * 30;
                Location loc = center.clone().add(
                    Math.cos(angle) * radius,
                    height - 15,
                    Math.sin(angle) * radius
                );
                player.getWorld().spawnParticle(Particle.FLAME, loc, 5);
                player.getWorld().spawnParticle(Particle.LAVA, loc, 3);
            }
            
            for (Entity entity : player.getWorld().getNearbyEntities(center, 40, 40, 40)) {
                if (entity instanceof LivingEntity && !entity.equals(player)) {
                    ((LivingEntity) entity).damage(200 + (level * 50));
                }
            }
        }
        
        @Override
        public void setupLevel(int level) {
            manaCost = 3000 + (level * 500);
            castTime = (long) (4000 - (level * 100));
        }
    }
    
    public static class GodFlameCataclysm extends Spell {
        public GodFlameCataclysm() {
            super("God Flame Cataclysm", "FIRE", "GOD", 5000, 5000, 120000);
        }
        
        @Override
        public void cast(Player player, Vector direction) {
            Location center = player.getLocation();
            
            player.playSound(center, Sound.ENTITY_WARDEN_ROAR, 2, 0);
            player.playSound(center, Sound.ENTITY_DRAGON_DEATH, 2, 0.1f);
            player.getWorld().createExplosion(center, 10, false, true);
            
            // Cataclysm effect - massive destruction
            for (int i = 0; i < 500; i++) {
                double angle = Math.random() * Math.PI * 2;
                double radius = Math.random() * 50;
                double height = Math.random() * 40;
                Location loc = center.clone().add(
                    Math.cos(angle) * radius,
                    height - 20,
                    Math.sin(angle) * radius
                );
                player.getWorld().spawnParticle(Particle.FLAME, loc, 10);
                player.getWorld().spawnParticle(Particle.LAVA, loc, 5);
                player.getWorld().spawnParticle(Particle.SMOKE, loc, 8);
            }
            
            for (Entity entity : player.getWorld().getNearbyEntities(center, 60, 60, 60)) {
                if (entity instanceof LivingEntity && !entity.equals(player)) {
                    ((LivingEntity) entity).damage(300 + (level * 100));
                }
            }
        }
        
        @Override
        public void setupLevel(int level) {
            manaCost = 5000 + (level * 1000);
            castTime = (long) (5000 - (level * 150));
        }
    }
}