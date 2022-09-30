/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PetShop.Servicios;

import com.PetShop.Entidades.Rol;
import com.PetShop.Entidades.Usuario;
import com.PetShop.Repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional(rollbackFor = {Exception.class})
    public Usuario crear(String nombre, String domicilio, Integer edad, Integer celular, String email, String password) {

        Usuario usuario = new Usuario();
        usuario.setCelular(celular);
        usuario.setDomicilio(domicilio);
        usuario.setEmail(email);
        usuario.setNombre(nombre);
        usuario.setEdad(edad);
        String passwordEncriptado = new BCryptPasswordEncoder().encode(password);
        usuario.setPassword(passwordEncriptado);
        long respuesta = usuarioRepositorio.count();
        if (respuesta < 3) {
            usuario.setRol(Rol.ADMIN);
        } else {
            usuario.setRol(Rol.GENERAL);
        }
        usuarioRepositorio.save(usuario);
        return usuario;
    }

    public Long rolContador() {
        return usuarioRepositorio.count();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(String id) throws Exception {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            return usuario;

        } else {
            throw new Exception("Usuario Inexistente o incorrecto");
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    public Usuario editar(String id, String nombre, String domicilio, Integer edad, Integer celular, String email) throws Exception {
        Usuario usuario = buscarPorId(id);
        if (nombre != null) {
            usuario.setNombre(nombre);
        }
        if (domicilio != null) {
            usuario.setDomicilio(domicilio);
        }
        if (edad != null) {
            usuario.setEdad(edad);
        }
        if (celular != null) {
            usuario.setCelular(celular);
        }
        if (email != null) {
            usuario.setEmail(email);
        }

        usuarioRepositorio.save(usuario);

        return usuario;
    }

    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return usuarioRepositorio.findAll();

    }

    @Transactional(rollbackFor = {Exception.class})
    public Usuario darBaja(String id) throws Exception {
        Usuario usuario = buscarPorId(id);
        usuario.setId(null);
        return usuario;

    }

    @Transactional(rollbackFor = {Exception.class})
    public Usuario modificarPassword( String email, String password, String nombre, Integer celular){

       Usuario usuario= usuarioRepositorio.buscarPorEmail(email);
        

        if (usuario.getCelular().equals(celular) && password != null && usuario.getEmail().equals(email)  && usuario.getNombre().equals(nombre) ) {
            String passwordEncriptado = new BCryptPasswordEncoder().encode(password);
            usuario.setPassword(passwordEncriptado);

        }
        usuarioRepositorio.save(usuario);

        return usuario;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario == null) {

            return null;
        }

        List<GrantedAuthority> permisos = new ArrayList<>();

        GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
        permisos.add(p1);

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("usuariosession", usuario);

        return new User(usuario.getEmail(), usuario.getPassword(), permisos);

    }

    public List<Usuario> getAll() {
        return usuarioRepositorio.findAll();
    }

}
