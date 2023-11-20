package com.api.login.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EmpresaUtil {
    private  EmpresaUtil(){

    }

    public static ResponseEntity<String> getResponseEntity1(String message, HttpStatus httpStatus){
        return new ResponseEntity<String>("Mensaje" + message, httpStatus);
    }
}
