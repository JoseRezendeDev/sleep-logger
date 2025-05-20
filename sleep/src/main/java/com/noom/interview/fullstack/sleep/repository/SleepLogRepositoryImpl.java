package com.noom.interview.fullstack.sleep.repository;

import com.noom.interview.fullstack.sleep.model.MorningMood;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.model.User;
import com.noom.interview.fullstack.sleep.service.adapter.SleepLogRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Repository
public class SleepLogRepositoryImpl implements SleepLogRepository {
    @Override
    public SleepLog save(SleepLog sleepLog) {
        // TODO Add Spring Data JPA
        return sleepLog;
    }

    @Override
    public SleepLog getLastNight(int userId) {
        // TODO Implement later
        User user = new User(1, "Jose");
        LocalTime goToBedTime = LocalTime.of(1, 0);
        LocalTime wakeUpTime = LocalTime.of(10, 40);

        return new SleepLog(goToBedTime, wakeUpTime, MorningMood.GOOD, user);
    }

    @Override
    public Set<SleepLog> getAllByDate(int userId, LocalDate initialDate, LocalDate finalDate) {
        // TODO Implement later
        User user = new User(1, "Jose");
        LocalTime goToBedTime = LocalTime.of(1, 0);
        LocalTime wakeUpTime = LocalTime.of(10, 40);

        SleepLog sleepLog1 = new SleepLog(goToBedTime, wakeUpTime, MorningMood.GOOD, user);
        SleepLog sleepLog2 = new SleepLog(goToBedTime.plusHours(1), wakeUpTime.plusHours(1), MorningMood.BAD, user);

        return Set.of(sleepLog1, sleepLog2);
    }
}
