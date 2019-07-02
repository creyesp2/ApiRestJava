package com.creyes.almacen.almacen.core.models.services;

import com.creyes.almacen.almacen.core.models.dao.IUsuarioDao;
import com.creyes.almacen.almacen.core.models.entity.Usuaario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// NO VA A FUNCIONAR  SI NO SE COLOCA @SERVICE
@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService  {
    //inyeccion de dependencias


    private Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    private final IUsuarioDao usuarioDao;
    public UsuarioServiceImpl(IUsuarioDao usuarioDao){
        this.usuarioDao=usuarioDao;


    }

    @Override
    public List<Usuaario> findAll() {
        return  this.usuarioDao.findAll();
    }

    @Override
    public Page<Usuaario> findAll(Pageable pageable) {
        return this.usuarioDao.findAll(pageable);
    }

    @Override
    public Usuaario findAll(Long id) {
        return this.usuarioDao.findById(id).orElse(null);
    }

    @Override
    public Usuaario save(Usuaario usuario) {
        return this.usuarioDao.save(usuario);
    }

    @Override
    public void delete(Usuaario usuario) {
        this.usuarioDao.delete(usuario);

    }

    @Override
    public Usuaario findByUsername(String username) {
        return this.usuarioDao.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuaario usuario =this.usuarioDao.findByUsername(username);
        if(usuario==null){
            logger.error("Error en el login: no existe el usuario "+ username+" en el sistema");
            throw new UsernameNotFoundException("Error en el login: no existe el usuario "+ username+" en el sistema");
        }

        List<GrantedAuthority> authorities =usuario.getRoles()
                .stream()
                .map(role-> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toList());
        return new User(usuario.getUsername(),usuario.getPassword(),usuario.isEnabled(),true,true,true,authorities);



    }
}
