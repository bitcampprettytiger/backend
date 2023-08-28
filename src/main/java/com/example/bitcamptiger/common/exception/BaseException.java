package com.example.bitcamptiger.common.exception;

import com.example.bitcamptiger.response.BaseResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException {
    private BaseResponseStatus status;

    public BaseException(BaseResponseStatus status) {
        super(status.getMessage());
        this.status = status;
    }

//    public BaseException(int statusCode, String message) {
//        super(message);
//        this.status = new BaseResponseStatus(false, statusCode, message);
//    }
}
