package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.entity.EmailProveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEmailProveedorService {

    public List<EmailProveedor> findAll();
    public Page<EmailProveedor> findAll(Pageable pageable);
    public EmailProveedor save(EmailProveedor emailProveedor);
    public EmailProveedor findById(Long id);
    public void delete(EmailProveedor emailProveedor);
    public void delete(Long id);

}
