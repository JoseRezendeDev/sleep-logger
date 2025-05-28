package com.noom.interview.fullstack.sleep.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.noom.interview.fullstack.sleep.model.MorningMood;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class SleepLogDTO {

    private final int id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate sleepDate;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime goToBedTime;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime wakeUpTime;

    private final Duration totalTimeInBed;

    private final MorningMood morningMood;

    public SleepLogDTO(int id, LocalDate sleepDate, LocalTime goToBedTime, LocalTime wakeUpTime, Duration totalTimeInBed, MorningMood morningMood) {
        this.id = id;
        this.sleepDate = sleepDate;
        this.goToBedTime = goToBedTime;
        this.wakeUpTime = wakeUpTime;
        this.totalTimeInBed = totalTimeInBed;
        this.morningMood = morningMood;
    }

    public int getId() {
        return id;
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
