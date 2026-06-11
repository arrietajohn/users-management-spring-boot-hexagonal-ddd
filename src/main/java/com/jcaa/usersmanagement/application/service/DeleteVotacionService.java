package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteVotacionUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteVotacionPort;
import com.jcaa.usersmanagement.application.port.out.GetVotacionByIdPort;
import com.jcaa.usersmanagement.domain.exception.VotacionNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteVotacionService implements DeleteVotacionUseCase {

  private final DeleteVotacionPort deleteVotacionPort;
  private final GetVotacionByIdPort getVotacionByIdPort;

  @Override
  public void execute(Long id) {
    log.info("Eliminando votacion con id: {}", id);
    getVotacionByIdPort.getById(id)
        .orElseThrow(() -> new VotacionNotFoundException("Votacion no encontrada con id: " + id));
    deleteVotacionPort.delete(id);
  }
}
