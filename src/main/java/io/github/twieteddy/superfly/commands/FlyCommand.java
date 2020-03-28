package io.github.twieteddy.superfly.commands;

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

public class FlyCommand implements CommandExecutor {

  private final MessageHelper messages;

  public FlyCommand(SuperFlyConfig config) {
    this.messages = config.getMessageHelper();
    //this.protocolLibrary = (ProtocolLibrary) ProtocolLibrary.getPlugin();
  }

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Iterator<String> argsIterator = Arrays.asList(args).iterator();
    String argOther = argsIterator.hasNext() ? argsIterator.next() : "";
    //String argMode = argsIterator.hasNext() ? argsIterator.next() : "";
    Player target;

    // Check permissions and target player
    if (argOther.isEmpty()) {
      if (sender instanceof Player) {
        if (sender.hasPermission(SuperFlyPermissions.FLY)) {
          target = (Player) sender;
        } else {
          sender.sendMessage(messages.getNoPermsFlySelf());
          return true;
        }
      } else {
        sender.sendMessage(messages.getSenderNotPlayer());
        return true;
      }
    } else {
      if (sender.hasPermission(SuperFlyPermissions.FLY_OTHERS)) {
        target = Bukkit.getPlayer(argOther);
        if (target == null) {
          sender.sendMessage(messages.getPlayerNotOnline());
          return true;
        }
      } else {
        sender.sendMessage(messages.getNoPermsFlyOthers());
        return true;
      }
    }

    // Toggle flight
    target.setAllowFlight(!target.getAllowFlight());
    if (target.isOnGround()) {
      target.setFlying(target.getAllowFlight());
    }

    if (target == sender) {
      if (target.getAllowFlight()) {
        target.sendMessage(messages.getFlyActivatedSelf());
      } else {
        target.sendMessage(messages.getFlyDeactivatedSelf());
      }
    } else {
      if (target.getAllowFlight()) {
        sender.sendMessage(messages.getFlyActivatedFor(target.getName()));
        target.sendMessage(messages.getFlyActivatedBy(sender.getName()));
      } else {
        sender.sendMessage(messages.getFlyDeactivatedFor(target.getName()));
        target.sendMessage(messages.getFlyDeactivatedBy(sender.getName()));
      }
    }

    return false;
  }
}
