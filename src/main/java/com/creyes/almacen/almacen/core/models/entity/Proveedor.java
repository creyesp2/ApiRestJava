package com.creyes.almacen.almacen.core.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.bridge.Message;

import javax.lang.model.element.Name;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

@Entity
@Table(name="proveedor")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Proveedor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_proveedor")
    private Long codigoCategoria;
    @NotEmpty(message = "debe definir algo")
    @Column(name = "nit")
    private String nit;
    @Column(name = "razon_social")
    private String razinSocial;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "pagina_web")
    private String paginaWeb;
    @Column(name = "contacto_principal")
    private String contactoPrincipal;

}
