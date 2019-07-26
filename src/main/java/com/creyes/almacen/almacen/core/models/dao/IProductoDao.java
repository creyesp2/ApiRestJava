package com.creyes.almacen.almacen.core.models.dao;

import com.creyes.almacen.almacen.core.models.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoDao extends JpaRepository<Producto,Long> {
}
