package io.github.twieteddy.fly.commands;

import io.github.twieteddy.fly.FlyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Iterator;

public class FlyCommand implements CommandExecutor {
    private final FlyPlugin flyPlugin;

    public FlyCommand(FlyPlugin flyPlugin) {
        this.flyPlugin = flyPlugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Iterator<String> argsIterator = Arrays.asList(args).iterator();
        String argOther = argsIterator.hasNext() ? argsIterator.next() : "";
        String argMode = argsIterator.hasNext() ? argsIterator.next() : "";
        Player target;

        // Check permissions and target player
        if (argOther.isEmpty()) {
            if (sender instanceof Player) {
                if (sender.hasPermission("neverfly.fly.self")) {
                    target = (Player) sender;
                } else {
                    sender.sendMessage(flyPlugin.getMessage("no_perms_fly_self"));
                    return true;
                }
            } else {
                sender.sendMessage(flyPlugin.getMessage("sender_not_a_player"));
                return true;
            }
        } else {
            if (sender.hasPermission("neverfly.fly.others")) {
                target = Bukkit.getPlayer(argOther);
                if (target == null) {
                    sender.sendMessage(flyPlugin.getMessage("player_not_online"));
                    return true;
                }
            }
            else {
                sender.sendMessage(flyPlugin.getMessage("no_perms_fly_others"));
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
                target.sendMessage(flyPlugin.getMessage("fly_activated_self"));
            } else {
                target.sendMessage(flyPlugin.getMessage("fly_deactivated_self"));
            }
        } else {
            if (target.getAllowFlight()) {
                sender.sendMessage(flyPlugin.getMessage("fly_activated_for", target.getName()));
                target.sendMessage(flyPlugin.getMessage("fly_activated_by", sender.getName()));
            } else {
                sender.sendMessage(flyPlugin.getMessage("fly_deactivated_for", target.getName()));
                target.sendMessage(flyPlugin.getMessage("fly_deactivated_by", sender.getName()));
            }
        }

        return false;
    }
}