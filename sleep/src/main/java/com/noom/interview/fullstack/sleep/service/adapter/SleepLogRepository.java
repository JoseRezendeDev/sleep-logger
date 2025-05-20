package com.noom.interview.fullstack.sleep.service.adapter;

import com.noom.interview.fullstack.sleep.model.SleepLog;
import java.time.LocalDate;
import java.util.Set;

public interface SleepLogRepository {
    SleepLog save(SleepLog sleepLog);

    SleepLog getLastNight(int userId);

    Set<SleepLog> getAllByDate(int userId, LocalDate initialDate, LocalDate finalDate);
}
