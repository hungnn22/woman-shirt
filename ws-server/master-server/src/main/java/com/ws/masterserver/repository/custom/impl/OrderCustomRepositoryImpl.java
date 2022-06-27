package com.ws.masterserver.repository.custom.impl;

import com.ws.masterserver.dto.admin.order.detail.DetailRes;
import com.ws.masterserver.dto.admin.order.detail.ItemDto;
import com.ws.masterserver.dto.admin.order.search.*;
import com.ws.masterserver.repository.custom.OrderCustomRepository;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.PageReq;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    private final EntityManager entityManager;

    @Override
    public PageData<OrderRes> search4Admin(CurrentUser currentUser, OrderReq req) {
        var sql = "select o1.id                                    as order_id,\n" +
                "       o1.code                                  as order_code,\n" +
                "       o1.created_date                          as order_date,\n" +
                "       o1.note                                  as order_note,\n" +
                "       o1.total                                 as order_total,\n" +
                "       o1.\"type\"                                as order_type,\n" +
                "       o1.payed                                 as order_payed,\n" +
                "       u1.id                                    as cus_id,\n" +
                "       u1.gender                                as cus_gender,\n" +
                "       concat(u1.first_name, ' ', u1.last_name) as cus_full_name,\n" +
                "       u1.phone                                 as cus_phone,\n" +
                "       concat(a1.exact, ', ', a1.combination)   as order_address,\n" +
                "       st1.\"name\"                               as ship_type_name,\n" +
                "       os3.status                               as status_now,\n" +
                "       os3.created_date                         as status_date,\n" +
                "       u2.\"role\"                                as status_role,\n" +
                "       concat(u2.first_name, ' ', u2.last_name) as status_full_name,\n" +
                "       os3.note                                 as status_note\n" +
                "from orders o1\n" +
                "         left join address a1 on\n" +
                "    a1.id = o1.address_id\n" +
                "         left join users u1 on\n" +
                "    u1.id = o1.user_id\n" +
                "         left join ship_type st1 on\n" +
                "    st1.id = o1.ship_type_id\n" +
                "         left join (\n" +
                "    select os1.order_id          as os2_order_id,\n" +
                "           max(os1.created_date) as os2_created_date\n" +
                "    from order_status os1\n" +
                "    group by os1.order_id) os2 on\n" +
                "    os2.os2_order_id = o1.id\n" +
                "         left join order_status os3 on\n" +
                "    os3.created_date = os2.os2_created_date\n" +
                "         left join users u2 on\n" +
                "    u2.id = os3.created_by\n" +
                "where 1 = 1\n";
        if (!StringUtils.isNullOrEmpty(req.getStatus())) {
            sql += "and os3.status = '" + req.getStatus() + "'\n";
        }
        if (!StringUtils.isNullOrEmpty(req.getProvinceCode())) {
            sql += "and a1.province_code = '" + req.getProvinceCode() + "'\n";
        }
        if (!StringUtils.isNullOrEmpty(req.getDistrictCode())) {
            sql += "and a1.district_code = '" + req.getDistrictCode() + "'\n";
        }
        if (!StringUtils.isNullOrEmpty(req.getWardCode())) {
            sql += "and a1.ward_code = '" + req.getWardCode() + "'\n";
        }
        if (!StringUtils.isNullOrEmpty(req.getTime())) {
            switch (req.getTime()) {
                case "day":
                    sql += "and cast(o1.created_date as date) = cast(current_date as date)\n";
                    break;
                case "week":
                    sql += "and extract('week' from o1.created_date) = extract('week' from current_date)\n";
                    break;
                case "month":
                    sql += "and extract('month' from o1.created_date) = extract('month' from current_date)\n";
                    break;
                default:
                    break;
            }
        }
        if (!StringUtils.isNullOrEmpty(req.getTextSearch())) {
            var textSearch = "'%" + req.getTextSearch().trim().toUpperCase(Locale.ROOT) + "%'";
            sql += "and (unaccent(upper(trim(concat(u1.first_name, ' ', u1.last_name)))) like " + textSearch + " or\n" +
                    "u1.phone like " + textSearch + " or o1.code like" + textSearch + ")\n";
        }


        this.addOrderFilter(req.getPageReq());

        sql += "order by " + req.getPageReq().getSortField() + " " + req.getPageReq().getSortDirection() + "\n";

        log.info("OrderCustomRepositoryImpl search4Admin sql: {}", sql);
        Query query = entityManager.createNativeQuery(sql);
        Long totalElements = Long.valueOf(query.getResultList().size());

        if (totalElements == 0) {
            return new PageData<>(true);
        }

        query.setFirstResult(req.getPageReq().getPage() * req.getPageReq().getPageSize());
        query.setMaxResults(req.getPageReq().getPageSize());

        List<Object[]> objects = query.getResultList();

        List<OrderRes> orderRes = objects.stream().map(obj -> OrderRes.builder()
                .id(JpaUtils.getString(obj[0]))
                .code(JpaUtils.getString(obj[1]))
                .orderDate(DateUtils.toStr(JpaUtils.getDate(obj[2]), DateUtils.DATE_TIME_FORMAT_VI))
                .note(JpaUtils.getString(obj[3]))
                .total(MoneyUtils.format(JpaUtils.getLong(obj[4])))
                .type(OrderTypeUtils.getOrderTypeStr(JpaUtils.getString(obj[5]), JpaUtils.getBoolean(obj[6])))
                .customerId(JpaUtils.getString(obj[7]))
                .customer(UserUtils.getCustomerInfo(JpaUtils.getBoolean(obj[8]), JpaUtils.getString(obj[9])))
                .phone(JpaUtils.getString(obj[10]))
                .address(JpaUtils.getString(obj[11]))
                .status(OrderUtils.getStatusCombination(JpaUtils.getString(obj[13]), JpaUtils.getDate(obj[14]), JpaUtils.getString(obj[15]), JpaUtils.getString(obj[16])))
                .options(OrderUtils.getOptions4Admin(JpaUtils.getString(obj[13])))
                .build()).collect(Collectors.toList());
        return PageData.setResult(
                orderRes,
                req.getPageReq().getPage(),
                req.getPageReq().getPageSize(),
                totalElements);
    }

    private void addOrderFilter(PageReq pageReq) {
        if (StringUtils.isNullOrEmpty(pageReq.getSortDirection())) {
            pageReq.setSortDirection("desc");
        }
        if (StringUtils.isNullOrEmpty(pageReq.getSortField()) || pageReq.getSortField().equals("date")) {
            pageReq.setSortField("o1.created_date");
            return;
        }
        var orderField = "";
        switch (pageReq.getSortField()) {
            case "customer":
                orderField = "u1.last_name";
                break;
            case "total":
                orderField = "o1.total";
                break;
            default:
                throw new WsException(WsCode.INTERNAL_SERVER);
        }
        pageReq.setSortField(orderField);
    }

    @Override
    public ResData<DetailRes> detail4Admin(CurrentUser currentUser, String id) {
        var repository = BeanUtils.getBean(WsRepository.class);
        if (!repository.orderRepository.existsById(id)) {
            return new ResData<>(true);
        }
        var shipPrice = repository.orderRepository.findPriceShipById(id);
        var shopPrice = 0L;
        var res = DetailRes.builder().id(id);
        var itemSql = "select distinct po1.id    as poId,\n" +
                "                p1.id     as pId,\n" +
                "                p1.name   as pName,\n" +
                "                c1.name   as cName,\n" +
                "                od1.price as odPrice,\n" +
                "                od1.qty   as odQty,\n" +
                "                po1.size  as poSize,\n" +
                "                cl1.name  as clName,\n" +
                "                m1.name   as mName,\n" +
                "                po1.image as poIng\n" +
                "from order_detail od1\n" +
                "         left join product_option po1 on od1.product_option_id = po1.id\n" +
                "         left join color cl1 on po1.color_id = cl1.id\n" +
                "         left join product p1 on po1.product_id = p1.id\n" +
                "         left join category c1 on p1.category_id = c1.id\n" +
                "         left join material m1 on p1.material_id = m1.id\n" +
                "where od1.order_id = :orderId";
        log.info("Item SQL: {}", itemSql);
        var itemQuery = entityManager.createNativeQuery(itemSql);
        itemQuery.setParameter("orderId", id);
        List<Object[]> objects = itemQuery.getResultList();
        List<ItemDto> items = new ArrayList<>();
        if (!objects.isEmpty()) {
            for (var obj : objects) {
                var total = JpaUtils.getLong(obj[4]) * JpaUtils.getLong(obj[5]);
                shopPrice += total;
                items.add(ItemDto.builder()
                        .id(JpaUtils.getString(obj[0]))
                        .productId(JpaUtils.getString(obj[1]))
                        .name(JpaUtils.getString(obj[2]))
                        .category(JpaUtils.getString(obj[3]))
                        .price(JpaUtils.getLong(obj[4]))
                        .priceFmt(MoneyUtils.format(JpaUtils.getLong(obj[4])))
                        .qty(JpaUtils.getLong(obj[5]))
                        .size(JpaUtils.getString(obj[6]))
                        .color(JpaUtils.getString(obj[7]))
                        .material(JpaUtils.getString(obj[8]))
                        .img(JpaUtils.getString(obj[9]))
                        .total(total)
                        .totalFmt(MoneyUtils.format(total))
                        .build());
            }
            res.items(items);
        }

        var promotions = repository.orderPromotionRepository.findByOrderId(id);
        promotions = promotions.stream().peek(p -> p.setTypeName(PromotionTypeUtils.getName(p.getTypeCode()))).collect(Collectors.toList());

        res.promotions(promotions);

        var result = OrderUtils.getResultDto(shopPrice, shipPrice, promotions);

        res.result(result);

        var orderStatusList = repository.orderStatusRepository.findHistory(id);
        if (!orderStatusList.isEmpty()) {
            res.history(OrderUtils.getHistory(orderStatusList));
        }

        return new ResData<>(res.build(), WsCode.OK);
    }

}
