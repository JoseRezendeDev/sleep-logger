package com.noom.interview.fullstack.sleep.dto;

import com.noom.interview.fullstack.sleep.model.MorningMood;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class SleepLogMonthAverageDTO {
    private LocalDate initialDate;
    private LocalDate finalDate;
    private LocalTime goToBedTimeAverage;
    private LocalTime wakeUpTimeAverage;
    private Duration totalTimeInBedAverage;
    private Map<MorningMood, Integer> morningMoodFrequency;

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    public LocalTime getGoToBedTimeAverage() {
        return goToBedTimeAverage;
    }

    public void setGoToBedTimeAverage(LocalTime goToBedTimeAverage) {
        this.goToBedTimeAverage = goToBedTimeAverage;
    }

    public LocalTime getWakeUpTimeAverage() {
        return wakeUpTimeAverage;
    }

    public void setWakeUpTimeAverage(LocalTime wakeUpTimeAverage) {
        this.wakeUpTimeAverage = wakeUpTimeAverage;
    }

    public Duration getTotalTimeInBedAverage() {
        return totalTimeInBedAverage;
    }

    public void setTotalTimeInBedAverage(Duration totalTimeInBedAverage) {
        this.totalTimeInBedAverage = totalTimeInBedAverage;
    }

    public Map<MorningMood, Integer> getMorningMoodFrequency() {
        return morningMoodFrequency;
    }

    public void setMorningMoodFrequency(Map<MorningMood, Integer> morningMoodFrequency) {
        this.morningMoodFrequency = morningMoodFrequency;
    }
}
