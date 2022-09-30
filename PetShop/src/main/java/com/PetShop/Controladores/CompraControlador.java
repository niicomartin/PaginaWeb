package com.PetShop.Controladores;

import com.PetShop.Entidades.Accesorio;
import com.PetShop.Entidades.Alimento;
import com.PetShop.Entidades.Compra;
import com.PetShop.Servicios.AccesorioServicio;
import com.PetShop.Servicios.AlimentoServicio;
import com.PetShop.Servicios.CompraServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

@RequestMapping("/Carrito")

@PreAuthorize("hasAnyRole('ROLE_GENERAL')")

public class CompraControlador {

    @Autowired
    private CompraServicio compraServicio;

    @Autowired
    private AlimentoServicio alimentoServicio;
    @Autowired
    private AccesorioServicio accesorioServicio;

    @GetMapping("/crear/{id}")
    public String crearCompra(ModelMap modelo,@PathVariable String id) throws Exception {   // @PathVariable String idCompra, @PathVariable String idAlimento, @PathVariable String idAccesorio
        
        Alimento alimento = alimentoServicio.buscarPorId(id);
        List<Alimento> alimentos = alimentoServicio.listar();
        modelo.put("alimento", alimento);
        modelo.put("alimentos", alimentos);

//        Accesorio accesorio = accesorioServicio.buscarPorId(idAccesorio);
//        List<Accesorio> accesorios = accesorioServicio.listar();
//        modelo.put("accesorio", accesorio);
//        modelo.put("accesorios", accesorios);
//
//        Compra compra = compraServicio.buscarPorId(idCompra);
//        List<Compra> compras = compraServicio.buscarActivas();
//        modelo.put("compra", compra);
//        modelo.put("compras", compras);
        
        return "CarritoCompra.html"; 
    }

    @PostMapping("/crear")
    public String crearCompra(@RequestParam List<Alimento> alimentos, @RequestParam List<Accesorio> accesorios, @RequestParam Long valorCompra, @RequestParam String idUsuario,@RequestParam String idAlimento, ModelMap modelo) {
        try {
            compraServicio.crearCompra(valorCompra, alimentos, accesorios, idUsuario,idAlimento);
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }
        return "";  //cuando los chicos del front tengan el index lo retorno.
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) {
        List<Compra> compras = compraServicio.buscarActivas();
        modelo.put("compras", compras);
        try {
            Compra compra = compraServicio.buscarPorId(id);
            modelo.put("Compra", compra);
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }
        return "";  //cuando los chicos del front tengan el index lo retorno.
    }

    @PostMapping("/editar")
    public String editar(@RequestParam String id, @RequestParam List<Alimento> alimentos, @RequestParam List<Accesorio> accesorios, @RequestParam Long valorCompra, @RequestParam String idUsuario, RedirectAttributes attr) {
        try {
            compraServicio.modificar(id, valorCompra, alimentos, accesorios);
        } catch (Exception e) {
        }
        return "";  //cuando los chicos del front tengan el index lo retorno.
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) throws Exception {
        compraServicio.eliminar(id);
        return "";  //cuando los chicos del front tengan el index lo retorno.
    }
}
