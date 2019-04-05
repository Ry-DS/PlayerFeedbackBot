package me.simplyballistic.playerfeedbackbot.bot;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import me.simplyballistic.playerfeedbackbot.config.Game;

import java.io.IOException;

/**
 * Created by SimplyBallistic on 4/04/2019
 *
 * @author SimplyBallistic
 **/
public class SlackHandler extends BotConnection {
    private SlackSession session;
    public SlackHandler(String token) {
        super(token);

    }

    @Override
    public void connect() throws IOException {
        session = SlackSessionFactory.createWebSocketSlackSession(getToken());
        session.connect();


    }

    @Override
    public boolean registerGame(Game game) {
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


}
