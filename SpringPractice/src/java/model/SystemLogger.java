package java.model;

import java.util.Date;

public class SystemLogger {
    private final String startTime;

    public SystemLogger(){
        this.startTime = new Date().toString();
        System.out.println("-> [SystemLogger] Initialized at: " + this.startTime);
    }
    public void log(String message){
        System.out.printf("[LOG %s] %s%n", this.startTime.substring(11, 19), message);
    }
}
