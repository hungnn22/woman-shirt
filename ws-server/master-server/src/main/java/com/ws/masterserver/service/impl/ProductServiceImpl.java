package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.dto.admin.product.ProductDetailResponse;
import com.ws.masterserver.dto.admin.product.ProductDto;
import com.ws.masterserver.dto.admin.product_option.ProductOptionResponse;
import com.ws.masterserver.dto.customer.product.ProductResponse;
import com.ws.masterserver.entity.CategoryEntity;
import com.ws.masterserver.entity.ProductEntity;
import com.ws.masterserver.entity.ProductOptionEntity;
import com.ws.masterserver.service.ProductService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.WsConst;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.validate.AuthValidator;
import com.ws.masterserver.utils.validate.ProductValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final WsRepository repository;

    @Override
    public ResData<String> create(CurrentUser currentUser, ProductDto dto) {
        AuthValidator.checkAdmin(currentUser);

        //todo: validate dto
        ProductValidator.validCreate(dto);
        var product = ProductEntity.builder()
                .id(UidUtils.generateUid())
                .name(dto.getName().trim())
                .categoryId(dto.getCategoryId())
                .material(dto.getMaterial())
                .des(dto.getDes().trim())
                .type(dto.getType())
                .build();
        repository.productRepository.save(product);

        //todo: dùng cloudiary api để update ảnh -> trả về đường dẫn
        repository.productOptionRepository.saveAll(dto.getProductOptions().stream().map(item ->
                ProductOptionEntity.builder()
                        .id(UidUtils.generateUid())
                        .productId(product.getId())
                        .size(item.getSize())
                        .color(item.getColor())
                        .price(Long.valueOf(item.getPrice()))
                        .image("")
                        .build()).collect(Collectors.toList()));

        return new ResData<>(product.getId(), WsCode.OK);
    }

    @Override
    public ResData<ProductDetailResponse> getProductDetail(String id) {

        ProductDetailResponse response = new ProductDetailResponse();

        ProductEntity product = repository.productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(WsConst.Messages.NOT_FOUND, WsConst.Nouns.CATEGORY_VI)));

        response.setProductId(product.getId());
        response.setProductName(product.getName());
        response.setDescription(product.getDes());

        CategoryEntity category = repository.categoryRepository.findById(product.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Not found category with id = " + id));
        response.setCategoryName(category.getName());

        List<ProductOptionEntity> productOptions = repository.productOptionRepository.findByProductId(id);

        response.setProductOptions(
                productOptions.stream().map(option -> {

                    ProductOptionResponse productOptionResponse = new ProductOptionResponse();
                    productOptionResponse.setImage(option.getImage());
                    productOptionResponse.setColorName("");
                    productOptionResponse.setId(option.getId());
                    productOptionResponse.setQty(option.getQty());
                    productOptionResponse.setSizeName("");
                    productOptionResponse.setProductName(product.getName());
                    productOptionResponse.setImage(option.getImage());

                    return productOptionResponse;

                }).collect(Collectors.toList())
        );

        response.setMaterial("");
        response.setType("");

        return new ResData<>(response, WsCode.OK);
    }

    @Override
    public PageData<ProductResponse> search(CurrentUser currentUser, ProductReq req) {
        //Nếu là khách hàng thì không cho search theo trạng thái
        if (req.getActive() != null && RoleEnum.ROLE_CUSTOMER.equals(currentUser.getRole())) {
            return new PageData<>(true);
        }
        return repository.productCustomRepository.search(currentUser, req);
    }
}
