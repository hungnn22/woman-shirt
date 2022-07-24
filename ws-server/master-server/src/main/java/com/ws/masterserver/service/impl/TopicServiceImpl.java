package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.topic.TopicDto;
import com.ws.masterserver.entity.TopicEntity;
import com.ws.masterserver.service.TopicService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validator.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicServiceImpl implements TopicService {
    private final WsRepository repository;

    @Override
    @Transactional
    public ResData<String> create(CurrentUser currentUser, TopicDto dto) {
        AuthValidator.checkAdmin(currentUser);
        var topicName = repository.topicRepository.findByName(dto.getName());

        if (dto.getName().equals(topicName.getName())){
            throw new WsException(WsCode.TOPIC_EXISTS_NAME);
        }
        var topic = TopicEntity.builder()
                .id(UidUtils.generateUid())
                .name(dto.getName())
                .build();
        repository.topicRepository.save(topic);
        log.info("creat finished at {} with response: {}", new Date(), JsonUtils.toJson(topic));
        return new ResData<>(topic.getId(), WsCode.OK);
    }

    @Override
    @Transactional
    public ResData<String> delete(CurrentUser currentUser, TopicDto dto) {
        AuthValidator.checkAdmin(currentUser);
        if (dto.getId() == null){
            throw new WsException(WsCode.TOPIC_NOT_FOUND);
        }

        var topic = repository.topicRepository.findByid(dto.getId());
        repository.topicRepository.delete(topic);
        log.info("delete finished at {} with response: {}", new Date(), JsonUtils.toJson(topic));
        return new ResData<>(topic.getId(), WsCode.OK);
    }

    @Override
    @Transactional
    public ResData<String> update(CurrentUser currentUser, TopicDto dto) {
        AuthValidator.checkAdmin(currentUser);
        if (dto.getId() == null){
            throw new WsException(WsCode.TOPIC_NOT_FOUND);
        }

        var topic = repository.topicRepository.findByid(dto.getId());
        topic.setName(dto.getName());
        repository.topicRepository.save(topic);
        log.info("update finished at {} with response: {}", new Date(), JsonUtils.toJson(topic));
        return new ResData<>(topic.getId(), WsCode.OK);
    }
}