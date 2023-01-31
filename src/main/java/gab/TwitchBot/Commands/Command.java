package gab.TwitchBot.Commands;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.NameNotFoundException;

import gab.TwitchBot.Utils.CommandEvent;

public abstract class Command {
    protected String name;
    protected Set<String> aliases = new HashSet<String>();

    public boolean isThisCommand(String command) {
        return (name.compareTo(command) == 0) || aliases.contains(command);
    }

    public abstract void execute(CommandEvent event) throws IOException, NameNotFoundException, InterruptedException;

    protected void sendMessage(CommandEvent event, String message) {
        event.getInnerEvent().getChannel().sendMessage("/me " + message);
    }

    protected <T> void save(List<T> objects, String fileName) {
        try {
            List<String> content = new ArrayList<String>();
            for(Object object : objects)
                content.add(object.toString());

            Files.write(Path.of(fileName), content, StandardCharsets.UTF_8, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
