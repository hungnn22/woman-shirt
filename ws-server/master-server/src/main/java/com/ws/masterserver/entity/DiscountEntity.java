package com.ws.masterserver.entity;

import com.ws.masterserver.utils.constants.enums.ApplyTypeEnum;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "apply_code")
    private ApplyTypeEnum applyCode;

    @Column(name = "apply_value")
    private String applyValue;

    /**
     * Loại khách hàng áp dụng
     */
    @Column(name = "customer_type_id")
    private String customerTypeId;

    /**
     * giới hạn số lần mã giảm giá được áp dụng hay không?
     */
    @Column(name = "has_usage_limit")
    private Boolean hasUsageLimit;

    /**
     * Số lần mã giám giá được áp dụng
     */
    @Column(name = "usage_limit")
    private Long usageLimit;

    /**
     * Giói hạn mỗi khách hàng chỉ được sử dụng mã này 1 lần hay không(Kiểm tra bằng email)
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

    @Column(name = "apply_type_id")
    private String applyTypeId;


    ////Loai KM theo %
    /**
     * % khuyến mãi(max 100%)
     */
    @Column(name = "percentage_value")
    private Integer percentageValue;

    /**
     * Giá trị giảm tối đa
     */
    @Column(name = "value_limit_amount")
    private Long valueLimitAmount;

    //Điều kiện áp dụng khuyến mãi
    /**
     * loại điều kiện áp dụng khuyết mãi
     */
    @Column(name = "prerequisite_type_code")
    private PrerequisiteTypeEnum prerequisiteTypeCode;

    /**
     * Giá trị để thỏa màn đièu kiện áp dụng khuyến mãi
     */
    @Column(name = "prerequisite_type_value")
    private Long prerequisiteTypeValue;

}
