package me.simplyballistic.playerfeedbackbot.bot.slack.commands;

/**
 * Created by SimplyBallistic on 7/04/2019
 *
 * @author SimplyBallistic
 **/
public abstract class Command {
    private String name;
    private String usage;
    private int requiredArgs;

    public Command(String name, String usage, int requiredArgs) {
        this.name = name;
        this.usage = usage;
        this.requiredArgs = requiredArgs;
    }

    public abstract void execute(String command, String[] args);

}
