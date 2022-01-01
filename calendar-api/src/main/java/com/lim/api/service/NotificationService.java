package com.lim.api.service;

import com.lim.api.dto.AuthUser;
import com.lim.api.dto.NotificationCreateReq;
import com.lim.core.domain.entity.Schedule;
import com.lim.core.domain.entity.User;
import com.lim.core.domain.entity.repository.ScheduleRepository;
import com.lim.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;


    @Transactional
    public void create(NotificationCreateReq notificationCreateReq, AuthUser authUser) {
        final User user = userService.getByUserIdOrThrow(authUser.getId());

        final List<LocalDateTime> notifyAtList =
                notificationCreateReq.getRepeatTimes();

        notifyAtList.forEach(notify -> {
            final Schedule notificationSchedule =
                    Schedule.notification(
                            notificationCreateReq.getTitle()
                            , notify
                            , user );
            scheduleRepository.save(notificationSchedule);
        });
    }
}
