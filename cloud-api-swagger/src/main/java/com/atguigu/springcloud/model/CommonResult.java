package org.o2.swagger.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class CommonResult<T> {

    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code, String message) {
        this(code, message, null);
    }
    public CommonResult(HttpStatus httpStatus, String message) {
        this(httpStatus.value(), message, null);
    }
}
