package com.example.mindtrack.auth;

import com.example.mindtrack.common.exception.BusinessException;
import com.example.mindtrack.security.JwtService;
import com.example.mindtrack.user.Role;
import com.example.mindtrack.user.User;
import com.example.mindtrack.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new BusinessException("E-mail já cadastrado");
        }
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role() != null ? request.role() : Role.ROLE_COLABORADOR);

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, "Bearer");
    }

    public AuthResponse authenticate(AuthRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        User user = (User) auth.getPrincipal();
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, "Bearer");
    }
}
