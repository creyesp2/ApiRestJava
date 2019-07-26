package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.entity.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IProveedorService {
    public List<Proveedor> finAll();
    public Page<Proveedor> finAll(Pageable pageable);
    public Proveedor save(Proveedor proveedor);
    public Proveedor findById(Long id);
    public void delete(Proveedor proveedor);
    public void delete(Long id);
}
