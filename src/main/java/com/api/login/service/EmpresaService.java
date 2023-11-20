package com.api.login.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface EmpresaService {
    ResponseEntity<String> register(Map<String,String> requestMap);

    ResponseEntity<String> update (Integer id, Map<String,String> requestMap);
}
