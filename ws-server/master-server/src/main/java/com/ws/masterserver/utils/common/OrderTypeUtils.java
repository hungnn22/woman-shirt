package com.ws.masterserver.utils.common;

import com.ws.masterserver.dto.admin.order.search.TypeDto;
import com.ws.masterserver.utils.constants.enums.OrderTypeEnum;

/**
 * @author hungnn22
 */
public class OrderTypeUtils {
    public static TypeDto getOrderTypeDto(String input) {
        try {
            var orderType = OrderTypeEnum.valueOf(input);
            return TypeDto.builder()
                    .code(orderType.name())
                    .tittle(orderType.getTittle())
                    .name(orderType.getName())
                    .build();
        } catch (Exception e) {
            return new TypeDto();
        }
    }

    public static String getOrderTypeStr(String input) {
        try {
            var orderType = OrderTypeEnum.valueOf(input);
            return TypeDto.builder()
                    .code(orderType.name())
                    .tittle(orderType.getTittle())
                    .name(orderType.getName())
                    .build().getName();
        } catch (Exception e) {
            return new TypeDto().getName();
        }
    }
}
