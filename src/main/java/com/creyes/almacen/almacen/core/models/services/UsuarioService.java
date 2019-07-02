package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.entity.Usuaario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {
    public List<Usuaario>findAll();
    public Page<Usuaario> findAll(Pageable pageable);
    public Usuaario findAll(Long id);
    public Usuaario save(Usuaario usuario);
    public void  delete(Usuaario usuario);
    public Usuaario findByUsername(String username);
}
