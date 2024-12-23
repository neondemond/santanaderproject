package com.santander.mx.security.mapper;


import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Clave secreta para firmar el JWT (debe ser más compleja en producción)
    private static final String SECRET_KEY = "mySecretKeyForJwtGenerationMySecretKeyForJwtGeneration";

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		// TODO Auto-generated constructor stub
	}

	// Método principal de filtro
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtener el token del encabezado Authorization
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            // Extraemos el token (eliminamos la parte "Bearer " del inicio)
            token = token.substring(7);

            try {
                // Validamos el JWT y extraemos las claims
                Claims claims = validateTokenAndGetClaims(token);
                
                if (claims != null) {
                    // Extraemos el nombre de usuario y roles del token
                    String username = claims.getSubject();
                    @SuppressWarnings("unchecked")
                    ArrayList<String> roles = (ArrayList<String>) claims.get("roles");

                    // Crear el token de autenticación
                    UsernamePasswordAuthenticationToken authentication = 
                    		new UsernamePasswordAuthenticationToken(username, null, roles != null ? getAuthorities(roles) : new ArrayList<>());

                    // Establecer la autenticación en el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // En caso de un error, no se establece ninguna autenticación
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        // Continuamos con la siguiente etapa del filtro (pasar la solicitud al siguiente filtro o controlador)
        filterChain.doFilter(request, response);
    }

    // Método para validar el token JWT y extraer las claims
    private Claims validateTokenAndGetClaims(String token) {
        try {
            Key secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // Generar la clave secreta
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature");
        } catch (Exception e) {
            throw new RuntimeException("Token validation failed");
        }
    }

    // Convertir los roles en GrantedAuthority para Spring Security
    private Collection<GrantedAuthority> getAuthorities(ArrayList<String> roles) {
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
    }
}
