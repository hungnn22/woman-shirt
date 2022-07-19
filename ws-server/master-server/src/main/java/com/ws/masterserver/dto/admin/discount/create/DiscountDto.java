package com.ws.masterserver.dto.admin.discount.create;

import com.ws.masterserver.dto.admin.discount.type.DiscountTypeDto;
import com.ws.masterserver.utils.constants.enums.ApplyTypeEnum;
import com.ws.masterserver.utils.constants.enums.DiscountCustomerTypeEnums;
import com.ws.masterserver.utils.constants.enums.PrerequisiteTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class DiscountDto {
    private String id;

    //mã km
    private String code;

    //loại km
    private String type;

    //giá trị loại km
    private DiscountTypeDto typeValues;

    //áp dụng cho
    private ApplyTypeEnum applyType;

    //dsasch áp dụng
    private List<String> applyTypeIds;

    //được tính 1 lần trên mỗi đơn hàng hhay k
    private Boolean appliesAcross;

    //loại điều kiện áp dụng
    private PrerequisiteTypeEnum prerequisiteType;

    //điều kiện áp dụng
    private String prerequisiteTypeValue;

    //loại KH áp dụng
    private DiscountCustomerTypeEnums customerType;

    //danh sách KH
    private List<String> customerTypeIds;

    //giới hạn số lần mã giảm giá được sử dụng hay k
    private Boolean hasUsageLimit;

    //số lần sử dụng giới hạn
    private String usageLimit;

    //giói hạn mỗi KH chỉ dc dùng 1 mã hay k
    private Boolean oncePerCustomer;

    //ngày bắt đầu
    private String startDate;

    //thời gian bắt đầu
    private String startTime;

    //có kết thức k
    private Boolean hasEndsDate;

    //ngày kết thúc
    private String endDate;

    //thời gian kết thúc
    private String endTime;


}
