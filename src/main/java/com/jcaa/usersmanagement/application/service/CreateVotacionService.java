package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateVotacionUseCase;
import com.jcaa.usersmanagement.application.port.out.SaveVotacionPort;
import com.jcaa.usersmanagement.domain.model.VotacionModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateVotacionService implements CreateVotacionUseCase {

  private final SaveVotacionPort saveVotacionPort;

  @Override
  public VotacionModel execute(VotacionModel votacion) {
    log.info("Creando votacion: {}", votacion.getCandidato());
    return saveVotacionPort.save(votacion);
  }
}