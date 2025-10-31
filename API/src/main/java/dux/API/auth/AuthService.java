package dux.API.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dux.API.auth.exception.AutenticacionFallidaException;
import dux.API.equiposDeFutbol.exception.RecursoNoEncontradoException;
import dux.API.jwt.JwtService;
import dux.API.user.Role;
import dux.API.user.User;
import dux.API.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();

    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .pais(request.getPais())
                .role(Role.USER)
                .build();

        System.out.println(user.getPassword());

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
        
    }

}
