package com.api.login.dao;

import com.api.login.pojo.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaDao extends JpaRepository<Empresa, Integer> {

}
