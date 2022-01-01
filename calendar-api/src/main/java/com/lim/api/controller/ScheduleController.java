package com.lim.api.controller;

import com.lim.api.dto.AuthUser;
import com.lim.api.dto.TaskCreateReq;
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

    @PostMapping("/tasks")
    public ResponseEntity<Void> createTask(
            @RequestBody TaskCreateReq taskCreateReq
            , AuthUser authUser
    ) {

        taskService.create(taskCreateReq, authUser);
        return ResponseEntity.ok().build();
    }

}
