package com.example.mindtrack.auth;

public record AuthResponse(
        String token,
        String tokenType
) {}
