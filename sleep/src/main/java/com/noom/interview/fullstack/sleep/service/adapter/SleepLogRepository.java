package com.noom.interview.fullstack.sleep.service.adapter;

import com.noom.interview.fullstack.sleep.model.SleepLog;

public interface SleepLogRepository {
    SleepLog save(SleepLog sleepLog);
}
