package gab.Utils;

public class Profile {
    private String identifier;
    private boolean attendanceOptedIn;
    private int attendanceCounter;

    public Profile(String identifier, boolean attendanceOptedIn, int attendanceCounter) {
        this.identifier = identifier;
        this.attendanceOptedIn = attendanceOptedIn;
        this.attendanceCounter = attendanceCounter;
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

    public void setAttendanceOptedIn(boolean attendanceOptedIn) {
        this.attendanceOptedIn = attendanceOptedIn;
    }

    public void setAttendanceCounter(int attendanceCounter) {
        this.attendanceCounter = attendanceCounter;
    }

    @Override
    public String toString() {
        return identifier + ";" + (attendanceOptedIn ? 1 : 0) + ";" + attendanceCounter;
    }
}
