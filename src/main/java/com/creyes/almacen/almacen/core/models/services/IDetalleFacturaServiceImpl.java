package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.dao.IDetalleFacturaDao;
import com.creyes.almacen.almacen.core.models.dao.IFacturaDao;
import com.creyes.almacen.almacen.core.models.entity.DetalleFactura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IDetalleFacturaServiceImpl implements IDetalleFacturaService {
    private final IDetalleFacturaDao iDetalleFacturaDao;
    //inyeccion de dependencias
    public IDetalleFacturaServiceImpl(IDetalleFacturaDao iDetalleFacturaDao){
        this.iDetalleFacturaDao = iDetalleFacturaDao;
    }
    @Override
    public List<DetalleFactura> findAll() {
        return this.iDetalleFacturaDao.findAll();
    }

    @Override
    public Page<DetalleFactura> findAll(Pageable pageable) {
        return this.iDetalleFacturaDao.findAll(pageable);
    }

    @Override
    public DetalleFactura save(DetalleFactura detalleFactura) {
        return this.iDetalleFacturaDao.save(detalleFactura);
    }

    @Override
    public DetalleFactura findById(Long id) {
        return this.iDetalleFacturaDao.findById(id).orElse(null);
    }

    @Override
    public void delete(DetalleFactura detalleFactura) {
       this.iDetalleFacturaDao.delete(detalleFactura);
    }

    @Override
    public void delete(Long id) {
     this.iDetalleFacturaDao.deleteById(id);
    }
}
