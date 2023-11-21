package com.api.login.dao;

import com.api.login.pojo.Empresa;
import com.api.login.wrapper.EmpresaWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaDao extends JpaRepository<Empresa, Integer> {

    List<Empresa> getAllEmpresa();

}
