package com.lim.api.service;

import com.lim.api.dto.AuthUser;
import com.lim.api.dto.EngagementEmailStuff;
import com.lim.api.dto.EventCreateReq;
import com.lim.core.domain.RequestStatus;
import com.lim.core.domain.entity.Engagement;
import com.lim.core.domain.entity.Schedule;
import com.lim.core.domain.entity.User;
import com.lim.core.domain.entity.repository.EngagementRepository;
import com.lim.core.domain.entity.repository.ScheduleRepository;
import com.lim.core.exception.CalendarException;
import com.lim.core.exception.ErrorCode;
import com.lim.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
            throw new CalendarException(ErrorCode.EVENT_CREATE_OVERLAPPED_PERIOD);
        }

        final Schedule eventSchedule = Schedule.event(
                eventCreateReq.getTitle(),
                eventCreateReq.getDescription(),
                eventCreateReq.getStartAt(),
                eventCreateReq.getEndAt(),
                userService.getByUserIdOrThrow(authUser.getId())
        );

        scheduleRepository.save(eventSchedule);
        final List<User> attendees =
            eventCreateReq.getAttendeeIds().stream()
                    .map(aId -> userService.getByUserIdOrThrow(aId))
                    .collect(Collectors.toList());

        attendees.forEach(attendee -> {
                    final Engagement engagement = Engagement.builder()
                                                            .schedule(eventSchedule)
                                                            .requestStatus(RequestStatus.REQUESTED)
                                                            .attendee(attendee)
                                                            .build();
                    engagementRepository.save(engagement);
                    emailService.sendEngagement(
                            EngagementEmailStuff.builder()
                                    .engagementId(engagement.getId())
                                    .title(engagement.getEvent().getTitle())
                                    .toEmail(engagement.getAttendee().getEmail())
                                    .attendeeEmails(
                                            attendees.stream()
                                                    .map(a -> a.getEmail())
                                                    .collect(Collectors.toList())
                                    )
                                    .period(engagement.getEvent().getPeriod())
                                    .build());
                });


    }
}
