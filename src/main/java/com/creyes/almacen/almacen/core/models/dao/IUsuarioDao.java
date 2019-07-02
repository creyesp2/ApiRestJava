package com.creyes.almacen.almacen.core.models.dao;

import com.creyes.almacen.almacen.core.models.entity.Usuaario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUsuarioDao extends JpaRepository <Usuaario,Long> {
    public Usuaario findByUsername(String username);
    @Query("from Usuaario u where u.username=?1")
    public  Usuaario findByUsername2(String username);
}