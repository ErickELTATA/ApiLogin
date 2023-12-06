package com.api.login.wrapper;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaWrapper {
    private Integer idEmpresa;
    private String nombreEmpresa;
    private String sectorEmpresa;
    private String sucursalEmpresa;
    private String logoEmpresa;
    private String nombreContacto;
    private String telefonoContacto;
    private String usuario;
    private String contrasena;
    private String dominioEmpresa;
}
