package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.entity.TelefonoCliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITelefonoClienteService {
    public List<TelefonoCliente> findAll();
    public Page<TelefonoCliente> findAll(Pageable pageable);
    public TelefonoCliente save(TelefonoCliente telefonoCliente);
    public TelefonoCliente findById(Long id);
    public void delete(TelefonoCliente telefonoCliente);
    public void delete(Long id);


}
