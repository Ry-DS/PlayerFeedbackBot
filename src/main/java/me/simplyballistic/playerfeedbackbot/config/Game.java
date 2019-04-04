package me.simplyballistic.playerfeedbackbot.config;

/**
 * Created by SimplyBallistic on 4/04/2019
 *
 * @author SimplyBallistic
 **/
public class Game {
    private String gameName, slackChannel, discordChannel;

    public Game(String gameName, String slackChannel, String discordChannel) {
        this.gameName = gameName;
        this.slackChannel = slackChannel;
        this.discordChannel = discordChannel;
    }
}
