package com.creyes.almacen.almacen.core.models.dao;

import com.creyes.almacen.almacen.core.models.entity.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompraDetalleDao extends JpaRepository<DetalleCompra,Long> {
}
