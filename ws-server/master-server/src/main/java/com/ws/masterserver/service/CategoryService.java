package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.category.CategoryDto;
import com.ws.masterserver.dto.admin.category.CategoryReq;
import com.ws.masterserver.dto.admin.category.CategoryRes;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.ResData;

public interface CategoryService {
    /**
     * @param currentUser: người dùng hiện tại
     * @param req
     * id : mã loại sản phẩm
     * status : trạng thái
     * textSearch: tìm kiếm theo tên, mô tả
     * @return
     */
    PageData<CategoryRes> search(CurrentUser currentUser, CategoryReq req) throws WsException;

    /**
     * @param currentUser: người dùng hiện tại
     * @param dto
     * @return categoryId
     */
    ResData<String> create(CurrentUser currentUser, CategoryDto dto);

    /**
     * @param currentUser: người dùng hiện tại
     * @param dto
     * @return categoryId
     * Khi xóa 1 loại sản phẩm thì các sản phẩm trong loại sản phẩm đó cũng phải xóa theo.
     */
    ResData<String> delete(CurrentUser currentUser, CategoryDto dto);

    /**
     * @param currentUser: người dùng hiện tại
     * @param dto
     * @return categoryId
     */
    ResData<String> update(CurrentUser currentUser, CategoryDto dto);

    /**
     * @param currentUser: người dùng hiện tại
     * @param dto
     * @return chi tiết loại sản phẩm
     */
    ResData<CategoryRes> detail(CurrentUser currentUser, CategoryDto dto);
}
