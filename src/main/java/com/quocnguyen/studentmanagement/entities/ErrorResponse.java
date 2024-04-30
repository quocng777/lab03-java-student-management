package com.quocnguyen.studentmanagement.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponse {
    int statusCode;
    String errorCode;
    String message;
    long timestamp;

    public ErrorResponse(HttpStatus httpStatus, Exception e) {
        this.statusCode = httpStatus.value();
        this.errorCode = httpStatus.name();
        this.message = e.getMessage();
        this.timestamp = System.currentTimeMillis();
    }


}
