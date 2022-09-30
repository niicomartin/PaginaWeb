/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PetShop.Repositorios;


import com.PetShop.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    
@Query("SELECT l FROM Usuario l WHERE l.email LIKE :email")
public Usuario buscarPorEmail(@Param("email") String email);

//@Query("SELECT c FROM Usuario c WHERE c.celular LIKE :celular")
//public Usuario buscarPorCelular(@Param("celular") Integer celular);
//
//@Query("SELECT a FROM Usuario a WHERE a.nombre LIKE :nombre")
//public Usuario buscarPorNombre(@Param("email") String nombre);
//        
    
    
}
