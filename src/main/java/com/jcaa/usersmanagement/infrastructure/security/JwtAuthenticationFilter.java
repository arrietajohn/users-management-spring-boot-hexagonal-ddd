package com.jcaa.usersmanagement.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.ApiErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro que protege los endpoints de /api/** exigiendo un JWT valido en el header
 * Authorization, salvo en los endpoints explicitamente publicos (login y registro).
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_PREFIX = "Bearer ";
  private static final String API_PREFIX = "/api/";

  private final JwtTokenProvider jwtTokenProvider;
  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain filterChain)
      throws ServletException, IOException {

    final String path = request.getRequestURI();
    final String method = request.getMethod();

    if (!path.startsWith(API_PREFIX) || isPublicEndpoint(method, path)) {
      filterChain.doFilter(request, response);
      return;
    }

    final String header = request.getHeader(AUTHORIZATION_HEADER);
    if (header == null || !header.startsWith(BEARER_PREFIX)) {
      writeUnauthorized(response, "Falta el token de autenticacion.");
      return;
    }

    final String token = header.substring(BEARER_PREFIX.length());
    try {
      jwtTokenProvider.validateAndGetClaims(token);
    } catch (final JwtException | IllegalArgumentException exception) {
      writeUnauthorized(response, "Token invalido o expirado.");
      return;
    }

    filterChain.doFilter(request, response);
  }

  private static boolean isPublicEndpoint(final String method, final String path) {
    return "POST".equals(method) && ("/api/login".equals(path) || "/api/users".equals(path));
  }

  private void writeUnauthorized(final HttpServletResponse response, final String message)
      throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    final ApiErrorResponse body =
        new ApiErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, message);
    response.getWriter().write(objectMapper.writeValueAsString(body));
  }
}