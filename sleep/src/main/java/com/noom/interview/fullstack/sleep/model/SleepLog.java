package com.noom.interview.fullstack.sleep.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class SleepLog {
    private int id;
    private final LocalDate sleepDate;
    private final LocalTime goToBedTime;
    private final LocalTime wakeUpTime;
    private final Duration totalTimeInBed;
    private final MorningMood morningMood;
    private User user;

    public SleepLog(LocalDate sleepDate, LocalTime goToBedTime, LocalTime wakeUpTime, MorningMood morningMood, User user) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("Cannot create a Sleep Log without a user");
        }

        this.goToBedTime = goToBedTime;
        this.wakeUpTime = wakeUpTime;
        this.morningMood = morningMood;
        this.sleepDate = sleepDate;
        this.user = user;

        // This checks if the user slept before or after MIDNIGHT
        if (goToBedTime.isAfter(LocalTime.NOON)) {
            Duration afterMidnight = Duration.between(LocalTime.MIDNIGHT, wakeUpTime);
            Duration beforeMidnight = Duration.between(goToBedTime, LocalTime.of(23, 59)).plusMinutes(1);
            this.totalTimeInBed = afterMidnight.plus(beforeMidnight);
        } else {
            this.totalTimeInBed = Duration.between(goToBedTime, wakeUpTime).abs();
        }
    }

    /**
     * DO NOT USE THIS CONSTRUCTOR. IT IS USED ONLY FOR DATABASE MAPPING.
     * IF YOU USE IT, IT MAY LEAD TO INCONSISTENCIES AMONG FIELDS GO TO BE TIME, WAKE UP TIME
     * AND TOTAL TIME IN BED
     */
    public SleepLog(int id, LocalDate sleepDate, LocalTime goToBedTime, LocalTime wakeUpTime, Duration totalTimeInBed, MorningMood morningMood, User user) {
        this.id = id;
        this.sleepDate = sleepDate;
        this.goToBedTime = goToBedTime;
        this.wakeUpTime = wakeUpTime;
        this.totalTimeInBed = totalTimeInBed;
        this.morningMood = morningMood;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getSleepDate() {
        return sleepDate;
    }

    public LocalTime getGoToBedTime() {
        return goToBedTime;
    }

    public LocalTime getWakeUpTime() {
        return wakeUpTime;
    }

    public Duration getTotalTimeInBed() {
        return totalTimeInBed;
    }

    public MorningMood getMorningMood() {
        return morningMood;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SleepLog sleepLog = (SleepLog) o;
        return id == sleepLog.id && Objects.equals(sleepDate, sleepLog.sleepDate) && Objects.equals(goToBedTime, sleepLog.goToBedTime) && Objects.equals(wakeUpTime, sleepLog.wakeUpTime) && Objects.equals(totalTimeInBed, sleepLog.totalTimeInBed) && morningMood == sleepLog.morningMood && Objects.equals(user, sleepLog.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sleepDate, goToBedTime, wakeUpTime, totalTimeInBed, morningMood, user);
    }
}
