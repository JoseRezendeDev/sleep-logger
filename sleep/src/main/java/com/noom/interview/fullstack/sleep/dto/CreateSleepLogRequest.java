package com.noom.interview.fullstack.sleep.dto;

import java.util.Objects;

public class CreateSleepLogRequest {
    private final String goToBedTime;
    private final String wakeUpTime;
    private final String morningMood;
    private final int userId;

    public CreateSleepLogRequest(String goToBedTime, String wakeUpTime, String morningMood, int userId) {
        this.goToBedTime = goToBedTime;
        this.wakeUpTime = wakeUpTime;
        this.morningMood = morningMood;
        this.userId = userId;
    }

    public String getGoToBedTime() {
        return goToBedTime;
    }

    public String getWakeUpTime() {
        return wakeUpTime;
    }

    public String getMorningMood() {
        return morningMood;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CreateSleepLogRequest that = (CreateSleepLogRequest) o;
        return userId == that.userId && Objects.equals(goToBedTime, that.goToBedTime) && Objects.equals(wakeUpTime, that.wakeUpTime) && Objects.equals(morningMood, that.morningMood);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goToBedTime, wakeUpTime, morningMood, userId);
    }
}
