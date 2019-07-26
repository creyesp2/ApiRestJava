package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.entity.DetalleCompra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDetalleCompraService {

    public List<DetalleCompra> findAll();
    public Page<DetalleCompra> finAll(Pageable pageable);
    public  DetalleCompra save(DetalleCompra detalleCompra);
    public DetalleCompra findById(Long id);
    public void delete(DetalleCompra detalleCompra);
    public void delete(Long id);
}
