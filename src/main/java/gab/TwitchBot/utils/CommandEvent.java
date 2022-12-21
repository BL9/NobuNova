package gab.TwitchBot.Utils;

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;

public class CommandEvent {
    private ChannelMessageEvent innerEvent;
    private String arguments;
    
    public CommandEvent(ChannelMessageEvent innerEvent, String arguments) {
        this.innerEvent = innerEvent;
        this.arguments = arguments;
    }
    
    public ChannelMessageEvent getInnerEvent() {
        return innerEvent;
    }
    public String getArgs() {
        return arguments;
    }
}
