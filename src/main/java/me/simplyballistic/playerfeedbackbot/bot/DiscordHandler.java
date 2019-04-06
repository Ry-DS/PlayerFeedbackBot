package me.simplyballistic.playerfeedbackbot.bot;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.entity.MessageChannel;
import me.simplyballistic.playerfeedbackbot.config.Game;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Created by SimplyBallistic on 4/04/2019
 *
 * @author SimplyBallistic
 **/
public class DiscordHandler extends BotConnection {
    private DiscordClient session;
    private volatile boolean failedConnection = false;
    private Guild registeredGuild;
    public DiscordHandler(String token) {
        super(token);
    }

    @Override
    public void connect() throws IOException {

        session = new DiscordClientBuilder(getToken()).build();


        session.login().doOnError(e -> {
            failedConnection = true;
            e.printStackTrace();
        }).subscribe();
        while (!session.isConnected()) {
            if (failedConnection)
                throw new IOException();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            registeredGuild = session.getGuilds().single().block();
        } catch (NoSuchElementException e) {
            getLogger().error("Discord bot doesn't have a valid guild!");
            throw new IOException();
        } catch (IndexOutOfBoundsException e) {
            getLogger().error("Discord bot is member of more than one guild.");
            getLogger().error("The discord bot may only be the member of one server and cannot continue.");
            throw new IOException();
        }


    }

    @Override
    public boolean registerGame(Game game) {
        if (game.getDiscordChannelName() == null) {
            getLogger().warn("Game " + game.getGameName() + " doesn't have a discord channel name!");
            return false;
        }
        GuildChannel channel = registeredGuild.getChannels().filter(p -> p.getName().equals("general")).blockFirst();
        getLogger().info(channel.getType().toString());
        if (channel instanceof MessageChannel) {
            ((MessageChannel) channel).createMessage("YOYOYOYYO").block();
        }
        return false;

    }

    @Override
    public void disconnect() {
        if (session.isConnected())
            session.logout().block();
    }
}
