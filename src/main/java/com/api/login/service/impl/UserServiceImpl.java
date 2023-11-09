package com.api.login.service.impl;

import com.api.login.constantes.UsuarioConstantes;
import com.api.login.dao.UserDao;
import com.api.login.pojo.User;
import com.api.login.security.CustomerDetailsService;
import com.api.login.security.jwt.JwtUtil;
import com.api.login.service.UserService;
import com.api.login.util.UsuarioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Registro interno de un usuario {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)){
                User user = userDao.findByEmail(requestMap.get("email"));
                if (Objects.isNull(user)){
                    userDao.save(getUserFromMap(requestMap));
                    return UsuarioUtil.getResponseEntity("Usuario registrado con exito", HttpStatus.CREATED);
                }else {
                    return UsuarioUtil.getResponseEntity("El usuario con ese email ya existe", HttpStatus.BAD_REQUEST);
                }
            }else {
                return UsuarioUtil.getResponseEntity(UsuarioConstantes.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return UsuarioUtil.getResponseEntity(UsuarioConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Dentro de login");
        try {
            Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password"))
            );
            if (authentication.isAuthenticated()){
                if(customerDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>(
                            "{\"token\":\""+ jwtUtil.generateToken(customerDetailsService.getUserDetail().getEmail(),
                                    customerDetailsService.getUserDetail().getRole())+"\"}",
                            HttpStatus.OK);
                }else {
                    return new ResponseEntity<String>("{\"mensaje\":\""+"Espere la aprobacion del administrador"+"\"}",HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception exception){
            log.error("{}",exception);
        }
        return new ResponseEntity<String>("{\"mensaje\":\""+"Credendiales incorrectas"+"\"}",HttpStatus.BAD_REQUEST);
    }


    private boolean validateSignUpMap(Map<String, String> requestMap){
        if(requestMap.containsKey("nombre") && requestMap.containsKey("numeroContacto") && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String, String> requestMap){
        User user = new User();
        user.setNombre(requestMap.get("nombre"));
        user.setNumeroContacto(requestMap.get("numeroContacto"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }
}
