package com.lim.core;

import com.lim.core.domain.ScheduleType;
import com.lim.core.domain.entity.Schedule;
import com.lim.core.domain.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomainCreateTest {

    @Test
    void eventCreate() {
        final User me = new User(
                "meme",
                "email",
                "pw",
                LocalDateTime.now()
        );
        final Schedule taskSchedule = Schedule.task(
                "할일",
                "청소하기",
                LocalDateTime.now(),
                me
                );
        assertEquals(taskSchedule.getScheduleType(), ScheduleType.TASK);
        assertEquals(taskSchedule.toTask().getTitle(), "할일");
    }
}
