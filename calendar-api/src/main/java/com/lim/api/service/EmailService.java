package com.lim.api.service;

import com.lim.api.dto.EngagementEmailStuff;
import com.lim.core.domain.entity.Engagement;

public interface EmailService {
    void sendEngagement(EngagementEmailStuff engagement);
}
