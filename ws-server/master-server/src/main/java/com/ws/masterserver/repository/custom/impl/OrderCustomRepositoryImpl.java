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
import javax.persistence.Query;
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
        var sql = "select o1.id                                                            as id,\n" +
                "       o1.code                                                          as code,\n" +
                "       u1.gender                                                        as customer_gender,\n" +
                "       trim(concat(u1.first_name, ' ', u1.last_name))                   as customer_name,\n" +
                "       u1.phone                                                         as customer_phone,\n" +
                "       os2.created_date                                                 as created_date,\n" +
                "       concat(a1.exact, ', ', a1.combination)                           as address,\n" +
                "       o1.ship_price                                                    as ship_price,\n" +
                "       od2.od1_total                                                    as shop_price,\n" +
                "       o1.note                                                          as note,\n" +
                "       o1.\"type\"                                                        as \"type\",\n" +
                "       o1.payed                                                         as payed,\n" +
                "       os5.status                                                       as status,\n" +
                "       os5.created_date                                                 as status_date,\n" +
                "       os5.note                                                         as status_note,\n" +
                "       trim(concat(status_user.first_name, ' ', status_user.last_name)) as status_user_name,\n" +
                "       status_user.\"role\"                                               as status_user_role,\n" +
                "       st1.name                                                         as ship_type_name\n" +
                "from orders o1\n" +
                "         left join address a1 on\n" +
                "    a1.id = o1.address_id\n" +
                "         left join (\n" +
                "    select os1.order_id     as order_id,\n" +
                "           os1.created_date as created_date\n" +
                "    from order_status os1\n" +
                "    where os1.status = 'PENDING') os2 on\n" +
                "    os2.order_id = o1.id\n" +
                "         left join users u1 on\n" +
                "    u1.id = o1.user_id\n" +
                "         left join (\n" +
                "    select od1.order_id             as od1_order_id,\n" +
                "           sum(od1.qty * od1.price) as od1_total\n" +
                "    from order_detail od1\n" +
                "    group by od1.order_id) od2 on\n" +
                "    od2.od1_order_id = o1.id\n" +
                "         left join (\n" +
                "    select os3.order_id          as os3_order_id,\n" +
                "           max(os3.created_date) as os3_created_date\n" +
                "    from order_status os3\n" +
                "    group by os3.order_id) os4 on\n" +
                "    os4.os3_order_id = o1.id\n" +
                "         left join order_status os5 on\n" +
                "    os5.created_date = os4.os3_created_date\n" +
                "         left join users status_user on\n" +
                "    status_user.id = os5.created_by\n" +
                "         left join ship_type st1 on\n" +
                "    o1.ship_type_id = st1.id\n" +
                "where 1 = 1\n";
        if (!StringUtils.isNullOrEmpty(req.getStatus())) {
            sql += "and os5.status = '" + req.getStatus() + "'\n";
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
                    sql += "and os2.created_date::date = current_date::date\n";
                    break;
                case "week":
                    sql += "and extract('week' from os2.created_date) = extract('week' from current_date)\n";
                    break;
                case "month":
                    sql += "and extract('month' from os2.created_date) = extract('month' from current_date)\n";
                    break;
                default:
                    break;
            }
        }
        if (!StringUtils.isNullOrEmpty(req.getTextSearch())) {
            var textSearch = "'%" + req.getTextSearch().trim().toUpperCase(Locale.ROOT) + "%'";
            sql += "and (upper(as customer_name) like " + textSearch + " or\n" +
                    "u1.phone like " + textSearch + ")\n";
        }
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
                .customer(UserUtils.getCustomerInfo(JpaUtils.getBoolean(obj[2]), JpaUtils.getString(obj[3])))
                .phone(JpaUtils.getString(obj[4]))
                .orderDate(DateUtils.toStr(JpaUtils.getDate(obj[5]), DateUtils.DATE_TIME_FORMAT_VI_OUTPUT))
                .address(JpaUtils.getString(obj[6]))
                .total(MoneyUtils.format(OrderUtils.getTotal(JpaUtils.getLong(obj[7]), JpaUtils.getLong(obj[8]), repository.orderPromotionRepository.findByOrderId(JpaUtils.getString(obj[0])))))
                .note(JpaUtils.getString(obj[9]))
                .type(OrderTypeUtils.getOrderTypeStr(JpaUtils.getString(JpaUtils.getString(obj[10])), JpaUtils.getBoolean(obj[11])))
                .status(OrderUtils.getStatusCombination(JpaUtils.getString(obj[12]), JpaUtils.getDate(obj[13]), JpaUtils.getString(obj[15]), JpaUtils.getString(obj[16])))
                .build()).collect(Collectors.toList());
        return PageData.setResult(
                orderRes,
                req.getPageReq().getPage(),
                req.getPageReq().getPageSize(),
                totalElements);
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
