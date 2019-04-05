package me.simplyballistic.playerfeedbackbot.bot;

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
    private Logger log;

    public BotConnection(String token) {

        this.token = token;
        log = LoggerFactory.getLogger(getClass());
    }

    public String getToken() {
        return token;
    }

    Logger getLogger() {
        return log;
    }

    public abstract void connect() throws IOException;

    public abstract boolean registerGame(Game game);
}
