package org.example.exception;

import lombok.Data;

@Data
public class AppException extends RuntimeException {

    private final String errorCode;
    private final String errorMsg;

}
