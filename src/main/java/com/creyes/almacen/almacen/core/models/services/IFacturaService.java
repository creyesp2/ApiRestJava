package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.entity.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFacturaService {

    public List<Factura> findAll();
    public Page<Factura> findAll(Pageable pageable);
    public Factura save(Factura factura);
    public Factura findById(Long id);
    public void delete(Factura factura);
    public void delete(Long id);
}
