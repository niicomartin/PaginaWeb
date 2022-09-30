/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PetShop.Controladores;

import com.PetShop.Entidades.Usuario;
import com.PetShop.Repositorios.UsuarioRepositorio;
import com.PetShop.Servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PostMapping("/registro")
    public String registro(ModelMap modelo, @RequestParam String nombre, @RequestParam String domicilio,
            @RequestParam Integer edad, @RequestParam Integer celular, 
            @RequestParam String email, @RequestParam String password) {
        
        if (usuarioRepositorio.buscarPorEmail(email) == null) {
            usuarioServicio.crear(nombre, domicilio, edad, celular, email, password);
        } else {
            modelo.put("error", "Correo ya registrado");
            return "registro.html";
        }
        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.getAll();
        modelo.put("usuarios", usuarios);

        return "lista.html";
    }
    @PostMapping("/login/contraseñaOlvidada")
    public String contraseñaOlvidada(ModelMap modelo,  
            @RequestParam String email, @RequestParam String password, @RequestParam String nombre, @RequestParam Integer celular) {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        System.out.println(usuario.getEmail().toString());
        if (usuario.getEmail().equals(email)) {
            System.out.println(usuario.getEmail());
            usuarioServicio.modificarPassword( email, password, nombre, celular);
        } else {
            modelo.put("error", "NO COINCIDE EL NOMBRE CELULAR Y EMAIL, INTENTE DE NUEVO");
            return "contraseñaOlvidada.html";
        }
        modelo.put("exito", "CONTRASEÑA MODIFICADA EXITOSAMENTE");
        return "contraseñaOlvidada.html";
    }
//    @GetMapping("/login/usuario")
//    public String datosUsuario(ModelMap modelo, String idUsuario) {
//         Usuario usuario = usuarioServicio.buscarPorId(idUsuario);
//        modelo.put("datos", usuario);
//
//        return "lista.html";
//    }
}

