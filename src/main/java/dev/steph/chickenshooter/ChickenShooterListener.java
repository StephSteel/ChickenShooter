package dev.steph.chickenshooter;

import org.bukkit.*;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class ChickenShooterListener implements Listener {

    private final ChickenShooter plugin;

    public ChickenShooterListener(ChickenShooter plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

        ItemStack bow = new ItemStack(Material.BOW, 1);
        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Chicken Shooter");

        bowMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "chickenShooter"), PersistentDataType.INTEGER, 1);

        bow.setItemMeta(bowMeta);

        player.getInventory().addItem(bow);

    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent e){
        if(!(e.getEntity() instanceof  Player)){
            return;
        }

        ItemMeta bowMeta = e.getBow().getItemMeta();

        if(bowMeta != null && bowMeta.getPersistentDataContainer().has(new NamespacedKey(plugin, "chickenShooter"), PersistentDataType.INTEGER)){

            Chicken chicken = (Chicken) e.getProjectile().getWorld().spawnEntity(e.getProjectile().getLocation(), EntityType.CHICKEN);
            chicken.setCollidable(false);
            chicken.setInvulnerable(true);
            chicken.setVelocity(e.getProjectile().getVelocity());
            chicken.setGravity(false);
            chicken.setGlowing(true);
            chicken.getWorld().playSound(chicken.getLocation(), Sound.ENTITY_CHICKEN_HURT, 1, 2 );

            e.setProjectile(chicken);

            new BukkitRunnable(){
                int i = 0;

                @Override
                public void run() {
                    if(i >= 100){
                        chicken.remove();
                        cancel();
                    }

                     chicken.getWorld().spawnParticle(Particle.FLAME, chicken.getLocation(), 50 ,0 ,0 ,0);
                    i += 10;
                }
            }.runTaskTimer(plugin, 0 , 10);
        }

    }
}
