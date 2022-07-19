package com.ws.masterserver.entity;

import com.ws.masterserver.utils.constants.enums.ApplyTypeEnum;
import com.ws.masterserver.utils.constants.enums.DiscountCustomerTypeEnums;
import com.ws.masterserver.utils.constants.enums.DiscountStatusEnums;
import com.ws.masterserver.utils.constants.enums.PrerequisiteTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "discount")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class DiscountEntity {
    @Id
    private String id;

    private String name;

    private String code;

    /**
     * Áp dụng cho
     */
    //Loại áp dụng: loại sản phẩm / sản phẩm
    @Enumerated(EnumType.STRING)
    @Column(name = "apply_code")
    private ApplyTypeEnum applyCode;

    /**
     * Mã giảm giá sẽ được tính 1 lần trên mỗi đơn hàng hay không:(khong tinh th giam gia van chuyen)
     * true: tính theo cả đơn hàng
     * false: Tính theo từng sản phẩm trong đơn hàng
     */
    private Boolean isApplyAcross;

    /**
     * Loại khách hàng áp dụng
     */

    //mã loại khách hàng áp dụng KM
    @Column(name = "customer_type_code")
    @Enumerated(EnumType.STRING)
    private DiscountCustomerTypeEnums customerTypeCode;

    /**
     * giới hạn số lần mã giảm giá được áp dụng hay không?
     */
    @Column(name = "has_usage_limit")
    private Boolean hasUsageLimit;

    /**
     * Nếu giới hạn số lần mã giảm giá được áp dụng
     * => Số lần mã giám giá được áp dụng
     */
    @Column(name = "usage_limit")
    private Long usageLimit;

    /**
     * Giói hạn mỗi khách hàng chỉ được sử dụng mã này 1 lần hay không(Kiểm tra bằng email) ?
     */
    @Column(name = "once_per_customer")
    private Boolean oncePerCustomer;

    /**
     * Ngày bắt đầu
     */
    private LocalDate startDate;

    /**
     * Thời gian bắt đầu
     */
    private LocalTime startTime;

    /**
     * Ngày kết thúc
     */
    private LocalDate endDate;

    /**
     * Thời gian kết thúc
     */
    private LocalTime endTime;

    //Điều kiện áp dụng khuyến mãi
    /**
     * loại điều kiện áp dụng khuyết mãi
     */
    @Column(name = "prerequisite_type_code")
    @Enumerated(EnumType.STRING)
    private PrerequisiteTypeEnum prerequisiteTypeCode;

    /**
     * Giá trị để thỏa màn đièu kiện áp dụng khuyến mãi
     * Tổng giá trị đơn hàng tối thiệu / Số lượng sản phẩm tối thiểu
     */
    @Column(name = "prerequisite_type_value")
    private String prerequisiteTypeValue;

    /**
     * trạng thái
     * Ngung áp dụng/ đang áp dụng / chua ap dung
     */
    @Enumerated(EnumType.STRING)
    private DiscountStatusEnums status;
}
