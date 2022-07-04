package com.ws.masterserver.repository.custom.impl;

import com.ws.masterserver.dto.admin.product.search.ProductRes;
import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.dto.customer.product.ProductResponse;
import com.ws.masterserver.dto.customer.product.search.ProductSubDto;
import com.ws.masterserver.repository.custom.ProductCustomRepository;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.PageReq;
import com.ws.masterserver.utils.common.MoneyUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.WsCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

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
        var sql = "select new com.ws.masterserver.dto.customer.product.search.ProductSubDto(\n" +
                "p1.id,\n" +
                "p1.name,\n" +
                "po1.poSub1MinPrice,\n" +
                "po2.poSub2MaxPrice,\n" +
                "m1.name,\n" +
                "ct1.name,\n" +
                "p1.des,\n" +
                "t1.name)\n" +
                "from ProductEntity p1\n" +
                "left join (\n" +
                "select poSub1.productId as poSub1ProductId,\n" +
                "min(poSub1.price) as poSub1MinPrice\n" +
                "from ProductOptionEntity poSub1\n" +
                "group by poSub1.productId) po1 on po1.poSub1ProductId = p1.id\n" +
                "left join (\n" +
                "select poSub2.productId as poSub2ProductId,\n" +
                "max(poSub2.price) as poSub2MaxPrice\n" +
                "from ProductOptionEntity poSub2\n" +
                "group by poSub2.productId) po2 on po2.poSub1ProductId = p1.id\n" +
                "left join MaterialEntity m1 on m1.id = p1.materialId\n" +
                "left join CategoryEntity ct1 on ct1.id = p1.categoryId\n" +
                "left join TypeEntity t1 on t1.id = ct1.TypeId\n" +
                "left join ProductOptionEntity po3 on po3.productId = p1.id\n" +
                "left join SizeEntity s1 on s1.id = po3.sizeId\n" +
                "left join ColorEntity c1 on c1.id = po3.colorId\n" +
                "where (p1.name is not null \n" +
                "or upper(unaccent(p1.name)) like :textSearch\n" +
                "or ct1.name is not null\n" +
                "or upper(unaccent(ct1.name)) like :textSearch)\n" +
                "and (po1.poSub1MinPrice is null or po1.poSub1MinPrice >= :minPrice)\n" +
                "and (po2.poSub2MaxPrice is null or po2.poSub2MaxPrice <= :maxPrice)\n" +
                "and (po3.colorId is not null or po3.colorId in :colorIds)\n" +
                "and (po3.sizeId is not null or po3.sizeId in :sizeIds)\n";
        sql += getOrderFilter(req.getPageReq());
        var query = entityManager.createQuery(sql);
        if (!StringUtils.isNullOrEmpty(req.getTextSearch())) {
            query.setParameter("textSearch", "%" + req.getTextSearch().trim().toUpperCase(Locale.ROOT) + "%");
        }
        if (!StringUtils.isNullOrEmpty(req.getMinPrice())) {
            query.setParameter("minPrice", Long.valueOf(req.getMinPrice()));
        }
        if (!StringUtils.isNullOrEmpty(req.getMaxPrice())) {
            query.setParameter("maxPrice", Long.valueOf(req.getMaxPrice()));
        }
        if (!req.getColorIds().isEmpty()) {
            query.setParameter("colorIds", req.getColorIds());
        }
        if (!req.getSizeIds().isEmpty()) {
            query.setParameter("sizeIds", req.getSizeIds());
        }
        var totalElements = Long.valueOf(query.getResultList().size());

        query.setFirstResult(req.getPageReq().getPage() * req.getPageReq().getPageSize());
        query.setMaxResults(req.getPageReq().getPageSize());

        List<ProductSubDto> productSubDtos = query.getResultList();

        return productSubDtos;
    }

    private String getOrderFilter(PageReq pageReq) {
        var sortDirection = " " + (pageReq.getSortDirection() == null ? "desc" : "asc");
        var sortField = "";
        var result = "order by ";
        switch (pageReq.getSortField()) {
            case "name":
                sortField = "ct1.name";
                break;
            case "price":
                sortField = "po1.poSub1MinPrice";
                break;
            default:
                sortField = "p1.createdDate";
        }
        return result + sortField + sortDirection;
    }
}
