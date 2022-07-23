package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.blog.BlogDto;
import com.ws.masterserver.entity.BlogEntity;
import com.ws.masterserver.service.BlogService;
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
public class BlogServiceImpl implements BlogService {
    private final WsRepository repository;

    @Override
    @Transactional
    public ResData<String> create(CurrentUser currentUser, BlogDto dto) {
        AuthValidator.checkAdmin(currentUser);
        var blogs = repository.blogRepository.findByTitleAndContent(dto.getTitle(), dto.getContent());

        if (dto.getTitle().equals(blogs.getTitle()) || dto.getContent().equals(blogs.getContent())){
            throw new WsException(WsCode.BLOG_EXISTS);
        }
        var blog = BlogEntity.builder()
                .id(UidUtils.generateUid())
                .title(dto.getTitle().trim())
                .content(dto.getContent().trim())
                .topicId(dto.getTopicId())
                .build();
        repository.blogRepository.save(blog);
        log.info("creat finished at {} with response: {}", new Date(), JsonUtils.toJson(blog));
        return new ResData<>(blog.getId(), WsCode.OK);
    }

    @Override
    @Transactional
    public ResData<String> delete(CurrentUser currentUser, BlogDto dto) {
        AuthValidator.checkAdmin(currentUser);
        if (dto.getId() == null){
            throw new WsException(WsCode.BLOG_NOT_FOUND);
        }

        var blog = repository.blogRepository.findByID(dto.getId());
        repository.blogRepository.delete(blog);
        log.info("delete finished at {} with response: {}", new Date(), JsonUtils.toJson(blog));
        return new ResData<>(blog.getId(), WsCode.OK);
    }

    @Override
    @Transactional
    public ResData<String> update(CurrentUser currentUser, BlogDto dto) {
        AuthValidator.checkAdmin(currentUser);
        if (dto.getId() == null){
            throw new WsException(WsCode.BLOG_NOT_FOUND);
        }

        var blog = repository.blogRepository.findByID(dto.getId());
        blog.setTitle(dto.getTitle());
        blog.setContent(dto.getContent());
        repository.blogRepository.save(blog);
        log.info("update finished at {} with response: {}", new Date(), JsonUtils.toJson(blog));
        return new ResData<>(blog.getId(), WsCode.OK);
    }
}
