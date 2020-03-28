package io.github.twieteddy.superfly;

import java.util.Objects;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class MessageHelper {
  private final ConfigurationSection section;
  private final String prefix;
  private final String senderNotPlayer;
  private final String playerNotOnline;
  private final String noPermsFlySelf;
  private final String noPermsFlyOthers;
  private final String flyActivatedSelf;
  private final String flyActivatedFor;
  private final String flyActivatedBy;
  private final String flyDeactivatedSelf;
  private final String flyDeactivatedFor;
  private final String flyDeactivatedBy;
  private final String noPermsFlyspeedSelf;
  private final String noPermsFlyspeedOthers;
  private final String flyspeedChangedOwn;
  private final String flyspeedChangedFor;
  private final String flyspeedChangedBy;
  private final String flyspeedNoArgument;
  private final String flyspeedNotParsable;

  public MessageHelper(ConfigurationSection messageSection) {
    this.section = messageSection;
    this.prefix = getTranslatedMessage("prefix");
    this.senderNotPlayer = getTranslatedMessage("sender-not-player");
    this.playerNotOnline = getTranslatedMessage("player-not-online");
    this.noPermsFlySelf = getTranslatedMessage("no-perms-fly-self");
    this.noPermsFlyOthers = getTranslatedMessage("no-perms-fly-others");
    this.flyActivatedSelf = getTranslatedMessage("fly-activated-self");
    this.flyActivatedFor = getTranslatedMessage("fly-activated-for");
    this.flyActivatedBy = getTranslatedMessage("fly-activated-by");
    this.flyDeactivatedSelf = getTranslatedMessage("fly-deactivated-self");
    this.flyDeactivatedFor = getTranslatedMessage("fly-deactivated-for");
    this.flyDeactivatedBy = getTranslatedMessage("fly-deactivated-by");
    this.noPermsFlyspeedSelf = getTranslatedMessage("no-perms-flyspeed-self");
    this.noPermsFlyspeedOthers = getTranslatedMessage("no-perms-flyspeed-others");
    this.flyspeedChangedOwn = getTranslatedMessage("flyspeed-changed-own");
    this.flyspeedChangedFor = getTranslatedMessage("flyspeed-changed-for");
    this.flyspeedChangedBy = getTranslatedMessage("flyspeed-changed-by");
    this.flyspeedNoArgument = getTranslatedMessage("flyspeed-no-argument");
    this.flyspeedNotParsable = getTranslatedMessage("flyspeed-not-parsable");
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

  public String getFlyActivatedSelf() {
    return flyActivatedSelf;
  }

  public String getFlyActivatedFor(String target) {
    return flyActivatedFor.replace("%target%", target);
  }

  public String getFlyActivatedBy(String sender) {
    return flyActivatedBy.replace("%sender%", sender);
  }

  public String getFlyDeactivatedSelf() {
    return flyDeactivatedSelf;
  }

  public String getFlyDeactivatedFor(String target) {
    return flyDeactivatedFor.replace("%target%", target);
  }

  public String getFlyDeactivatedBy(String sender) {
    return flyDeactivatedBy.replace("%sender%", sender);
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
