package me.simplyballistic.playerfeedbackbot;

import ch.qos.logback.classic.Level;
import me.simplyballistic.playerfeedbackbot.bot.BotConnection;
import me.simplyballistic.playerfeedbackbot.bot.DiscordHandler;
import me.simplyballistic.playerfeedbackbot.bot.SlackHandler;
import me.simplyballistic.playerfeedbackbot.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by SimplyBallistic on 4/04/2019
 *
 * @author SimplyBallistic
 **/
public class PlayerFeedbackBot {
    private static Config config;
    private SlackHandler slackHandler;
    private DiscordHandler discordHandler;
    private Logger log = LoggerFactory.getLogger(getClass());


    public PlayerFeedbackBot() {
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)).setLevel(Level.INFO);
        log.info("Loading config...");

        config = loadConfig();
        if (config == null) {
            log.error("Config couldn't be loaded, Shutting Down...");
            shutdown();
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
            shutdown();
            return;
        }

        log.info("Connecting to Discord...");

        discordHandler = new DiscordHandler(config.getDiscordToken());
        if (!connectBot(discordHandler)) {
            log.error("Shutting Down...");
            shutdown();
            return;
        }
        log.info("Connections Successful!");
        log.info("Matching Config Channels to Slack and Discord Channels...");
        config.getGames().forEach(game -> {
            boolean validSlack = slackHandler.registerGame(game),
                    validDiscord = discordHandler.registerGame(game);
            if (!validSlack ||
                    !validDiscord) {
                log.warn("The game " + game.getGameName() + " doesn't have valid slack or discord names. It may not operate properly");
            }

        });



    }

    private boolean connectBot(BotConnection connection) {
        try {
            connection.connect();
            return true;
        } catch (Exception e) {
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

    public void shutdown() {
        discordHandler.disconnect();
        slackHandler.disconnect();
    }

    public static Config getConfig() {
        return config;
    }
}
