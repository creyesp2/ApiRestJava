package com.creyes.almacen.almacen.core.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "email_proveedor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_email")
    private Long codigoEmail;
    @Column(name = "email")
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="codigo_proveedor")
    private Proveedor proveedor;

}
