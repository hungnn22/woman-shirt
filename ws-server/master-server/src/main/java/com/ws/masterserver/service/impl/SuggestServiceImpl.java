package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.suggest.CategoryDto;
import com.ws.masterserver.dto.customer.suggest.SizeDto;
import com.ws.masterserver.service.SuggestService;
import com.ws.masterserver.utils.base.WsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuggestServiceImpl implements SuggestService {

    private final WsRepository repository;

    @Override
    public List<CategoryDto> getCategories() {
        return repository.categoryRepository.findSuggestCategories();
    }

    @Override
    public String getSizeAvailable(SizeDto dto) {
        return null;
    }
}
