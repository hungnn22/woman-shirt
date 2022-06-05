package com.ws.masterserver.utils.base.rest;

import com.ws.masterserver.utils.constants.WsCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ResData<T> {
    private T data;
    private String statusCode;
    private String message;
    private Date timeStamp;

    public ResData(T data, WsCode statusCode) {
        this.data = data;
        this.statusCode = statusCode.getCode();
        this.message = statusCode.getMessage();
        this.timeStamp = new Date();
    }

    public ResData(boolean isEmpty) {
        this.data = null;
        this.statusCode = String.valueOf(HttpStatus.NO_CONTENT.value());
        this.message = HttpStatus.NO_CONTENT.getReasonPhrase();
        this.timeStamp = new Date();
    }
}
