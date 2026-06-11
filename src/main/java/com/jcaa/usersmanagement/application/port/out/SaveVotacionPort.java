package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.VotacionModel;

public interface SaveVotacionPort {
  VotacionModel save(VotacionModel votacion);
}
