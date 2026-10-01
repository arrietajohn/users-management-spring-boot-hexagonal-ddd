package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response;

public record LoginRestResponse(String token, String tokenType, String userId, String role) {}