package com.ws.masterserver.utils.common;

import lombok.extern.slf4j.Slf4j;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
        try {
            var result = "";
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
            decimalFormat.applyPattern("###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###");
            decimalFormat.setRoundingMode(RoundingMode.CEILING);
            result = decimalFormat.format(money).replace(",", ".");
            return result;
        } catch (Exception e) {
            log.error("getString error: {}", e.getMessage());
            return "";
        }
    }
}
