package me.simplyballistic.playerfeedbackbot.bot.discord;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.entity.MessageChannel;
import me.simplyballistic.playerfeedbackbot.bot.BotConnection;
import me.simplyballistic.playerfeedbackbot.config.Config;
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
    private MessageChannel pollsChannel;

    public DiscordHandler(String token, Config config) {
        super(token, config);
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
        if (registeredGuild.getChannels().filter(c -> c.getName().equals(getConfig().getSuggestionsDiscordChat()) && c.getType() == Channel.Type.GUILD_TEXT).blockFirst() == null) {
            getLogger().warn("The suggestions channel " + getConfig().getSuggestionsDiscordChat() + " doesn't exist. Suggestions may not work as intended");

        }
        pollsChannel = (MessageChannel) registeredGuild.getChannels().filter(c -> c.getName().equals(getConfig().getPollDiscordChat()) && c.getType() == Channel.Type.GUILD_TEXT).blockFirst();
        if (pollsChannel == null) {
            getLogger().warn("The polls channel " + getConfig().getSuggestionsDiscordChat() + " doesn't exist. Polls may not work as intended");

        }





    }

    @Override
    public boolean registerGame(Game game) {
        if (game.getDiscordChannelName() == null) {
            getLogger().warn("Game " + game.getGameName() + " doesn't have a discord channel name!");
            return false;
        }
        GuildChannel channel = registeredGuild.getChannels().filter(p -> p.getName().equals(game.getDiscordChannelName())).blockFirst();
        if (channel != null && channel.getType() == Channel.Type.GUILD_TEXT) {
            game.setDiscordChannel((MessageChannel) channel);
            return true;
        } else {
            getLogger().warn("Given channel " + game.getDiscordChannelName() + " is either not a text channel or doesn't exist!");

            return false;
        }

    }

    @Override
    public void disconnect() {
        if (session.isConnected())
            session.logout().block();
    }
}
