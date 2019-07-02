package com.creyes.almacen.almacen.core.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="tipo_empaque")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoEnpaque implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_empaque")
    private Long codigoEmpaque;
    @NotEmpty(message = "debe definir una descripcion")
    @Column(name ="descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "tipoEmpaque",fetch = FetchType.LAZY)
    private List<Producto> productos;

}
