package com.ws.master_service.utils.common;

import com.ws.master_service.utils.constants.enums.PromotionTypeEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PromotionTypeUtils {

    public static String getName(String code) {
        try {
            var promotionTypeEnum = PromotionTypeEnum.valueOf(PromotionTypeEnum.class, code);
            return promotionTypeEnum.getName();
        } catch (Exception e) {
            log.error("getName: {}", e.getMessage());
            return "";
        }
    }

}
