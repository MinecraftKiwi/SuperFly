package io.github.twieteddy.fly.commands;

import io.github.twieteddy.fly.FlyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Iterator;

import static java.lang.Float.parseFloat;

public class FlyspeedCommand implements CommandExecutor {
    private FlyPlugin flyPlugin;
    public FlyspeedCommand(FlyPlugin flyPlugin) {
        this.flyPlugin = flyPlugin;
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
                sender.sendMessage(flyPlugin.getMessage("sender_not_a_player"));
                return true;
            }
            if (sender.hasPermission("neverfly.flyspeed.self")) {
                target = (Player) sender;
            } else {
                sender.sendMessage(flyPlugin.getMessage("no_perms_flyspeed_self"));
                return true;
            }
        } else {
            // Check if sender can change others flySpeed
            if (sender.hasPermission("neverfly.flyspeed.others")) {
                target = Bukkit.getPlayer(argOtherPlayer);
                // Check if target player is online
                if (target == null) {
                    sender.sendMessage(flyPlugin.getMessage("player_not_online"));
                    return true;
                }
            } else {
                sender.sendMessage(flyPlugin.getMessage("no_perms_flyspeed_others"));
                return true;
            }
        }

        // Cancel if no flyspeed was given
        if (argFlyspeed.isEmpty()) {
            sender.sendMessage(flyPlugin.getMessage("flyspeed_no_argument"));
            return true;
        }

        // Parse flyspeed string to float
        try {
            flySpeed = parseFloat(argFlyspeed);
        } catch (NumberFormatException e) {
            sender.sendMessage(flyPlugin.getMessage("flyspeed_not_parsable"));
            return true;
        }

        // Fix flySpeed boundaries (from -1 to 1)
        if (flySpeed >= 10) {
            flySpeed = 10;
        } else if (flySpeed <= -10) {
            flySpeed = -10;
        }
        flySpeed /= 10;

        // Set fly speed
        target.setFlySpeed(flySpeed);
        if (target == sender) {
            target.sendMessage(flyPlugin.getMessage("flyspeed_change_own", String.valueOf(flySpeed*10)));
        } else {
            sender.sendMessage(flyPlugin.getMessage("flyspeed_change_for", String.valueOf(flySpeed*10)));
            target.sendMessage(flyPlugin.getMessage("flyspeed_changed_by", String.valueOf(flySpeed*10)));
        }
        return false;
    }
}

