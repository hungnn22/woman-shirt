package com.ws.masterserver.repository.custom.impl;

import com.ws.masterserver.dto.admin.order.request.OrderReq;
import com.ws.masterserver.dto.admin.order.response.OrderRes;
import com.ws.masterserver.dto.admin.order.response.StatusDto;
import com.ws.masterserver.dto.admin.order.response.UserDto;
import com.ws.masterserver.repository.custom.OrderCustomRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.WsConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
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
        var prefix = "select\n";
        var distinct = "distinct\n";
        var select = "" +
                "                o1.id                                                  as orderId,                 \n" +
                "                os3.orderDate                                          as orderDate,               \n" +
                "                (select coalesce(sum(od1.qty * od1.price), 0)\n" +
                "                 from order_detail od1)                                as total,                   \n" +
                "                coalesce(concat(a1.exact, ', ', a1.combination), '')   as address,                 \n" +
                "                o1.note                                                as note,                    \n" +
                "                os4.status                                             as statusNow,               \n" +
                "                os4.note                                               as statusNote,              \n" +
                "                os4.created_date                                       as statusDate,              \n" +
                "                u2.id                                                  as statusUserId,            \n" +
                "                u2.first_name                                          as statusUserFirstName,     \n" +
                "                u2.last_name                                           as statusUserLastName,      \n" +
                "                u2.gender                                              as statusUserGender,        \n" +
                "                u2.phone                                               as statusUserPhone,         \n" +
                "                u2.role                                                as statusUserRole,          \n" +
                "                coalesce(concat(u2.first_name, ' ', u2.last_name), '') as statusUserCombination,   \n" +
                "                o1.user_id                                             as cusId,                   \n" +
                "                u1.first_name                                          as cusFirstName,            \n" +
                "                u1.last_name                                           as cusLastName,             \n" +
                "                u1.gender                                              as cusGender,               \n" +
                "                u1.phone                                               as cusPhone,                \n" +
                "                u1.role                                                as cusRole,                 \n" +
                "                coalesce(concat(u1.first_name, ' ', u1.last_name), '') as cusCombination           \n";

        var fromAndJoins = "from orders o1                                                                                  \n" +
                "         left join     users u1                                                        on o1.user_id = u1.id                                   \n" +
                "         left join     address a1                                                      on o1.address_id = a1.id                                \n" +
                "         left join     order_detail od1                                                on o1.id = od1.order_id                                 \n" +
                "         left join     order_status os1                                                on o1.id = os1.order_id and os1.status <> 'PROCESSING'  \n" +
                "         right join    (select o2.created_date as orderDate                                                                                    \n" +
                "                           from orders o2                                                                                                      \n" +
                "                              left join order_status os2                               on o2.id = os2.order_id and os2.status = 'PENDING'      \n" +
                "                       group by os2.order_id) as os3                                   on o1.created_date = os3.orderDate                      \n" +
                "         right join    (select os3.order_id,                                                                                                   \n" +
                "                            os3.status,                                                                                                        \n" +
                "                            os3.created_date,                                                                                                  \n" +
                "                            os3.note,                                                                                                          \n" +
                "                            os3.created_by                                                                                                     \n" +
                "                       from order_status os3                                                                                                   \n" +
                "                       where os3.created_date = (select max(os1.created_date)                                                                  \n" +
                "                                               from order_status os1                                                                           \n" +
                "                                               where os3.order_id = os1.order_id                                                               \n" +
                "                                               group by os3.order_id)) as os4          on os4.order_id = o1.id                                 \n" +
                "         left join     users u2                                                        on os4.created_by = u2.id                               \n";

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

        var responses = objects.stream().map(obj -> OrderRes.builder()
                .id(JpaUtils.getString(obj[0]))
                .orderDate(DateUtils.toStr((JpaUtils.getDate(obj[1])), DateUtils.F_VI))
                .total(JpaUtils.getLong(obj[2]))
                .totalFmt(MoneyUtils.format(JpaUtils.getLong(obj[2])))
                .address(JpaUtils.getString(obj[3]))
                .note(JpaUtils.getString(obj[4]))
                .status(StatusDto.builder()
                        .status(StatusUtils.getFromStr(JpaUtils.getString(obj[5])))
                        .note(JpaUtils.getString(obj[6]))
                        .createdDate(DateUtils.toStr(JpaUtils.getDate(obj[7]), DateUtils.F_VI))
                        .combination(OrderUtils.getStatusCombination(JpaUtils.getString(obj[5]),
                                JpaUtils.getDate(obj[7]),
                                JpaUtils.getString(obj[14]),
                                JpaUtils.getString(obj[13])))
                        .user(UserDto.builder()
                                .id(JpaUtils.getString(obj[8]))
                                .firstName(JpaUtils.getString(obj[9]))
                                .lastName(JpaUtils.getString(obj[10]))
                                .gender(JpaUtils.getBoolean(obj[11]))
                                .phone(JpaUtils.getString(obj[12]))
                                .role(RoleUtils.getRoleFromStr(JpaUtils.getString(obj[13])))
                                .combination(UserUtils.getCombination(JpaUtils.getString(obj[14]),
                                        JpaUtils.getString(obj[9]),
                                        JpaUtils.getString(obj[10]),
                                        JpaUtils.getBoolean(obj[11])))
                                .build())
                        .build())
                .customer(UserDto.builder()
                        .id(JpaUtils.getString(obj[15]))
                        .firstName(JpaUtils.getString(obj[16]))
                        .lastName(JpaUtils.getString(obj[17]))
                        .gender(JpaUtils.getBoolean(obj[18]))
                        .phone(JpaUtils.getString(obj[19]))
                        .role(RoleUtils.getRoleFromStr(JpaUtils.getString(obj[20])))
                        .combination(UserUtils.getCombination(JpaUtils.getString(obj[21]),
                                JpaUtils.getString(obj[16]),
                                JpaUtils.getString(obj[17]),
                                JpaUtils.getBoolean(obj[18])))
                        .build())
                .build()).collect(Collectors.toList());

        return new PageData<>(
                responses,
                req.getPageReq().getPage(),
                req.getPageReq().getPageSize(),
                totalElements,
                WsCode.OK
        );
    }

    private String addLocation(String provinceCode, String districtCode, String wardCode) {
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

    private String addStatus(String status) {
        var statusIn = "";
        if ("CANCEL_OR_REJECT".equals(status)) {
            statusIn = "'CANCEL', 'REJECT'";
        } else statusIn = "'" + status + "'";
        return "and os4.status in (" + statusIn + ")\n";

    }

    private String addTextSearch(String input) {
        var textSearch = input.trim().toLowerCase(Locale.ROOT);
        return "and (lower(coalesce(concat(u1.first_name, ' ', u1.last_name), '')) like '%" + textSearch + "%'    \n" +
                "or lower(coalesce(concat(a1.exact, ', ', a1.combination), '')) like '%" + textSearch + "%'         \n" +
                "or lower(u1.phone) like '%" + textSearch + "%')";
    }

    private String addTime(String time) {
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

    private String addOrderField(String sortField) {
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
