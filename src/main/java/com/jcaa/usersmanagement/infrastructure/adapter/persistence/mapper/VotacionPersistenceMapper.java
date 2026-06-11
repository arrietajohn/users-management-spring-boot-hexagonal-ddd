package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.model.VotacionModel;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.VotacionEntity;
import org.springframework.stereotype.Component;

@Component
public class VotacionPersistenceMapper {

    public VotacionModel toDomain(VotacionEntity entity) {
        return new VotacionModel(
            entity.getId(),
            entity.getFecha(),
            entity.getPartidoPolitico(),
            entity.getCandidato(),
            entity.getVotante(),
            entity.getPais(),
            entity.getDepartamento(),
            entity.getCiudad(),
            entity.getMesa(),
            entity.getPuestoPolitico(),
            entity.getDuracion(),
            entity.getNumeroTarjeton()
        );
    }

    public VotacionEntity toEntity(VotacionModel model) {
        return VotacionEntity.builder()
            .id(model.getId())
            .fecha(model.getFecha())
            .partidoPolitico(model.getPartidoPolitico())
            .candidato(model.getCandidato())
            .votante(model.getVotante())
            .pais(model.getPais())
            .departamento(model.getDepartamento())
            .ciudad(model.getCiudad())
            .mesa(model.getMesa())
            .puestoPolitico(model.getPuestoPolitico())
            .duracion(model.getDuracion())
            .numeroTarjeton(model.getNumeroTarjeton())
            .build();
    }
}
