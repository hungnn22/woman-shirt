package com.ws.master_service.repository.custom.impl;

import com.ws.master_service.dto.admin.order.detail.DetailRes;
import com.ws.master_service.dto.admin.order.detail.ItemDto;
import com.ws.master_service.dto.admin.order.search.*;
import com.ws.master_service.repository.custom.OrderCustomRepository;
import com.ws.master_service.utils.base.WsException;
import com.ws.master_service.utils.base.WsRepository;
import com.ws.master_service.utils.base.rest.CurrentUser;
import com.ws.master_service.utils.base.rest.PageData;
import com.ws.master_service.utils.base.rest.PageReq;
import com.ws.master_service.utils.base.rest.ResData;
import com.ws.master_service.utils.common.*;
import com.ws.master_service.utils.constants.WsCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
        var res = DetailRes.builder().id(id);

        List<ItemDto> items = repository.orderDetailRepository.getItemList(id);
        if (!items.isEmpty()) {
            for (var obj : items) {
                obj.setPriceFmt(MoneyUtils.format(obj.getPrice()));
                obj.setTotalFmt(MoneyUtils.format(obj.getTotal()));
            }
        }
        res.items(items);

        var orderStatusList = repository.orderStatusRepository.findHistory(id);
        if (!orderStatusList.isEmpty()) {
            res.history(OrderUtils.getHistory(orderStatusList));
        }

        return new ResData<>(res.build(), WsCode.OK);
    }

}
