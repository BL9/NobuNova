package gab.TwitchBot.Commands;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.kitteh.irc.client.library.element.MessageTag;
import org.kitteh.irc.client.library.feature.twitch.messagetag.Badges;
import org.kitteh.irc.client.library.feature.twitch.messagetag.Badges.Badge;

import gab.TwitchBot.Utils.CommandEvent;
import gab.TwitchBot.Utils.Counter;
import gab.TwitchBot.Utils.Exceptions.FormatException;

public class CounterCommand extends Command {
    private static final String COUNTERS_FILE_NAME = "counters.txt";
    private List<Counter> counters;

    public CounterCommand() {
        this.name = "counter";

        counters = new ArrayList<Counter>();
        loadCounters();
    }

    @Override
    public boolean isThisCommand(String command) {
        return super.isThisCommand(command) || counters.stream().anyMatch(counter -> (counter.getName().equals(command)));
    }

    @Override
    public void execute(CommandEvent event) {
        String command = event.getCommand();
        String[] args =  event.getArgs().split(" ");
        Boolean success = false;


        // --------------- Badge debugging + isAllowed logic =================================================
        /*List<MessageTag> tags = event.getInnerEvent().getTags();
        
        String message2 = "Message tags:";
        for(MessageTag tag : tags) {
            message2 += "\n- " + tag;
        }*/

        Badges badges = event.getInnerEvent().getTag("badges", Badges.class).get();
        //message2 += "\nBadges:";
        
        Boolean isAllowed = false;
        for(Badge badge : badges.getBadges()){
            //message2 += "\n- " + badge;

            isAllowed = badge.getName().equals("broadcaster") || badges.getName().equals("moderator");

            if(isAllowed)
                break;
        }
        //System.out.println(message2);
        // ---------------- End Badge Debugging + isAllowed logic =============================================


        // COUNTER HANDLING
        if(!command.equals(this.name))
        {
            Optional<Counter> counter = counters.stream().filter(c -> (c.getName().equals(command))).findFirst();
            
            if(counter.isPresent())
            {
                if(isAllowed && args.length == 1) {
                    int index = counters.indexOf(counter.get());
                    switch(args[0]) {
                        case "+":
                            counter.get().setValue(counter.get().getValue() + 1);
                            success = true;
                            break;
    
                        case "-":
                            counter.get().setValue(counter.get().getValue() - 1);
                            success = true;
                            break;
                    }

                    counters.set(index, counter.get());
                }

                String message = counter.get().getName() + " : " + counter.get().getValue();
                sendMessage(event, message);
            }
        } else if(isAllowed) {
            // CREATE UPDATE DELETE RESET Counters
            if(args.length >= 2) {
                switch(args[0]) {
                    case "add":
                        if(counters.stream().anyMatch(counter -> (counter.getName().equals(args[1])))) {
                            sendMessage(event, "A counter with the name '" + args[1] + "' already exists.");
                        } else {
                            int value = args.length > 2 ? Integer.parseInt(args[2]) : 0;
                            Counter counter = new Counter(args[1], value, value);
                            counters.add(counter);
                            success = true;
                        }
                        break;

                    case "remove":
                        Optional<Counter> toDelete = counters.stream().filter(counter -> (counter.getName().equals(args[1]))).findFirst();
                        if(!toDelete.isPresent()) {
                            sendMessage(event, "No counter with the name '" + args[1] + "' exists.");
                        } else {
                            counters.remove(toDelete.get());
                            success = true;
                        }
                        break;

                    case "reset":
                        Optional<Counter> toReset = counters.stream().filter(counter -> (counter.getName().equals(args[1]))).findFirst();
                        if(!toReset.isPresent()) {
                            sendMessage(event, "No counter with the name '" + args[1] + "' exists.");
                        } else {
                            int index = counters.indexOf(toReset.get());
                            Counter c = toReset.get();
                            c.reset();
                            counters.set(index, c);

                            sendMessage(event, c.getName() + " : " + c.getValue());
                            success = true;
                        }
                        break;

                    case "set":
                        if(args.length == 3) {
                            Optional<Counter> toSet = counters.stream().filter(counter -> (counter.getName().equals(args[1]))).findFirst();
                            if(!toSet.isPresent()) {
                                sendMessage(event, "No counter with the name '" + args[1] + "' exists.");
                            } else {
                                int index = counters.indexOf(toSet.get());
                                Counter c = toSet.get();
                                c.setValue(Integer.parseInt(args[2]));
                                counters.set(index, c);

                                sendMessage(event, c.getName() + " - " + c.getValue());
                                success = true;
                            }
                        } else {
                            sendMessage(event, "To set a counter, a value must be given.");
                        }
                        break;
                }
            }
        }

        if(success)
            new Thread(() -> {save(counters, COUNTERS_FILE_NAME);}).start();
    }

    private void loadCounters() {
        try {
            File file = new File(COUNTERS_FILE_NAME);
            if(!file.exists())
                file.createNewFile();

            Scanner reader = new Scanner(file, StandardCharsets.UTF_8);
            while(reader.hasNextLine()) {
                try {
                    Counter counter = Counter.fromString(reader.nextLine());
                    counters.add(counter);
                } catch (FormatException e) {
                    System.out.println("An error occured while loading counters.");
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
