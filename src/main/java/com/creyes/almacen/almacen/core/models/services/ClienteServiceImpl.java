package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.dao.IClienteDao;
import com.creyes.almacen.almacen.core.models.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {

    public final IClienteDao clienteDao;


    public ClienteServiceImpl(IClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    @Override
    public List<Cliente> findAll() {
        return this.clienteDao.findAll();
    }

    @Override
    public Page<Cliente> finAll(Pageable pageable) {
        return this.clienteDao.findAll(pageable);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return this.clienteDao.save(cliente);
    }

    @Override
    public Cliente findByNit(String nit) {
        return this.clienteDao.findById(nit).orElse(null);
    }

    @Override
    public void delete(Cliente cliente) {

        this.clienteDao.delete( cliente);

    }
}
