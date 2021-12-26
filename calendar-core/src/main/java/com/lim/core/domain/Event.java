package com.lim.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Event {

    private Long id;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String title;
    private String description;
    private User writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Engagement> engagementList;

    public Event(
            Long id
            , LocalDateTime startAt
            , LocalDateTime endAt
            , String title
            , String description
            , User writer
            , LocalDateTime createdAt
            , LocalDateTime updatedAt
    ) {
        this.id = id;
        this.startAt = startAt;
        this.endAt = endAt;
        this.title = title;
        this.description = description;
        this.writer = writer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void addEngagement(Engagement engagement) {
        if (engagementList == null) {
            engagementList = Arrays.asList(engagement);
        } else {
            this.engagementList.add(engagement);
        }
    }
}
