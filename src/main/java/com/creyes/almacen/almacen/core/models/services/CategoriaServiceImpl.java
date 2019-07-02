package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.dao.IcategoriaDao;
import com.creyes.almacen.almacen.core.models.entity.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CategoriaServiceImpl implements  ICategoriaService {

    private final IcategoriaDao categoriaDao;
//inyeccion de dependencias
    public CategoriaServiceImpl(IcategoriaDao categoriaDao){
        this.categoriaDao = categoriaDao;
    }

    @Override
    public List<Categoria> findAll() {
        return this.categoriaDao.findAll();
    }

    @Override
    public Page<Categoria> findAll(Pageable pageable) {
        return this.categoriaDao.findAll(pageable);
    }

    @Override
    public Categoria save(Categoria categoria) {
        return this.categoriaDao.save(categoria);
    }

    @Override
    public Categoria findById(Long id) {
        return this.categoriaDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Categoria categoria) {
this.categoriaDao.delete(categoria);
    }
    @Override
    public void delete(Long id){
        this.categoriaDao.deleteById(id);
    }
}
