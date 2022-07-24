package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.discount.create.DiscountDto;
import com.ws.masterserver.dto.admin.discount.prerequisite.PrerequisiteType01;
import com.ws.masterserver.dto.admin.discount.prerequisite.PrerequisiteType02;
import com.ws.masterserver.dto.admin.discount.type.DiscountTypeValue01;
import com.ws.masterserver.dto.admin.discount.type.DiscountTypeValue02;
import com.ws.masterserver.dto.admin.discount.type.DiscountTypeValue03;
import com.ws.masterserver.entity.DiscountCategoryEntity;
import com.ws.masterserver.entity.DiscountCustomerTypeEntity;
import com.ws.masterserver.entity.DiscountEntity;
import com.ws.masterserver.entity.DiscountProductEntity;
import com.ws.masterserver.service.DiscountService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.enums.*;
import com.ws.masterserver.utils.validator.AuthValidator;
import com.ws.masterserver.utils.validator.admin.AdminDiscountValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountServiceImpl implements DiscountService {
    private final WsRepository repository;
    private static final String SPACE = " ";

    @Override
    @Transactional
    public Object create(CurrentUser currentUser, DiscountDto payload) {
        log.info("DiscountServiceImpl create() with payload: {}", JsonUtils.toJson(payload));
        AuthValidator.checkAdmin(currentUser);
        AdminDiscountValidator.validCreateDtoData(payload);
        AdminDiscountValidator.validCreateDtoConstraint(payload, repository);

        var discount = new DiscountEntity();
        var discountId = UidUtils.generateUid();
        discount.setId(discountId);
        discount.setCode(payload.getCode().trim());

        //type
        var type = DiscountTypeEnums.valueOf(payload.getType());
        discount.setType(type.name());
        this.saveTypeValue(payload, discount);

        /**
         * ap dung cho
         * */
        var applyType = ApplyTypeEnums.valueOf(payload.getApplyType());
        //5. applyType
        discount.setApplyType(applyType.name());
        this.saveApplyTypeValue(payload, discountId, applyType);

        /**
         * Điều kiện áp dụng
         * */
        var prerequisiteType = DiscountPrerequisiteTypeEnums.valueOf(payload.getPrerequisiteType());
        discount.setPrerequisiteType(prerequisiteType.name());
        this.savePrerequisiteValue(payload, discount, prerequisiteType);

        /**
         * Nhóm khách hàng
         * */
        var customerType = DiscountCustomerTypeEnums.valueOf(payload.getCustomerType());
        discount.setCustomerType(customerType.name());
        this.saveDiscountCustomerType(payload, discountId, customerType);
        //gioi han su dung
        /**
         * giới hạn số lần mã giảm giá được áp dụng
         */
        discount.setUsageLimit(Long.valueOf(payload.getUsageLimit()));

        /**
         * Giói hạn mỗi khách hàng chỉ được sử dụng mã này 1 lần hay không(Kiểm tra bằng email) ?
         */
        discount.setOncePerCustomer(payload.getOncePerCustomer());
        //mặc định trạng thái = chưa diễn ra
        var status = DiscountStatusEnums.PENDING;

        /**
         * Thời gian
         */
        var startStr = payload.getStartDate() + SPACE + payload.getStartTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateUtils.F_DDMMYYYYHHMM);
        var start = LocalDateTime.parse(startStr, dateTimeFormatter);
        discount.setStartDate(DateUtils.localDateTimeToDate(start));

        /**
         * Nếu ngày bắt đầu < hien tai => đang diễn ra
         * */
        if (start.isBefore(LocalDateTime.now())) {
            status = DiscountStatusEnums.ACTIVE;
        }
        if (Boolean.TRUE.equals(payload.getHasEndsDate())) {
            var endStr = payload.getEndDate() + " " + payload.getEndTime();
            var end = LocalDateTime.parse(endStr, dateTimeFormatter);
            //13: endDate
            discount.setEndDate(DateUtils.localDateTimeToDate(end));

            /**
             * Nếu ngày kết thúc < hiện tại => đã hết hạn
             * */
            if (end.isBefore(LocalDateTime.now())) {
                status = DiscountStatusEnums.DE_ACTIVE;
            }
        }
        discount.setStatus(status.name());
        discount.setIsApplyAcross(payload.getIsApplyAcross());
        this.setDes(discount, payload);
        log.info("DiscountServiceImpl create() discount before save: {}", JsonUtils.toJson(discount));
        repository.discountRepository.save(discount);
        log.info("DiscountServiceImpl create() discount after save: {}", JsonUtils.toJson(discount));
        return ResData.ok(discount.getId());
    }

    private void setDes(DiscountEntity discount, DiscountDto payload) {
        /**
         * vd: Giảm 22% tối đa 123₫ cho toàn bộ sản phẩm • Một mã trên mỗi khách hàng
         * */
        var applyTypeIdsSize = payload.getApplyTypeIds().size();
        List<String> desList = new ArrayList<>();
        var typeStr = "";
        var type = DiscountTypeEnums.valueOf(payload.getType());
        switch (type) {
            case PERCENT:
                var dto1 = (DiscountTypeValue01) payload.getTypeValue();
                typeStr = "Giảm" + SPACE + dto1.getPercentageValue() + "%";
                if (!StringUtils.isNullOrEmpty(dto1.getValueLimitAmount())) {
                    typeStr += SPACE + MoneyUtils.format(dto1.getValueLimitAmount()) + "VND";
                }
                typeStr = getApplyTypeStr(payload, applyTypeIdsSize, typeStr);
                break;
            case PRICE:
                var dto2 = (DiscountTypeValue02) payload.getTypeValue();
                typeStr = "Giảm" + SPACE + MoneyUtils.format(dto2.getAmountValue()) + "VND";
                typeStr = getApplyTypeStr(payload, applyTypeIdsSize, typeStr);
                break;
            case SHIP:
                var dto3 = (DiscountTypeValue03) payload.getTypeValue();
                typeStr = "Miễn phí vận chuyển";
                if (!StringUtils.isNullOrEmpty(dto3.getValueLimitAmount())) {
                    typeStr += SPACE + "tối đa" + SPACE + MoneyUtils.format(dto3.getValueLimitAmount()) + "VND";
                }
                if (!StringUtils.isNullOrEmpty(dto3.getMaximumShippingRate())) {
                    typeStr += SPACE + "cho đơn hàng có phí vận chuyện dưới" + SPACE + MoneyUtils.format(dto3.getMaximumShippingRate()) + "VND";
                }
                var provinceSelection = DiscountProvinceSelectionEnums.valueOf(dto3.getProvinceSelection());
                switch (provinceSelection) {
                    case ALL:
                        typeStr += SPACE + "với tất cả tỉnh thành";
                        break;
                    case SELECTION:
                        typeStr += SPACE + "với" + SPACE + dto3.getProvinceIds().size() + SPACE + "tỉnh/thành";
                        break;
                    default:
                        break;
                }
        }
        desList.add(typeStr);

        /**
         *  điều kiện áp dụng
         * */
        var prerequisiteStr = "Điều kiện áp dụng:";
        var prerequisiteType = DiscountPrerequisiteTypeEnums.valueOf(payload.getPrerequisiteType());
        switch (prerequisiteType) {
            case NONE:
                prerequisiteStr += "Không";
                break;
            case MIN_TOTAL:
                prerequisiteStr += prerequisiteType.getName() + ":" + SPACE + MoneyUtils.format(payload.getPrerequisiteTypeValue()) + "VND";
                break;
            case MIN_QTY:
                prerequisiteStr += prerequisiteType.getName() + ":" + SPACE + Long.valueOf(payload.getPrerequisiteTypeValue());
                break;
            default:
                break;
        }
        desList.add(prerequisiteStr);

        var customerTypeStr = "Áp dụng cho: ";
        var customerType = DiscountCustomerTypeEnums.valueOf(payload.getCustomerType());
        var customerTypeName = customerType.getName();
        var customerTypeIdSize = payload.getCustomerIds().size();
        switch (customerType) {
            case ALL:
                customerTypeStr += customerTypeName;
                break;
            case GROUP:
            case CUSTOMER:
                customerTypeStr += customerTypeIdSize + SPACE + customerTypeName;
                break;
            default:
                break;
        }
        desList.add(customerTypeStr);

        if (payload.getHasUsageLimit()) {
            var usageLimitStr = "Mã được phép sử dụng: ";
            usageLimitStr += Long.valueOf(payload.getUsageLimit()) + SPACE + "lần";
            desList.add(usageLimitStr);
        }

        if (payload.getOncePerCustomer()) {
            desList.add("Mỗi khách hàng chỉ được sử dụng 1 lần");
        }

        /**
         * Thời gian
         * */
        var timeStr = "Thời gian:" + SPACE + DateUtils.toStr(discount.getStartDate(), DateUtils.F_DDMMYYYYHHMM);
        if (discount.getEndDate() != null) {
            timeStr += SPACE + "đến" + SPACE + DateUtils.toStr(discount.getEndDate(), DateUtils.F_DDMMYYYYHHMM);
        }
        desList.add(timeStr);
        discount.setDes(String.join(" | ", desList));
    }

    private String getApplyTypeStr(DiscountDto payload, int applyTypeIdsSize, String typeStr) {
        var applyType = ApplyTypeEnums.valueOf(payload.getApplyType());
        switch (applyType) {
            case ALL_PRODUCT:
                typeStr += SPACE + "với tất cả sản phẩm";
                break;
            case CATEGORY:
                typeStr += SPACE + "với" + SPACE + applyTypeIdsSize + SPACE + "loại sản phẩm";
                break;
            case PRODUCT:
                typeStr += SPACE + "với" + SPACE + applyTypeIdsSize + SPACE + "sản phẩm";
                break;
            default:
                break;
        }
        return typeStr;
    }

    private void saveTypeValue(DiscountDto payload, DiscountEntity discount) {
        Object typeValues = payload.getTypeValue();
        discount.setTypeValue(JsonUtils.toJson(typeValues));
    }

    private void savePrerequisiteValue(DiscountDto payload, DiscountEntity discount, DiscountPrerequisiteTypeEnums prerequisiteType) {
        if (!DiscountPrerequisiteTypeEnums.NONE.equals(prerequisiteType)) {
            //7: prerequisiteValue
            switch (prerequisiteType) {
                case MIN_QTY:
                    discount.setPrerequisiteValue(JsonUtils.toJson(PrerequisiteType01.builder()
                            .minimumSaleTotalPrice(Long.valueOf(payload.getPrerequisiteTypeValue()))
                            .build()));
                    break;
                case MIN_TOTAL:
                    discount.setPrerequisiteValue(JsonUtils.toJson(PrerequisiteType02.builder()
                            .minimumQuantity(Long.valueOf(payload.getPrerequisiteTypeValue()))
                            .build()));
                    break;
                default:
                    break;
            }
        }
    }

    private void saveDiscountCustomerType(DiscountDto payload, String discountId, DiscountCustomerTypeEnums customerType) {
        if (!DiscountCustomerTypeEnums.ALL.equals(customerType)) {
            payload.getCustomerIds().forEach(o ->
                    repository.discountCustomerTypeRepository.save(DiscountCustomerTypeEntity.builder()
                            .id(UidUtils.generateUid())
                            .discountId(discountId)
                            .customerTypeId(o)
                            .build()));
        }
    }

    private void saveApplyTypeValue(DiscountDto payload, String discountId, ApplyTypeEnums applyType) {
        payload.getApplyTypeIds().forEach(o -> {
            switch (applyType) {
                case CATEGORY:
                    repository.discountCategoryRepository.save(DiscountCategoryEntity.builder()
                            .id(UidUtils.generateUid())
                            .discountId(discountId)
                            .categoryId(o)
                            .build());
                    break;
                case PRODUCT:
                    repository.discountProductRepository.save(DiscountProductEntity.builder()
                            .id(UidUtils.generateUid())
                            .discountId(discountId)
                            .productId(o)
                            .build());
                    break;
                default:
                    break;
            }
        });
    }
}
