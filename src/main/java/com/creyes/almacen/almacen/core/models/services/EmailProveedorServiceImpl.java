package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.dao.IEmailProveedorDao;
import com.creyes.almacen.almacen.core.models.entity.EmailProveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmailProveedorServiceImpl implements IEmailProveedorService {
    private final IEmailProveedorDao emailProveedorDao;
    //inyeccion de dependencias
    public EmailProveedorServiceImpl(IEmailProveedorDao emailProveedorDao){
        this.emailProveedorDao=emailProveedorDao;
    }
    @Override
    public List<EmailProveedor> findAll() {
        return this.emailProveedorDao.findAll();
    }

    @Override
    public Page<EmailProveedor> findAll(Pageable pageable) {
        return this.emailProveedorDao.findAll(pageable);
    }

    @Override
    public EmailProveedor save(EmailProveedor emailProveedor) {
        return this.emailProveedorDao.save(emailProveedor);
    }

    @Override
    public EmailProveedor findById(Long id) {
        return this.emailProveedorDao.findById(id).orElse(null);
    }

    @Override
    public void delete(EmailProveedor emailProveedor) {
     this.emailProveedorDao.delete(emailProveedor);
    }

    @Override
    public void delete(Long id) {
      this.emailProveedorDao.deleteById(id);
    }
}
