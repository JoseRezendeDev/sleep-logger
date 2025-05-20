package com.noom.interview.fullstack.sleep.service;

import com.noom.interview.fullstack.sleep.dto.SleepLogDTO;
import com.noom.interview.fullstack.sleep.dto.SleepLogMonthAverageDTO;
import com.noom.interview.fullstack.sleep.factory.SleepLogFactory;
import com.noom.interview.fullstack.sleep.model.MorningMood;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.model.User;
import com.noom.interview.fullstack.sleep.service.adapter.SleepLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetSleepLogTest {

    @Mock
    private SleepLogRepository sleepLogRepository;

    @InjectMocks
    private GetSleepLog getSleepLog;

    @Test
    public void getLastNightHappyFlowTest() {
        int userId = 1;

        LocalTime goToBedTime = LocalTime.of(1, 0);
        LocalTime wakeUpTime = LocalTime.of(10, 40);
        MorningMood morningMood = MorningMood.GOOD;

        when(sleepLogRepository.getLastNight(userId))
                .thenReturn(new SleepLog(goToBedTime, wakeUpTime, morningMood, new User(1, "Jose")));

        SleepLogDTO sleepLogDTO = getSleepLog.getLastNight(1);

        verify(sleepLogRepository).getLastNight(userId);
        verifyNoMoreInteractions(sleepLogRepository);

        assertEquals(goToBedTime, sleepLogDTO.getGoToBedTime());
        assertEquals(wakeUpTime, sleepLogDTO.getWakeUpTime());
        assertEquals(morningMood, sleepLogDTO.getMorningMood());
    }

    @Test
    public void getLastNightInvalidUserTest() {
        try {
            getSleepLog.getLastNight(0);
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            verifyNoInteractions(sleepLogRepository);
            assertNotNull(e);
            assertEquals("Field userId is missing", e.getMessage());
        }
    }

    @Test
    public void getLastMonthAveragesHappyFlowTest() {
        LocalDate today = LocalDate.now();
        LocalDate oneMonthAgo = today.minusDays(30);

        SleepLog sleepLog1 = SleepLogFactory.getSleepLog1(LocalTime.of(22, 0), LocalTime.of(7, 30));
        SleepLog sleepLog2 = SleepLogFactory.getSleepLog2(LocalTime.of(23, 0), LocalTime.of(6, 30));
        SleepLog sleepLog3 = SleepLogFactory.getSleepLog3(LocalTime.of(0, 0), LocalTime.of(8, 30));
        SleepLog sleepLog4 = SleepLogFactory.getSleepLog4(LocalTime.of(1, 0), LocalTime.of(9, 30));

        Map<MorningMood, Integer> morningMoods = new HashMap<>();
        morningMoods.put(MorningMood.GOOD, 3);
        morningMoods.put(MorningMood.BAD, 1);
        morningMoods.put(MorningMood.OK, 0);

        when(sleepLogRepository.getAllByDate(sleepLog1.getUser().getId(), oneMonthAgo, today))
                .thenReturn(Set.of(sleepLog1, sleepLog2, sleepLog3, sleepLog4));

        SleepLogMonthAverageDTO sleepLogMonthAverageDTO = getSleepLog.getLastMonthAverages(sleepLog2.getUser().getId());

        verify(sleepLogRepository).getAllByDate(sleepLog1.getUser().getId(), oneMonthAgo, today);
        verifyNoMoreInteractions(sleepLogRepository);

        assertEquals(oneMonthAgo, sleepLogMonthAverageDTO.getInitialDate());
        assertEquals(today, sleepLogMonthAverageDTO.getFinalDate());
        assertEquals(LocalTime.of(23, 30), sleepLogMonthAverageDTO.getGoToBedTimeAverage());
        assertEquals(LocalTime.of(8, 0), sleepLogMonthAverageDTO.getWakeUpTimeAverage());
        assertEquals(Duration.parse("PT8H30M"), sleepLogMonthAverageDTO.getTotalTimeInBedAverage());
        assertEquals(morningMoods, sleepLogMonthAverageDTO.getMorningMoodFrequency());
    }
}