package com.creyes.almacen.almacen.core.models.dao;

import com.creyes.almacen.almacen.core.models.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioDao extends JpaRepository<Inventario,Long> {

}
