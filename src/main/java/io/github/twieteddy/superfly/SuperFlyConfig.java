package io.github.twieteddy.superfly;

import javax.naming.ConfigurationException;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class SuperFlyConfig {

  private final MessageHelper messageHelper;

  public SuperFlyConfig(SuperFlyPlugin plugin) throws ConfigurationException {
    FileConfiguration config = plugin.getConfig();

    plugin.getConfig().options().copyDefaults(true);
    plugin.saveDefaultConfig();

    ConfigurationSection messageSection = config.getConfigurationSection("messages");
    if (messageSection == null) {
      throw new ConfigurationException("Message section not found in config");
    }

    this.messageHelper = new MessageHelper(messageSection);
  }

  public MessageHelper getMessageHelper() {
    return this.messageHelper;
  }
}
