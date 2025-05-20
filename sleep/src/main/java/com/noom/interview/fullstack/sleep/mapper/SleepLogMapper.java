package com.noom.interview.fullstack.sleep.mapper;

import com.noom.interview.fullstack.sleep.dto.SleepLogDTO;
import com.noom.interview.fullstack.sleep.model.SleepLog;

public class SleepLogMapper {

    public static SleepLogDTO toDTO(SleepLog sleepLog) {
        return new SleepLogDTO(sleepLog.getSleepDate(), sleepLog.getGoToBedTime(),
                sleepLog.getWakeUpTime(), sleepLog.getTotalTimeInBed(),
                sleepLog.getMorningMood());
    }
}
