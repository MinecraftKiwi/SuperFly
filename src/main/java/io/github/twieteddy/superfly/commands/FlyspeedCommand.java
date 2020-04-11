package io.github.twieteddy.superfly.commands;

import io.github.twieteddy.superfly.Config;
import io.github.twieteddy.superfly.Messages;
import io.github.twieteddy.superfly.Permissions;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyspeedCommand implements CommandExecutor {

  private final Messages messages;

  public FlyspeedCommand(Config config) {
    this.messages = config.getMessages();
  }

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

    if (!sender.hasPermission(Permissions.FLYSPEED)) {
      sender.sendMessage(messages.getNoPermsFlyspeedSelf());
      return false;
    }

    Iterator<String> iterator = Arrays.asList(args).iterator();
    String newFlyspeed = iterator.hasNext() ? iterator.next() : "";
    String targetName = iterator.hasNext() ? iterator.next() : "";
    Player target;

    if (targetName.isEmpty()) {
      if (sender instanceof Player) {
        target = (Player) sender;
      } else {
        sender.sendMessage(messages.getSenderNotPlayer());
        return false;
      }
    } else {
      target = Bukkit.getPlayer(targetName);
    }

    if (target == null) {
      sender.sendMessage(messages.getPlayerNotOnline());
      return true;
    }

    if (!sender.hasPermission(Permissions.FLYSPEED)) {
      sender.sendMessage(messages.getNoPermsFlyspeedSelf());
      return true;
    }


    // Cancel if no flyspeed was given
    if (newFlyspeed.isEmpty()) {
      sender.sendMessage(messages.getFlyspeedNoArgument());
      return true;
    }

    float flySpeed;
    try {
      flySpeed = Float.parseFloat(newFlyspeed);
    } catch (NumberFormatException e) {
      sender.sendMessage(messages.getFlyspeedNotParsable());
      return true;
    }

    // Fix boundaries (from -1 to 1)
    if (flySpeed >= 10) {
      flySpeed = 10;
    } else if (flySpeed <= -10) {
      flySpeed = -10;
    }

    String valueOfFlyspeed = String.valueOf(flySpeed);
    flySpeed /= 10;


    // Set fly speed
    target.setFlySpeed(flySpeed);
    if (target == sender) {
      target.sendMessage(messages.getFlyspeedChangedOwn(valueOfFlyspeed));
    } else {
      sender.sendMessage(messages.getFlyspeedChangedFor(valueOfFlyspeed));
      target.sendMessage(messages.getFlyspeedChangedBy(valueOfFlyspeed));
    }

    return true;
  }
}
