package com.creyes.almacen.almacen.core.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "detalle_factura")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_detalle")
    private Long codigoDetalle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="numero_factura")
    private Factura facturaDetalle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="codigo_producto")
    private Producto productoFactura;
    @Column(name = "catidad")
    private int cantidad;
    @Column(name = "precio")
    private Double precio;
    @Column(name = "descuento")
    private Double descuento;

}
