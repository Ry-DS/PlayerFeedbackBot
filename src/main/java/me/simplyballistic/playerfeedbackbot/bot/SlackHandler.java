package me.simplyballistic.playerfeedbackbot.bot;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

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


}
