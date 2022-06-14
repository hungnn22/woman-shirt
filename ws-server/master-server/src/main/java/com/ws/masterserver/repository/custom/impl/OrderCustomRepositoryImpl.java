package com.ws.masterserver.repository.custom.impl;

import com.ws.masterserver.dto.admin.order.detail.DetailRes;
import com.ws.masterserver.dto.admin.order.detail.ItemDto;
import com.ws.masterserver.dto.admin.order.search.*;
import com.ws.masterserver.repository.custom.OrderCustomRepository;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.WsConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    private final EntityManager entityManager;

    @Override
    public PageData<OrderRes> search4Admin(CurrentUser currentUser, OrderReq req) {
        var repository = BeanUtils.getBean(WsRepository.class);
        var prefix = "select\n";
        var distinct = "distinct\n";
        var select = "o1.id                                                  as orderId,\n" +
                "                os3.orderDate                                          as orderDate,\n" +
                "                coalesce(concat(a1.exact, ', ', a1.combination), '')   as address,\n" +
                "                o1.note                                                as note,\n" +
                "                os4.status                                             as statusNow,\n" +
                "                os4.note                                               as statusNote,\n" +
                "                os4.created_date                                       as statusDate,\n" +
                "                u2.id                                                  as statusUserId,\n" +
                "                u2.first_name                                          as statusUserFirstName,\n" +
                "                u2.last_name                                           as statusUserLastName,\n" +
                "                u2.gender                                              as statusUserGender,\n" +
                "                u2.phone                                               as statusUserPhone,\n" +
                "                u2.role                                                as statusUserRole,\n" +
                "                coalesce(concat(u2.first_name, ' ', u2.last_name), '') as statusUserCombination,\n" +
                "                o1.user_id                                             as cusId,\n" +
                "                u1.first_name                                          as cusFirstName,\n" +
                "                u1.last_name                                           as cusLastName,\n" +
                "                u1.gender                                              as cusGender,\n" +
                "                u1.phone                                               as cusPhone,\n" +
                "                u1.role                                                as cusRole,\n" +
                "                coalesce(concat(u1.first_name, ' ', u1.last_name), '') as cusCombination,\n" +
                "                o1.ship_price                                          as oShipPrice,\n" +
                "                o1.payed                                               as oPayed,\n" +
                "                o1.type                                                as oType,\n" +
                "                (select coalesce(sum(od1.qty * od1.price), 0)\n" +
                "                 from order_detail od1)                                as orderTotal\n";
        var fromAndJoins = "from orders o1\n" +
                "         left join users u1 on o1.user_id = u1.id\n" +
                "         left join address a1 on o1.address_id = a1.id\n" +
                "         left join order_detail od1 on o1.id = od1.order_id\n" +
                "         left join order_status os1 on o1.id = os1.order_id and os1.status <> 'PROCESSING'\n" +
                "         right join (select o2.created_date as orderDate\n" +
                "                     from orders o2\n" +
                "                              left join order_status os2 on o2.id = os2.order_id and os2.status = 'PENDING'\n" +
                "                     group by os2.order_id) as os3 on o1.created_date = os3.orderDate\n" +
                "         right join (select os3.order_id,\n" +
                "                            os3.status,\n" +
                "                            os3.created_date,\n" +
                "                            os3.note,\n" +
                "                            os3.created_by\n" +
                "                     from order_status os3\n" +
                "                     where os3.created_date = (select max(os1.created_date)\n" +
                "                                               from order_status os1\n" +
                "                                               where os3.order_id = os1.order_id\n" +
                "                                               group by os3.order_id)) as os4 on os4.order_id = o1.id\n" +
                "         left join users u2 on os4.created_by = u2.id\n";
        var where = "where 1 = 1\n";

        where += addLocation(req.getProvinceCode(), req.getDistrictCode(), req.getWardCode());

        if (!StringUtils.isNullOrEmpty(req.getStatus())) {
            where += addStatus(req.getStatus());
        }

        if (!StringUtils.isNullOrEmpty(req.getTime())) {
            where += addTime(req.getTime());
        }

        if (!StringUtils.isNullOrEmpty(req.getTextSearch())) {
            where += addTextSearch(req.getTextSearch());
        }

        var orderField = addOrderField(req.getPageReq().getSortField());

        var order = "order by " + orderField + " " + req.getPageReq().getSortDirection();

        var sql = prefix + distinct + select + fromAndJoins + where + order;
        log.info("SQL: {}", sql);

        var query = entityManager.createNativeQuery(sql);
        var totalElements = Long.valueOf(query.getResultList().size());

        query.setFirstResult(req.getPageReq().getPage() * req.getPageReq().getPageSize());
        query.setMaxResults(req.getPageReq().getPageSize());

        List<Object[]> objects = query.getResultList();

        if (objects.isEmpty()) {
            return (PageData<OrderRes>) PageData.setEmpty(req.getPageReq());
        }

        var data = objects.stream().map(obj -> {
            var shipPrice = JpaUtils.getLong(obj[21]);
            var promotions = repository.orderPromotionRepository.findByOrderId(JpaUtils.getString(obj[0]));
            var total = OrderUtils.getTotal(JpaUtils.getLong(obj[24]), shipPrice, promotions);
            return OrderRes.builder()
                    .id(JpaUtils.getString(obj[0]))
                    .orderDate(DateUtils.toStr((JpaUtils.getDate(obj[1])), DateUtils.F_VI))
                    .address(JpaUtils.getString(obj[2]))
                    .note(JpaUtils.getString(obj[3]))
                    .status(OrderUtils.getStatusCombination(
                            JpaUtils.getString(obj[4]),
                            JpaUtils.getDate(obj[6]),
                            JpaUtils.getString(obj[13]),
                            JpaUtils.getString(obj[12])))
                    .customer(UserUtils.getCombination(
                            JpaUtils.getString(obj[20]),
                            JpaUtils.getString(obj[15]),
                            JpaUtils.getString(obj[16]),
                            JpaUtils.getBoolean(obj[17])))
                    .phone(JpaUtils.getString(obj[18]))
                    .payed(Optional.ofNullable(JpaUtils.getBoolean(obj[22])).orElse(false) ?
                            "Đã thanh toán" :
                            "Chưa thanh toán")
                    .type(OrderTypeUtils.getOrderTypeStr(JpaUtils.getString(obj[23])))
                    .total(total)
                    .totalFmt(MoneyUtils.format(total))
                    .options(OrderUtils.getOptions4Admin(JpaUtils.getString(obj[4])))
                    .build();
        }).collect(Collectors.toList());

        return new PageData<>(
                data,
                req.getPageReq().getPage(),
                req.getPageReq().getPageSize(),
                totalElements,
                WsCode.OK);
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

        return new ResData<>(res.build(), WsCode.OK);
    }

    private @NotNull String addLocation(String provinceCode, String districtCode, String wardCode) {
        var location = "";
        if (!StringUtils.isNullOrEmpty(provinceCode)) {
            location += "and a1.province_code = '" + provinceCode + "'";
        }
        if (!StringUtils.isNullOrEmpty(districtCode)) {
            location += "and a1.district_code = '" + districtCode + "'";
        }
        if (!StringUtils.isNullOrEmpty(wardCode)) {
            location += "and a1.ward_code = '" + wardCode + "'";
        }
        return location + "\n";
    }

    @Contract(pure = true)
    private @NotNull String addStatus(String status) {
        var statusIn = "";
        if ("CANCEL_OR_REJECT".equals(status)) {
            statusIn = "'CANCEL', 'REJECT'";
        } else statusIn = "'" + status + "'";
        return "and os4.status in (" + statusIn + ")\n";

    }

    private @NotNull String addTextSearch(@NotNull String input) {
        var textSearch = input.trim().toLowerCase(Locale.ROOT);
        return "and (lower(coalesce(concat(u1.first_name, ' ', u1.last_name), '')) like '%" + textSearch + "%'    \n" + "or lower(coalesce(concat(a1.exact, ', ', a1.combination), '')) like '%" + textSearch + "%'         \n" + "or lower(u1.phone) like '%" + textSearch + "%')";
    }

    @Contract(pure = true)
    private @NotNull String addTime(@NotNull String time) {
        var result = "and ";
        switch (time) {
            case WsConst.TimeRanges.DAY:
                result += "date(os3.orderDate) = curdate()";
                break;
            case WsConst.TimeRanges.WEEK:
                result += "week(date(os3.orderDate)) = week(curdate())";
                break;
            case WsConst.TimeRanges.MONTH:
                result += "month(date(os3.orderDate)) = month(curdate())";
                break;
            default:
                break;
        }
        return result + "\n";

    }

    @Contract(pure = true)
    private String addOrderField(@NotNull String sortField) {
        var orderField = "";
        switch (sortField) {
            case "customer":
                orderField = "u1.last_name";
                break;
            case "phone":
                orderField = "u1.phone";
                break;
            case "orderDate":
                orderField = "os3.orderDate";
                break;
            case "address":
                orderField = "coalesce(concat(a1.exact, ', ', a1.combination), '')";
                break;
            case "total":
                orderField = "(select coalesce(sum(od1.qty * od1.price), 0) from order_detail od1)";
                break;
            case "status":
                orderField = "os4.status";
                break;
            default:
                orderField = "os3.orderDate";
        }
        return orderField;
    }


}
