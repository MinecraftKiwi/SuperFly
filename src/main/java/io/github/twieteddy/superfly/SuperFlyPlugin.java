package io.github.twieteddy.superfly;

import io.github.twieteddy.superfly.commands.FlyCommand;
import io.github.twieteddy.superfly.commands.FlyspeedCommand;
import java.util.Objects;
import java.util.logging.Logger;
import javax.naming.ConfigurationException;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SuperFlyPlugin extends JavaPlugin {
  private static final Logger logger = Bukkit.getLogger();

  @Override
  public void onEnable() {
    PluginManager pm = this.getServer().getPluginManager();

    try {
      SuperFlyConfig config = new SuperFlyConfig(this);
      PluginCommand flyCommand = Objects.requireNonNull(this.getCommand("fly"));
      PluginCommand flyspeedCommand = Objects.requireNonNull(this.getCommand("flyspeed"));

      flyCommand.setExecutor(new FlyCommand(config));
      flyspeedCommand.setExecutor(new FlyspeedCommand(config));
    } catch (ConfigurationException | NullPointerException e) {
      logger.severe(e.getMessage());
      pm.disablePlugin(this);
    }
  }
}
