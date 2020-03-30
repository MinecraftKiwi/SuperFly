package io.github.twieteddy.superfly;

import javax.naming.ConfigurationException;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

  private final Messages messages;
  private final SuperFlyPlugin plugin;

  public Config(SuperFlyPlugin plugin) throws ConfigurationException {
    this.plugin = plugin;
    FileConfiguration config = plugin.getConfig();

    plugin.getConfig().options().copyDefaults(true);
    plugin.saveDefaultConfig();

    ConfigurationSection messageSection = config.getConfigurationSection("messages");
    if (messageSection == null) {
      throw new ConfigurationException("Message section not found in config");
    }

    this.messages = new Messages(messageSection);
  }

  public Messages getMessages() {
    return this.messages;
  }

  public SuperFlyPlugin getPlugin() {
    return this.plugin;
  }
}
