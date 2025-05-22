package com.noom.interview.fullstack.sleep.service;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetMonthAveragesTest {

    @Mock
    private SleepLogRepository sleepLogRepository;

    @Mock
    private GetUser getUser;

    @InjectMocks
    private GetMonthAverages getMonthAverages;

    @Test
    public void getLastMonthAveragesHappyFlowScenario1Test() {
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

        when(getUser.getById(sleepLog2.getUser().getId())).thenReturn(sleepLog2.getUser());
        when(sleepLogRepository.getAllByDateRange(sleepLog1.getUser().getId(), oneMonthAgo, today))
                .thenReturn(Set.of(sleepLog1, sleepLog2, sleepLog3, sleepLog4));

        SleepLogMonthAverageDTO sleepLogMonthAverageDTO = getMonthAverages.getLastMonthAverages(sleepLog2.getUser().getId());

        verify(getUser).getById(sleepLog2.getUser().getId());
        verify(sleepLogRepository).getAllByDateRange(sleepLog1.getUser().getId(), oneMonthAgo, today);
        verifyNoMoreInteractions(getUser, sleepLogRepository);

        assertEquals(oneMonthAgo, sleepLogMonthAverageDTO.getInitialDate());
        assertEquals(today, sleepLogMonthAverageDTO.getFinalDate());
        assertEquals(LocalTime.of(23, 30), sleepLogMonthAverageDTO.getGoToBedTimeAverage());
        assertEquals(LocalTime.of(8, 0), sleepLogMonthAverageDTO.getWakeUpTimeAverage());
        assertEquals(Duration.parse("PT8H30M"), sleepLogMonthAverageDTO.getTotalTimeInBedAverage());
        assertEquals(morningMoods, sleepLogMonthAverageDTO.getMorningMoodFrequency());
    }

    @Test
    public void getLastMonthAveragesHappyFlowScenario2Test() {
        LocalDate today = LocalDate.now();
        LocalDate oneMonthAgo = today.minusDays(30);

        SleepLog sleepLog1 = SleepLogFactory.getSleepLog1(LocalTime.of(23, 59), LocalTime.of(4, 59));
        SleepLog sleepLog2 = SleepLogFactory.getSleepLog2(LocalTime.of(0, 2), LocalTime.of(5, 1));
        SleepLog sleepLog3 = SleepLogFactory.getSleepLog3(LocalTime.of(0, 3), LocalTime.of(5, 1));

        Map<MorningMood, Integer> morningMoods = new HashMap<>();
        morningMoods.put(MorningMood.GOOD, 2);
        morningMoods.put(MorningMood.BAD, 1);
        morningMoods.put(MorningMood.OK, 0);

        when(getUser.getById(sleepLog2.getUser().getId())).thenReturn(sleepLog2.getUser());
        when(sleepLogRepository.getAllByDateRange(sleepLog1.getUser().getId(), oneMonthAgo, today))
                .thenReturn(Set.of(sleepLog1, sleepLog2, sleepLog3));

        SleepLogMonthAverageDTO sleepLogMonthAverageDTO = getMonthAverages.getLastMonthAverages(sleepLog2.getUser().getId());

        verify(getUser).getById(sleepLog2.getUser().getId());
        verify(sleepLogRepository).getAllByDateRange(sleepLog1.getUser().getId(), oneMonthAgo, today);
        verifyNoMoreInteractions(getUser, sleepLogRepository);

        assertEquals(oneMonthAgo, sleepLogMonthAverageDTO.getInitialDate());
        assertEquals(today, sleepLogMonthAverageDTO.getFinalDate());
        assertEquals(LocalTime.of(0, 1), sleepLogMonthAverageDTO.getGoToBedTimeAverage());
        assertEquals(LocalTime.of(5, 0), sleepLogMonthAverageDTO.getWakeUpTimeAverage());
        assertEquals(Duration.parse("PT4H59M"), sleepLogMonthAverageDTO.getTotalTimeInBedAverage());
        assertEquals(morningMoods, sleepLogMonthAverageDTO.getMorningMoodFrequency());
    }

    @Test
    public void getLastMonthAveragesEmptySleepLogListTest() {
        LocalDate today = LocalDate.now();
        LocalDate oneMonthAgo = today.minusDays(30);
        int userId = 1;

        Map<MorningMood, Integer> morningMoods = new HashMap<>();
        morningMoods.put(MorningMood.GOOD, 0);
        morningMoods.put(MorningMood.BAD, 0);
        morningMoods.put(MorningMood.OK, 0);

        when(getUser.getById(userId)).thenReturn(new User(userId, "Jose"));
        when(sleepLogRepository.getAllByDateRange(userId, oneMonthAgo, today))
                .thenReturn(Collections.emptySet());

        SleepLogMonthAverageDTO sleepLogMonthAverageDTO = getMonthAverages.getLastMonthAverages(userId);

        verify(getUser).getById(userId);
        verify(sleepLogRepository).getAllByDateRange(userId, oneMonthAgo, today);
        verifyNoMoreInteractions(getUser, sleepLogRepository);

        assertEquals(oneMonthAgo, sleepLogMonthAverageDTO.getInitialDate());
        assertEquals(today, sleepLogMonthAverageDTO.getFinalDate());
        assertNull(sleepLogMonthAverageDTO.getGoToBedTimeAverage());
        assertNull(sleepLogMonthAverageDTO.getWakeUpTimeAverage());
        assertNull(sleepLogMonthAverageDTO.getTotalTimeInBedAverage());
        assertEquals(morningMoods, sleepLogMonthAverageDTO.getMorningMoodFrequency());
    }

    @Test
    public void getLastNightInvalidUserTest() {
        try {
            getMonthAverages.getLastMonthAverages(0);
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            verifyNoInteractions(sleepLogRepository);
            assertNotNull(e);
            assertEquals("Field userId is missing", e.getMessage());
        }
    }
}