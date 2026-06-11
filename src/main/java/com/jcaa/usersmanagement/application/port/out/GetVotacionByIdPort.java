package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.VotacionModel;
import java.util.Optional;

public interface GetVotacionByIdPort {
  Optional<VotacionModel> getById(Long id);
}
