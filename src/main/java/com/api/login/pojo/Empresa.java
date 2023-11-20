package com.api.login.pojo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "empresas")
public class Empresa {

    @Id
    @Column(name = "idEmpresa")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpresa;

    @Column(name = "nombreEmpresa")
    private String nombreEmpresa;

    @Column(name = "sectorEmpresa")
    private String sectorEmpresa;

    @Column(name = "sucursalEmpresa")
    private String sucursalEmpresa;

    @Lob
    @Column(name = "logoEmpresa", length = 5000)
    private String logoEmpresa;

    @Column(name = "nombreContacto")
    private String nombreContacto;

    @Column(name = "telefonoContacto")
    private String telefonoContacto;

    @Column(name = "usuario")
    private  String usuario;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "dominioEmpresa")
    private String dominioEmpresa;
}
