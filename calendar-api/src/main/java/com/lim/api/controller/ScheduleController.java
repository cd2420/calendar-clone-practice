package com.lim.api.controller;

import com.lim.api.dto.AuthUser;
import com.lim.api.dto.EventCreateReq;
import com.lim.api.dto.TaskCreateReq;
import com.lim.api.service.EventService;
import com.lim.api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/schedules")
@RequiredArgsConstructor
@RestController
public class ScheduleController {

    private final TaskService taskService;
    private final EventService eventService;

    @PostMapping("/tasks")
    public ResponseEntity<Void> createTask(
            @RequestBody TaskCreateReq taskCreateReq
            , AuthUser authUser
    ) {

        taskService.create(taskCreateReq, authUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/events")
    public ResponseEntity<Void> createEvent(
            @RequestBody EventCreateReq eventCreateReq
            , AuthUser authUser
    ) {

        eventService.create(eventCreateReq, authUser);
        return ResponseEntity.ok().build();
    }

}
