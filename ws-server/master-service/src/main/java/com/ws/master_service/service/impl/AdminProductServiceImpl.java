package com.ws.master_service.service.impl;

import com.ws.master_service.dto.admin.product.create_update.ProductDto;
import com.ws.master_service.dto.admin.product.search.ProductRes;
import com.ws.master_service.dto.customer.product.ProductReq;
import com.ws.master_service.entity.ProductEntity;
import com.ws.master_service.entity.ProductOptionEntity;
import com.ws.master_service.proxy.CloudProxy;
import com.ws.master_service.service.AdminProductService;
import com.ws.master_service.utils.base.WsException;
import com.ws.master_service.utils.base.WsRepository;
import com.ws.master_service.utils.base.rest.CurrentUser;
import com.ws.master_service.utils.base.rest.PageData;
import com.ws.master_service.utils.common.*;
import com.ws.master_service.utils.constants.WsCode;
import com.ws.master_service.utils.constants.enums.RoleEnum;
import com.ws.master_service.utils.validator.AuthValidator;
import com.ws.master_service.utils.validator.product.AdminProductValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminProductServiceImpl implements AdminProductService {

    private final WsRepository repository;
    private final EntityManager entityManager;
    private final CloudProxy cloudProxy;

    @Override
    public Object search(CurrentUser currentUser, ProductReq req) {
        AuthValidator.checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_STAFF);
        log.info("AdminProductServiceImpl search start with req: {}", JsonUtils.toJson(req));
        var sql = "select p1.id as p1_id,\n" +
                "p1.name as p1_name,\n" +
                "(select min(po1.price) from product_option po1 where po1.product_id = p1.id) as p1_min_price,\n" +
                "(select max(po2.price) from product_option po2 where po2.product_id = p1.id) as p1_max_price,\n" +
                "(select sum(po3.qty) from product_option po3 where po3.product_id = p1.id) as p1_qty,\n" +
                "p1.des as p1_des,\n" +
                "p1.active as p1_active,\n" +
                "p1.created_date as p1_created_date,\n" +
                "m1.name as m1_name,\n" +
                "ct1.name as ct1_name,\n" +
                "t1.name as t1_name\n" +
                "from product p1\n" +
                "left join material m1 on m1.id = p1.material_id\n" +
                "left join category ct1 on ct1.id = p1.category_id\n" +
                "left join type t1 on t1.id = ct1.type_id\n" +
                "where t1.active = true\n" +
                "and ct1.active = true\n";
        if (!StringUtils.isNullOrEmpty(req.getTextSearch())) {
            var textSearch = req.getTextSearch().trim().toUpperCase(Locale.ROOT);
            sql += "and (unaccent(upper(p1.name)) like concat('%', unaccent('" + textSearch + "'), '%')\n" +
                    "or unaccent(upper(p1.des)) like concat('%', unaccent('" + textSearch + "'), '%')\n" +
                    "or unaccent(upper(ct1.name)) like concat('%', unaccent('" + textSearch + "'), '%'))\n";
        }

        var minPrice = 0L;
        var maxPrice = 0L;
        if (!StringUtils.isNullOrEmpty(req.getMinPrice())) {
            if (!StringUtils.isNumber(req.getMinPrice())) {
                throw new WsException(WsCode.BAD_REQUEST, "Giá không hợp lệ");
            }
            minPrice = Long.valueOf(req.getMinPrice());
            sql += "and p1_min_price <= " + minPrice + "\n";
        }
        if (!StringUtils.isNullOrEmpty(req.getMaxPrice())) {
            if (!StringUtils.isNumber(req.getMaxPrice())) {
                throw new WsException(WsCode.BAD_REQUEST, "Giá không hợp lệ");
            }
            maxPrice = Long.valueOf(req.getMaxPrice());
            sql += "and p1_max_price >= " + maxPrice + "\n";
        }

        if (minPrice > maxPrice) {
            throw new WsException(WsCode.BAD_REQUEST, "Giá tối thiểu phải nhỏ hơn giá tối đa");
        }

        if (null != req.getActive()) {
            sql += "p1.active = " + req.getActive() + "\n";
        }

        var orderField = "";
        var orderDirection = " " + (StringUtils.isNullOrEmpty(req.getPageReq().getSortDirection()) || "desc".equals(req.getPageReq().getSortDirection()) ? "desc" : "asc") + "\n";
        switch (req.getPageReq().getSortField()) {
            case "name":
                orderField = "p1.name";
                break;
            case "price":
                orderField = "p1_min_price";
                break;
            default:
                orderField = "p1.created_date";
        }
        sql += "order by " + orderField + orderDirection;
        log.info("AdminProductServiceImpl search sql: {}", sql);
        var query = entityManager.createNativeQuery(sql);
        Long totalElements = Long.valueOf(query.getResultList().size());
        query.setFirstResult(req.getPageReq().getPage() * req.getPageReq().getPageSize());
        query.setMaxResults(req.getPageReq().getPageSize());

        List<Object[]> objects = query.getResultList();
        if (objects.isEmpty()) {
            return PageData.setEmpty(req.getPageReq());
        }
        List<ProductRes> productRes = objects.stream().map(obj -> {
            var id = JpaUtils.getString(obj[0]);
            return ProductRes.builder()
                    .id(id)
                    .name(JpaUtils.getString(obj[1]))
                    .minPrice(MoneyUtils.format(JpaUtils.getLong(obj[2])))
                    .maxPrice(MoneyUtils.format(JpaUtils.getLong(obj[3])))
                    .qty(JpaUtils.getLong(obj[4]))
                    .des(JpaUtils.getString(obj[5]))
                    .active(JpaUtils.getBoolean(obj[6]))
                    .createdDate(DateUtils.toStr(JpaUtils.getDate(obj[7]), DateUtils.F_DDMMYYYYHHMMSS))
                    .materialName(JpaUtils.getString(obj[8]))
                    .categoryName(JpaUtils.getString(obj[9]))
                    .typeName(JpaUtils.getString(obj[10]))
                    .sizes(repository.sizeRepository.findDistinctByProductId(id))
                    .colors(repository.colorRepository.findDistinctByProductId(id))
                    .soldNumber(repository.productRepository.countSellNumber(id))
                    .build();
        }).collect(Collectors.toList());
        return PageData.setResult(productRes,
                req.getPageReq().getPage(),
                req.getPageReq().getPageSize(),
                totalElements);
    }

    @Override
    public Object detail(CurrentUser currentUser, String id) {
        return null;
    }

    @Override
    @Transactional
    public Object create(CurrentUser currentUser, ProductDto dto) {
        log.info("AdminProductServiceImpl create start with dto: {}", JsonUtils.toJson(dto));
        AuthValidator.checkAdmin(currentUser);
        AdminProductValidator.validCreate(dto);
        var product = ProductEntity.builder()
                .id(UidUtils.generateUid())
                .name(dto.getName().trim())
                .des(dto.getDes().stream().map(obj -> obj.trim()).collect(Collectors.joining("|")))
                .materialId(dto.getMaterialId())
                .active(true)
                .categoryId(dto.getCategoryId())
                .build();
        log.info("product before save: {}", JsonUtils.toJson(product));
        repository.productRepository.save(product);
        log.info("product after save: {}", JsonUtils.toJson(product));

        for (var option : dto.getOptions()) {
            var po = ProductOptionEntity.builder()
                    .id(UidUtils.generateUid())
                    .productId(product.getId())
                    .active(true)
                    .colorId(option.getColorId())
                    .sizeId(option.getSizeId())
                    .qty(Long.valueOf(option.getQty()))
                    .price(Long.valueOf(option.getPrice()))
//                    .image(cloudProxy.uploadImage(option.getImage()))
                    .build();
            log.info("po before save: {}", JsonUtils.toJson(po));
            repository.productOptionRepository.save(po);
            log.info("po afre save: {}", JsonUtils.toJson(po));
        }
        return null;
    }
}
