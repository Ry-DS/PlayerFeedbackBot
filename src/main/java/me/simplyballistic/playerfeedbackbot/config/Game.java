package me.simplyballistic.playerfeedbackbot.config;

import com.ullink.slack.simpleslackapi.SlackChannel;

/**
 * Created by SimplyBallistic on 4/04/2019
 *
 * @author SimplyBallistic
 **/
public class Game {
    private String gameName, slackChannelName, discordChannelName;
    private transient SlackChannel slackChannel;

    public Game(String gameName, String slackChannelName, String discordChannelName) {
        this.gameName = gameName;
        this.slackChannelName = slackChannelName;
        this.discordChannelName = discordChannelName;
    }

    public String getGameName() {
        return gameName;
    }

    public String getSlackChannelName() {
        return slackChannelName;
    }

    public String getDiscordChannelName() {
        return discordChannelName;
    }

    public SlackChannel getSlackChannel() {
        return slackChannel;
    }

    public void setSlackChannel(SlackChannel slackChannel) {
        this.slackChannel = slackChannel;
    }
}
