package me.simplyballistic.playerfeedbackbot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.simplyballistic.playerfeedbackbot.bot.DiscordHandler;
import me.simplyballistic.playerfeedbackbot.bot.SlackHandler;
import me.simplyballistic.playerfeedbackbot.config.Config;
import me.simplyballistic.playerfeedbackbot.config.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by SimplyBallistic on 4/04/2019
 *
 * @author SimplyBallistic
 **/
public class Main {
    private Gson gson;
    private Config config;
    private SlackHandler slackHandler;
    private DiscordHandler discordHandler;
    private Logger log = LoggerFactory.getLogger(getClass());

    public Main() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        log.info("Loading config...");

        config = loadConfig();
        log.info("Connecting to Slack...");
        slackHandler = new SlackHandler(config.getSlackToken());
        try {
            slackHandler.connect();
        } catch (IOException e) {

            log.error("Failed to connect to Slack with token: " + config.getSlackToken());
            log.error("Shutting down...");
            e.printStackTrace();
            return;


        }
        log.info("Connecting to Discord...");

        discordHandler = new DiscordHandler(config.getDiscordToken());
        try {
            discordHandler.connect();
        } catch (IOException e) {

            log.error("Failed to connect to Discord with token: " + config.getDiscordToken());
            log.error("Shutting down...");
            e.printStackTrace();
            return;


        }


    }

    public static void main(String[] args) {
        new Main();
    }

    private Config loadConfig() {//TODO
        return new Config("j", "l", 9, 9, 9,
                new Game("s", "s", "s"), new Game("s", "s", "s"));
    }
}
