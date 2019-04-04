package me.simplyballistic.playerfeedbackbot.bot;

import java.io.IOException;

/**
 * Created by SimplyBallistic on 4/04/2019
 *
 * @author SimplyBallistic
 **/
public abstract class BotConnection {
    private final String token;

    public BotConnection(String token) {
        this.token = token;
    }

    protected String getToken() {
        return token;
    }

    abstract void connect() throws IOException;
}
