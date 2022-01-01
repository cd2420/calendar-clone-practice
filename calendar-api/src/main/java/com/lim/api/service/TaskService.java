package com.lim.api.service;

import com.lim.api.dto.AuthUser;
import com.lim.api.dto.TaskCreateReq;
import com.lim.core.domain.entity.Schedule;
import com.lim.core.domain.entity.repository.ScheduleRepository;
import com.lim.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void create(TaskCreateReq taskCreateReq, AuthUser authUser) {
        final Schedule taskSchedule =
                Schedule.task(
                        taskCreateReq.getTitle()
                        , taskCreateReq.getDescription()
                        , taskCreateReq.getTaskAt()
                        , userService.getByUserIdOrThrow(authUser.getId())
                );

        scheduleRepository.save(taskSchedule);
    }
}
