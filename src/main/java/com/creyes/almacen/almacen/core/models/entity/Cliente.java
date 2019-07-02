package com.creyes.almacen.almacen.core.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @Column(name = "nit")
    private String  nit;
    @NotEmpty(message = "debe ingresar algo ")
    @Column(name = "dpi")
    private String dpi;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "direccion")
    private String direccion;


}
