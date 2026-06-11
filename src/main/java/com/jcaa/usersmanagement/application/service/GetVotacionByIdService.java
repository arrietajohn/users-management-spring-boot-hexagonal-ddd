package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetVotacionByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetVotacionByIdPort;
import com.jcaa.usersmanagement.domain.model.VotacionModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetVotacionByIdService implements GetVotacionByIdUseCase {

  private final GetVotacionByIdPort getVotacionByIdPort;

  @Override
  public Optional<VotacionModel> execute(Long id) {
    log.info("Buscando votacion con id: {}", id);
    return getVotacionByIdPort.getById(id);
  }
}