package com.noom.interview.fullstack.sleep.dto;

import com.noom.interview.fullstack.sleep.model.MorningMood;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SleepLogDTO that = (SleepLogDTO) o;
        return Objects.equals(sleepDate, that.sleepDate) && Objects.equals(goToBedTime, that.goToBedTime) && Objects.equals(wakeUpTime, that.wakeUpTime) && Objects.equals(totalTimeInBed, that.totalTimeInBed) && morningMood == that.morningMood;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sleepDate, goToBedTime, wakeUpTime, totalTimeInBed, morningMood);
    }
}
