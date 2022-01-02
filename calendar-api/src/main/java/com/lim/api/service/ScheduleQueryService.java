package com.lim.api.service;

import com.lim.api.dto.AuthUser;
import com.lim.api.dto.ScheduleDto;
import com.lim.api.util.DtoConverter;
import com.lim.core.domain.entity.repository.EngagementRepository;
import com.lim.core.domain.entity.repository.ScheduleRepository;
import com.lim.core.util.Period;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleQueryService {

    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;

    public List<ScheduleDto> getScheduleByDay(
            AuthUser authUser
            , LocalDate date
    ) {
        final Period period = Period.of(date, date);
        return getScheduleByPeriod(authUser, period);
    }

    public List<ScheduleDto> getScheduleByWeek(
            AuthUser authUser
            , LocalDate startOfWeek
    ) {
        final Period period = Period.of(startOfWeek, startOfWeek.plusDays(6));
        return getScheduleByPeriod(authUser, period);
    }

    public List<ScheduleDto> getScheduleByMonth(
            AuthUser authUser
            , YearMonth yearMonth
    ) {

        final Period period = Period.of(yearMonth.atDay(1), yearMonth.atEndOfMonth());
        return getScheduleByPeriod(authUser, period);
    }

    private List<ScheduleDto> getScheduleByPeriod(AuthUser authUser, Period period) {
        return Stream.concat(
                        scheduleRepository.findAllByWriter_Id(authUser.getId())
                                .stream()
                                .filter(schedule -> schedule.isOverlapped(period))
                                .map(schedule -> DtoConverter.fromSchedul(schedule)),

                        engagementRepository.findAllByAttendee_Id(authUser.getId())
                                .stream()
                                .filter(engagement -> engagement.isOverlapped(period))
                                .map(engagement -> DtoConverter.fromSchedul(engagement.getSchedule())))
                .collect(Collectors.toList());
    }

}
