package com.creyes.almacen.almacen.core.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

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
    private String razonSocial;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "pagina_web")
    private String paginaWeb;
    @Column(name = "contacto_principal")
    private String contactoPrincipal;
    @JsonIgnore
    @OneToMany(mappedBy = "proveedor",fetch = FetchType.LAZY)
    private List<EmailProveedor> emailProveedors;

    @JsonIgnore
    @OneToMany(mappedBy = "telefonoProveedor",fetch = FetchType.LAZY)
    private List<TelefonoProveedor> telefonoProveedors;

    @JsonIgnore
    @OneToMany(mappedBy = "compraProveedor",fetch = FetchType.LAZY)
    private List<Compra> compras;

}
