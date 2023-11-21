package com.api.login.service;

import com.api.login.pojo.Empresa;
import com.api.login.wrapper.EmpresaWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface EmpresaService {
    ResponseEntity<String> register(Map<String,String> requestMap);

    ResponseEntity<String> update (Integer id, Map<String,String> requestMap);

    ResponseEntity<String> delete(Integer id);

    ResponseEntity<List<Empresa>> getAllEmpresa();
}
