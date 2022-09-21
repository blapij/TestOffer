package com.test.offer.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class OfferResponseEntity {
        public static org.springframework.http.ResponseEntity<Object> build(String errorMessage, int status, String path, HttpStatus httpStatus) {
        return new org.springframework.http.ResponseEntity<>(
                new ErrorResponse(errorMessage, status, path),
                new HttpHeaders(),
                httpStatus
        );
    }
}
