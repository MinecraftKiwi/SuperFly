package io.github.twieteddy.superfly.commands;

import static java.lang.Float.parseFloat;

import io.github.twieteddy.superfly.MessageHelper;
import io.github.twieteddy.superfly.SuperFlyConfig;
import io.github.twieteddy.superfly.SuperFlyPermissions;
import java.util.Arrays;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyspeedCommand implements CommandExecutor {

  private final MessageHelper messages;

  public FlyspeedCommand(SuperFlyConfig config) {
    this.messages = config.getMessageHelper();
  }

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Iterator<String> argsIterator = Arrays.asList(args).iterator();
    String argFlyspeed = argsIterator.hasNext() ? argsIterator.next() : "";
    String argOtherPlayer = argsIterator.hasNext() ? argsIterator.next() : "";

    Player target;
    float flySpeed;

    // Select yourself or the other player and check permissions
    if (argOtherPlayer.isEmpty()) {
      // Cancel if sender is not a player
      if (!(sender instanceof Player)) {
        sender.sendMessage(messages.getSenderNotPlayer());
        return true;
      }
      if (sender.hasPermission(SuperFlyPermissions.FLYSPEED)) {
        target = (Player) sender;
      } else {
        sender.sendMessage(messages.getNoPermsFlyspeedSelf());
        return true;
      }
    } else {
      // Check if sender can change others flySpeed
      if (sender.hasPermission(SuperFlyPermissions.FLYSPEED_OTHERS)) {
        target = Bukkit.getPlayer(argOtherPlayer);
        // Check if target player is online
        if (target == null) {
          sender.sendMessage(messages.getPlayerNotOnline());
          return true;
        }
      } else {
        sender.sendMessage(messages.getNoPermsFlyspeedOthers());
        return true;
      }
    }

    // Cancel if no flyspeed was given
    if (argFlyspeed.isEmpty()) {
      sender.sendMessage(messages.getFlyspeedNoArgument());
      return true;
    }

    // Parse flyspeed string to float
    try {
      flySpeed = parseFloat(argFlyspeed);
    } catch (NumberFormatException e) {
      sender.sendMessage(messages.getFlyspeedNotParsable());
      return true;
    }

    // Fix flySpeed boundaries (from -1 to 1)
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
