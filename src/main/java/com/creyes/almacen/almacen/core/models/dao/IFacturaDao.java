package com.creyes.almacen.almacen.core.models.dao;

import com.creyes.almacen.almacen.core.models.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFacturaDao extends JpaRepository<Factura,Long> {

}
