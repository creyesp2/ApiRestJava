package com.creyes.almacen.almacen.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "compra")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    private Long idCompra;
    @Column(name = "numero_documento")
    private int numeroDocumento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="codigo_proveedor")
    private Proveedor compraProveedor;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "total")
    private Double total;
    @JsonIgnore
    @OneToMany(mappedBy = "detalleCompra",fetch = FetchType.LAZY)
    private List<DetalleCompra> detalleCompras;
}
