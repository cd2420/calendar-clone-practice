package com.lim.api.dto;

import com.lim.core.domain.ScheduleType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class NotificationDto implements ScheduleDto {

    private final Long scheduleId;
    private final LocalDateTime notifyAt;
    private final String title;
    private final Long writerId;

    @Override
    public ScheduleType getScheduletype() {
        return ScheduleType.NOTIFICATION;
    }
}
