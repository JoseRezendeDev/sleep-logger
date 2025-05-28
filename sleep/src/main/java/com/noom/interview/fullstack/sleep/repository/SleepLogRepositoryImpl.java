package com.noom.interview.fullstack.sleep.repository;

import com.noom.interview.fullstack.sleep.model.MorningMood;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.model.User;
import com.noom.interview.fullstack.sleep.service.adapter.SleepLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Repository
public class SleepLogRepositoryImpl implements SleepLogRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public SleepLogRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(SleepLog sleepLog) {
        String sql = "INSERT INTO sleep_log (sleep_date, go_to_bed_time, wake_up_time, total_time_in_bed, morning_mood, user_id) " +
                "VALUES (:sleepDate, :goToBedTime, :wakeUpTime, CAST(:totalTimeInBed AS interval), :morningMood, :userId)";

        Map<String, Object> params = new HashMap<>();
        params.put("sleepDate", sleepLog.getSleepDate());
        params.put("goToBedTime", sleepLog.getGoToBedTime());
        params.put("wakeUpTime", sleepLog.getWakeUpTime());
        params.put("totalTimeInBed", parseDurationToInverval(sleepLog.getTotalTimeInBed()));
        params.put("morningMood", sleepLog.getMorningMood().name());
        params.put("userId", sleepLog.getUser().getId());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public SleepLog getByDate(int userId, LocalDate date) {
        String sql = "SELECT * FROM sleep_log WHERE sleep_date = :sleepDate and user_id = :userId";

        Map<String, Object> params = new HashMap<>();
        params.put("sleepDate", date);
        params.put("userId", userId);

        return jdbcTemplate.queryForObject(sql, params, new SleepLogRowMapper());
    }

    @Override
    public Set<SleepLog> getAllByDateRange(int userId, LocalDate initialDate, LocalDate finalDate) {
        String sql = "SELECT * FROM sleep_log WHERE sleep_date >= :initialDate " +
                "and sleep_date <= :finalDate " +
                "and user_id = :userId " +
                "ORDER BY sleep_date";

        Map<String, Object> params = new HashMap<>();
        params.put("initialDate", initialDate);
        params.put("finalDate", finalDate);
        params.put("userId", userId);

        return new HashSet<>(jdbcTemplate.query(sql, params, new SleepLogRowMapper()));
    }

    private String parseDurationToInverval(Duration duration) {
        long totalMinutes = duration.toMinutes();
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'")
                .append(hours)
                .append(":")
                .append(minutes)
                .append("'");

        return stringBuilder.toString();
    }

    private static class SleepLogRowMapper implements RowMapper<SleepLog> {

        @Override
        public SleepLog mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            LocalDate sleepDate = rs.getDate("sleep_date").toLocalDate();
            LocalTime goToBedTime = rs.getTime("go_to_bed_time").toLocalTime();
            LocalTime wakeUpTime = rs.getTime("wake_up_time").toLocalTime();

            String totalTimeInBedString = rs.getString("total_time_in_bed");
            Duration totalTimeInBed = parseIntervalToDuration(totalTimeInBedString);

            String morningMood = rs.getString("morning_mood");
            int userId = rs.getInt("user_id");
            User user = new User(userId);

            return new SleepLog(id, sleepDate, goToBedTime, wakeUpTime, totalTimeInBed, MorningMood.valueOf(morningMood), user);
        }

        private Duration parseIntervalToDuration(String totalTimeInBedString) {
            String[] parts = totalTimeInBedString.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            return Duration.ofHours(hours).plusMinutes(minutes);
        }
    }
}
