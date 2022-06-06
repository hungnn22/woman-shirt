package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.constants.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class OrderUtils {
    /**
     * @author hungnn22
     * @param statusStr trạng thái hiện tại
     * @param createdDate thời gian tạo trạng thái hiện tại
     * @param combinationName tên người tạo ra trạng thái = firstName + lastName
     * @param roleStr role người tạo ra trạng thái
     * @return vd: Đơn hàng đã được chấp nhận bởi Admin lúc 10:22:23 12/02/2022
     * */
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
                    result = "Đã bị từ chối bởi " + role.getValue() + " " + combinationName + " vào lúc " + dateFmt;
                    break;
                case ACCEPT:
                    result = "Được chấp nhận bởi " + role.getValue() + " " + combinationName + " vào lúc: " + dateFmt;
                    break;
                default:
                    throw new WsException(WsCode.INTERNAL_SERVER);
            }
        } catch (Exception e) {
            log.error("getStatusCombination error: {}", e.getMessage());
        }

        return result;
    }
}
