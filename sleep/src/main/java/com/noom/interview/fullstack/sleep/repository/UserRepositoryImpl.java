package com.noom.interview.fullstack.sleep.repository;

import com.noom.interview.fullstack.sleep.model.User;
import com.noom.interview.fullstack.sleep.service.adapter.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getById(int id) {
        String sql = "SELECT * FROM sleep_user WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return jdbcTemplate.queryForObject(sql, params, new UserRowMapper());
    }

    private static class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            return new User(id, name);
        }
    }
}
