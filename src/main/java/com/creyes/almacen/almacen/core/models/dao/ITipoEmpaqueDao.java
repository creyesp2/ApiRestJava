package com.creyes.almacen.almacen.core.models.dao;

import com.creyes.almacen.almacen.core.models.entity.TipoEnpaque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITipoEmpaqueDao extends JpaRepository<TipoEnpaque,Long> {
}
