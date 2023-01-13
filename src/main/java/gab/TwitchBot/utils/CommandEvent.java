package gab.TwitchBot.Utils;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

public class CommandEvent {
    private ChannelMessageEvent innerEvent;
    private String command;
    private String arguments;
    
    public CommandEvent(ChannelMessageEvent innerEvent, String command, String arguments) {
        this.innerEvent = innerEvent;
        this.command = command;
        this.arguments = arguments;
    }
    
    public ChannelMessageEvent getInnerEvent() {
        return innerEvent;
    }
    public String getCommand() {
        return command;
    }
    public String getArgs() {
        return arguments;
    }
}
