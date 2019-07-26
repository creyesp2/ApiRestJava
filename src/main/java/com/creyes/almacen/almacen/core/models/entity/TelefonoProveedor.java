package com.creyes.almacen.almacen.core.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelefonoProveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_telefono")
    private Long codigoTelefono;
    @Column(name = "numero")
    private String numero;
    @Column(name = "descripcion")
    private String descripcion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="codigo_proveedor")
    private Proveedor telefonoProveedor;
}
