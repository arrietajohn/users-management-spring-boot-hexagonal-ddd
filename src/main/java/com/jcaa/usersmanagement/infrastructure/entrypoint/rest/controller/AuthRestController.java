package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.application.port.in.LoginUseCase;
import com.jcaa.usersmanagement.application.service.dto.command.LoginCommand;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.LoginRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.LoginRestResponse;
import com.jcaa.usersmanagement.infrastructure.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Autenticacion: login y emision de token JWT.")
public class AuthRestController {

  private final LoginUseCase loginUseCase;
  private final JwtTokenProvider jwtTokenProvider;

  @PostMapping("/login")
  @Operation(
      summary = "Iniciar sesion",
      description =
          "Valida el email y la contrasena del usuario y, si son correctos y el usuario "
              + "esta ACTIVE, devuelve un token JWT que debe enviarse en el header "
              + "Authorization (Bearer <token>) en las siguientes peticiones protegidas.")
  public LoginRestResponse login(@Valid @RequestBody final LoginRestRequest request) {
    final LoginCommand command = new LoginCommand(request.email(), request.password());
    final UserModel user = loginUseCase.execute(command);
    final String token = jwtTokenProvider.generateToken(user);
    return new LoginRestResponse(token, "Bearer", user.getId().value(), user.getRole().name());
  }
}