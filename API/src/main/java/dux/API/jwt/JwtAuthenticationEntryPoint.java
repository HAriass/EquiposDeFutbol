package dux.API.jwt;

import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        
        // Aquí es donde escribes tu respuesta JSON personalizada
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Tu JSON personalizado
        String jsonError = String.format(
            "{\"mensaje\": \"Autenticación fallida: %s\", \"codigo\": %d}",
            authException.getMessage(), // Puedes usar authException.getMessage() si quieres más detalle
            HttpServletResponse.SC_UNAUTHORIZED
        );
        
        response.getWriter().write(jsonError);
    }
}