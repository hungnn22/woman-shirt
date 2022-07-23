package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.blog.BlogDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;

public interface BlogService {
    ResData<String> create(CurrentUser currentUser, BlogDto dto);

    ResData<String> delete(CurrentUser currentUser, BlogDto dto);

    ResData<String> update(CurrentUser currentUser, BlogDto dto);
}
