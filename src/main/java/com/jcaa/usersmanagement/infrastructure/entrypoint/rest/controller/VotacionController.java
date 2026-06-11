package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.application.port.in.*;
import com.jcaa.usersmanagement.domain.model.VotacionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votaciones")
@RequiredArgsConstructor
public class VotacionController {

    private final CreateVotacionUseCase createVotacionUseCase;
    private final GetAllVotacionesUseCase getAllVotacionesUseCase;
    private final GetVotacionByIdUseCase getVotacionByIdUseCase;
    private final UpdateVotacionUseCase updateVotacionUseCase;
    private final DeleteVotacionUseCase deleteVotacionUseCase;

    @PostMapping
    public ResponseEntity<VotacionModel> crear(@RequestBody VotacionModel votacion) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createVotacionUseCase.execute(votacion));
    }

    @GetMapping
    public ResponseEntity<List<VotacionModel>> listar() {
        return ResponseEntity.ok(getAllVotacionesUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VotacionModel> obtenerPorId(@PathVariable Long id) {
        return getVotacionByIdUseCase.execute(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VotacionModel> actualizar(@PathVariable Long id,
                                                     @RequestBody VotacionModel votacion) {
        return ResponseEntity.ok(updateVotacionUseCase.execute(id, votacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        deleteVotacionUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
