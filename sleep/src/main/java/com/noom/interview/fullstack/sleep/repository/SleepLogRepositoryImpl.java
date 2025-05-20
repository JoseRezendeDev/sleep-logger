package com.noom.interview.fullstack.sleep.repository;

import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.service.adapter.SleepLogRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SleepLogRepositoryImpl implements SleepLogRepository {
    @Override
    public SleepLog save(SleepLog sleepLog) {
        // TODO Add Spring Data JPA
        return sleepLog;
    }
}
