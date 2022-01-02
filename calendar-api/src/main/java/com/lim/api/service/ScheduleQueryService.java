package com.lim.api.service;

import com.lim.api.dto.AuthUser;
import com.lim.api.dto.ScheduleDto;
import com.lim.api.util.DtoConverter;
import com.lim.core.domain.entity.Engagement;
import com.lim.core.domain.entity.Schedule;
import com.lim.core.domain.entity.repository.EngagementRepository;
import com.lim.core.domain.entity.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleQueryService {

    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;

    public List<ScheduleDto> getScheduleByDay(AuthUser authUser, LocalDate date) {

        return Stream
                .concat(
                        scheduleRepository.findAllByWriter_Id(authUser.getId())
                        .stream()
                        .filter(schedule -> schedule.isOverlapped(date))
                        .map(schedule -> DtoConverter.fromSchedul(schedule)) ,

                        engagementRepository.findAllByAttendee_Id(authUser.getId())
                                .stream()
                                .filter(engagement -> engagement.isOverlapped(date))
                                .map(engagement -> DtoConverter.fromSchedul(engagement.getSchedule())))
                .collect(Collectors.toList());
    }
}
