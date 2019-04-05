package me.simplyballistic.playerfeedbackbot;

import ch.qos.logback.classic.Level;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.simplyballistic.playerfeedbackbot.bot.BotConnection;
import me.simplyballistic.playerfeedbackbot.bot.DiscordHandler;
import me.simplyballistic.playerfeedbackbot.bot.SlackHandler;
import me.simplyballistic.playerfeedbackbot.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by SimplyBallistic on 4/04/2019
 *
 * @author SimplyBallistic
 **/
public class PlayerFeedbackBot {
    private Gson gson;
    private Config config;
    private SlackHandler slackHandler;
    private DiscordHandler discordHandler;
    private Logger log = LoggerFactory.getLogger(getClass());

    public PlayerFeedbackBot() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)).setLevel(Level.INFO);
        log.info("Loading config...");

        config = loadConfig();
        if (config == null) {
            log.error("Config couldn't be loaded, Shutting Down...");
            return;
        }
        if (config.getDiscordToken().isEmpty() || config.getSlackToken().isEmpty()) {
            log.warn("The Slack or Discord tokens are not configured within the config.json, enter appropriate values then try running again");
            return;
        }
        log.info("Connecting to Slack...");
        slackHandler = new SlackHandler(config.getSlackToken());
        if (!connectBot(slackHandler)) {
            log.info("Shutting Down...");
            return;
        }

        log.info("Connecting to Discord...");

        discordHandler = new DiscordHandler(config.getDiscordToken());
        if (!connectBot(discordHandler)) {
            log.error("Shutting Down...");
            return;
        }


    }

    private boolean connectBot(BotConnection connection) {
        try {
            connection.connect();
            return true;
        } catch (IOException e) {
            log.error("Failed to connect with token: " + connection.getToken());
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String[] args) {
        new PlayerFeedbackBot();
    }

    private Config loadConfig() {
        try {
            return Config.load();
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }
}
