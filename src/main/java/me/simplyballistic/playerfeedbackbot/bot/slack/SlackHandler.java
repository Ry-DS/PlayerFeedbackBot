package me.simplyballistic.playerfeedbackbot.bot.slack;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import me.simplyballistic.playerfeedbackbot.bot.BotConnection;
import me.simplyballistic.playerfeedbackbot.config.Config;
import me.simplyballistic.playerfeedbackbot.config.Game;

import java.io.IOException;

/**
 * Created by SimplyBallistic on 4/04/2019
 *
 * @author SimplyBallistic
 **/
public class SlackHandler extends BotConnection {
    private SlackSession session;

    public SlackHandler(String token, Config config) {
        super(token, config);

    }

    @Override
    public void connect() throws IOException {
        session = SlackSessionFactory.createWebSocketSlackSession(getToken());
        session.connect();
        session.addMessagePostedListener((event, session) -> {
            getConfig().getGames().forEach(game -> {
                if (game.getSlackChannel() != null && game.getSlackChannel().getId().equals(event.getChannel().getId())) {
                    processSlackMessage(event.getMessageContent());
                }
            });
        });


    }

    private void processSlackMessage(String messageContent) {
        if (messageContent.toLowerCase().startsWith("/poll")) {

        }
    }

    @Override
    public boolean registerGame(Game game) {
        if (game.getSlackChannelName() == null) {
            getLogger().warn("Game " + game.getGameName() + " doesn't have a slack channel name!");
            return false;
        }
        SlackChannel channel = session.findChannelByName(game.getSlackChannelName());
        if (channel == null) {
            getLogger().warn("Slack Channel " + game.getSlackChannelName() + " doesn't exist!");
            return false;
        }
        if (!channel.isMember()) {
            getLogger().warn("Slack Bot isn't a member of needed channel: " + game.getSlackChannelName() + "!");
            return false;
        }
        game.setSlackChannel(channel);

        return true;

    }

    @Override
    public void disconnect() {
        if (session.isConnected()) {
            try {
                session.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
