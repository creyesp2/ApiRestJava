package com.creyes.almacen.almacen.core.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "detalle_compra")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class DetalleCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_compra")
    private Compra detalleCompra;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="codigo_producto")
    private Producto detalleProducto;
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "precio")
    private Double precio;

}
