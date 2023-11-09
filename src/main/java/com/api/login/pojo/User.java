package com.api.login.pojo;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;


@NamedQuery(name = "User.findByEmail",query = "select u from User u where u.email=:email")
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "usuariosLogin")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "numeroContacto")
    private  String numeroContacto;

    @Column(name = "email")
    private  String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;

}