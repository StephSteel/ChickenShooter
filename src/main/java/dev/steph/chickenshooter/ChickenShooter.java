package dev.steph.chickenshooter;

import org.bukkit.plugin.java.JavaPlugin;

public final class ChickenShooter extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ChickenShooterListener(this), this);
    }

    @Override
    public void onDisable() {

    }
}
