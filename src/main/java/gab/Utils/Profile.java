package gab.Utils;

import java.time.LocalDateTime;

public class Profile {
    private String twitchName;
    private String twitchId;
    private String discordName;
    private boolean attendanceOptedIn;
    private int attendanceCounter;
    private LocalDateTime lastAttendance;

    public Profile(String twitchName, String twitchId, String discordName, boolean attendanceOptedIn, int attendanceCounter, LocalDateTime lastAttendance) {
        this.twitchName = twitchName;
        this.twitchId = twitchId;
        this.discordName = discordName;
        this.attendanceOptedIn = attendanceOptedIn;
        this.attendanceCounter = attendanceCounter;
        this.lastAttendance = lastAttendance;
    }

    public String getTwitchName() {
        return twitchName;
    }
    public String getTwitchId() {
        return twitchId;
    }
    public String getDiscordName() {
        return discordName;
    }
    public boolean isAttendanceOptedIn() {
        return attendanceOptedIn;
    }
    public int getAttendanceCounter() {
        return attendanceCounter;
    }
    public LocalDateTime getLastAttendance() {
        return lastAttendance;
    }

    public void setAttendanceOptedIn(boolean attendanceOptedIn) {
        this.attendanceOptedIn = attendanceOptedIn;
    }
    public void setAttendanceCounter(int attendanceCounter) {
        this.attendanceCounter = attendanceCounter;
    }
    public void setLastAttendance(LocalDateTime lastAttendance) {
        this.lastAttendance = lastAttendance;
    }
    public void setTwitchName(String twitchName) {
        this.twitchName = twitchName;
    }
    public void setDiscordName(String discordName) {
        this.discordName = discordName;
    }

    @Override
    public String toString() {
        return twitchName + ";" + twitchId + ";" + discordName + ";" + (attendanceOptedIn ? 1 : 0) + ";" + attendanceCounter + ";" + (lastAttendance != null ? lastAttendance.toString() : "0");
    }
}
