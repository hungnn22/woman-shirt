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
     * @param defaultTotal Tổng tiền sản phẩm chưa trừ đi khuyến mãi sản phẩm.
     * @param shipPrice    Tiền ship
     * @param promotions   Danh sách khuyến mãi
     * @return giá phải trả
     * @apiNote Dựa vào loại khuyến mãi và % giảm giá sẽ trừ đi vào giá
     */
    public static Long getTotal(Long defaultTotal, Long shipPrice, List<PromotionDto> promotions) {
        try {
            /**
             * Nếu không có khuyến mãi thì = defaultTotal + shipPrice
             * */
            if (promotions.isEmpty()) {
                return defaultTotal + shipPrice;
            } else {
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
        }
        return defaultTotal;
    }

    public static ResultDto getResultDto(long shopPrice, Long shipPrice, List<PromotionDto> promotions) {
        var result = ResultDto.builder();
        var shipTotal = shipPrice;
        var shopTotal = shopPrice;
        try {
            var ship = PriceDto.builder()
                    .name(PromotionTypeEnum.TYPE1.getName())
                    .price(MoneyUtils.format(shipPrice))
                    .total(MoneyUtils.format(shipPrice));
            var shop = PriceDto.builder()
                    .name(PromotionTypeEnum.TYPE2.getName())
                    .price(MoneyUtils.format(shopPrice))
                    .total(MoneyUtils.format(shopPrice));
            if (!promotions.isEmpty()) {
                for (var promotion : promotions) {
                    var type = PromotionTypeEnum.valueOf(promotion.getTypeCode());
                    switch (type) {
                        case TYPE1:
                            var shipDiscount = shipPrice * promotion.getPercentDiscount().longValue() / 100;
                            shipTotal = shipPrice - shipDiscount;
                            ship.discount(MoneyUtils.format(shipDiscount))
                                    .total(MoneyUtils.format(shipTotal));
                            break;
                        case TYPE2:
                            var shopDiscount = shopPrice * promotion.getPercentDiscount().longValue() / 100;
                            shopTotal = shopPrice - shopDiscount;
                            shop.discount(MoneyUtils.format(shopDiscount))
                                    .total(MoneyUtils.format(shopTotal));
                            break;

                        default:
                            throw new WsException(WsCode.INTERNAL_SERVER);
                    }

                }
            }
            result.ship(ship.build())
                    .shop(shop.build())
                    .total(MoneyUtils.format(shipTotal + shipTotal));
        } catch (Exception e) {
            log.error("getResultDto: {}", e.getMessage());
        }
        return result.build();
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
