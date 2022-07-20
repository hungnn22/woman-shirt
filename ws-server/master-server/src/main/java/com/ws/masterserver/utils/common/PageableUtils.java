package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.base.rest.PageReq;
import com.ws.masterserver.utils.constants.WsConst;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageableUtils {

    private static final List<String> fieldIgnoreCases = List.of(
            "name",
            "address",
            "province",
            "firstName",
            "lastName",
            "email"
    );
    
    public static Pageable getPageable(PageReq req) {
        getPageReq(req);
        var direction = req.getSortDirection().equals("desc") ? Sort.Direction.DESC : Sort.Direction.DESC;
        Sort.Order order;
        if (fieldIgnoreCases.stream().anyMatch(item -> req.getSortField().equals(item))) {
            order = new Sort.Order(direction, req.getSortField()).ignoreCase();
        } else {
            order = new Sort.Order(direction, req.getSortField());
        }
        return PageRequest.of(req.getPage(), req.getPageSize(), Sort.by(order));
    }

    private static void getPageReq(PageReq req) {
        if (null == req.getPage() || req.getPage() < 0) {
            req.setPage(WsConst.Values.PAGE_DEFAULT);
        }
        if (null == req.getPageSize() || req.getPageSize() < 0) {
            req.setPageSize(WsConst.Values.PAGE_SIZE_DEFAULT);
        }
        if (StringUtils.isNullOrEmpty(req.getSortField())) {
            req.setSortField(WsConst.Values.SORT_FIELD_DEFAULT);
        }
        if (StringUtils.isNullOrEmpty(req.getSortDirection())) {
            req.setSortDirection(WsConst.Values.SORT_DIRECTION_DEFAULT);
        }
    }
}
