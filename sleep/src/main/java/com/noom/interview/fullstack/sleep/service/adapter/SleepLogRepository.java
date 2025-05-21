package com.noom.interview.fullstack.sleep.service.adapter;

import com.noom.interview.fullstack.sleep.model.SleepLog;
import java.time.LocalDate;
import java.util.Set;

public interface SleepLogRepository {
    void save(SleepLog sleepLog);

    SleepLog getByDate(int userId, LocalDate date);

    Set<SleepLog> getAllByDate(int userId, LocalDate initialDate, LocalDate finalDate);
}
