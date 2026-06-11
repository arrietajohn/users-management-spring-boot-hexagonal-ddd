package com.jcaa.usersmanagement.infrastructure.adapter.persistence;

import com.jcaa.usersmanagement.application.port.out.DeleteVotacionPort;
import com.jcaa.usersmanagement.application.port.out.GetAllVotacionesPort;
import com.jcaa.usersmanagement.application.port.out.GetVotacionByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveVotacionPort;
import com.jcaa.usersmanagement.domain.model.VotacionModel;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.VotacionPersistenceMapper;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository.VotacionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class VotacionPersistenceAdapter implements
    SaveVotacionPort,
    GetAllVotacionesPort,
    GetVotacionByIdPort,
    DeleteVotacionPort {

    private final VotacionJpaRepository repository;
    private final VotacionPersistenceMapper mapper;

    @Override
    public VotacionModel save(VotacionModel votacion) {
        return mapper.toDomain(repository.save(mapper.toEntity(votacion)));
    }

    @Override
    public List<VotacionModel> getAll() {
        return repository.findAll()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<VotacionModel> getById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
