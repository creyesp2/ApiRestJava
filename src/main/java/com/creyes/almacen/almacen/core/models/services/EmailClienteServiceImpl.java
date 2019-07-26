package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.dao.IEmailClienteDao;
import com.creyes.almacen.almacen.core.models.entity.EmailCliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmailClienteServiceImpl implements IEmailClienteService {


    private final IEmailClienteDao emailClienteDao;
    //inyeccion de dependencias
    public EmailClienteServiceImpl(IEmailClienteDao emailClienteDao){
        this.emailClienteDao=emailClienteDao;
    }
    @Override
    public List<EmailCliente> findAll() {
        return this.emailClienteDao.findAll();
    }

    @Override
    public Page<EmailCliente> findAll(Pageable pageable) {
        return this.emailClienteDao.findAll(pageable);
    }

    @Override
    public EmailCliente save(EmailCliente emailCliente) {
        return this.emailClienteDao.save(emailCliente);
    }

    @Override
    public EmailCliente findById(Long id) {
        return this.emailClienteDao.findById(id).orElse(null);
    }

    @Override
    public void delete(EmailCliente emailCliente) {
      this.emailClienteDao.delete(emailCliente);
    }

    @Override
    public void delete(Long id) {
     this.emailClienteDao.deleteById(id);
    }
}
