package com.creyes.almacen.almacen.core.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.awt.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuaario implements Serializable {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "id")
    private Long id;
    @Column(name = "enabled")
    private boolean enabled;
    @Column(name = "password")
    @NotEmpty(message = "debe ingrear una contrasena")
    @Size(min = 6)
    private String password;
    @Column(name = "username")
    @NotEmpty(message = "debe agregar un nombre de usuario valido")
    private String username;
    @Column(name = "email")
    @Email(message = "debe ingresar un correo valido")
    private String email;
    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    @JoinTable(name = "usuario_rol",joinColumns = @JoinColumn(name = "usuario_id"),inverseJoinColumns = @JoinColumn(name = "role_id"),uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","role_id"})})

    private List<Rol> roles;


}
