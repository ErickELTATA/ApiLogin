package com.api.login.service.impl;

import com.api.login.constantes.UsuarioConstantes;
import com.api.login.dao.EmpresaDao;
import com.api.login.pojo.Empresa;
import com.api.login.security.jwt.JwtFilter;
import com.api.login.security.jwt.JwtUtil;
import com.api.login.service.EmpresaService;
import com.api.login.util.EmpresaUtil;
import com.api.login.wrapper.EmpresaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaDao empresaDao;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public ResponseEntity<String> register(Map<String, String> requestMap) {
        log.info("Registro interno de una empresa {}", requestMap);
        try {
            if (validateRegister(requestMap)){
                empresaDao.save(getEmpresaFromMap(requestMap));
                return EmpresaUtil.getResponseEntity1("Empresa registrada con exito", HttpStatus.CREATED);
            }else {
                return EmpresaUtil.getResponseEntity1(UsuarioConstantes.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return EmpresaUtil.getResponseEntity1(UsuarioConstantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Integer id,Map<String, String> requestMap ) {
        try {
                // Obtén el ID del usuario de la solicitud y trata de encontrarlo en la base de datos
                Optional<Empresa> optionalUser = empresaDao.findById(id);

                if (!optionalUser.isEmpty()) {
                    // Obtén el usuario de la base de datos
                    Empresa empresa = optionalUser.get();

                    // Actualiza los campos del usuario con los valores proporcionados en el mapa
                    empresa.setNombreEmpresa(requestMap.get("nombreEmpresa"));
                    empresa.setSectorEmpresa(requestMap.get("sectorEmpresa"));
                    empresa.setSucursalEmpresa(requestMap.get("sucursalEmpresa"));
                    empresa.setLogoEmpresa(requestMap.get("logoEmpresa"));
                    empresa.setNombreContacto(requestMap.get("nombreContacto"));
                    empresa.setTelefonoContacto(requestMap.get("telefonoContacto"));
                    empresa.setUsuario(requestMap.get("usuario"));
                    empresa.setContrasena(requestMap.get("contrasena"));
                    empresa.setDominioEmpresa(requestMap.get("dominioEmpresa"));
                    // Actualiza el usuario en la base de datos
                    empresaDao.save(empresa);

                    // Retorna una respuesta con un mensaje exitoso y el código de estado 200 (OK)
                    return EmpresaUtil.getResponseEntity1("Datos del usuario actualizados", HttpStatus.OK);
                } else {
                    // Retorna una respuesta con un mensaje indicando que el usuario no existe y el código de estado 404 (Not Found)
                    return EmpresaUtil.getResponseEntity1("Este usuario no existe", HttpStatus.NOT_FOUND);
                }
        } catch (Exception exception) {
            // Imprime la traza de la excepción en caso de un error inesperado
            exception.printStackTrace();
        }

        // Retorna una respuesta con un mensaje de error genérico y el código de estado 500 (Internal Server Error)
        return EmpresaUtil.getResponseEntity1(UsuarioConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> delete(Integer userId) {
        try {
                // Trata de encontrar al usuario en la base de datos utilizando el ID proporcionado
                Optional<Empresa> optionalUser = empresaDao.findById(userId);

                if (optionalUser.isPresent()) {
                    // Elimina al usuario de la base de datos
                    empresaDao.deleteById(userId);

                    // Retorna una respuesta con un mensaje exitoso y el código de estado 200 (OK)
                    return EmpresaUtil.getResponseEntity1("Usuario eliminado exitosamente", HttpStatus.OK);
                } else {
                    // Retorna una respuesta con un mensaje indicando que el usuario no existe y el código de estado 404 (Not Found)
                    return EmpresaUtil.getResponseEntity1("Este usuario no existe", HttpStatus.NOT_FOUND);
                }
        } catch (Exception exception) {
            // Imprime la traza de la excepción en caso de un error inesperado
            exception.printStackTrace();
        }

        // Retorna una respuesta con un mensaje de error genérico y el código de estado 500 (Internal Server Error)
        return EmpresaUtil.getResponseEntity1(UsuarioConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Empresa>> getAllEmpresa() {
        try{
            return new ResponseEntity<>(empresaDao.getAllEmpresa(),HttpStatus.OK);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateRegister(Map<String,String> requestMap){
        if (requestMap.containsKey("nombreEmpresa") &&
                requestMap.containsKey("sectorEmpresa") &&
                requestMap.containsKey("sucursalEmpresa") &&
                requestMap.containsKey("logoEmpresa") &&
                requestMap.containsKey("nombreContacto") &&
                requestMap.containsKey("telefonoContacto") &&
                requestMap.containsKey("usuario") &&
                requestMap.containsKey("contrasena") &&
                requestMap.containsKey("dominioEmpresa")){
            return true;
        }
        return false;
    }

    private Empresa getEmpresaFromMap(Map<String, String> requestMap){
        Empresa empresa = new Empresa();
        empresa.setNombreEmpresa(requestMap.get("nombreEmpresa"));
        empresa.setSectorEmpresa(requestMap.get("sectorEmpresa"));
        empresa.setSucursalEmpresa(requestMap.get("sucursalEmpresa"));
        empresa.setLogoEmpresa(requestMap.get("logoEmpresa"));
        empresa.setNombreContacto(requestMap.get("nombreContacto"));
        empresa.setTelefonoContacto(requestMap.get("telefonoContacto"));
        empresa.setUsuario(requestMap.get("usuario"));
        empresa.setContrasena(requestMap.get("contrasena"));
        empresa.setDominioEmpresa(requestMap.get("dominioEmpresa"));
        return empresa;
    }
}
