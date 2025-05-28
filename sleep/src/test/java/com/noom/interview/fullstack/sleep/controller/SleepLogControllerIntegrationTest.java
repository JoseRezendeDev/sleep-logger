package com.noom.interview.fullstack.sleep.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noom.interview.fullstack.sleep.dto.CreateSleepLogRequest;
import com.noom.interview.fullstack.sleep.dto.SleepLogMonthAverageDTO;
import com.noom.interview.fullstack.sleep.factory.SleepLogFactory;
import com.noom.interview.fullstack.sleep.model.MorningMood;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.service.adapter.SleepLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SleepLogControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private SleepLogRepository sleepLogRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void cleanDatabase() {
        jdbcTemplate.getJdbcTemplate().execute("DELETE FROM sleep_log");
    }

    @Test
    void createTest() throws Exception {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalTime goToBedTime = LocalTime.of(23, 59);
        LocalTime wakeUpTime = LocalTime.of(6, 0);
        Duration totalTimeInBed = Duration.parse("PT6H1M");
        MorningMood morningMood = MorningMood.BAD;

        CreateSleepLogRequest request = new CreateSleepLogRequest(yesterday.toString(), goToBedTime.toString(),
                wakeUpTime.toString(), morningMood.name(), 1);

        mockMvc.perform(post("/sleep-logs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.sleepDate").value(yesterday.toString()))
                .andExpect(jsonPath("$.goToBedTime").value(goToBedTime.toString()))
                .andExpect(jsonPath("$.wakeUpTime").value(wakeUpTime.toString()))
                .andExpect(jsonPath("$.totalTimeInBed").value(totalTimeInBed.toString()))
                .andExpect(jsonPath("$.morningMood").value(morningMood.name()));
    }

    @Test
    void getByDateTest() throws Exception {
        LocalDate today = LocalDate.now();
        LocalTime goToBedTime = LocalTime.of(23, 0);
        LocalTime wakeUpTime = LocalTime.of(7, 30);
        Duration totalTimeInBed = Duration.parse("PT8H30M");

        SleepLog sleepLog = SleepLogFactory.getSleepLog1(goToBedTime, wakeUpTime);
        sleepLogRepository.save(sleepLog);

        mockMvc.perform(get("/sleep-logs/" + sleepLog.getUser().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.sleepDate").value(today.toString()))
                .andExpect(jsonPath("$.goToBedTime").value(goToBedTime.toString()))
                .andExpect(jsonPath("$.wakeUpTime").value(wakeUpTime.toString()))
                .andExpect(jsonPath("$.totalTimeInBed").value(totalTimeInBed.toString()))
                .andExpect(jsonPath("$.morningMood").value(sleepLog.getMorningMood().name()));
    }

    @Test
    void getLastMonthAveragesTest() throws Exception {
        LocalTime goToBedTimeYesterday = LocalTime.of(23, 40);
        LocalTime wakeUpTimeYesterday = LocalTime.of(6, 0);

        LocalTime goToBedTimeToday = LocalTime.of(0, 10);
        LocalTime wakeUpTimeToday = LocalTime.of(7, 30);

        SleepLog sleepLogYesterday = SleepLogFactory.getSleepLog1DayAgo(goToBedTimeYesterday, wakeUpTimeYesterday);
        sleepLogRepository.save(sleepLogYesterday);

        SleepLog sleepLogToday = SleepLogFactory.getSleepLog1(goToBedTimeToday, wakeUpTimeToday);
        sleepLogRepository.save(sleepLogToday);

        LocalDate initialDate = LocalDate.now().minusDays(30);
        LocalDate finalDate = LocalDate.now();
        LocalTime goToBedTimeAverage = LocalTime.of(23, 55);
        LocalTime wakeUpTimeAverage = LocalTime.of(6, 45);
        Duration totalTimeInBedAverage = Duration.parse("PT6H50M");

        Map<MorningMood, Integer> morningMoodFrequency = new HashMap<>();
        morningMoodFrequency.put(MorningMood.GOOD, 1);
        morningMoodFrequency.put(MorningMood.OK, 1);
        morningMoodFrequency.put(MorningMood.BAD, 0);

        SleepLogMonthAverageDTO sleepLogMonthAverageDTO = new SleepLogMonthAverageDTO(initialDate, finalDate,
                goToBedTimeAverage, wakeUpTimeAverage, totalTimeInBedAverage, morningMoodFrequency);

        mockMvc.perform(get("/sleep-logs/month-averages/" + sleepLogToday.getUser().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.initialDate").value(sleepLogMonthAverageDTO.getInitialDate().toString()))
                .andExpect(jsonPath("$.finalDate").value(sleepLogMonthAverageDTO.getFinalDate().toString()))
                .andExpect(jsonPath("$.goToBedTimeAverage").value(sleepLogMonthAverageDTO.getGoToBedTimeAverage().toString()))
                .andExpect(jsonPath("$.wakeUpTimeAverage").value(sleepLogMonthAverageDTO.getWakeUpTimeAverage().toString()))
                .andExpect(jsonPath("$.totalTimeInBedAverage").value(sleepLogMonthAverageDTO.getTotalTimeInBedAverage().toString()))
                .andExpect(jsonPath("$.morningMoodFrequency.GOOD").value(1))
                .andExpect(jsonPath("$.morningMoodFrequency.OK").value(1))
                .andExpect(jsonPath("$.morningMoodFrequency.BAD").value(0));

    }
}