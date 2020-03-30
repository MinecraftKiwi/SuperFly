package io.github.twieteddy.superfly;

import java.util.Objects;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class Messages {
  private final ConfigurationSection section;
  private final String prefix;
  private final String senderNotPlayer;
  private final String playerNotOnline;
  private final String noPermsFlySelf;
  private final String noPermsFlyOthers;
  private final String flyActivated;
  private final String flyDeactivated;
  private final String flyActivatedFor;
  private final String flyDeactivatedFor;
  private final String noPermsFlyspeedSelf;
  private final String noPermsFlyspeedOthers;
  private final String flyspeedChangedOwn;
  private final String flyspeedChangedFor;
  private final String flyspeedChangedBy;
  private final String flyspeedNoArgument;
  private final String flyspeedNotParsable;

  public Messages(ConfigurationSection messageSection) {
    this.section = messageSection;
    this.prefix = getTranslatedMessage("prefix");

    this.senderNotPlayer = prefix + getTranslatedMessage("sender-not-player");
    this.playerNotOnline = prefix + getTranslatedMessage("player-not-online");
    this.noPermsFlySelf = prefix + getTranslatedMessage("no-perms-fly-self");
    this.noPermsFlyOthers = prefix + getTranslatedMessage("no-perms-fly-others");
    this.flyActivated = prefix + getTranslatedMessage("fly-activated-self");
    this.flyActivatedFor = prefix + getTranslatedMessage("fly-activated-for");
    this.flyDeactivated = prefix + getTranslatedMessage("fly-deactivated-self");
    this.flyDeactivatedFor = prefix + getTranslatedMessage("fly-deactivated-for");
    this.noPermsFlyspeedSelf = prefix + getTranslatedMessage("no-perms-flyspeed-self");
    this.noPermsFlyspeedOthers = prefix + getTranslatedMessage("no-perms-flyspeed-others");
    this.flyspeedChangedOwn = prefix + getTranslatedMessage("flyspeed-changed-own");
    this.flyspeedChangedFor = prefix + getTranslatedMessage("flyspeed-changed-for");
    this.flyspeedChangedBy = prefix + getTranslatedMessage("flyspeed-changed-by");
    this.flyspeedNoArgument = prefix + getTranslatedMessage("flyspeed-no-argument");
    this.flyspeedNotParsable = prefix + getTranslatedMessage("flyspeed-not-parsable");
  }

  private String getTranslatedMessage(String node) {
    return ChatColor.translateAlternateColorCodes(
        '&', Objects.requireNonNull(section.getString(node), "Message " + node + " not found"));
  }

  public String getPrefix() {
    return prefix;
  }

  public String getSenderNotPlayer() {
    return senderNotPlayer;
  }

  public String getPlayerNotOnline() {
    return playerNotOnline;
  }

  public String getNoPermsFlySelf() {
    return noPermsFlySelf;
  }

  public String getNoPermsFlyOthers() {
    return noPermsFlyOthers;
  }

  public String getFlyActivated() {
    return flyActivated;
  }

  public String getFlyActivatedFor(String target) {
    return flyActivatedFor.replace("%target%", target);
  }

  public String getFlyDeactivated() {
    return flyDeactivated;
  }

  public String getFlyDeactivatedFor(String target) {
    return flyDeactivatedFor.replace("%target%", target);
  }

  public String getNoPermsFlyspeedSelf() {
    return noPermsFlyspeedSelf;
  }

  public String getNoPermsFlyspeedOthers() {
    return noPermsFlyspeedOthers;
  }

  public String getFlyspeedChangedOwn(String flyspeed) {
    return flyspeedChangedOwn.replace("%flyspeed%", flyspeed);
  }

  public String getFlyspeedChangedFor(String flyspeed) {
    return flyspeedChangedFor.replace("%flyspeed%", flyspeed);
  }

  public String getFlyspeedChangedBy(String flyspeed) {
    return flyspeedChangedBy.replace("%flyspeed%", flyspeed);
  }

  public String getFlyspeedNoArgument() {
    return flyspeedNoArgument;
  }

  public String getFlyspeedNotParsable() {
    return flyspeedNotParsable;
  }
}
