package com.ws.master_service.dto.admin.discount.type;

import lombok.Data;

import java.util.List;

@Data
public class DiscountTypeValue03 extends DiscountTypeDto {
        /**
         * Miễn phi tối đa
         */
        private String valueLimitAmount;

        /**
         * tỉnh/thành: all hoặc list danh sách tỉnh thành
         */
        private String provinceSelection;

        /**
         * Danh sách tỉnh thành được áp dụng
         */
        private List<String> provinceIds;

        /**
         * Có Áp dụng với phí vận chuyển dưới ${maximumShippingRate} hay không?
         */
        private Boolean hasMaximumShippingRate;

        /**
         * Phí vận chuyện tối đa để có thể áp dụng
         */
        private String maximumShippingRate;
}
