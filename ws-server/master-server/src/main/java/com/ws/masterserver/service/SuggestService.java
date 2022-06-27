package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.suggest.CategoryDto;
import com.ws.masterserver.dto.customer.suggest.SizeDto;

import java.util.List;

public interface SuggestService {
    List<CategoryDto> getCategories();

    String getSizeAvailable(SizeDto dto);
}
