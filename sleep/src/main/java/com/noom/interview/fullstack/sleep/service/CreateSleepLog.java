package com.noom.interview.fullstack.sleep.service;

import com.noom.interview.fullstack.sleep.dto.CreateSleepLogRequest;
import com.noom.interview.fullstack.sleep.dto.SleepLogDTO;
import com.noom.interview.fullstack.sleep.exception.SleepDateAlreadyExistsException;
import com.noom.interview.fullstack.sleep.model.MorningMood;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.model.User;
import com.noom.interview.fullstack.sleep.service.adapter.SleepLogRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;

@Service
public class CreateSleepLog {

    private final SleepLogRepository sleepLogRepository;
    private final GetSleepLog getSleepLog;
    private final GetUser getUser;

    public CreateSleepLog(SleepLogRepository sleepLogRepository, GetSleepLog getSleepLog, GetUser getUser) {
        this.sleepLogRepository = sleepLogRepository;
        this.getSleepLog = getSleepLog;
        this.getUser = getUser;
    }

    public SleepLogDTO create(CreateSleepLogRequest request) {
        validateRequest(request);

        LocalTime goToBedTime;
        LocalTime wakeUpTime;
        try {
            goToBedTime = LocalTime.of(Integer.parseInt(request.getGoToBedTime().substring(0, 2)), Integer.parseInt(request.getGoToBedTime().substring(3, 5)));
            wakeUpTime = LocalTime.of(Integer.parseInt(request.getWakeUpTime().substring(0, 2)), Integer.parseInt(request.getWakeUpTime().substring(3, 5)));
        } catch (DateTimeException | NumberFormatException e) {
            throw new IllegalArgumentException("Failed to obtain time from fields goToBedTime and wakeUpTime");
        }

        User user = getUser.getById(request.getUserId());

        SleepLog sleepLog = new SleepLog(LocalDate.now(), goToBedTime, wakeUpTime, MorningMood.valueOf(request.getMorningMood()), user);

        try {
            sleepLogRepository.save(sleepLog);
        } catch (DuplicateKeyException e) {
            throw new SleepDateAlreadyExistsException("There is already a sleep log for this date: " + sleepLog.getSleepDate());
        }

        return getSleepLog.getLastNight(user.getId());
    }

    private void validateRequest(CreateSleepLogRequest request) {
        // TODO Implement Controller Advice and Exception Handler
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException("Sleep Log data is missing on body request");
        }

        if (Objects.isNull(request.getGoToBedTime()) || request.getGoToBedTime().length() != 5) {
            throw new IllegalArgumentException("Field goToBedTime must be in the format HH:MM");
        }

        if (Objects.isNull(request.getWakeUpTime()) || request.getWakeUpTime().length() != 5) {
            throw new IllegalArgumentException("Field wakeUpTime must be in the format HH:MM");
        }

        try {
            MorningMood.valueOf(request.getMorningMood());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Field morningMood must be one of these: " + Arrays.toString(MorningMood.values()));
        }

        if (request.getUserId() == 0) {
            throw new IllegalArgumentException("Field userId is missing");
        }
    }
}
