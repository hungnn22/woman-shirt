package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.base.rest.PageReq;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageableUtils {

    private static final List<String> fieldIgnoreCases = List.of(
            "name",
            "address",
            "province"
    );
    
    public static Pageable getPageable(PageReq req) {
        var direction = req.getSortDirection().equals("desc") ? Sort.Direction.DESC : Sort.Direction.DESC;
        Sort.Order order;
        if (fieldIgnoreCases.stream().anyMatch(item -> req.getSortField().equals(item))) {
            order = new Sort.Order(direction, req.getSortField()).ignoreCase();
        } else {
            order = new Sort.Order(direction, req.getSortField());
        }
        return PageRequest.of(req.getPage(), req.getPageSize(), Sort.by(order));
    }
}
