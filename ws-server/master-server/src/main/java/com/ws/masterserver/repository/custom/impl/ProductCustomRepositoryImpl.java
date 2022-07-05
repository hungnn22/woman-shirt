package com.ws.masterserver.repository.custom.impl;

import com.ws.masterserver.dto.admin.product.search.ProductRes;
import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.dto.customer.product.ProductResponse;
import com.ws.masterserver.dto.customer.product.search.ProductDto;
import com.ws.masterserver.dto.customer.product.search.ProductSubDto;
import com.ws.masterserver.repository.custom.ProductCustomRepository;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.PageReq;
import com.ws.masterserver.utils.common.BeanUtils;
import com.ws.masterserver.utils.common.JpaUtils;
import com.ws.masterserver.utils.common.MoneyUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.WsCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PrePostAdviceReactiveMethodInterceptor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductCustomRepositoryImpl implements ProductCustomRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public PageData<ProductResponse> search(ProductReq req) {
        var prefix = "select\n";
        var select =
                "new com.ws.masterserver.dto.customer.product.ProductResponse(\n" +
                        "p.id as productId,\n" +
                        "p.name as productName,\n" +
                        "p.active as active,\n" +
                        "po.image as thumbnail,\n" +
                        "cast(po.price as string) as price,\n" +
                        "c.name as categoryName,\n" +
                        "p.des as description,\n" +
                        "m.name as materialName,\n" +
//                "t.name as typeName,\n" +
                        "p.createdDate as productCreatedDate,\n" +
                        "p.createdBy as productCreatedBy,\n" +
                        "trim(concat(coalesce(u.firstName, ''), ' ', coalesce(u.lastName, ''))) as createdName)\n";
        var from = "from ProductEntity p\n";
        var joins = "left join CategoryEntity c on p.categoryId = c.id\n" +
                "left join MaterialEntity m on p.materialId = m.id\n" +
//                "left join TypeEntity t on p.typeId = t.id\n" +
                "left join UserEntity u on p.createdBy = u.id\n" +
                "left join ProductOptionEntity po on p.id = po.productId\n";
        var where = "where 1 = 1\n" +
                "and c.active = 1\n" +
                "and p.active = 1\n" +
                "and po.price in (select min(price) from ProductOptionEntity where po.productId = p.id)\n" +
                "and po.image in (select image from ProductOptionEntity where po.productId = p.id)\n";

        if (Boolean.FALSE.equals(StringUtils.isNullOrEmpty(req.getTextSearch()))) {
            where += "and (lower(p.name) like concat('%', '" + req.getTextSearch().trim().toLowerCase(Locale.ROOT) + "', '%')\n" +
                    "or lower(c.name) like concat('%', '" + req.getTextSearch().trim().toLowerCase(Locale.ROOT) + "', '%'))\n";
        }

        if (Boolean.FALSE.equals(StringUtils.isNullOrEmpty(req.getPriceMin()))
                && Boolean.FALSE.equals(StringUtils.isNullOrEmpty(req.getPriceMax()))) {
            where += "and po.price BETWEEN " + req.getPriceMin().trim() + " AND " + req.getPriceMax().trim() + "\n";
        }

        var order = "order by ";
        switch (req.getPageReq().getSortField()) {
            case "name":
                order += "p.name";
                break;
            case "price":
                order += "po.price";
                break;
            case "createdName":
                order += "u.lastName";
                break;
            default:
                order += "p.createdDate";
        }
        order += " " + req.getPageReq().getSortDirection() + "\n";

        //Result jpa
        var jpql = prefix + select + from + joins + where + order;

        log.info("JPQL: {}", jpql);

        var query = entityManager.createQuery(jpql);
        var totalElements = 0L;
        if (Boolean.FALSE.equals(query.getResultList().isEmpty())) totalElements = query.getResultList().size();
        query.setFirstResult(req.getPageReq().getPage() * req.getPageReq().getPageSize());
        query.setMaxResults(req.getPageReq().getPageSize());

        List<ProductResponse> productList = query.getResultList();

        if (productList.isEmpty()) {
            return new PageData<>(true);
        }

        productList.forEach(product -> {
            Long price = Long.valueOf(product.getPrice());
            String priceFormat = MoneyUtils.format(price);
            product.setPrice(priceFormat);
        });

        var productMoneyFormatedList = (List) productList;
        return PageData
                .setResult(
                        productMoneyFormatedList,
                        req.getPageReq().getPage(),
                        req.getPageReq().getPageSize(),
                        totalElements,
                        WsCode.OK);
    }

    @Override
    public PageData<ProductRes> search4Admin(ProductReq req) {
        var sql = "";
        return new PageData<ProductRes>(true);
    }

    @Override
    public Object searchV2(com.ws.masterserver.dto.customer.product.search.ProductReq req) {
        var repository = BeanUtils.getBean(WsRepository.class);
        var sql = "select distinct p1.id,\n" +
                "                p1.name as p1_name,\n" +
                "                po1.po_sub1_min_price,\n" +
                "                po1.po_sub1_max_price,\n" +
                "                p1.des,\n" +
                "                m1.name as m1_name,\n" +
                "                ct1.name as ct1_name,\n" +
                "                t1.name as t1_name\n," +
                "                p1.created_date\n" +
                "from product p1\n" +
                "         left join (\n" +
                "    select po_sub1.product_id as po_sub1_product_id,\n" +
                "           min(po_sub1.price) as po_sub1_min_price,\n" +
                "           max(po_sub1.price) as po_sub1_max_price\n" +
                "    from product_option po_sub1\n" +
                "    group by po_sub1.product_id) po1 on\n" +
                "        po1.po_sub1_product_id = p1.id\n" +
                "         left join material m1 on\n" +
                "        m1.id = p1.material_id\n" +
                "         left join category ct1 on\n" +
                "        ct1.id = p1.category_id\n" +
                "         left join \"type\" t1 on\n" +
                "        t1.id = ct1.type_id\n" +
                "         left join product_option po2 on\n" +
                "        po2.product_id = p1.id\n" +
                "where 1 = 1\n";

//        sql += getOrderFilter(req.getPageReq());
        if (!StringUtils.isNullOrEmpty(req.getTextSearch())) {
            var textSearch = req.getTextSearch().trim().toUpperCase(Locale.ROOT);
            var like = "concat('%', unaccent('" + textSearch +"'), '%')";
            sql += "and (unaccent(upper(p1.name)) like " + like + "\n" +
                    "or unaccent(upper(ct1.name)) like " + like + ")\n";
        }
        if (!StringUtils.isNullOrEmpty(req.getMinPrice())) {
            sql += "and po1.po_sub1_min_price >= " + Long.valueOf(req.getMinPrice()) + "\n";
        }
        if (!StringUtils.isNullOrEmpty(req.getMaxPrice())) {
            sql += "and po1.po_sub1_max_price <= " + Long.valueOf(req.getMaxPrice()) + "\n";
        }
        if (!req.getColorIds().isEmpty()) {
            sql += "and po2.color_id in " + req.getColorIds().stream().map(o -> "'" + o + "'").collect(Collectors.joining(", ", "(", ")"));
        }
        if (!req.getSizeIds().isEmpty()) {
            sql += "and po2.size_id in " + req.getSizeIds().stream().map(o -> "'" + o + "'").collect(Collectors.joining(", ", "(", ")"));
        }
        log.info("ProductCustomRepositoryImpl searchV2 sql: ", sql);

        var query = entityManager.createNativeQuery(sql);

        var totalElements = Long.valueOf(query.getResultList().size());

        List<Object[]> objects = query.getResultList();
        List<ProductDto> productDtos;
        List<ProductDto> res = new ArrayList<>();

        if (!objects.isEmpty()) {
            productDtos = objects.stream().map(obj -> {
                var productId = JpaUtils.getString(obj[0]);
                return ProductDto.builder()
                        .id(productId)
                        .name(JpaUtils.getString(obj[1]))
                        .minPrice(JpaUtils.getLong(obj[2]))
                        .maxPrice(JpaUtils.getLong(obj[3]))
                        .des(JpaUtils.getString(obj[4]))
                        .materialName(JpaUtils.getString(obj[5]))
                        .categoryName(JpaUtils.getString(obj[6]))
                        .typeName(JpaUtils.getString(obj[7]))
                        .colors(repository.colorRepository.findByProductId(productId))
                        .sizes(repository.sizeRepository.findByProductId(productId))
                        .images(repository.productOptionRepository.findImageByProductId(productId))
                        .createdDate(JpaUtils.getDate(obj[8]))
                        .build();
            }).collect(Collectors.toList());

            Comparator compare;
            switch (req.getPageReq().getSortField()) {
                case "name":
                    compare = Comparator.comparing(ProductDto::getName);
                    break;
                case "price":
                    compare = Comparator.comparing(ProductDto::getMinPrice);
                    break;
                default:
                    compare = Comparator.comparing(ProductDto::getCreatedDate);
            }

            productDtos.sort(compare);
            if (StringUtils.isNullOrEmpty(req.getPageReq().getSortDirection()) || "desc".equals(req.getPageReq().getSortDirection())) {
                Collections.reverse(productDtos);
            }
            var page = req.getPageReq().getPage();
            if (page == null || page < 0){
                page = 0;
            }

            var pageSize = req.getPageReq().getPageSize();
            if (pageSize == null || pageSize < 1) {
                pageSize = 10;
            }

            var start = page * pageSize;
            var end = start + pageSize;
            if (end >= totalElements - 1) {
                end = totalElements.intValue();
            }

            res = productDtos.subList(start, end);

        }

        return PageData.setResult(res, req.getPageReq().getPage(), req.getPageReq().getPageSize(), totalElements);
    }

    private String getOrderFilter(PageReq pageReq) {
        var sortDirection = " " + (pageReq.getSortDirection() == null ? "desc" : "asc");
        var sortField = "";
        var result = "order by ";
        switch (pageReq.getSortField()) {
            case "name":
                sortField = "p1.name";
                break;
            case "price":
                sortField = "po1.po_sub1_min_price";
                break;
            default:
                sortField = "p1.created_date";
        }
        return result + sortField + sortDirection;
    }
}
