/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PetShop.Controladores;

import com.PetShop.Entidades.Alimento;
import com.PetShop.Entidades.Rol;
import com.PetShop.Entidades.TipoAlimento;
import com.PetShop.Entidades.Usuario;
import com.PetShop.Servicios.AlimentoServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortalControlador {

    @Autowired
    public AlimentoServicio alimentoServicio;

    @GetMapping("/login")

    public String login(ModelMap modelo, @RequestParam(required = false)String error, @RequestParam(required = false)String logout ){
       if(error != null){
           modelo.put("error", "Usuario o Contraseña incorrectas");
       }
       if(logout != null){
           modelo.put("logout", "Has cerrado sesión");
       }
       modelo.put("login", "Has iniciado Sesión exitosamente :)");

        return "login.html";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/accesorio/crear")
    public String crearAccesorio() {
        return "accesorio-crear.html";
    }



    @GetMapping("/alimento")

    public String alimento(ModelMap modelo, @RequestParam(required = false) String tipo, @RequestParam(required=false) String animal){




        List<Alimento> alimentos;

        if (tipo == null) {
            alimentos = alimentoServicio.listar();
        } else {
            alimentos = alimentoServicio.filtrar(tipo, animal);
        }

        modelo.put("alimentos", alimentos);

        return "alimento.html";
    }

    @GetMapping("/CarritoCompra")
    public String CarritoCompra() {
        return "CarritoCompra.html";
    }

    @GetMapping("/contacto")
    public String Contacto() {
        return "contacto.html";
    }

    @GetMapping("/quienes_somos")
    public String QuienesSomos() {
        return "quienes_somos.html";
    }
@GetMapping("/login/usuario")
    public String vistaUsuario() {
        return "login-usuario.html";
    }
    @GetMapping("/login/contraseñaOlvidada")
    public String contraseñaOlvidada() {
        return "contraseñaOlvidada.html";
    }
}
