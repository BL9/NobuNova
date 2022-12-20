package gab.TwitchBot;

import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.element.Channel;
import org.kitteh.irc.client.library.event.channel.ChannelJoinEvent;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.event.client.ClientNegotiationCompleteEvent;
import org.kitteh.irc.client.library.feature.twitch.TwitchSupport;

import gab.ConfigHelper;
import gab.Tools.Logger;
import gab.TwitchBot.Commands.Command;
import gab.TwitchBot.utils.CommandEvent;
import net.engio.mbassy.listener.Handler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TwitchBot {
    private final Config config;
    private final Logger logger;

    private final Set<Command> commands;

    private final Client client;

    public TwitchBot(ConfigHelper config) throws IOException {
        this.config = new Config();
        this.logger = new Logger("Twitch");
        this.commands = new HashSet<Command>();

        this.config.Address = "irc.chat.twitch.tv";
        this.config.Port = 443;

        this.config.OAuthToken = config.getValue("oauth_token");
        this.config.Nick = config.getValue("nickname");
        this.config.Channel = config.getValue("channel");
        this.config.JoinMessage = config.getValue("join_message");
        this.config.Prefix = config.getValue("twitch_prefix");
        config.save();

        client = Client.builder()
                        .server().host(this.config.Address).port(this.config.Port)
                        .password(this.config.OAuthToken).then()
                        .nick(this.config.Nick)
                        .build();
        TwitchSupport.addSupport(client);

        client.getEventManager().registerEventListener(this);

        client.addChannel(this.config.Channel);

        logger.log("Initialization complete.");
    }

    public void connect() {
        client.connect();
        logger.log("Connecting");
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    @Handler
    public void onConnect(ClientNegotiationCompleteEvent event) {
        logger.log("Connected.");
        client.sendMessage(this.config.Channel, this.config.JoinMessage);
    }

    @Handler
    public void onMessageReceived(ChannelMessageEvent event) {
        String sender = event.getActor().getNick();
        String message = event.getMessage();

        logger.log("Message received: " + sender + " : " + message);

        if(!event.getClient().isUser((event.getActor()))){
            if(message.startsWith(this.config.Prefix)) {
                String command = message.substring(this.config.Prefix.length()).split(" ")[0];
                String arguments = message.substring(this.config.Prefix.length() + command.length());
                for(Command c : commands)
                    if(c.isThisCommand(command))
                        c.execute(new CommandEvent(event, arguments));
            }
        }
    }
}
