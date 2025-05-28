package com.noom.interview.fullstack.sleep.repository;

import com.noom.interview.fullstack.sleep.factory.SleepLogFactory;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.service.adapter.SleepLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
class SleepLogRepositoryImplTest {

    @Autowired
    private SleepLogRepository sleepLogRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Container
    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("sleepTestDb")
                    .withUsername("jose")
                    .withPassword("123456");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @BeforeEach
    public void cleanDatabase() {
        jdbcTemplate.getJdbcTemplate().execute("DELETE FROM sleep_log");
    }

    @Test
    public void saveTest() {
        SleepLog sleepLog = SleepLogFactory.getSleepLog1(LocalTime.of(22, 0), LocalTime.of(8, 0));

        sleepLogRepository.save(sleepLog);

        SleepLog createdSleepLog = sleepLogRepository.getByDate(sleepLog.getUser().getId(), LocalDate.now());

        createdSleepLog.setUser(sleepLog.getUser());
        sleepLog.setId(createdSleepLog.getId());

        assertEquals(sleepLog, createdSleepLog);
    }

    @Test
    public void getAllByDateRangeTest() {
        SleepLog sleepLog1 = SleepLogFactory.getSleepLog1(LocalTime.of(22, 0), LocalTime.of(7, 30));
        SleepLog sleepLog2 = SleepLogFactory.getSleepLog1DayAgo(LocalTime.of(22, 15), LocalTime.of(7, 45));
        SleepLog sleepLog3 = SleepLogFactory.getSleepLog2DaysAgo(LocalTime.of(22, 30), LocalTime.of(7, 50));

        sleepLogRepository.save(sleepLog1);
        sleepLogRepository.save(sleepLog2);
        sleepLogRepository.save(sleepLog3);

        LocalDate today = LocalDate.now();
        LocalDate oneMonthAgo = today.minusDays(30);

        Set<SleepLog> sleepLogs = sleepLogRepository.getAllByDateRange(sleepLog1.getUser().getId(), oneMonthAgo, today);

        Optional<SleepLog> lastNight = sleepLogs.stream()
                .filter(sleepLog -> LocalDate.now().equals(sleepLog.getSleepDate()))
                .findFirst();

        if (lastNight.isEmpty()) {
            fail("Should have found sleep log");
        }

        assertEquals(sleepLog1.getSleepDate(), lastNight.get().getSleepDate());
        assertEquals(sleepLog1.getGoToBedTime(), lastNight.get().getGoToBedTime());
        assertEquals(sleepLog1.getWakeUpTime(), lastNight.get().getWakeUpTime());
        assertEquals(sleepLog1.getTotalTimeInBed(), lastNight.get().getTotalTimeInBed());
        assertEquals(sleepLog1.getMorningMood(), lastNight.get().getMorningMood());
        assertEquals(sleepLog1.getUser().getId(), lastNight.get().getUser().getId());

        assertEquals(3, sleepLogs.size());
    }
}