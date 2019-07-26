package com.creyes.almacen.almacen.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

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
    @JsonIgnore
    @OneToMany(mappedBy = "emailCliente",fetch = FetchType.LAZY)
    private List<EmailCliente> emailClientes;

    @JsonIgnore
    @OneToMany(mappedBy = "telefonoCliente",fetch = FetchType.LAZY)
    private List<TelefonoCliente> telefonoClientes;
    @JsonIgnore
    @OneToMany(mappedBy = "nitFactura",fetch = FetchType.LAZY)
    private List<Factura> facturaClientes;

}


