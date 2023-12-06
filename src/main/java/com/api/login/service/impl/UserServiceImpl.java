package com.api.login.service.impl;

import com.api.login.constantes.UsuarioConstantes;
import com.api.login.dao.UserDao;
import com.api.login.pojo.User;
import com.api.login.security.CustomerDetailsService;
import com.api.login.security.jwt.JwtFilter;
import com.api.login.security.jwt.JwtUtil;
import com.api.login.service.UserService;
import com.api.login.util.UsuarioUtil;
import com.api.login.wrapper.UserWrapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

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
    private JwtFilter jwtFilter;
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
    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try {
            if (jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDao.getAllUsers(),HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            // Verifica si el usuario que realiza la solicitud es un administrador
            if (jwtFilter.isAdmin()) {
                // Obtiene el ID del usuario de la solicitud y trata de encontrarlo en la base de datos
                Optional<User> optionalUser = userDao.findById(Integer.parseInt(requestMap.get("idUser")));

                // Verifica si el usuario existe en la base de datos
                if (!optionalUser.isEmpty()) {
                    // Actualiza el estado del usuario en la base de datos
                    userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("idUser")));

                    // Retorna una respuesta con un mensaje exitoso y el código de estado 200 (OK)
                    return UsuarioUtil.getResponseEntity("Status del usuario actualizado", HttpStatus.OK);
                } else {
                    // Retorna una respuesta con un mensaje indicando que el usuario no existe y el código de estado 404 (Not Found)
                    return UsuarioUtil.getResponseEntity("Este usuario no existe", HttpStatus.NOT_FOUND);
                }
            } else {
                // Retorna una respuesta con un mensaje de acceso no autorizado y el código de estado 401 (Unauthorized)
                return UsuarioUtil.getResponseEntity(UsuarioConstantes.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception) {
            // Imprime la traza de la excepción en caso de un error inesperado
            exception.printStackTrace();
        }
        // Retorna una respuesta con un mensaje de error genérico y el código de estado 500 (Internal Server Error)
        return UsuarioUtil.getResponseEntity(UsuarioConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
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