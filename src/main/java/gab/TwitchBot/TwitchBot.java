package gab.TwitchBot;

import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.Client.Builder.Server.SecurityType;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.event.client.ClientNegotiationCompleteEvent;
import org.kitteh.irc.client.library.feature.twitch.TwitchSupport;

import gab.TwitchBot.Commands.Command;
import gab.TwitchBot.Handlers.WareHandler;
import gab.TwitchBot.Utils.CommandEvent;
import gab.Utils.ConfigHelper;
import gab.Utils.Logger;
import net.engio.mbassy.listener.Handler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TwitchBot {
    private final Config config;
    public final Logger logger;

    private final Set<Command> commands;
    private final Set<WareHandler> prewareHandlers;
    private final Set<WareHandler> postwareHandlers;

    private final Client client;

    public TwitchBot(ConfigHelper config) throws IOException {
        this.config = new Config();
        this.logger = new Logger("Twitch");
        this.commands = new HashSet<Command>();
        this.prewareHandlers = new HashSet<WareHandler>();
        this.postwareHandlers = new HashSet<WareHandler>();

        this.config.Address = "irc.chat.twitch.tv";
        this.config.Port = 443;

        this.config.OAuthToken = config.getValue("oauth_token");
        this.config.Nick = config.getValue("nickname");
        this.config.Channel = config.getValue("channel");
        this.config.JoinMessage = config.getValue("join_message");
        this.config.Prefix = config.getValue("twitch_prefix");
        config.save();

        client = Client.builder()
                        .server().host(this.config.Address).port(this.config.Port, SecurityType.SECURE)
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
    public void addPrewareHandler(WareHandler handler) {
        prewareHandlers.add(handler);
    }
    public void addPostwareHandler(WareHandler handler) {
        postwareHandlers.add(handler);
    }

    @Handler
    public void onConnect(ClientNegotiationCompleteEvent event) {
        logger.log("Connected.");
        client.sendMessage(this.config.Channel, "/me " + this.config.JoinMessage);
    }

    @Handler
    public void onMessageReceived(ChannelMessageEvent event) throws IOException, InterruptedException {
        String sender = event.getActor().getNick();
        String message = event.getMessage();

        logger.log("Message received: " + sender + " : " + message);

        if(!event.getClient().isUser((event.getActor()))){
            for(WareHandler h : prewareHandlers)
                h.execute(new CommandEvent(event, message));
            
            if(message.startsWith(this.config.Prefix)) {
                String command = message.substring(this.config.Prefix.length()).split(" ")[0];
                String arguments = message.substring(this.config.Prefix.length() + command.length());
                for(Command c : commands)
                    if(c.isThisCommand(command))
                        c.execute(new CommandEvent(event, arguments));
            }

            for(WareHandler h : postwareHandlers)
                h.execute(new CommandEvent(event, message));
        }
    }
}
