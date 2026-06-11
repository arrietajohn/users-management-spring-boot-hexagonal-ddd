package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.VotacionModel;
import java.util.List;

public interface GetAllVotacionesPort {
  List<VotacionModel> getAll();
}