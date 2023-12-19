package org.example.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AppErrorMapping {

    E001("Generic error"),
    E002("Please check encoding of file"),
    ;

    private final String errorMessage;

}
