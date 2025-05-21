package com.noom.interview.fullstack.sleep.service;

import com.noom.interview.fullstack.sleep.dto.CreateSleepLogRequest;
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
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateSleepLogTest {

    @Mock
    private SleepLogRepository sleepLogRepository;

    @Mock
    private GetSleepLog getSleepLog;

    @Mock
    private GetUser getUser;

    @InjectMocks
    private CreateSleepLog createSleepLog;

    @Test
    public void createHappyFlow() {
        User user = new User(1, "Jose Rezende");

        CreateSleepLogRequest request = new CreateSleepLogRequest("22:30", "07:00", "GOOD", user.getId());

        LocalTime goToBedTime = LocalTime.of(Integer.parseInt(request.getGoToBedTime().substring(0, 2)), Integer.parseInt(request.getGoToBedTime().substring(3, 5)));
        LocalTime wakeUpTime = LocalTime.of(Integer.parseInt(request.getWakeUpTime().substring(0, 2)), Integer.parseInt(request.getWakeUpTime().substring(3, 5)));

        SleepLogDTO sleepLogDTO = new SleepLogDTO(LocalDate.now(), goToBedTime, wakeUpTime, Duration.parse("PT8H30M"), MorningMood.GOOD);

        when(getUser.getById(request.getUserId())).thenReturn(user);
        doNothing().when(sleepLogRepository).save(any(SleepLog.class));
        when(getSleepLog.getLastNight(user.getId())).thenReturn(sleepLogDTO);

        SleepLogDTO createdSleepLogDTO = createSleepLog.create(request);

        verify(getUser).getById(request.getUserId());
        verify(sleepLogRepository).save(any(SleepLog.class));
        verify(getSleepLog).getLastNight(user.getId());
        verifyNoMoreInteractions(getUser, sleepLogRepository, getSleepLog);

        assertEquals(sleepLogDTO, createdSleepLogDTO);
    }

    @Test
    public void createEmptyRequest() {
        try {
            createSleepLog.create(null);
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
            assertEquals("Sleep Log data is missing on body request", e.getMessage());
        }
    }

    @Test
    public void createWrongSizeGoToBedTime() {
        CreateSleepLogRequest request = new CreateSleepLogRequest("22:300", "07:00", "GOOD", 1);

        try {
            createSleepLog.create(request);
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
            assertEquals("Field goToBedTime must be in the format HH:MM", e.getMessage());
        }
    }

    @Test
    public void createInvalidGoToBedTime() {
        CreateSleepLogRequest request = new CreateSleepLogRequest("25:30", "07:00", "GOOD", 1);

        try {
            createSleepLog.create(request);
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
            assertEquals("Failed to obtain time from fields goToBedTime and wakeUpTime", e.getMessage());
        }
    }

    @Test
    public void createNullGoToBedTime() {
        CreateSleepLogRequest request = new CreateSleepLogRequest(null, "07:40", "BAD", 1);

        try {
            createSleepLog.create(request);
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
            assertEquals("Field goToBedTime must be in the format HH:MM", e.getMessage());
        }
    }

    @Test
    public void createWrongSizeWakeUpTime() {
        CreateSleepLogRequest request = new CreateSleepLogRequest("22:30", "0700", "OK", 1);

        try {
            createSleepLog.create(request);
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
            assertEquals("Field wakeUpTime must be in the format HH:MM", e.getMessage());
        }
    }

    @Test
    public void createInvalidWakeUpTime() {
        CreateSleepLogRequest request = new CreateSleepLogRequest("22:30", "07:A0", "OK", 1);

        try {
            createSleepLog.create(request);
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
            assertEquals("Failed to obtain time from fields goToBedTime and wakeUpTime", e.getMessage());
        }
    }

    @Test
    public void createNullWakeUpTime() {
        CreateSleepLogRequest request = new CreateSleepLogRequest("22:30", null, "BAD", 1);

        try {
            createSleepLog.create(request);
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
            assertEquals("Field wakeUpTime must be in the format HH:MM", e.getMessage());
        }
    }

    @Test
    public void createInvalidMorningMood() {
        CreateSleepLogRequest request = new CreateSleepLogRequest("22:30", "07:00", "EXCELLENT", 1);

        try {
            createSleepLog.create(request);
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
            assertEquals("Field morningMood must be one of these: " + Arrays.toString(MorningMood.values()), e.getMessage());
        }
    }

    @Test
    public void createNullMorningMood() {
        CreateSleepLogRequest request = new CreateSleepLogRequest("22:30", "07:00", null, 1);

        try {
            createSleepLog.create(request);
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
            assertEquals("Field morningMood must be one of these: " + Arrays.toString(MorningMood.values()), e.getMessage());
        }
    }

    @Test
    public void createNullUser() {
        CreateSleepLogRequest request = new CreateSleepLogRequest("22:30", "07:00", "BAD", 0);

        try {
            createSleepLog.create(request);
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
            assertEquals("Field userId is missing", e.getMessage());
        }
    }
}