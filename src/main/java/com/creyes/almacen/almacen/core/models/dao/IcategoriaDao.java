package com.creyes.almacen.almacen.core.models.dao;

import com.creyes.almacen.almacen.core.models.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IcategoriaDao extends JpaRepository<Categoria,Long> {

}
