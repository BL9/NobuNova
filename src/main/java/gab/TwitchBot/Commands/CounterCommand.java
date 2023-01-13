package gab.TwitchBot.Commands;

import java.util.List;

import org.kitteh.irc.client.library.element.MessageTag;
import org.kitteh.irc.client.library.feature.twitch.messagetag.Badges;
import org.kitteh.irc.client.library.feature.twitch.messagetag.Badges.Badge;

import gab.TwitchBot.Utils.CommandEvent;

public class CounterCommand extends Command {

    public CounterCommand() {
        this.name = "counter";
    }
    
    @Override
    public void execute(CommandEvent event) {
        String command = event.getCommand();
        List<MessageTag> tags = event.getInnerEvent().getTags();
        
        String message = "Message tags:";
        for(MessageTag tag : tags) {
            message += "\n- " + tag;
        }

        Badges badges = event.getInnerEvent().getTag("badges", Badges.class).get();
        message += "\nBadges:";
        
        Boolean isAllowed = false;
        for(Badge badge : badges.getBadges()){
            message += "\n- " + badge;

            if(badge.getName().compareTo("broadcaster") == 0
                || badges.getName().compareTo("moderator") == 0) {
                isAllowed = true;
            }
        }

        if(command != this.name)
        {
            // TODO: Handle counters
            return;
        }

        /* ===== NOTES =====
         * TODO:
         * - Create new class for counters
         * - Create list for counters
         * - Add new counters to list
         * - Add counter name chack to base.isThisCommand
         */


        if(isAllowed) {
            String[] args =  event.getArgs().split(" ");
            if(args.length > 1) {
                switch(args[0]) {
                    case "add":
                        // TODO: Add new counter
                        break;

                    case "rem":
                        // TODO: Remove existing counter
                        break;

                    case "res":
                        // TODO: Reset existing counter
                        break;

                    case "set":
                        // TODO: Set existing counter to specified value
                        break;

                    default:
                        // TODO: Default
                        break;
                }
            }
        }

        System.out.println(message);
    }
}
