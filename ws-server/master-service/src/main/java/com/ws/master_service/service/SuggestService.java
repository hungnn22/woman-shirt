package com.ws.master_service.service;

import com.ws.master_service.dto.customer.suggest.CategoryDto;
import com.ws.master_service.dto.customer.suggest.SuggestDto;

import java.util.List;

public interface SuggestService {
    List<CategoryDto> getCategories();

    Object getSizeAvailable(SuggestDto dto);
}
