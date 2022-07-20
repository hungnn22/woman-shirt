package com.ws.master_service.utils.common;

import lombok.extern.slf4j.Slf4j;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 *
 */
@Slf4j
public class MoneyUtils {
    /**
     * @param money giá trị tiền(long, double)
     * @return template dạng tiền của Vn
     * @ex 10.000, 2.000.000
     * */
    public static String format(Long money) {
        if (money == null) {
            return "";
        }
            var result = "";
        try {
            DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
            decimalFormat.applyPattern("###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###");
            decimalFormat.setRoundingMode(RoundingMode.CEILING);
            result = decimalFormat.format(money).replace(",", ".");
        } catch (Exception e) {
            log.error("getString error: {}", e.getMessage());
        } finally {
            return result;
        }
    }
}
