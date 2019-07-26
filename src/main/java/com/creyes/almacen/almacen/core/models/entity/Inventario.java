package com.creyes.almacen.almacen.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "inventario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_inventario")
    private Long codigoInventario;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "tipo_registro")
    private String tipoRegistro;
    @Column(name = "precio")
    private Double precio;
    @Column(name = "entrada")
    private int entrada;
    @Column(name = "salida")
    private int salida;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="codigo_producto")
    private Producto producto;

}
