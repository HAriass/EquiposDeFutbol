package dux.API.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para el registro y el inicio de sesión (Login/Register)")
public class AuthController {

    private final AuthService authService;
    
    @Operation(
        summary = "Inicio de Sesión de Usuario", 
        description = "Autentica a un usuario existente y devuelve un token JWT.", 
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Login exitoso. Devuelve el token de autenticación.",
                content = @Content(mediaType = "application/json", 
                                   schema = @Schema(implementation = AuthResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas (usuario o contraseña incorrectos)"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(
        summary = "Registro de Nuevo Usuario",
        description = "Registra un nuevo usuario en el sistema y devuelve inmediatamente un token JWT.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Registro y autenticación exitosos.",
                content = @Content(mediaType = "application/json", 
                                   schema = @Schema(implementation = AuthResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Datos de registro inválidos (ej. usuario ya existe)"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
}
