package com.creyes.almacen.almacen.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "factura")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_factura")
    private int numeroFactura;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="nit")
    private Cliente nitFactura;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "total")
    private Double total;

    @JsonIgnore
    @OneToMany(mappedBy = "facturaDetalle",fetch = FetchType.LAZY)
    private List<DetalleFactura> facturas;
}
