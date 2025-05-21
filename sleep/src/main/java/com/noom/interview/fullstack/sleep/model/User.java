package com.noom.interview.fullstack.sleep.model;

import java.util.Objects;
import java.util.Set;

public class User {
    private int id;
    private String name;
    private Set<SleepLog> sleepLogs;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
