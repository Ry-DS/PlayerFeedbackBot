package me.simplyballistic.playerfeedbackbot.bot;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;

import java.io.IOException;

/**
 * Created by SimplyBallistic on 4/04/2019
 *
 * @author SimplyBallistic
 **/
public class DiscordHandler extends BotConnection {
    private DiscordClient session;
    public DiscordHandler(String token) {
        super(token);
    }

    @Override
    public void connect() throws IOException {
        session = new DiscordClientBuilder(getToken()).build();
        session.login().block();

    }
}
