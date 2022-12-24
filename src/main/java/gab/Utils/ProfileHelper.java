package gab.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.naming.NameNotFoundException;

public class ProfileHelper {
    private static final String PROFILES_FILE = "profiles.txt";
    private static ProfileHelper instance = null;

    private List<Profile> profiles;
    private boolean changed = false;

    private ProfileHelper() throws IOException {
        profiles = new ArrayList<>();

        File file = new File(PROFILES_FILE);
        if(!file.exists())
            file.createNewFile();

        Scanner reader = new Scanner(file, StandardCharsets.UTF_8);
        while(reader.hasNextLine())
        {
            String[] segments = reader.nextLine().split(";");

            if(segments.length == 6)
                profiles.add(new Profile(segments[0], segments[1], segments[2], (segments[3].compareTo("1") == 0), Integer.parseInt(segments[4]), (segments[5].compareTo("0") == 0) ? null : LocalDateTime.parse(segments[5])));
        }
        reader.close();
    }

    public static ProfileHelper getInstance() throws IOException {
        if(instance == null)
            synchronized(ProfileHelper.class){
                if(instance == null)
                    instance = new ProfileHelper();
            }

        return instance;
    }

    
    public Profile getProfile(String twitchName) throws NameNotFoundException, IOException, InterruptedException {
        for(Profile profile : profiles)
            if(profile.getTwitchName().compareTo(twitchName) == 0)
                return profile;

        TwitchHelper th = TwitchHelper.getInstance();
        String twitchId = th.getUserId(twitchName);

        for(Profile profile : profiles)
            if(profile.getTwitchId().compareTo(twitchId) == 0)
            {
                int index = profiles.indexOf(profile);
                profile.setTwitchName(twitchName);
                profiles.set(index, profile);
                changed = true;
                return profile;
            }

        return new Profile(twitchName, twitchId, "", false, 0, null);
    }
    public Profile getProfileByDiscord(String discordName) throws NameNotFoundException{
        for(Profile profile : profiles)
            if(profile.getDiscordName().compareTo(discordName) == 0)
                return profile;

        throw new NameNotFoundException();
    }

    public void setProfile(Profile profile) {
        int index = -1;

        for(int x = 0; x < profiles.size(); x++)
            if(profiles.get(x).getTwitchId() == profile.getTwitchId()) {
                index = x;
                break;
            }

        if(index != -1)
            profiles.set(index, profile);
        else
            profiles.add(profile);

        changed = true;
    }

    public void save() throws IOException {
        if(changed) {
            List<String> content = new ArrayList<>();

            for(Profile profile : profiles)
                content.add(profile.toString());

            Files.write(Path.of(PROFILES_FILE), content, StandardCharsets.UTF_8, StandardOpenOption.WRITE);

            changed = false;
        }
    }
}
