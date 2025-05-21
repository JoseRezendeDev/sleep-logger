package com.noom.interview.fullstack.sleep.factory;

import com.noom.interview.fullstack.sleep.model.MorningMood;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.model.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class SleepLogFactory {

    public static SleepLog getSleepLog1(LocalTime goToBedTime, LocalTime wakeUpTime) {
        User user = new User(1, "Jose");
        return new SleepLog(LocalDate.now(), goToBedTime, wakeUpTime, MorningMood.GOOD, user);
    }

    public static SleepLog getSleepLog2(LocalTime goToBedTime, LocalTime wakeUpTime) {
        User user = new User(1, "Jose");
        return new SleepLog(LocalDate.now(), goToBedTime, wakeUpTime, MorningMood.GOOD, user);
    }

    public static SleepLog getSleepLog3(LocalTime goToBedTime, LocalTime wakeUpTime) {
        User user = new User(1, "Jose");
        return new SleepLog(LocalDate.now(), goToBedTime, wakeUpTime, MorningMood.BAD, user);
    }

    public static SleepLog getSleepLog4(LocalTime goToBedTime, LocalTime wakeUpTime) {
        User user = new User(1, "Jose");
        return new SleepLog(LocalDate.now(), goToBedTime, wakeUpTime, MorningMood.GOOD, user);
    }

    public static SleepLog getSleepLog1DayAgo(LocalTime goToBedTime, LocalTime wakeUpTime) {
        User user = new User(1, "Jose");
        return new SleepLog(LocalDate.now().minusDays(1), goToBedTime, wakeUpTime, MorningMood.OK, user);
    }

    public static SleepLog getSleepLog2DaysAgo(LocalTime goToBedTime, LocalTime wakeUpTime) {
        User user = new User(1, "Jose");
        return new SleepLog(LocalDate.now().minusDays(2), goToBedTime, wakeUpTime, MorningMood.GOOD, user);
    }
}
