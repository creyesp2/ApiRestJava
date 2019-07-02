package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.entity.TipoEnpaque;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITipoEmpaqueService {
    public List<TipoEnpaque> finAll();
    public Page <TipoEnpaque>finAll(Pageable pageable);
    public TipoEnpaque save(TipoEnpaque tipoEnpaque);
    public TipoEnpaque findById(Long id);
    public void delete(TipoEnpaque tipoEnpaque);
}
