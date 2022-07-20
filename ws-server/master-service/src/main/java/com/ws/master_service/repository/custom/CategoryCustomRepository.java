package com.ws.master_service.repository.custom;

import com.ws.master_service.dto.admin.category.CategoryReq;
import com.ws.master_service.dto.admin.category.CategoryRes;
import com.ws.master_service.utils.base.rest.CurrentUser;
import com.ws.master_service.utils.base.rest.PageData;

public interface CategoryCustomRepository {
    PageData<CategoryRes> search(CurrentUser currentUser, CategoryReq req);
}
