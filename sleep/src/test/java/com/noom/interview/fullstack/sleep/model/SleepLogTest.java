package com.noom.interview.fullstack.sleep.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SleepLogTest {

    @Test
    public void constructorSleepBeforeMidnightTest() {
        User user = new User();
        LocalTime goToBedTime = LocalTime.of(22, 30);
        LocalTime wakeUpTime = LocalTime.of(7, 0);

        SleepLog sleepLog = new SleepLog(goToBedTime, wakeUpTime, MorningMood.GOOD, user);

        assertEquals(goToBedTime, sleepLog.getGoToBedTime());
        assertEquals(wakeUpTime, sleepLog.getWakeUpTime());
        assertEquals(MorningMood.GOOD, sleepLog.getMorningMood());
        assertEquals(Duration.parse("PT8H30M"), sleepLog.getTotalTimeInBed());
        assertEquals(LocalDate.now(), sleepLog.getSleepDate());
        assertEquals(user, sleepLog.getUser());
    }

    @Test
    public void constructorSleepAfterMidnightTest() {
        User user = new User();
        LocalTime goToBedTime = LocalTime.of(1, 0);
        LocalTime wakeUpTime = LocalTime.of(10, 40);

        SleepLog sleepLog = new SleepLog(goToBedTime, wakeUpTime, MorningMood.GOOD, user);

        assertEquals(goToBedTime, sleepLog.getGoToBedTime());
        assertEquals(wakeUpTime, sleepLog.getWakeUpTime());
        assertEquals(MorningMood.GOOD, sleepLog.getMorningMood());
        assertEquals(Duration.parse("PT9H40M"), sleepLog.getTotalTimeInBed());
        assertEquals(LocalDate.now(), sleepLog.getSleepDate());
        assertEquals(user, sleepLog.getUser());
    }
}