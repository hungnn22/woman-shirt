package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.topic.TopicDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;

public interface TopicService {
    ResData<String> create(CurrentUser currentUser, TopicDto dto);

    ResData<String> delete(CurrentUser currentUser, TopicDto dto);

    ResData<String> update(CurrentUser currentUser, TopicDto dto);
}
