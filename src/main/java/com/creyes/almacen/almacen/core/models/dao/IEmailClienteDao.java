package com.creyes.almacen.almacen.core.models.dao;

import com.creyes.almacen.almacen.core.models.entity.EmailCliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmailClienteDao extends JpaRepository<EmailCliente,Long> {


}
