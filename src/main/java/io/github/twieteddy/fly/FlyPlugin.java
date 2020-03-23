package io.github.twieteddy.fly;

import io.github.twieteddy.fly.commands.FlyCommand;
import io.github.twieteddy.fly.commands.FlyspeedCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class FlyPlugin extends JavaPlugin {
    private final Logger logger = Bukkit.getLogger();
    private YamlConfiguration config;

    @Override
    public void onEnable() {
        loadConfig();
        this.getCommand("fly").setExecutor(new FlyCommand(this));
        this.getCommand("flyspeed").setExecutor(new FlyspeedCommand(this));
    }

    @Override
    public void onDisable() {}

    private void loadConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            this.saveResource("config.yml", false);
        }
        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    public String getMessage(String key) {
        ConfigurationSection messages = this.config.getConfigurationSection("messages");
        StringBuilder msg = new StringBuilder();
        msg.append(this.config.getString("prefix"));

        if (messages.getKeys(false).contains(key)) {
            msg.append(messages.getString(key));
        } else {
            String warning = String.format("String for %s not found", key);
            msg.append(warning);
            logger.severe(msg.toString());
        }
        return ChatColor.translateAlternateColorCodes('&', msg.toString());
    }

    public String getMessage(String key, String name) {
        return String.format(this.getMessage(key), name);
    }
}
