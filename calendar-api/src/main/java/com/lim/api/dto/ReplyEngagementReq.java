package com.lim.api.dto;

import com.lim.core.domain.RequestReplyType;

public class ReplyEngagementReq {

    private final RequestReplyType type;

    public ReplyEngagementReq(RequestReplyType type) {
        this.type = type;
    }

    public RequestReplyType getType() {
        return type;
    }
}
