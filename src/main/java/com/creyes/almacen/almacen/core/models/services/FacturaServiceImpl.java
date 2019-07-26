package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.dao.IFacturaDao;
import com.creyes.almacen.almacen.core.models.entity.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FacturaServiceImpl implements IFacturaService {

    private final IFacturaDao facturaDaoao;
    //inyeccion de dependencias
    public FacturaServiceImpl(IFacturaDao facturaDaoao){
        this.facturaDaoao = facturaDaoao;
    }
    @Override
    public List<Factura> findAll() {
        return this.facturaDaoao.findAll();
    }

    @Override
    public Page<Factura> findAll(Pageable pageable) {
        return this.facturaDaoao.findAll(pageable);
    }

    @Override
    public Factura save(Factura factura) {
        return this.facturaDaoao.save(factura);
    }

    @Override
    public Factura findById(Long id) {
        return this.facturaDaoao.findById(id).orElse(null);
    }

    @Override
    public void delete(Factura factura) {
     this.facturaDaoao.delete(factura);
    }

    @Override
    public void delete(Long id) {
     this.facturaDaoao.deleteById(id);
    }
}
