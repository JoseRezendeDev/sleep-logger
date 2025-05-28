package com.noom.interview.fullstack.sleep.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Objects;

public class CreateSleepLogRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String sleepDate;

    @JsonFormat(pattern = "HH:mm")
    private String goToBedTime;

    @JsonFormat(pattern = "HH:mm")
    private String wakeUpTime;

    private String morningMood;
    private Integer userId;

    public CreateSleepLogRequest(String goToBedTime, String wakeUpTime, String morningMood, Integer userId) {
        this.goToBedTime = goToBedTime;
        this.wakeUpTime = wakeUpTime;
        this.morningMood = morningMood;
        this.userId = userId;
    }

    public CreateSleepLogRequest(String sleepDate, String goToBedTime, String wakeUpTime, String morningMood, Integer userId) {
        this.sleepDate = sleepDate;
        this.goToBedTime = goToBedTime;
        this.wakeUpTime = wakeUpTime;
        this.morningMood = morningMood;
        this.userId = userId;
    }

    public CreateSleepLogRequest() {
    }

    public String getSleepDate() {
        return sleepDate;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CreateSleepLogRequest request = (CreateSleepLogRequest) o;
        return Objects.equals(sleepDate, request.sleepDate) && Objects.equals(goToBedTime, request.goToBedTime) && Objects.equals(wakeUpTime, request.wakeUpTime) && Objects.equals(morningMood, request.morningMood) && Objects.equals(userId, request.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sleepDate, goToBedTime, wakeUpTime, morningMood, userId);
    }
}
