package com.noom.interview.fullstack.sleep.service;

import com.noom.interview.fullstack.sleep.dto.SleepLogDTO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetSleepLogTest {

    @Mock
    private SleepLogRepository sleepLogRepository;

    @Mock
    private GetUser getUser;

    @InjectMocks
    private GetSleepLog getSleepLog;

    @Test
    public void getLastNightHappyFlowTest() {
        int userId = 1;

        LocalDate today = LocalDate.now();
        LocalTime goToBedTime = LocalTime.of(22, 0);
        LocalTime wakeUpTime = LocalTime.of(6, 0);
        Duration totalTimeInBed = Duration.parse("PT8H");
        MorningMood morningMood = MorningMood.GOOD;

        when(sleepLogRepository.getByDate(userId, LocalDate.now()))
                .thenReturn(new SleepLog(today, goToBedTime, wakeUpTime, morningMood, new User(1, "Jose")));

        SleepLogDTO sleepLogDTO = getSleepLog.getByDate(1, today);

        verify(sleepLogRepository).getByDate(userId, today);
        verifyNoMoreInteractions(sleepLogRepository);

        assertEquals(today, sleepLogDTO.getSleepDate());
        assertEquals(goToBedTime, sleepLogDTO.getGoToBedTime());
        assertEquals(wakeUpTime, sleepLogDTO.getWakeUpTime());
        assertEquals(totalTimeInBed, sleepLogDTO.getTotalTimeInBed());
        assertEquals(morningMood, sleepLogDTO.getMorningMood());
    }

    @Test
    public void getByDateFromRequestTest() {
        int userId = 1;

        LocalDate date = LocalDate.of(2025, 5, 17);
        LocalTime goToBedTime = LocalTime.of(23, 0);
        LocalTime wakeUpTime = LocalTime.of(7, 30);
        Duration totalTimeInBed = Duration.parse("PT8H30M");
        MorningMood morningMood = MorningMood.GOOD;

        when(sleepLogRepository.getByDate(userId, date))
                .thenReturn(new SleepLog(date, goToBedTime, wakeUpTime, morningMood, new User(1, "Jose")));

        SleepLogDTO sleepLogDTO = getSleepLog.getByDateFromRequest(1, date.toString());

        verify(sleepLogRepository).getByDate(userId, date);
        verifyNoMoreInteractions(sleepLogRepository);

        assertEquals(date, sleepLogDTO.getSleepDate());
        assertEquals(goToBedTime, sleepLogDTO.getGoToBedTime());
        assertEquals(wakeUpTime, sleepLogDTO.getWakeUpTime());
        assertEquals(totalTimeInBed, sleepLogDTO.getTotalTimeInBed());
        assertEquals(morningMood, sleepLogDTO.getMorningMood());
    }

    @Test
    public void getByDateDefaultFromRequestTest() {
        int userId = 1;

        LocalDate today = LocalDate.now();
        LocalTime goToBedTime = LocalTime.of(1, 0);
        LocalTime wakeUpTime = LocalTime.of(10, 40);
        Duration totalTimeInBed = Duration.parse("PT9H40M");
        MorningMood morningMood = MorningMood.GOOD;

        when(sleepLogRepository.getByDate(userId, today))
                .thenReturn(new SleepLog(today, goToBedTime, wakeUpTime, morningMood, new User(1, "Jose")));

        SleepLogDTO sleepLogDTO = getSleepLog.getByDateFromRequest(1, null);

        verify(sleepLogRepository).getByDate(userId, today);
        verifyNoMoreInteractions(sleepLogRepository);

        assertEquals(today, sleepLogDTO.getSleepDate());
        assertEquals(goToBedTime, sleepLogDTO.getGoToBedTime());
        assertEquals(wakeUpTime, sleepLogDTO.getWakeUpTime());
        assertEquals(totalTimeInBed, sleepLogDTO.getTotalTimeInBed());
        assertEquals(morningMood, sleepLogDTO.getMorningMood());
    }

    @Test
    public void getByDateWrongFormatFromRequestTest() {
        try {
            getSleepLog.getByDateFromRequest(1, "05-05-2025");
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            verifyNoInteractions(sleepLogRepository);
            assertNotNull(e);
            assertEquals("Query param 'date' must be in the format YYYY-MM-DD", e.getMessage());
        }
    }

    @Test
    public void getLastNightInvalidUserTest() {
        try {
            getSleepLog.getByDate(0, LocalDate.now());
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            verifyNoInteractions(sleepLogRepository);
            assertNotNull(e);
            assertEquals("Field userId is missing", e.getMessage());
        }
    }
}