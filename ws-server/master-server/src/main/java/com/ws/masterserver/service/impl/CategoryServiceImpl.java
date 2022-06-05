package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.category.CategoryDto;
import com.ws.masterserver.dto.admin.category.CategoryReq;
import com.ws.masterserver.dto.admin.category.CategoryRes;
import com.ws.masterserver.entity.CategoryEntity;
import com.ws.masterserver.service.CategoryService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.validate.AuthValidator;
import com.ws.masterserver.utils.validate.CategoryValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final WsRepository repository;

    @Override
    public PageData<CategoryRes> search(CurrentUser currentUser, CategoryReq req) {
        //Nếu là khách hàng thì không cho search theo trạng thái
        if (req.getActive() != null && RoleEnum.ROLE_CUSTOMER.equals(currentUser.getRole())) {
            return new PageData<>(true);
        }
        return repository.categoryCustomRepository.search(currentUser, req);
    }

    @Override
    @Transactional
    public ResData<String> create(CurrentUser currentUser, CategoryDto dto) {
        AuthValidator.checkAdmin(currentUser);
        CategoryValidator.validCreate(dto);
        if (Boolean.TRUE.equals(repository.categoryRepository.existsByNameIgnoreCaseAndActive(dto.getName().trim(), Boolean.TRUE))) {
            throw new WsException(WsCode.CATEGORY_EXISTS_NAME);
        }
        var category = CategoryEntity.builder()
                .id(UidUtils.generateUid())
                .name(dto.getName().trim())
                .des(dto.getDes().trim())
                .active(Boolean.TRUE)
                .build();
        repository.categoryRepository.save(category);
        log.info("create finished at {} with response: {}", new Date(), JsonUtils.toJson(category));
        return new ResData<>(category.getId(), WsCode.OK);
    }

    @Override
    @Transactional
    public ResData<String> delete(CurrentUser currentUser, CategoryDto dto) {
        AuthValidator.checkAdmin(currentUser);
        if (Boolean.FALSE.equals(repository.categoryRepository.existsById(dto.getId()))) {
            throw new WsException(WsCode.CATEGORY_NOT_FOUND);
        }
        var category = repository.categoryRepository.findByIdAndActive(dto.getId(), Boolean.TRUE);
        category.setActive(Boolean.FALSE);
        repository.categoryRepository.save(category);
        var products = repository.productRepository.findByCategoryIdAndActive(category.getId(), Boolean.TRUE);
        if (Boolean.FALSE.equals(products.isEmpty())) {
            products.forEach(product -> {
                product.setActive(Boolean.FALSE);
                repository.productRepository.save(product);
                var productOptions = repository.productOptionRepository.findByProductIdAndActive(product.getId(), Boolean.TRUE);
                if (!productOptions.isEmpty()) {
                    productOptions.forEach(po -> {
                        po.setActive(Boolean.FALSE);
                        repository.productOptionRepository.save(po);
                    });
                }
            });
        }
        log.info("delete finished at {} with response: {}", new Date(), JsonUtils.toJson(category));
        return new ResData<>(category.getId(), WsCode.OK);
    }

    @Override
    @Transactional
    public ResData<String> update(CurrentUser currentUser, CategoryDto dto) {
        AuthValidator.checkAdmin(currentUser);
        CategoryValidator.validCreate(dto);
        if (dto.getId() == null || Boolean.FALSE.equals(repository.categoryRepository.existsByIdAndActive(dto.getId(), Boolean.TRUE))) {
            throw new WsException(WsCode.CATEGORY_NOT_FOUND);
        }
        if (Boolean.TRUE.equals(repository.categoryRepository.existsByNameIgnoreCaseAndActiveAndIdNot(dto.getName().trim(), Boolean.TRUE, dto.getId()))) {
            throw new WsException(WsCode.CATEGORY_EXISTS_NAME);
        }
        var category = repository.categoryRepository.findByIdAndActive(dto.getId(), Boolean.TRUE);
        category.setName(dto.getName().trim())
                .setDes(dto.getDes().trim());
        repository.categoryRepository.save(category);
        log.info("update finished at {} with response: {}", new Date(), JsonUtils.toJson(category));
        return new ResData<>(category.getId(), WsCode.OK);
    }

    @Override
    public ResData<CategoryRes> detail(CurrentUser currentUser, CategoryDto dto) {
        return null;
    }

}
