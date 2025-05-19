package com.noom.interview.fullstack.sleep.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class SleepLog {
    private int id;
    private LocalDate sleepDate;
    private LocalTime goToBedTime;
    private LocalTime wakeUpTime;
    private Duration totalTimeInBed;
    private MorningMood morningMood;
    private User user;

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
