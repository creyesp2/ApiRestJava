package com.creyes.almacen.almacen.core.models.dao;

import com.creyes.almacen.almacen.core.models.entity.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDetalleFacturaDao extends JpaRepository<DetalleFactura,Long> {


}
