package com.example.alexandru.joinme_android.domain.response;

import com.example.alexandru.joinme_android.domain.Event;

import java.util.ArrayList;

/**
 * Created by Alexandru on 11/18/2017.
 */

public class EventResponse {
    private ArrayList<Event> events;

    public EventResponse(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
