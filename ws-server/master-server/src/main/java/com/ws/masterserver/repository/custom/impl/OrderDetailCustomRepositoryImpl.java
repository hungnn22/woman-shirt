package com.ws.masterserver.repository.custom.impl;

import com.ws.masterserver.dto.admin.order.detail.CategoryDto;
import com.ws.masterserver.dto.admin.order.detail.ItemDto;
import com.ws.masterserver.dto.admin.order.detail.DetailRes;
import com.ws.masterserver.repository.custom.OrderDetailCustomRepository;
import com.ws.masterserver.utils.base.enum_dto.ColorDto;
import com.ws.masterserver.utils.base.enum_dto.MaterialDto;
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
    public PageData<DetailRes> findByOrderId4Admin(CurrentUser currentUser, String id) {
        var prefix = "select                                                    \n";
        var distinct = "distinct                                                \n";
        var select = "od1.id         as oId,\n" +
                "                od1.qty        as oQty,\n" +
                "                od1.price      as oPrice,\n" +
                "                po1.id         as poId,\n" +
                "                po1.color_id   as poColorId,\n" +
                "                c1.name        as cName,\n" +
                "                c1.hex         as cHex,\n" +
                "                po1.size       as poSize,\n" +
                "                po1.image      as poImage,\n" +
                "                po1.qty        as poQty,\n" +
                "                po1.price      as poPrice,\n" +
                "                p1.id          as pId,\n" +
                "                p1.name        as pName,\n" +
                "                p1.material_id as pMaterialId,\n" +
                "                m1.name        as mName,\n" +
                "                p1.type        as pType,\n" +
                "                p1.des         as pDes,\n" +
                "                p1.category_id as pCategoryId,\n" +
                "                c2.name        as c2Name,\n" +
                "                c2.des         as c2Des\n";
        var fromAndJoins = "from order_detail od1\n" +
                "         left join product_option po1 on po1.id = od1.product_option_id\n" +
                "         left join color c1 on po1.color_id = c1.id\n" +
                "         left join product p1 on po1.product_id = p1.id\n" +
                "         left join material m1 on p1.material_id = m1.id\n" +
                "         left join category c2 on p1.category_id = c2.id\n";

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
                objects.stream().map(obj -> {
                    Long total = JpaUtils.getLong(obj[1]) == null || JpaUtils.getLong(obj[2]) == null ? 0L : JpaUtils.getLong(obj[1]) * JpaUtils.getLong(obj[2]);
                    return DetailRes.builder()
                            .id(JpaUtils.getString(obj[0]))
                            .qty(JpaUtils.getLong(obj[1]))
                            .price(JpaUtils.getLong(obj[2]))
                            .priceFmt(JpaUtils.getString(MoneyUtils.format(JpaUtils.getLong(obj[2]))))
                            .total(total)
                            .totalFmt(MoneyUtils.format(total))
                            .item(ItemDto.builder()
                                    .id(JpaUtils.getString(obj[3]))
                                    .color(ColorDto.builder()
                                            .id(JpaUtils.getString(obj[4]))
                                            .name(JpaUtils.getString(obj[5]))
                                            .hex(JpaUtils.getString(obj[6]))
                                            .build())
                                    .size(SizeUtils.getSizeDto(JpaUtils.getString(obj[7])))
                                    .img(JpaUtils.getString(obj[8]))
                                    .qty(JpaUtils.getLong(obj[9]))
                                    .price(JpaUtils.getLong(obj[10]))
                                    .productId(JpaUtils.getString(obj[11]))
                                    .name(JpaUtils.getString(obj[12]))
                                    .material(MaterialDto.builder()
                                            .id(JpaUtils.getString(obj[13]))
                                            .name(JpaUtils.getString(obj[14]))
                                            .build())
                                    .type(TypeUtils.getTypeDto(JpaUtils.getString(obj[15])))
                                    .des(JpaUtils.getString(obj[16]))
                                    .des4Menu(HtmlUtils.convert4Menu(JpaUtils.getString(obj[16])))
                                    .category(CategoryDto.builder()
                                            .id(JpaUtils.getString(obj[17]))
                                            .name(JpaUtils.getString(obj[18]))
                                            .des(JpaUtils.getString(obj[19]))
                                            .build())
                                    .build())
                            .build();}).collect(Collectors.toList()),
                0,
                10000,
                totalElements,
                WsCode.OK
        );
    }
}
