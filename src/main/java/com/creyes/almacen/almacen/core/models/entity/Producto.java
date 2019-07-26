package com.creyes.almacen.almacen.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="producto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_producto")
    private Long codigoProducto;
    @Column(name = "descripcion")
    private  String descripcion;
    @Column(name = "precio_unitario")
    private Double precioUnitario;
    @Column(name = "codigo_por_docena")
    private Double precioPorDocena;
    @Column(name = "codigo_por_mayor")
    private Double precioPorMayor;
    @Column(name = "existencia")
    private int existencia;
    @Column(name = "imagen")
    private  String imagen;
    @JsonIgnore
    @OneToMany(mappedBy = "producto",fetch = FetchType.LAZY)
    private List<Inventario> inventarios;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="codigo_categoria")
    private  Categoria categoria;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="codigo_empaque")
    private  TipoEnpaque tipoEmpaque;
    @JsonIgnore
    @OneToMany(mappedBy = "detalleProducto",fetch = FetchType.LAZY)
    private List<DetalleCompra> detalleProductos;
    @JsonIgnore
    @OneToMany(mappedBy = "productoFactura",fetch = FetchType.LAZY)
    private List<DetalleFactura> detalleFacturas;

}
