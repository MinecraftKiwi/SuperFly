package io.github.twieteddy.superfly.commands;

import io.github.twieteddy.superfly.Config;
import io.github.twieteddy.superfly.Messages;
import io.github.twieteddy.superfly.Permissions;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public class FlyCommand implements TabExecutor {

  private final Messages messages;
  private final CommandLineParser parser;
  private final Options options;

  public FlyCommand(Config config) {
    this.messages = config.getMessages();

    this.parser = new DefaultParser();
    this.options = new Options();

    this.options.addOption(Option.builder().longOpt("on").desc("Turn fly on").build());
    this.options.addOption(Option.builder().longOpt("off").desc("Turn fly off").build());
  }

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!sender.hasPermission(Permissions.FLY)) {
      sender.sendMessage(messages.getNoPermsFlySelf());
      return false;
    }

    CommandLine cmd;

    try {
      cmd = this.parser.parse(this.options, args);
    } catch (ParseException e) {
      sender.sendMessage(e.getMessage());
      return false;
    }

    String targetName = cmd.getArgList().isEmpty() ? sender.getName() : cmd.getArgs()[0];
    Player target = Bukkit.getPlayer(targetName);

    if (target == null) {
      sender.sendMessage(messages.getPlayerNotOnline());
      return false;
    }

    if (sender != target && !sender.hasPermission(Permissions.FLY_OTHERS)) {
      sender.sendMessage(messages.getNoPermsFlyOthers());
      return false;
    }

    // Toggle fly considering options
    boolean fly = (cmd.hasOption("on") || !target.getAllowFlight()) && !cmd.hasOption("off");

    target.setAllowFlight(fly);
    target.setFlying(fly);

    if (fly) {
      sender.sendMessage(messages.getFlyActivatedFor(target.getName()));
      target.sendMessage(messages.getFlyActivated());
      target.teleport(target.getLocation());
    } else {
      sender.sendMessage(messages.getFlyDeactivatedFor(target.getName()));
      target.sendMessage(messages.getFlyDeactivated());
    }

    return true;
  }

  @Override
  public List<String> onTabComplete(
      CommandSender commandSender, Command command, String s, String[] strings) {
    return null;
  }
}
