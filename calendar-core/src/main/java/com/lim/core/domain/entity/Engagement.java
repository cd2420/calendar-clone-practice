package com.lim.core.domain.entity;

import com.lim.core.domain.Event;
import com.lim.core.domain.RequestStatus;
import com.lim.core.util.Period;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
@Table(name = "engagements")
public class Engagement extends BaseEntity {


    @JoinColumn(name = "schedule_id")
    @ManyToOne
    private Schedule schedule;

    @JoinColumn(name = "attendee_id")
    @ManyToOne
    private User attendee;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus requestStatus;

    public Event getEvent() {
        return schedule.toEvent();
    }

    public boolean isOverlapped(Period period) {
        return this.schedule.isOverlapped(period);
    }
}
