package gab.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

            if(segments.length == 3)
                profiles.add(new Profile(segments[0], (segments[1].compareTo("1") == 0), Integer.parseInt(segments[2])));
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

    
    public Profile getProfile(String identifier) {
        for(Profile profile : profiles)
            if(profile.getIdentifier().compareTo(identifier) == 0)
                return profile;

        return new Profile(identifier, false, 0);
    }

    public void setProfile(Profile profile) {
        int index = -1;

        for(int x = 0; x < profiles.size(); x++)
            if(profiles.get(x).getIdentifier() == profile.getIdentifier()) {
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
