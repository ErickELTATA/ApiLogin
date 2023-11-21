package com.api.login.rest;

import com.api.login.constantes.UsuarioConstantes;
import com.api.login.pojo.Empresa;
import com.api.login.service.EmpresaService;
import com.api.login.util.EmpresaUtil;
import com.api.login.wrapper.EmpresaWrapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/company")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;


    @PostMapping("/register")
    public ResponseEntity<String> registrarEmpresa(@RequestBody(required = true)Map<String,String> requestMap){
        try {
            return empresaService.register(requestMap);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return EmpresaUtil.getResponseEntity1(UsuarioConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> actualizarEmpresa(@PathVariable(required = true) Integer id, @RequestBody(required = true)Map<String, String> requestMap){
        try {
            return empresaService.update(id,requestMap);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return EmpresaUtil.getResponseEntity1(UsuarioConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Empresa>> listarEmpresas(){
        try {
            return empresaService.getAllEmpresa();
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<List<Empresa>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> borrarEmpresa(@PathVariable Integer id){
        return  empresaService.delete(id);
    }


}
