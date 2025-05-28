package com.noom.interview.fullstack.sleep.controller;

import com.noom.interview.fullstack.sleep.dto.CreateSleepLogRequest;
import com.noom.interview.fullstack.sleep.dto.SleepLogDTO;
import com.noom.interview.fullstack.sleep.dto.SleepLogMonthAverageDTO;
import com.noom.interview.fullstack.sleep.service.CreateSleepLog;
import com.noom.interview.fullstack.sleep.service.GetMonthAverages;
import com.noom.interview.fullstack.sleep.service.GetSleepLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sleep-logs")
public class SleepLogController {

    private final CreateSleepLog createSleepLog;
    private final GetSleepLog getSleepLog;
    private final GetMonthAverages getMonthAverages;

    public SleepLogController(CreateSleepLog createSleepLog, GetSleepLog getSleepLog, GetMonthAverages getMonthAverages) {
        this.createSleepLog = createSleepLog;
        this.getSleepLog = getSleepLog;
        this.getMonthAverages = getMonthAverages;
    }

    @PostMapping
    public ResponseEntity<SleepLogDTO> create(@RequestBody CreateSleepLogRequest createSleepLogRequest) {
        SleepLogDTO sleepLogDTO = createSleepLog.create(createSleepLogRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(sleepLogDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SleepLogDTO> getByDate(@PathVariable int userId, @RequestParam(required = false) String sleepDate) {
        SleepLogDTO sleepLogDTO = getSleepLog.getByDateFromRequest(userId, sleepDate);
        return ResponseEntity.ok(sleepLogDTO);
    }

    @GetMapping("/month-averages/{userId}")
    public ResponseEntity<SleepLogMonthAverageDTO> getLastMonthAverages(@PathVariable int userId) {
        SleepLogMonthAverageDTO monthAverageDTO = getMonthAverages.getLastMonthAverages(userId);
        return ResponseEntity.ok(monthAverageDTO);
    }
}
