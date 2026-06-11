package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.VotacionModel;

public interface CreateVotacionUseCase {
  VotacionModel execute(VotacionModel votacion);
}
