package com.ws.masterserver.utils.common;

import com.ws.masterserver.dto.admin.order.search.PromotionDto;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.PromotionTypeEnum;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.constants.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Slf4j
public class OrderUtils {
    /**
     * @param statusStr       trạng thái hiện tại
     * @param createdDate     thời gian tạo trạng thái hiện tại
     * @param combinationName tên người tạo ra trạng thái = firstName + lastName
     * @param roleStr         role người tạo ra trạng thái
     * @return vd: Đơn hàng đã được chấp nhận bởi Admin lúc 10:22:23 12/02/2022
     * @author hungnn22
     */
    public static String getStatusCombination(String statusStr, Date createdDate, String combinationName, String roleStr) {
        var result = "";
        try {
            var status = StatusEnum.valueOf(statusStr);
            var dateFmt = DateUtils.toStr(createdDate, DateUtils.F_VI);
            var role = RoleEnum.valueOf(roleStr);

            switch (status) {
                case PENDING:
                    result = "Đang chờ xứ lý";
                    break;
                case CANCEL:
                    result = "Đã bị hủy bới khách hàng vào lúc " + dateFmt;
                    break;
                case REJECT:
                    result = "Đã bị từ chối bởi " + role.getName() + " " + combinationName + " vào lúc " + dateFmt;
                    break;
                case ACCEPT:
                    result = "Được chấp nhận bởi " + role.getName() + " " + combinationName + " vào lúc: " + dateFmt;
                    break;
                default:
                    throw new WsException(WsCode.INTERNAL_SERVER);
            }
        } catch (Exception e) {
            log.error("getStatusCombination error: {}", e.getMessage());
        }

        return result;
    }

    /**
     * @param defaultTotal Tổng tiền mặc định.
     * @param shipPrice    Tiền ship
     * @param promotions   Danh sách khuyến mãi
     * @apiNote Dựa vào loại khuyến mãi và % giảm giá sẽ trừ đi vào giá
     * @return giá phải trả
     */
    public static Long getTotal(Long defaultTotal, Long shipPrice, List<PromotionDto> promotions) {
        try {
            if (!promotions.isEmpty()) {
                for (var promotion : promotions) {
                    var type = PromotionTypeEnum.valueOf(promotion.getTypeCode());
                    switch (type) {
                        /**
                         * Loại 1: SHIP
                         * Trừ tiền ship
                         * */
                        case TYPE1:
                            defaultTotal -= shipPrice * promotion.getPercentDiscount().longValue() / 100;
                            break;
                        /**
                         * Loại 2: Mua sắm
                         * Trừ tiền sản phẩm
                         * */
                        case TYPE2:
                            defaultTotal -= defaultTotal * promotion.getPercentDiscount().longValue() / 100;
                            break;
                        default:
                            throw new WsException(WsCode.INTERNAL_SERVER);
                    }
                }
            }
        } catch (Exception e) {
            log.error("getTotal: {}", e.getMessage());
        } finally {
            return defaultTotal;
        }

    }
}
