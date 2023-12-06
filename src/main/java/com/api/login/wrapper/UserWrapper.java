package com.api.login.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWrapper {
    private Integer idUser;
    private String nombre;
    private String email;
    private  String numeroContacto;
    private String status;
}
