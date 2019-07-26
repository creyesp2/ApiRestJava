package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.entity.TelefonoProveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITelefonoProveedorService {

    public List<TelefonoProveedor> findAll();
    public Page<TelefonoProveedor> findAll(Pageable pageable);
    public TelefonoProveedor save(TelefonoProveedor telefonoProveedor);
    public TelefonoProveedor findById(Long id);
    public void delete(TelefonoProveedor telefonoProveedor);
    public void delete(Long id);

}
