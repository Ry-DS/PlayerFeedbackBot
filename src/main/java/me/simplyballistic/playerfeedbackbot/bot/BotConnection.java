package me.simplyballistic.playerfeedbackbot.bot;

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
public abstract class BotConnection {
    private final String token;
    private Config config;
    private Logger log;

    public BotConnection(String token, Config config) {

        this.token = token;
        this.config = config;
        log = LoggerFactory.getLogger(getClass());
    }

    public String getToken() {
        return token;
    }

    public Config getConfig() {
        return config;
    }

    public Logger getLogger() {
        return log;
    }

    public abstract void connect() throws IOException;

    public abstract boolean registerGame(Game game);

    public abstract void disconnect();
}
