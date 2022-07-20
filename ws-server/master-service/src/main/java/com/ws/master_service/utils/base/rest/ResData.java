package com.ws.master_service.utils.base.rest;

import com.ws.master_service.utils.constants.WsCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ResData<T> {
    private T data;
    private Integer statusCode;
    private String message;
    private Date timeStamp;

    public ResData(T data, WsCode wsCode) {
        this.data = data;
        this.statusCode = Integer.parseInt(wsCode.getCode());
        this.message = wsCode.getMessage();
        this.timeStamp = new Date();
    }

    public ResData(T data, WsCode wsCode, String message) {
        this.data = data;
        this.statusCode = Integer.parseInt(wsCode.getCode());
        this.message = message;
        this.timeStamp = new Date();
    }

    public ResData(boolean isEmpty) {
        this.data = null;
        this.statusCode = HttpStatus.NO_CONTENT.value();
        this.message = HttpStatus.NO_CONTENT.getReasonPhrase();
        this.timeStamp = new Date();
    }

    public static <T> ResData ok(T data) {
        return new ResData(data, WsCode.OK);
    }

    public static <T> ResData ok(T data, String message) {
        return new ResData(data, WsCode.OK, message);
    }
}
