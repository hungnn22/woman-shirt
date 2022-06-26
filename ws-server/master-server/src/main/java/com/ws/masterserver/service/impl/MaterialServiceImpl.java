package com.ws.masterserver.service.impl;

import com.ws.masterserver.entity.MaterialEntity;
import com.ws.masterserver.service.MaterialService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.enum_dto.MaterialDto;
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
public class MaterialServiceImpl implements MaterialService {

    private final WsRepository repository;

    @Override
    @Transactional
    public ResData<String> create(CurrentUser currentUser, MaterialDto dto) {
        //dasdas
        AuthValidator.checkAdmin(currentUser);
        var products = repository.productRepository.findById(dto.getId());
        if (Boolean.FALSE.equals(products.isEmpty())){
            throw new WsException(WsCode.PRODUCT_NOT_FOUND);
        }
        var material = MaterialEntity.builder()
                .id(UidUtils.generateUid())
                .name(dto.getName().trim())
                .active(Boolean.TRUE)
                .build();
        repository.materialRepository.save(material);
        log.info("creat finished at {} with response: {}", new Date(), JsonUtils.toJson(material));
        return new ResData<>(material.getId(), WsCode.OK);
    }

    @Override
    public ResData<String> delete(CurrentUser currentUser, MaterialDto dto) {
        AuthValidator.checkAdmin(currentUser);
        if (dto.getId() == null || Boolean.FALSE.equals(repository.colorRepository.findByIdAndActive(dto.getId(), Boolean.TRUE))) {
            throw new WsException(WsCode.MATERIAL_NOT_FOUND);
        }
        var material = repository.materialRepository.findByIdAndActive(dto.getId(),Boolean.TRUE);
        material.setActive(Boolean.FALSE);
        repository.materialRepository.save(material);
        log.info("delete finished at {} with response: {}", new Date(), JsonUtils.toJson(material));
        return new ResData<>(material.getId(), WsCode.OK);
    }

    @Override
    public ResData<String> update(CurrentUser currentUser, MaterialDto dto) {
        AuthValidator.checkAdmin(currentUser);
        if (dto.getId() == null || Boolean.FALSE.equals(repository.materialRepository.existsByIdAndActive(dto.getId(), Boolean.TRUE))) {
            throw new WsException(WsCode.MATERIAL_NOT_FOUND);
        }
        var material = repository.materialRepository.findByIdAndActive(dto.getId(), Boolean.TRUE);
        material.setName(dto.getName().trim());
        repository.materialRepository.save(material);
        log.info("update finish at {} with response: {}" ,new Date(), JsonUtils.toJson(material));
        return new ResData<>(material.getId(), WsCode.OK);
    }
}
