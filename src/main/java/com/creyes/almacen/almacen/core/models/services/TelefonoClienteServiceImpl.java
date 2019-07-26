package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.dao.ITelefonoClienteDao;
import com.creyes.almacen.almacen.core.models.entity.TelefonoCliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelefonoClienteServiceImpl implements ITelefonoClienteService{

    private final ITelefonoClienteDao telefonoClienteDao;
    public TelefonoClienteServiceImpl(ITelefonoClienteDao telefonoClienteDao){
        this.telefonoClienteDao = telefonoClienteDao;
    }

    @Override
    public List<TelefonoCliente> findAll() {
        return this.telefonoClienteDao.findAll();
    }

    @Override
    public Page<TelefonoCliente> findAll(Pageable pageable) {
        return this.telefonoClienteDao.findAll(pageable);
    }

    @Override
    public TelefonoCliente save(TelefonoCliente telefonoCliente) {
        return this.telefonoClienteDao.save(telefonoCliente);
    }

    @Override
    public TelefonoCliente findById(Long id) {
        return this.telefonoClienteDao.findById(id).orElse(null);
    }

    @Override
    public void delete(TelefonoCliente telefonoCliente) {
     this.telefonoClienteDao.delete(telefonoCliente);
    }

    @Override
    public void delete(Long id) {
      this.telefonoClienteDao.deleteById(id);
    }
}
