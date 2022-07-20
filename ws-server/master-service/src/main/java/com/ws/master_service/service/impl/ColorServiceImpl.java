package com.ws.master_service.service.impl;

import com.ws.master_service.dto.customer.product.ColorResponse;
import com.ws.master_service.entity.ColorEntity;
import com.ws.master_service.service.ColorService;
import com.ws.master_service.utils.base.WsException;
import com.ws.master_service.utils.base.WsRepository;
import com.ws.master_service.utils.base.enum_dto.ColorDto;
import com.ws.master_service.utils.base.rest.CurrentUser;
import com.ws.master_service.utils.base.rest.ResData;
import com.ws.master_service.utils.common.JsonUtils;
import com.ws.master_service.utils.common.UidUtils;
import com.ws.master_service.utils.constants.WsCode;
import com.ws.master_service.utils.validator.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ColorServiceImpl implements ColorService {

    private final WsRepository repository;

    @Override
    @Transactional
    public ResData<String> create(CurrentUser currentUser, ColorDto dto) {
        AuthValidator.checkAdmin(currentUser);
        var products = repository.productRepository.findById(dto.getId());
        if (Boolean.FALSE.equals(products.isEmpty())){
            throw new WsException(WsCode.PRODUCT_NOT_FOUND);
        }
        var color = ColorEntity.builder()
                .id(UidUtils.generateUid())
                .name(dto.getName().trim())
                .hex(dto.getHex().trim())
                .active(Boolean.TRUE)
                .build();
        repository.colorRepository.save(color);
        log.info("create finished at {} with response: {}", new Date(), JsonUtils.toJson(color));
        return new ResData<>(color.getId(), WsCode.OK);
    }

    @Override
    @Transactional
    public ResData<String> delete(CurrentUser currentUser, ColorDto dto) {
        AuthValidator.checkAdmin(currentUser);
        if (dto.getId() == null || Boolean.FALSE.equals(repository.colorRepository.findByIdAndActive(dto.getId(), Boolean.TRUE))) {
            throw new WsException(WsCode.COLOR_NOT_FOUND);
        }
        var color = repository.colorRepository.findByIdAndActive(dto.getId(), Boolean.TRUE);
        color.setActive(Boolean.FALSE);
        repository.colorRepository.save(color);
        log.info("delete finished at {} with response: {}", new Date(), JsonUtils.toJson(color));
        return new ResData<>(color.getId(), WsCode.OK);
    }

    @Override
    @Transactional
    public ResData<String> update(CurrentUser currentUser, ColorDto dto) {
        AuthValidator.checkAdmin(currentUser);
        if (dto.getId() == null || Boolean.FALSE.equals(repository.colorRepository.existsByIdAndActive(dto.getId(), Boolean.TRUE))) {
            throw new WsException(WsCode.COLOR_NOT_FOUND);
        }
        var color = repository.colorRepository.findByIdAndActive(dto.getId(), Boolean.TRUE);
        color.setName(dto.getName().trim());
        color.setHex(dto.getHex().trim());
        repository.colorRepository.save(color);
        log.info("update finished at {} with response: {}", new Date(), JsonUtils.toJson(color));
        return new ResData<>(color.getId(), WsCode.OK);
    }

    @Override
    public ResData<List<ColorResponse>> getListColor() {
        List<ColorResponse> color = repository.colorRepository.findAllColor();
        return new ResData<>(color, WsCode.OK);
    }

}
