package com.jcaa.usersmanagement.infrastructure.security;

import com.jcaa.usersmanagement.domain.model.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Adaptador de infraestructura responsable de generar y validar tokens JWT.
 * No pertenece al dominio ni a la aplicacion: JWT es un detalle de transporte/seguridad,
 * tal como exige la regla arquitectonica del proyecto.
 */
@Component
public class JwtTokenProvider {

  private final SecretKey secretKey;
  private final long expirationMs;

  public JwtTokenProvider(
      @Value("${security.jwt.secret}") final String secret,
      @Value("${security.jwt.expiration-ms}") final long expirationMs) {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.expirationMs = expirationMs;
  }

  public String generateToken(final UserModel user) {
    final Date now = new Date();
    final Date expiry = new Date(now.getTime() + expirationMs);

    return Jwts.builder()
        .subject(user.getId().value())
        .claim("email", user.getEmail().value())
        .claim("role", user.getRole().name())
        .issuedAt(now)
        .expiration(expiry)
        .signWith(secretKey)
        .compact();
  }

  public Claims validateAndGetClaims(final String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
  }
}