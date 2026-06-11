package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateVotacionUseCase;
import com.jcaa.usersmanagement.application.port.out.GetVotacionByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveVotacionPort;
import com.jcaa.usersmanagement.domain.model.VotacionModel;
import com.jcaa.usersmanagement.domain.exception.VotacionNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateVotacionService implements UpdateVotacionUseCase {

  private final GetVotacionByIdPort getVotacionByIdPort;
  private final SaveVotacionPort saveVotacionPort;

  @Override
  public VotacionModel execute(Long id, VotacionModel votacion) {
    log.info("Actualizando votacion con id: {}", id);
    getVotacionByIdPort.getById(id)
        .orElseThrow(() -> new VotacionNotFoundException("Votacion no encontrada con id: " + id));
    VotacionModel actualizada = new VotacionModel(
        id, votacion.getFecha(), votacion.getPartidoPolitico(), votacion.getCandidato(),
        votacion.getVotante(), votacion.getPais(), votacion.getDepartamento(),
        votacion.getCiudad(), votacion.getMesa(), votacion.getPuestoPolitico(),
        votacion.getDuracion(), votacion.getNumeroTarjeton());
    return saveVotacionPort.save(actualizada);
  }
}
