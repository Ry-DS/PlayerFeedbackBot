package me.simplyballistic.playerfeedbackbot.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by SimplyBallistic on 4/04/2019
 *
 * @author SimplyBallistic
 **/
public class Main {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String parsed = gson.toJson(new Config("j", "l", 9, 9, 9,
                new Game("s", "s", "s"), new Game("s", "s", "s")));
        System.out.println(parsed);
    }
}
