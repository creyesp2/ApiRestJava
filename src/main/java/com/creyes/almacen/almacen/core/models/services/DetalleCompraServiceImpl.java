package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.dao.ICompraDetalleDao;
import com.creyes.almacen.almacen.core.models.entity.DetalleCompra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DetalleCompraServiceImpl implements IDetalleCompraService {

    public final ICompraDetalleDao iCompraDetalleDao;
    public DetalleCompraServiceImpl(ICompraDetalleDao iCompraDetalleDao) {
        this.iCompraDetalleDao = iCompraDetalleDao;
    }
    @Override
    public List<DetalleCompra> findAll() {
        return this.iCompraDetalleDao.findAll();
    }

    @Override
    public Page<DetalleCompra> finAll(Pageable pageable) {
        return this.iCompraDetalleDao.findAll(pageable);
    }

    @Override
    public DetalleCompra save(DetalleCompra detalleCompra) {
        return this.iCompraDetalleDao.save(detalleCompra);
    }

    @Override
    public DetalleCompra findById(Long id) {
        return this.iCompraDetalleDao.findById(id).orElse(null);
    }

    @Override
    public void delete(DetalleCompra detalleCompra) {
    this.iCompraDetalleDao.delete(detalleCompra);
    }

    @Override
    public void delete(Long id) {
     this.iCompraDetalleDao.deleteById(id);
    }
}
