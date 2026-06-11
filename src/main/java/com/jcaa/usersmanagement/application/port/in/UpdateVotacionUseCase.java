package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.VotacionModel;

public interface UpdateVotacionUseCase {
  VotacionModel execute(Long id, VotacionModel votacion);
}
