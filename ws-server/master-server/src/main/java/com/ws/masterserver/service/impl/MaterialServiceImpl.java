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
import com.ws.masterserver.utils.validate.MaterialValidator;
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
        AuthValidator.checkAdmin(currentUser);
        MaterialValidator.validCreate(dto);

        var material = MaterialEntity.builder()
                .id(UidUtils.generateUid())
                .name(dto.getName().trim())
                .des(dto.getDes().trim())
                .active(Boolean.TRUE)
                .build();
        repository.materialRepository.save(material);
        log.info("creat finished at {} with response: {}", new Date(), JsonUtils.toJson(material));
        return new ResData<>(material.getId(), WsCode.OK);
    }

    @Override
    @Transactional
    public ResData<String> delete(CurrentUser currentUser, MaterialDto dto) {
        AuthValidator.checkAdmin(currentUser);
        if (dto.getId() == null){
            throw new WsException(WsCode.MATERIAL_NOT_FOUND);
        }

        var material = repository.materialRepository.findByIdAndActive(dto.getId(),Boolean.TRUE);
        material.setActive(Boolean.FALSE);
        repository.materialRepository.save(material);
        log.info("delete finished at {} with response: {}", new Date(), JsonUtils.toJson(material));
        return new ResData<>(material.getId(), WsCode.OK);
    }

    @Override
    @Transactional
    public ResData<String> update(CurrentUser currentUser, MaterialDto dto) {
        AuthValidator.checkAdmin(currentUser);
        MaterialValidator.validCreate(dto);
        if (dto.getId() == null) {
            throw new WsException(WsCode.MATERIAL_NOT_FOUND);
        }

        var material = repository.materialRepository.findByIdAndActive(dto.getId(), Boolean.TRUE);
        material.setName(dto.getName().trim());
        material.setDes(dto.getDes().trim());
        repository.materialRepository.save(material);
        log.info("update finish at {} with response: {}" ,new Date(), JsonUtils.toJson(material));
        return new ResData<>(material.getId(), WsCode.OK);
    }
}
