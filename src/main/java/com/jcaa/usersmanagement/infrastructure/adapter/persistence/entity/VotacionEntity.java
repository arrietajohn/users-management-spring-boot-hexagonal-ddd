package com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Votacion")
public class VotacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fecha;
    private String partidoPolitico;
    private String candidato;
    private String votante;
    private String pais;
    private String departamento;
    private String ciudad;
    private String mesa;
    private String puestoPolitico;
    private String duracion;
    private String numeroTarjeton;
}
