package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.dao.ITipoEmpaqueDao;
import com.creyes.almacen.almacen.core.models.entity.TipoEnpaque;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TipoEmpaqueServiceImpl implements ITipoEmpaqueService {

        private  final ITipoEmpaqueDao tipoEmpaqueDao;

        public TipoEmpaqueServiceImpl(ITipoEmpaqueDao tipoEmpaqueDao){
            this.tipoEmpaqueDao = tipoEmpaqueDao;
        }
    @Override
    public List<TipoEnpaque> finAll() {
        return this.tipoEmpaqueDao.findAll();
    }

    @Override
    public Page<TipoEnpaque> finAll(Pageable pageable) {
        return this.tipoEmpaqueDao.findAll(pageable);
    }

    @Override
    public TipoEnpaque save(TipoEnpaque tipoEnpaque) {
        return this.tipoEmpaqueDao.save(tipoEnpaque);
    }

    @Override
    public TipoEnpaque findById(Long id) {
        return this.tipoEmpaqueDao.findById(id).orElse(null);
    }

    @Override
    public void delete(TipoEnpaque tipoEnpaque) {
            this.tipoEmpaqueDao.delete(tipoEnpaque);

    }
}
