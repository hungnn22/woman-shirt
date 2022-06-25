package com.ws.masterserver.utils.common;

import com.ws.masterserver.dto.admin.order.detail.PriceDto;
import com.ws.masterserver.dto.admin.order.detail.PromotionDto;
import com.ws.masterserver.dto.admin.order.detail.ResultDto;
import com.ws.masterserver.dto.admin.order.search.OptionDto;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.PromotionTypeEnum;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.constants.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.util.*;

@Slf4j
public class OrderUtils {

    private OrderUtils() {
    }

    /**
     * @param statusStr       trạng thái hiện tại
     * @param createdDate     thời gian tạo trạng thái hiện tại
     * @param combinationName tên người tạo ra trạng thái = firstName + lastName
     * @param roleStr         role người tạo ra trạng thái
     * @return vd: Đơn hàng đã được chấp nhận bởi Admin lúc 10:22:23 12/02/2022
     */
    public static String getStatusCombination(String statusStr, Date createdDate, String combinationName, String roleStr) {
        var result = "";
        try {
            var status = StatusEnum.valueOf(statusStr);
            var dateFmt = DateUtils.toStr(createdDate, DateUtils.F_DDMMYYYYHHMMSS);
            var role = RoleEnum.valueOf(roleStr);

            switch (status) {
                case PENDING:
                    result = "Đang chờ xứ lý";
                    break;
                case CANCEL:
                    result = "Đã bị hủy bới khách hàng vào lúc " + dateFmt;
                    break;
                case REJECT:
                    result = "Đã bị từ chối bởi " + role.getName() + " " + combinationName + " vào lúc: " + dateFmt;
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
     * @param shopPrice  Tổng tiền sản phẩm chưa trừ đi khuyến mãi sản phẩm.
     * @param shipPrice  Tiền ship
     * @param promotions Danh sách khuyến mãi
     * @return giá phải trả
     * @apiNote Dựa vào loại khuyến mãi và % giảm giá sẽ trừ đi vào giá
     */
    public static Long getTotal(Long shipPrice, Long shopPrice, List<PromotionDto> promotions) {
        try {
            /**
             * Nếu không có khuyến mãi thì = defaultTotal + shipPrice
             * */
            if (!promotions.isEmpty()) {
                for (var promotion : promotions) {
                    var type = PromotionTypeEnum.valueOf(promotion.getTypeCode());
                    switch (type) {
                        /**
                         * Loại 1: SHIP
                         * Trừ tiền ship
                         * */
                        case TYPE1:
                            shipPrice -= shipPrice * promotion.getPercentDiscount().longValue() / 100;
                            break;
                        /**
                         * Loại 2: Mua sắm
                         * Trừ tiền sản phẩm
                         * */
                        case TYPE2:
                            shopPrice -= shopPrice * promotion.getPercentDiscount().longValue() / 100;
                            break;
                        default:
                            throw new WsException(WsCode.INTERNAL_SERVER);
                    }
                }
            }
        } catch (Exception e) {
            log.error("getTotal: {}", e.getMessage());
        }
        return shopPrice + shipPrice;
    }

    public static ResultDto getResultDto(long shopPrice, Long shipPrice, List<PromotionDto> promotions) {
        var shipDiscount = 0L;
        var shopDiscount = 0l;

        if (!promotions.isEmpty()) {
            for (var promotion : promotions) {
                var type = PromotionTypeEnum.valueOf(promotion.getTypeCode());
                switch (type) {
                    case TYPE1:
                        shipDiscount += shipPrice * promotion.getPercentDiscount().longValue() / 100;
                        break;
                    case TYPE2:
                        shopDiscount += shopPrice * promotion.getPercentDiscount().longValue() / 100;
                        break;
                    default:
                        throw new WsException(WsCode.INTERNAL_SERVER);
                }

            }
        }

        var shipTotal = shipPrice - shipDiscount;
        var shopTotal = shopPrice - shopDiscount;

        var total = shipTotal + shopTotal;

        return ResultDto.builder()
                .ship(PriceDto.builder()
                        .name("Vận chuyển")
                        .price(MoneyUtils.format(shipPrice))
                        .discount(MoneyUtils.format(shipDiscount))
                        .total(MoneyUtils.format(shipTotal))
                        .build())
                .shop(PriceDto.builder()
                        .name("Mua sắm")
                        .price(MoneyUtils.format(shopPrice))
                        .discount(MoneyUtils.format(shopDiscount))
                        .total(MoneyUtils.format(shopTotal))
                        .build())
                .total(MoneyUtils.format(total))
                .build();
    }

    public static List<OptionDto> getOptions4Admin(String statusNow) {
        var result = new ArrayList<OptionDto>();
        try {
            var status = StatusEnum.valueOf(statusNow);
            switch (status) {
                case PENDING:
                    result.add(OptionDto.builder()
                            .name("Chấp nhận đơn hàng")
                            .status(StatusEnum.ACCEPT.name().toLowerCase(Locale.ROOT))
                            .clazz("success")
                            .build());
                    result.add(OptionDto.builder()
                            .name("Hủy đơn hàng")
                            .status(StatusEnum.REJECT.name().toLowerCase(Locale.ROOT))
                            .clazz("danger")
                            .build());
                    break;
                case ACCEPT:
                    result.add(OptionDto.builder()
                            .name("Hủy đơn hàng")
                            .status(StatusEnum.REJECT.name().toLowerCase(Locale.ROOT))
                            .clazz("danger")
                            .build());
                    break;
                default:
                    return Collections.emptyList();
            }

        } catch (Exception e) {
            log.error("getOptions: {}", e.getMessage());
        }
        return result;
    }
}
