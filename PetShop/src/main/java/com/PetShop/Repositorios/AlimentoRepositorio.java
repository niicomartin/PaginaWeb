/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PetShop.Repositorios;

import com.PetShop.Entidades.Alimento;
import com.PetShop.Entidades.Animal;
import com.PetShop.Entidades.TipoAlimento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimentoRepositorio extends JpaRepository<Alimento, String>{

    @Query ("SELECT a FROM Alimento a WHERE a.activo IS TRUE AND a.tipoAlimento LIKE :animal ")
    public List<Alimento> filtrarPorAnimal(@Param ("animal") Animal animal);

    @Query ("SELECT a FROM Alimento a WHERE a.activo IS TRUE AND a.marca LIKE :marca")
    public List<Alimento> filtrarPorMarca(@Param ("marca") String marca);

    @Query ("SELECT a FROM Alimento a WHERE a.activo IS TRUE AND a.tipoAlimento LIKE :tipoAlimento AND a.animal LIKE :animal")
    public List<Alimento> filtrar(@Param ("tipoAlimento") TipoAlimento tipoAlimento, @Param ("animal") Animal animal);
    
    @Query("SELECT a FROM Alimento a WHERE a.activo IS TRUE")
    public List<Alimento> buscarActivas();


    
}
