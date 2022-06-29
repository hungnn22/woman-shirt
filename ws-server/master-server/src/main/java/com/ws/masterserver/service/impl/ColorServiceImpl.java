package com.ws.masterserver.service.impl;

import com.ws.masterserver.entity.ColorEntity;
import com.ws.masterserver.service.ColorService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.enum_dto.ColorDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validate.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

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
}
