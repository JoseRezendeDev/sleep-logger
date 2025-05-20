package com.noom.interview.fullstack.sleep.dto;

import com.noom.interview.fullstack.sleep.model.MorningMood;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class SleepLogDTO {
    private final LocalDate sleepDate;
    private final LocalTime goToBedTime;
    private final LocalTime wakeUpTime;
    private final Duration totalTimeInBed;
    private final MorningMood morningMood;

    public SleepLogDTO(LocalDate sleepDate, LocalTime goToBedTime, LocalTime wakeUpTime, Duration totalTimeInBed, MorningMood morningMood) {
        this.sleepDate = sleepDate;
        this.goToBedTime = goToBedTime;
        this.wakeUpTime = wakeUpTime;
        this.totalTimeInBed = totalTimeInBed;
        this.morningMood = morningMood;
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
}
