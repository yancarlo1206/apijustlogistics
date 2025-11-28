package com.justlogistics.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseJust<T> {
    private String mensaje;
    private int status;
    private T data;
}