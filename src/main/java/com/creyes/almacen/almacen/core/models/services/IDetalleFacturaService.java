package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.entity.DetalleFactura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDetalleFacturaService {
    public List<DetalleFactura> findAll();
    public Page<DetalleFactura> findAll(Pageable pageable);
    public DetalleFactura save(DetalleFactura detalleFactura);
    public DetalleFactura findById(Long id);
    public void delete(DetalleFactura detalleFactura);
    public void delete(Long id);
}
