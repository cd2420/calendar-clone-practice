package com.lim.api.dto;

import com.lim.core.domain.ScheduleType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class EventDto implements ScheduleDto {

    private final Long scheduleId;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;
    private final String title;
    private final String description;
    private final Long writerId;


    @Override
    public ScheduleType getScheduletype() {
        return ScheduleType.EVENT;
    }
}
