package com.creyes.almacen.almacen.core.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GeneratorType;

import javax.lang.model.element.Name;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="proveedor")
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="codigo_categoria")
    private  Categoria categoria;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="codigo_empaque")
    private  TipoEnpaque tipoEmpaque;


}
