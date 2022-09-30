package com.PetShop.Servicios;

import com.PetShop.Entidades.Alimento;
import com.PetShop.Entidades.Animal;
import com.PetShop.Entidades.Imagen;
import com.PetShop.Entidades.TipoAlimento;
import com.PetShop.Repositorios.AlimentoRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AlimentoServicio {

    @Autowired
    public AlimentoRepositorio alimentoRepositorio;

    @Autowired
    public ImagenServicio imagenServicio;

    @Transactional(rollbackFor = Exception.class)
    public Alimento crear(Integer cantidad, Animal animal, TipoAlimento tipoAlimento, String marca, Integer stock, double precio, MultipartFile archivo) {
        
        Alimento alimento = new Alimento();
        alimento.setCantidad(cantidad);
        alimento.setAnimal(animal);
        alimento.setTipoAlimento(tipoAlimento);
        alimento.setMarca(marca);
        alimento.setStock(stock);
        alimento.setPrecio(precio);
        alimento.setActivo(true);
        try {
            Imagen imagen = imagenServicio.guardar(archivo);

            alimento.setImagen(imagen);
        } catch (Exception e) {
        }

        alimentoRepositorio.save(alimento);
        return alimento;

    }

    @Transactional(rollbackFor = Exception.class)
    public Alimento editar(String id, Integer cantidad, Animal animal, TipoAlimento tipoAlimento, String marca, Integer stock, double precio, boolean activo,  MultipartFile archivo) throws Exception {
        
        Optional<Alimento> respuesta = alimentoRepositorio.findById(id);
        if (respuesta.isPresent()) {
           
            Alimento alimento = respuesta.get();
            alimento.setCantidad(cantidad);
            alimento.setAnimal(animal);
            alimento.setTipoAlimento(tipoAlimento);
            alimento.setMarca(marca);
            alimento.setStock(stock);
            alimento.setPrecio(precio);
            alimento.setActivo(activo);
            if (!archivo.isEmpty()) {
            Imagen imagen = imagenServicio.guardar(archivo);

            alimento.setImagen(imagen);
            }
            alimentoRepositorio.save(alimento);
            return alimento;

        } else {
            throw new Exception("No se encuentra el Alimento con ese id");
        }

    }

    @Transactional(readOnly = true)
    public List<Alimento> listar() {
        return alimentoRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public List<Alimento> filtrarPorAnmial(Animal animal) {
        return alimentoRepositorio.filtrarPorAnimal(animal);
    }

    @Transactional(readOnly = true)
    public List<Alimento> filtrarPorMarca(String marca) {

        return alimentoRepositorio.filtrarPorMarca(marca);

    }

    @Transactional(readOnly = true)
    public List<Alimento> filtrar(String tipoAlimento, String animal) {
        return alimentoRepositorio.filtrar(TipoAlimento.valueOf(tipoAlimento), Animal.valueOf(animal));
    }

    @Transactional(rollbackFor = Exception.class)
    public void anular(String id) throws Exception {
        Optional<Alimento> respuesta = alimentoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Alimento alimento = respuesta.get();
            alimento.setActivo(!alimento.isActivo());

        } else {
            throw new Exception("No se encuentra el Alimento con ese id");
        }
    }
    
    @Transactional(readOnly=true)
    public Alimento buscarPorId(String id) throws Exception{
        Optional<Alimento> respuesta = alimentoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Alimento alimento = respuesta.get();
            return alimento;

        } else {
            throw new Exception("No se encuentra el Alimento con ese id");
        }
    }

}
