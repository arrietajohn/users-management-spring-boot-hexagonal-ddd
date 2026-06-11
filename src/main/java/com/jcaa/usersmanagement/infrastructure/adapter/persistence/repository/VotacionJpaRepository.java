package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.VotacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotacionJpaRepository extends JpaRepository<VotacionEntity, Long> {
}