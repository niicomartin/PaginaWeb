package com.PetShop.Servicios;

import com.PetShop.Entidades.Accesorio;
import com.PetShop.Entidades.Alimento;
import com.PetShop.Entidades.Compra;
import com.PetShop.Entidades.Usuario;
import com.PetShop.Repositorios.CompraRepositorio;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompraServicio {

    @Autowired
    private CompraRepositorio compraRepositorio;
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private AlimentoServicio alimentoServicio;

    @Transactional(rollbackFor = {Exception.class})
    public Compra crearCompra(Long valorCompra, List<Alimento> alimentos, List<Accesorio> accesorios,String idUsuario,String idAlimento) throws Exception {
        
            //validar(alimentos, accesorios, valorCompra);
            
        Compra compra = new Compra();
        compra.setAlimentos(alimentos);
        compra.setAccesorios(accesorios);
        Alimento alimento = alimentoServicio.buscarPorId(idAlimento);
        compra.setValorCompra(alimento.getPrecio());      
        compra.setTotal(0);
        
        for (int i = 0; i < alimentos.size(); i++) {
            compra.setTotal(compra.getTotal()+alimentos.get(i).getPrecio());
        }   
        
        Usuario usuario = usuarioServicio.buscarPorId(idUsuario);
        compra.setUsuario(usuario);
        compra.setAlta(true);
        compra.setFechaAlta(new Date());
       

        return compraRepositorio.save(compra);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void modificar(String id, Long valorCompra, List<Alimento> alimentos, List<Accesorio> accesorios) throws Exception {
        
        validar(alimentos, accesorios, valorCompra);
        
        Optional<Compra> respuesta = compraRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Compra compra = respuesta.get();
            compra.setAlimentos(alimentos);
            compra.setAccesorios(accesorios);
            compra.setValorCompra(valorCompra);

        }else{
            throw new Exception("No se encuentra la compra con ese id");
        }
    }
    @Transactional(rollbackFor = {Exception.class})
    public void eliminar(String id) throws Exception{
        Optional<Compra> respuesta = compraRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Compra compra = respuesta.get();
            compraRepositorio.delete(compra);
        }else{
            throw new Exception("No se encuentra la compra con ese id");
        }
    }
    @Transactional(readOnly = true)
    public List<Compra> buscarActivas(){
        return compraRepositorio.buscarActivas();
        
    }
    @Transactional(readOnly = true)
    public List<Compra> MostrarTodas(){
        return compraRepositorio.findAll();
    }
    
        @Transactional(readOnly = true)
    public Compra buscarPorId(String id) throws Exception {
        Optional<Compra> respuesta = compraRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Compra compra = respuesta.get();
            return compra;

        } else {
            throw new Exception("No existe esta compra con ese id");
        }
    }
    public void validar(List<Alimento> alimentos,List<Accesorio> accesorios,Long valorCompra) throws Exception{
        if(alimentos == null || alimentos.isEmpty()|| accesorios == null || accesorios.isEmpty()|| valorCompra == null){
            throw new Exception("No hay Productos en el carrito");
        }
       
    }
    
}
