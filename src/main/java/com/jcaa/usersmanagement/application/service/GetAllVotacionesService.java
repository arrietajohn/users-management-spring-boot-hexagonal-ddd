package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllVotacionesUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllVotacionesPort;
import com.jcaa.usersmanagement.domain.model.VotacionModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllVotacionesService implements GetAllVotacionesUseCase {

  private final GetAllVotacionesPort getAllVotacionesPort;

  @Override
  public List<VotacionModel> execute() {
    log.info("Listando todas las votaciones");
    return getAllVotacionesPort.getAll();
  }
}
