package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.entity.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ICategoriaService {
    public List<Categoria> findAll();
    public Page<Categoria> findAll(Pageable pageable);
    public Categoria save(Categoria categoria);
    public Categoria findById(Long id);
    public void delete(Categoria categoria);
    public void delete(Long id);
}
