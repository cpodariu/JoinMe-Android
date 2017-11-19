package com.example.alexandru.joinme_android.domain;

import java.util.ArrayList;

public class Event {
    private int id;
    private String name;
    private String description;
    private String category;
    private String date;
    private String time;
    private String location;
    private ArrayList<User> users;
    private int admin; //TODO: User admin
    private Boolean open;


    public Event(String name, String description, String category, String date, String time,
                 String location, ArrayList<User> users, int admin, Boolean open) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.date = date;
        this.time = time;
        this.location = location;
        this.users = users;
        this.admin = admin;
        this.open = open;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", location='" + location + '\'' +
                ", users=" + users +
                ", admin=" + admin +
                '}';
    }
}
