package gab.Utils;

import java.time.LocalDateTime;

public class Profile {
    private String identifier;
    private boolean attendanceOptedIn;
    private int attendanceCounter;
    private LocalDateTime lastAttendance;

    public Profile(String identifier, boolean attendanceOptedIn, int attendanceCounter, LocalDateTime lastAttendance) {
        this.identifier = identifier;
        this.attendanceOptedIn = attendanceOptedIn;
        this.attendanceCounter = attendanceCounter;
        this.lastAttendance = lastAttendance;
    }

    public String getIdentifier() {
        return identifier;
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
    
    @Override
    public String toString() {
        return identifier + ";" + (attendanceOptedIn ? 1 : 0) + ";" + attendanceCounter + ";" + (lastAttendance != null ? lastAttendance.toString() : "0");
    }
}
