package com.creyes.almacen.almacen.core.models.dao;

import com.creyes.almacen.almacen.core.models.entity.TelefonoCliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITelefonoClienteDao extends JpaRepository<TelefonoCliente,Long> {
}
