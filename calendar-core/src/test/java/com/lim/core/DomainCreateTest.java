package com.lim.core;

import com.lim.core.domain.Engagement;
import com.lim.core.domain.Event;
import com.lim.core.domain.RequestStatus;
import com.lim.core.domain.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomainCreateTest {

    @Test
    void eventCreate() {
        final User user1 = new User(
                1L
                ,"writer"
                , "email@email"
                , "password"
                , LocalDateTime.now()
                , LocalDateTime.now()
                , LocalDateTime.now()
        );

        final User user2 = new User(
                2L
                ,"attendee"
                , "email@email"
                , "password"
                , LocalDateTime.now()
                , LocalDateTime.now()
                , LocalDateTime.now()
        );

        final Event event = new Event(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now(),
                "title",
                "descr",
                user1,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        event.addEngagement(
                new Engagement(
                        1L
                        , event
                        , user2
                        , LocalDateTime.now()
                        , RequestStatus.REQUESTED
                )
        );

        assertEquals(event.getEngagementList().get(0).getEvent().getWriter().getName(), "writer");
    }
}
