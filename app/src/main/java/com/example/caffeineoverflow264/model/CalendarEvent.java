package com.example.caffeineoverflow264.model;

public class CalendarEvent {
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getEventCount() {
        return eventCount;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    String eventName;
    int eventCount;

    public CalendarEvent(String eventName, int eventCount ) {
        this.eventName = eventName;
        this.eventCount = eventCount;
    }
}