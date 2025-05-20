package com.noom.interview.fullstack.sleep.factory;

import com.noom.interview.fullstack.sleep.model.MorningMood;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.model.User;

import java.time.LocalTime;

public class SleepLogFactory {

    public static SleepLog getSleepLog1(LocalTime goToBedTime, LocalTime wakeUpTime) {
        User user = new User(1, "Jose");
        MorningMood morningMood = MorningMood.GOOD;
        return new SleepLog(goToBedTime, wakeUpTime, morningMood, user);
    }

    public static SleepLog getSleepLog2(LocalTime goToBedTime, LocalTime wakeUpTime) {
        User user = new User(1, "Jose");
        MorningMood morningMood = MorningMood.GOOD;
        return new SleepLog(goToBedTime, wakeUpTime, morningMood, user);
    }

    public static SleepLog getSleepLog3(LocalTime goToBedTime, LocalTime wakeUpTime) {
        User user = new User(1, "Jose");
        MorningMood morningMood = MorningMood.BAD;
        return new SleepLog(goToBedTime, wakeUpTime, morningMood, user);
    }

    public static SleepLog getSleepLog4(LocalTime goToBedTime, LocalTime wakeUpTime) {
        User user = new User(1, "Jose");
        MorningMood morningMood = MorningMood.GOOD;
        return new SleepLog(goToBedTime, wakeUpTime, morningMood, user);
    }
}
