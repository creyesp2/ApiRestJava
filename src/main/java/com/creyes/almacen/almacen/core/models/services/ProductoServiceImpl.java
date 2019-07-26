package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.dao.IProductoDao;
import com.creyes.almacen.almacen.core.models.dao.IProveedorDao;
import com.creyes.almacen.almacen.core.models.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements IProductoService  {
    private final IProductoDao productoDao;

    public ProductoServiceImpl(IProductoDao productoDao){this.productoDao=productoDao;}

    @Override
    public List<Producto> findAll() {
        return this.productoDao.findAll();
    }

    @Override
    public Page<Producto> findAll(Pageable pageable) {
        return this.productoDao.findAll(pageable);
    }

    @Override
    public Producto save(Producto producto) {
        return this.productoDao.save(producto);
    }

    @Override
    public Producto findById(Long id) {
        return this.productoDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Producto producto) {
      this.productoDao.delete(producto);
    }

    @Override
    public void delete(Long id) {
     this.productoDao.deleteById(id);
    }
}
