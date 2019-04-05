package me.simplyballistic.playerfeedbackbot.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
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
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static File file = new File(System.getProperty("user.dir") + File.separator + "config.json");
    private long pollDurationMs;

    public Config(String slackToken, String discordToken, int voteCapSuggestions, int maxSuggestionsPerDay, long pollDuration, Game... games) {
        this.slackToken = slackToken;
        this.discordToken = discordToken;
        this.voteCapSuggestions = voteCapSuggestions;
        this.maxSuggestionsPerDay = maxSuggestionsPerDay;
        this.pollDurationMs = pollDuration;
        this.games = new ArrayList<>();
        this.games.addAll(Arrays.asList(games));

    }

    public Config() {
        this("", "", 20, 2, 24 * 60 * 60 * 1000,
                new Game("exampleGame", "project-exampleGame", "exampleGame-suggestions"));

    }

    public static Config load() throws JsonParseException, IOException {
        Config config;
        if (file.createNewFile()) {
            config = new Config();
        } else {
            String json = String.join("", Files.readAllLines(file.toPath()));
            config = gson.fromJson(json, Config.class);

        }
        save(config);


        return config;


    }

    public static void save(Config config) throws IOException {
        String json = gson.toJson(config);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json);
            writer.flush();
        }


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

    public long getPollDurationMs() {
        return pollDurationMs;
    }
}
