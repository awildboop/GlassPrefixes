package com.awildboop.glassprefixes;

import com.awildboop.glassprefixes.managers.DatabaseManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class GlassPrefixes extends JavaPlugin {
    private DatabaseManager dbManager;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        // Load the database
        this.dbManager = new DatabaseManager(this);
        try {
            dbManager.connect();
        } catch (Exception e) {
            this.getLogger().severe("Unable to connect to database, disabling!");
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public DatabaseManager getDbManager() {
        return this.dbManager;
    }
}
