package me.simplyballistic.playerfeedbackbot.bot;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.object.entity.Guild;
import me.simplyballistic.playerfeedbackbot.config.Game;

import java.io.IOException;
import java.time.Duration;

/**
 * Created by SimplyBallistic on 4/04/2019
 *
 * @author SimplyBallistic
 **/
public class DiscordHandler extends BotConnection {
    private DiscordClient session;
    private Guild registeredGuild;
    public DiscordHandler(String token) {
        super(token);
    }

    @Override
    public void connect() throws IOException {
        session = new DiscordClientBuilder(getToken()).build();
        //session.login().block();
        session.login().block();
        registeredGuild = session.getGuilds().single().blockOptional(Duration.ofSeconds(3)).orElseThrow(IOException::new);

    }

    @Override
    public boolean registerGame(Game game) {
        return false;

    }
}
