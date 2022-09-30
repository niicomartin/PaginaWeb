
package com.PetShop.Repositorios;

import com.PetShop.Entidades.Compra;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepositorio extends JpaRepository<Compra, String> {
 @Query("SELECT c FROM Compra c WHERE c.alta IS TRUE")
    public List<Compra> buscarActivas();
}
