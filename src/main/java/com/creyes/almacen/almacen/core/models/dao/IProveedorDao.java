package com.creyes.almacen.almacen.core.models.dao;

import com.creyes.almacen.almacen.core.models.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProveedorDao extends JpaRepository<Proveedor,Long> {
}
