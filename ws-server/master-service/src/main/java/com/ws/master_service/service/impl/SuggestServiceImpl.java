package com.ws.master_service.service.impl;

import com.ws.master_service.dto.customer.suggest.CategoryDto;
import com.ws.master_service.dto.customer.suggest.SuggestDto;
import com.ws.master_service.entity.SuggestEntity;
import com.ws.master_service.service.SuggestService;
import com.ws.master_service.utils.base.WsException;
import com.ws.master_service.utils.base.WsRepository;
import com.ws.master_service.utils.base.rest.ResData;
import com.ws.master_service.utils.common.JsonUtils;
import com.ws.master_service.utils.constants.WsCode;
import com.ws.master_service.utils.validator.suggest.SuggestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SuggestServiceImpl implements SuggestService {

    private final WsRepository repository;

    @Override
    public List<CategoryDto> getCategories() {
        return repository.categoryRepository.findSuggestCategories();
    }

    @Override
    public Object getSizeAvailable(SuggestDto dto) {
        log.info("SuggestServiceImpl getSizeAvailable start with dto: {}", JsonUtils.toJson(dto));
        log.info("SuggestServiceImpl getSizeAvailable validate");
        SuggestValidator.validGetSizeAvailable(dto);
        var height = repository.bodyHeightRepository.findByHeight(Long.valueOf(dto.getHeight()));
        var weight = repository.bodyWeightRepository.findByWeight(Long.valueOf(dto.getWeight()));
        if (height == null || weight == null) {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
        var code = height.getCode().getValue() - weight.getCode().getValue();
        SuggestEntity suggest;
        if (code < 0) {
            suggest = repository.suggestRepository.findByBodyWeightIdAndCategoryId(weight.getId(), dto.getCategoryId());
            if (suggest == null) {
                suggest = repository.suggestRepository.findByBodyWeightIdAndCategoryIdIsNull(weight.getId());
            }
        } else if (code == 0) {
            suggest = repository.suggestRepository.findByBodyWeightIdAndBodyHeightIdAndCategoryId(weight.getId(), height.getId(), dto.getCategoryId());
            if (suggest == null) {
                suggest = repository.suggestRepository.findByBodyWeightIdAndBodyHeightIdAndCategoryIdIsNull(weight.getId(), height.getId());
            }
        } else {
            suggest = repository.suggestRepository.findByBodyHeightIdAndCategoryId(height.getId(), dto.getCategoryId());
            if (suggest == null) {
                suggest = repository.suggestRepository.findByBodyHeightIdAndCategoryIdIsNull(height.getId());
            }
        }
        if (suggest == null) {
            throw new WsException(WsCode.SIZE_SUGGEST_NOT_FOUND);
        }
        var size = repository.sizeRepository.findById(suggest.getSizeId()).orElseThrow(() -> {
            throw new WsException(WsCode.SIZE_SUGGEST_NOT_FOUND);
        });
        log.info("SuggestServiceImpl getSizeAvailable finish with res: {}", JsonUtils.toJson(size));
        return ResData.ok(size);
    }
}
