package com.jcaa.usersmanagement.domain.model;

import lombok.Getter;

@Getter
public class VotacionModel {

  private final Long id;
  private final String fecha;
  private final String partidoPolitico;
  private final String candidato;
  private final String votante;
  private final String pais;
  private final String departamento;
  private final String ciudad;
  private final String mesa;
  private final String puestoPolitico;
  private final String duracion;
  private final String numeroTarjeton;

  public VotacionModel(Long id, String fecha, String partidoPolitico, String candidato,
      String votante, String pais, String departamento, String ciudad,
      String mesa, String puestoPolitico, String duracion, String numeroTarjeton) {
    this.id = id;
    this.fecha = fecha;
    this.partidoPolitico = partidoPolitico;
    this.candidato = candidato;
    this.votante = votante;
    this.pais = pais;
    this.departamento = departamento;
    this.ciudad = ciudad;
    this.mesa = mesa;
    this.puestoPolitico = puestoPolitico;
    this.duracion = duracion;
    this.numeroTarjeton = numeroTarjeton;
  }

  public static VotacionModel create(
      final String fecha, final String partidoPolitico, final String candidato,
      final String votante, final String pais, final String departamento,
      final String ciudad, final String mesa, final String puestoPolitico,
      final String duracion, final String numeroTarjeton) {
    return new VotacionModel(null, fecha, partidoPolitico, candidato, votante,
        pais, departamento, ciudad, mesa, puestoPolitico, duracion, numeroTarjeton);
  }
}