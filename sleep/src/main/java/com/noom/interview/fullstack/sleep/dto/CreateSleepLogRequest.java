package com.noom.interview.fullstack.sleep.dto;

import java.util.Objects;

public class CreateSleepLogRequest {
    private String goToBedTime;
    private String wakeUpTime;
    private String morningMood;
    private Integer userId;

    public CreateSleepLogRequest(String goToBedTime, String wakeUpTime, String morningMood, Integer userId) {
        this.goToBedTime = goToBedTime;
        this.wakeUpTime = wakeUpTime;
        this.morningMood = morningMood;
        this.userId = userId;
    }

    public CreateSleepLogRequest() {
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

    public Integer getUserId() {
        return userId;
    }

    public void setGoToBedTime(String goToBedTime) {
        this.goToBedTime = goToBedTime;
    }

    public void setWakeUpTime(String wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    public void setMorningMood(String morningMood) {
        this.morningMood = morningMood;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CreateSleepLogRequest request = (CreateSleepLogRequest) o;
        return Objects.equals(goToBedTime, request.goToBedTime) && Objects.equals(wakeUpTime, request.wakeUpTime) && Objects.equals(morningMood, request.morningMood) && Objects.equals(userId, request.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goToBedTime, wakeUpTime, morningMood, userId);
    }
}
