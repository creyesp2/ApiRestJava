package com.creyes.almacen.almacen.core.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "email_cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_email")
    private Long codigoEmail;
    @Column(name = "email")
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="nit")
    private Cliente emailCliente;
}
