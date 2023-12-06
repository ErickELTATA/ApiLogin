package com.api.login.util;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UsuarioUtil {
    private  UsuarioUtil(){
    }
    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus httpStatus){
        return new ResponseEntity<String>("Mensaje : " + message,httpStatus);
    }
}
