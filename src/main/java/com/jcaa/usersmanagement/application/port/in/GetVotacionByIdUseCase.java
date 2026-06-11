package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.VotacionModel;
import java.util.Optional;

public interface GetVotacionByIdUseCase {
  Optional<VotacionModel> execute(Long id);
}
