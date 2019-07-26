package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.entity.EmailCliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEmailClienteService {
    public List<EmailCliente> findAll();
    public Page<EmailCliente> findAll(Pageable pageable);
    public EmailCliente save(EmailCliente emailCliente);
    public EmailCliente findById(Long id);
    public void delete(EmailCliente emailCliente);
    public void delete(Long id);
}
