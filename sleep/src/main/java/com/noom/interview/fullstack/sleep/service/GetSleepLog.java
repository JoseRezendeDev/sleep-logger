package com.noom.interview.fullstack.sleep.service;

import com.noom.interview.fullstack.sleep.dto.SleepLogDTO;
import com.noom.interview.fullstack.sleep.dto.SleepLogMonthAverageDTO;
import com.noom.interview.fullstack.sleep.exception.SleepLogNotFoundException;
import com.noom.interview.fullstack.sleep.mapper.SleepLogMapper;
import com.noom.interview.fullstack.sleep.model.MorningMood;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.model.User;
import com.noom.interview.fullstack.sleep.service.adapter.SleepLogRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GetSleepLog {

    private final SleepLogRepository sleepLogRepository;
    private final GetUser getUser;

    public GetSleepLog(SleepLogRepository sleepLogRepository, GetUser getUser) {
        this.sleepLogRepository = sleepLogRepository;
        this.getUser = getUser;
    }

    public SleepLogDTO getByDateFromRequest(int userId, String date) {
        return getByDate(userId, getDate(date));
    }

    /**
     * MAIN METHOD TO GET A SINGLE SLEEP LOG
     */
    public SleepLogDTO getByDate(int userId, LocalDate date) {
        validateRequest(userId);

        try {
            return SleepLogMapper.toDTO(sleepLogRepository.getByDate(userId, date));
        } catch (EmptyResultDataAccessException e) {
            throw new SleepLogNotFoundException("Cannot find sleep log for user ID " + userId + " and date " + date);
        }
    }

    private LocalDate getDate(String dateString) {
        if (!StringUtils.hasText(dateString)) {
            return LocalDate.now();
        }

        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Query param 'date' must be in the format YYYY-MM-DD");
        }
    }

    private void validateRequest(int userId) {
        if (userId == 0) {
            throw new IllegalArgumentException("Field userId is missing");
        }
    }

    /**
     * MAIN METHOD TO GET MONTH AVERAGES
     */
    public SleepLogMonthAverageDTO getLastMonthAverages(int userId) {
        validateRequest(userId);

        LocalDate today = LocalDate.now();
        LocalDate oneMonthAgo = today.minusDays(30);

        User user = getUser.getById(userId);

        Set<SleepLog> sleepLogs = sleepLogRepository.getAllByDateRange(user.getId(), oneMonthAgo, today);

        if (sleepLogs.isEmpty()) {
            return generateEmptyAverages(oneMonthAgo, today);
        }

        SleepLogMonthAverageDTO sleepLogMonthAverageDTO = new SleepLogMonthAverageDTO();
        sleepLogMonthAverageDTO.setInitialDate(oneMonthAgo);
        sleepLogMonthAverageDTO.setFinalDate(today);
        sleepLogMonthAverageDTO.setGoToBedTimeAverage(calculateGoToBedTimeAverage(sleepLogs));
        sleepLogMonthAverageDTO.setWakeUpTimeAverage(calculateWakeUpTimeAverage(sleepLogs));
        sleepLogMonthAverageDTO.setTotalTimeInBedAverage(calculateTotalTimeInBedAverage(sleepLogs));
        sleepLogMonthAverageDTO.setMorningMoodFrequency(generateMorningMoodFrequency(sleepLogs));

        return sleepLogMonthAverageDTO;
    }

    private SleepLogMonthAverageDTO generateEmptyAverages(LocalDate oneMonthAgo, LocalDate today) {
        Map<MorningMood, Integer> frequencies = new HashMap<>();

        for (MorningMood morningMood : MorningMood.values()) {
            frequencies.put(morningMood, 0);
        }

        return new SleepLogMonthAverageDTO(oneMonthAgo, today, null, null, null, frequencies);
    }

    private Map<MorningMood, Integer> generateMorningMoodFrequency(Set<SleepLog> sleepLogs) {
        Map<MorningMood, Integer> frequencies = new HashMap<>();

        for (MorningMood morningMood : MorningMood.values()) {
            frequencies.put(morningMood, 0);
        }

        for (SleepLog sleepLog : sleepLogs) {
            frequencies.put(sleepLog.getMorningMood(), frequencies.get(sleepLog.getMorningMood()) + 1);
        }

        return frequencies;
    }

    private Duration calculateTotalTimeInBedAverage(Set<SleepLog> sleepLogs) {
        Set<Duration> totalTimesInBed = sleepLogs.stream()
                .map(SleepLog::getTotalTimeInBed)
                .collect(Collectors.toSet());

        Duration minutes = Duration.ZERO;

        for (Duration totalTimeInBed : totalTimesInBed) {
            minutes = minutes.plusMinutes(totalTimeInBed.toMinutes());
        }

        return minutes.dividedBy(totalTimesInBed.size());
    }

    private LocalTime calculateGoToBedTimeAverage(Set<SleepLog> sleepLogs) {
        Set<LocalTime> times = sleepLogs.stream()
                .map(SleepLog::getGoToBedTime)
                .collect(Collectors.toSet());

        int minutes = 0;

        for (LocalTime time : times) {
            if (time.isAfter(LocalTime.NOON)) {
                minutes = minutes + time.getHour() * 60;
            } else {
                minutes = minutes + 24 * 60 + time.getHour() * 60;
            }

            minutes = minutes + time.getMinute();
        }

        int averageMinutes = minutes / times.size();

        int averageHour = averageMinutes / 60;

        return LocalTime.of(averageHour >= 24 ? averageHour - 24 : averageHour, averageMinutes % 60);
    }

    private LocalTime calculateWakeUpTimeAverage(Set<SleepLog> sleepLogs) {
        Set<LocalTime> times = sleepLogs.stream()
                .map(SleepLog::getWakeUpTime)
                .collect(Collectors.toSet());

        int minutes = 0;

        for (LocalTime time : times) {
            minutes = minutes + time.getHour() * 60 + time.getMinute();
        }

        int averageMinutes = minutes / times.size();

        return LocalTime.of(averageMinutes / 60, averageMinutes % 60);
    }
}
