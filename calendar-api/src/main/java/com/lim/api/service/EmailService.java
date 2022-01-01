package com.lim.api.service;

import com.lim.core.domain.entity.Engagement;

public interface EmailService {
    void sendEngagement(Engagement engagement);
}
