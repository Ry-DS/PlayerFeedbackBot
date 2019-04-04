package me.simplyballistic.playerfeedbackbot.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SimplyBallistic on 4/04/2019
 *
 * @author SimplyBallistic
 **/
public class Config {
    private String slackToken, discordToken;
    private List<Game> games;
    private int voteCapSuggestions;
    private int maxSuggestionsPerDay;
    private long pollDuration;

    public Config(String slackToken, String discordToken, int voteCapSuggestions, int maxSuggestionsPerDay, long pollDuration, Game... games) {
        this.slackToken = slackToken;
        this.discordToken = discordToken;
        this.voteCapSuggestions = voteCapSuggestions;
        this.maxSuggestionsPerDay = maxSuggestionsPerDay;
        this.pollDuration = pollDuration;
        this.games = new ArrayList<>();
        this.games.addAll(Arrays.asList(games));

    }

    public String getSlackToken() {
        return slackToken;
    }

    public String getDiscordToken() {
        return discordToken;
    }

    public List<Game> getGames() {
        return games;
    }

    public int getVoteCapSuggestions() {
        return voteCapSuggestions;
    }

    public int getMaxSuggestionsPerDay() {
        return maxSuggestionsPerDay;
    }

    public long getPollDuration() {
        return pollDuration;
    }
}
