package com.ws.masterserver.repository.custom.impl;

import com.ws.masterserver.dto.admin.order.response.detail.Item;
import com.ws.masterserver.dto.admin.order.response.detail.OrderDetailRes;
import com.ws.masterserver.repository.custom.OrderDetailCustomRepository;
import com.ws.masterserver.utils.base.enums.ColorDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hungnn22
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class OrderDetailCustomRepositoryImpl implements OrderDetailCustomRepository {

    private final EntityManager entityManager;

    @Override
    public PageData<OrderDetailRes> findByOrderId4Admin(CurrentUser currentUser, String id) {
        var prefix = "select                                                    \n";
        var distinct = "distinct                                                \n";
        var select = "" +
                "od1.id              as odId,                                   \n" +
                "od1.qty             as odQty,                                  \n" +
                "od1.price            as odPrice,                                \n" +
                "od1.qty * od1.price as odTotal,                                \n" +
                "od1.created_date    as odDate,                                 \n" +
                "po1.id              as poId,                                   \n" +
                "po1.color           as poColor,                                \n" +
                "po1.image           as poImg,                                  \n" +
                "po1.price           as poPrice,                                \n" +
                "po1.qty             as poQty,                                  \n" +
                "po1.size            as poSize,                                 \n" +
                "p1.id               as pId,                                    \n" +
                "p1.name             as pName,                                  \n" +
                "p1.material         as pMaterial,                              \n" +
                "p1.type             as pType,                                  \n" +
                "p1.des              as pDes,                                   \n" +
                "c1.name             as cName                                   \n";
        var fromAndJoins = "from order_detail od1                               \n" +
                "left join product_option po1 on od1.product_option_id = po1.id \n" +
                "left join product p1 on po1.product_id = p1.id                 \n" +
                "left join category c1 on c1.id = p1.category_id                \n";

        var where = "where od1.order_id = '" + id + "'\n";

        var order = "order by od1.created_date desc";

        var sql = prefix + distinct + select + fromAndJoins + where + order;

        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> objects = query.getResultList();

        var totalElements = Long.valueOf(objects.size());

        if (objects.isEmpty()) {
            return new PageData<>(true);
        }

        return new PageData<>(
                objects.stream().map(obj -> OrderDetailRes.builder()
                        .id(JpaUtils.getString(obj[0]))
                        .qty(JpaUtils.getLong(obj[1]))
                        .price(JpaUtils.getLong(obj[2]))
                        .priceFmt(MoneyUtils.format(JpaUtils.getLong(obj[2])))
                        .total(JpaUtils.getLong(obj[3]))
                        .totalFmt(MoneyUtils.format(JpaUtils.getLong(obj[3])))
                        .item(Item.builder()
                                .id(JpaUtils.getString(obj[5]))
                                .color(ColorUtils.getColorDto(JpaUtils.getString(obj[6])))
                                .img(JpaUtils.getString(obj[7]))
                                .price(JpaUtils.getLong(obj[8]))
                                .priceFmt(MoneyUtils.format(JpaUtils.getLong(obj[8])))
                                .qty(JpaUtils.getLong(obj[9]))
                                .size(SizeUtils.getSizeDto(JpaUtils.getString(obj[10])))
                                .productId(JpaUtils.getString(obj[11]))
                                .name(JpaUtils.getString(obj[12]))
                                .material(MaterialUtils.getMaterialDto(JpaUtils.getString(obj[13])))
                                .type(TypeUtils.getTypeDto(JpaUtils.getString(obj[14])))
                                .des(JpaUtils.getString(obj[15]))
                                .des4Menu(HtmlUtils.convert4Menu(JpaUtils.getString(obj[15])))
                                .category(JpaUtils.getString(obj[16]))
                                .build())
                        .build()).collect(Collectors.toList()),
                0,
                10000,
                totalElements,
                WsCode.OK
        );
    }
}
