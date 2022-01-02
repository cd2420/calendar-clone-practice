package com.lim.api.service;

import com.lim.api.dto.AuthUser;
import com.lim.core.domain.RequestReplyType;
import com.lim.core.domain.RequestStatus;
import com.lim.core.domain.entity.repository.EngagementRepository;
import com.lim.core.exception.CalendarException;
import com.lim.core.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EngagementService {
    private final EngagementRepository engagementRepository;


    @Transactional
    public RequestStatus update(
            AuthUser authUser
            , Long engagementId
            , RequestReplyType type
    ) {


        return engagementRepository.findById(engagementId)
                .filter(e -> e.getRequestStatus() == RequestStatus.REQUESTED)
                .filter(e -> e.getAttendee().getId().equals(authUser.getId()))
                .map(e -> e.reply(type))
                .orElseThrow(() -> new CalendarException(ErrorCode.BAD_REQUEST))
                .getRequestStatus();
    }
}
