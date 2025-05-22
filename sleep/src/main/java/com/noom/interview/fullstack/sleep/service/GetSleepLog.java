package com.noom.interview.fullstack.sleep.service;

import com.noom.interview.fullstack.sleep.dto.SleepLogDTO;
import com.noom.interview.fullstack.sleep.exception.SleepLogNotFoundException;
import com.noom.interview.fullstack.sleep.mapper.SleepLogMapper;
import com.noom.interview.fullstack.sleep.service.adapter.SleepLogRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class GetSleepLog {

    private final SleepLogRepository sleepLogRepository;

    public GetSleepLog(SleepLogRepository sleepLogRepository) {
        this.sleepLogRepository = sleepLogRepository;
    }

    public SleepLogDTO getByDate(int userId, LocalDate date) {
        validateUser(userId);

        try {
            return SleepLogMapper.toDTO(sleepLogRepository.getByDate(userId, date));
        } catch (EmptyResultDataAccessException e) {
            throw new SleepLogNotFoundException("Cannot find sleep log for user ID " + userId + " and date " + date);
        }
    }

    public SleepLogDTO getByDateFromRequest(int userId, String date) {
        return getByDate(userId, getDate(date));
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

    private void validateUser(int userId) {
        if (userId == 0) {
            throw new IllegalArgumentException("Field userId is missing");
        }
    }
}
