package com.api.login.pojo;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;


@NamedQuery(name = "User.findByEmail",query = "select u from User u where u.email=:email")
@NamedQuery(name = "User.getAllUsers",query = "select new com.api.login.wrapper.UserWrapper(u.idUser,u.nombre,u.email,u.numeroContacto,u.status) from User u where u.role='user'")
@NamedQuery(name = "User.updateStatus",query = "update User u set u.status=:status where u.idUser=:idUser")
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "usuariosLogin")
public class User{

    @Id
    @Column(name = "idUser")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

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
