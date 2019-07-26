package com.creyes.almacen.almacen.core.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "telefono_cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelefonoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="codigo_telefono")
    private Long codigoTelefono;
    @Column(name = "numero")
    private String Numero;
    @Column(name = "descripcion")
    private String descripcion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="nit")
    private Cliente telefonoCliente;
}
