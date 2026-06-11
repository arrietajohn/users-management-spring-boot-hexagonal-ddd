package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.VotacionModel;
import java.util.List;

public interface GetAllVotacionesUseCase {
  List<VotacionModel> execute();
}
