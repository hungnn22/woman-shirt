package com.ws.masterserver.repository.custom.impl;

import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.dto.customer.product.ProductResponse;
import com.ws.masterserver.repository.custom.ProductCustomRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.WsCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductCustomRepositoryImpl implements ProductCustomRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public PageData<ProductResponse> search(CurrentUser currentUser, ProductReq req) {
        var prefix = "select\n";
        var select = "new com.ws.masterserver.dto.customer.product.ProductResponse(\n" +
                "p.id as productId,\n" +
                "p.name as productName,\n" +
                "p.active as active,\n" +
                "c.name as categoryName,\n" +
                "p.des as description,\n" +
                "p.material as material,\n" +
                "p.type as type,\n" +
                "p.createdDate as productCreatedDate,\n" +
                "p.createdBy as productCreatedBy,\n" +
                "trim(concat(coalesce(u.firstName, ''), ' ', coalesce(u.lastName, ''))) as createdName)\n";
        var from = "from ProductEntity p\n";
        var joins = "left join CategoryEntity c on p.categoryId = c.id\n" +
                "left join UserEntity u on p.createdBy = u.id\n";
        var where = "where 1 = 1 and c.active = 1\n";

        if (Boolean.FALSE.equals(StringUtils.isNullOrEmpty(req.getTextSearch()))) {
            where += "and (lower(p.name) like concat('%', '" + req.getTextSearch().trim().toLowerCase(Locale.ROOT) + "', '%')\n" +
                    "or lower(p.des) like concat('%', '" + req.getTextSearch().trim().toLowerCase(Locale.ROOT) + "', '%'))\n";
        }

        var order = "order by ";
        switch (req.getPageReq().getSortField()) {
            case "name":
                order += "p.name";
                break;
            case "des":
                order += "p.des";
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

        var productList = query.getResultList();
        if (productList.isEmpty()) {
            return new PageData<>(true);
        }
        return PageData
                .setResult(
                        productList,
                        req.getPageReq().getPage(),
                        req.getPageReq().getPageSize(),
                        totalElements,
                        WsCode.OK);
    }
}
