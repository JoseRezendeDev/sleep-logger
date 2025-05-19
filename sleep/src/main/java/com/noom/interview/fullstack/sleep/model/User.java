package com.noom.interview.fullstack.sleep.model;

import java.util.Objects;
import java.util.Set;

public class User {
    private String id;
    private String name;
    private Set<SleepLog> sleepLogs;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
