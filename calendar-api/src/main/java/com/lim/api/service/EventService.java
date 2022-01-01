package com.lim.api.service;

import com.lim.api.dto.AuthUser;
import com.lim.api.dto.EventCreateReq;
import com.lim.core.domain.RequestStatus;
import com.lim.core.domain.entity.Engagement;
import com.lim.core.domain.entity.Schedule;
import com.lim.core.domain.entity.User;
import com.lim.core.domain.entity.repository.EngagementRepository;
import com.lim.core.domain.entity.repository.ScheduleRepository;
import com.lim.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EngagementRepository engagementRepository;
    private final ScheduleRepository scheduleRepository;

    private final EmailService emailService;
    private final UserService userService;

    @Transactional
    public void create(
            EventCreateReq eventCreateReq
            , AuthUser authUser) {

        final List<Engagement> engagementList = engagementRepository.findAll(); // TODO findall 개선
        if (engagementList.stream()
                .anyMatch(e ->
                    eventCreateReq.getAttendeeIds().contains(e.getAttendee().getId())
                            && e.getRequestStatus() == RequestStatus.ACCEPTED
                            && e.getEvent().isOverlapped(
                                    eventCreateReq.getStartAt()
                                    , eventCreateReq.getEndAt()
                            )
                )
        ) {
            throw new RuntimeException("cannot make engagement. period overlapped! ");
        }

        final Schedule eventSchedule = Schedule.event(
                eventCreateReq.getTitle(),
                eventCreateReq.getDescription(),
                eventCreateReq.getStartAt(),
                eventCreateReq.getEndAt(),
                userService.getByUserIdOrThrow(authUser.getId())
        );

        scheduleRepository.save(eventSchedule);
        eventCreateReq.getAttendeeIds()
                .forEach(atId -> {
                    final User attendee = userService.getByUserIdOrThrow(atId);
                    final Engagement engagement = Engagement.builder()
                                                            .schedule(eventSchedule)
                                                            .requestStatus(RequestStatus.REQUESTED)
                                                            .attendee(attendee)
                                                            .build();
                    engagementRepository.save(engagement);
                    emailService.sendEngagement(engagement);
                });


    }
}
