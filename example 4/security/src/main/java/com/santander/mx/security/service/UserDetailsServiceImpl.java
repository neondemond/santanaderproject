package com.santander.mx.security.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.santander.mx.security.model.Usuario;
import com.santander.mx.security.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar el usuario en la base de datos
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre: " + username));

        // Convertir el Usuario en un objeto UserDetails
        return User.builder()
                .username(usuario.getUsername())  // Nombre de usuario
                .password(usuario.getPassword())  // Contraseña cifrada
                .roles(usuario.getRoles().toArray(new String[0]))  // Convertir roles a arreglo de cadenas
                .build();
    }
}
