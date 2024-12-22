package com.santander.mx.users.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioService {

    public List<Usuario> leerUsuariosDesdeArchivo(String path) throws IOException {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return Files.lines(Paths.get(path))
            .map(linea -> {
                String[] partes = linea.split(",");
                String nombre = partes[0];
                String dateStr = partes[1].replaceAll("^\\s+", "").replaceAll("\\s+$", "");  // Elimina espacios al principio y al final
                LocalDate fechaNacimiento = LocalDate.parse(dateStr,formatter);
                String email = partes[2];
                return new Usuario(nombre, fechaNacimiento, email);
            })
            .collect(Collectors.toList());
    }
    
    public List<Usuario> filtrarUsuariosPorEdad(List<Usuario> usuarios) {
        return usuarios.parallelStream()
            .filter(usuario -> usuario.edad() >= 25 && usuario.edad() <= 40)
            .collect(Collectors.toList());
    }

    public void exportarUsuariosAArchivo(List<Usuario> usuarios, String path) throws IOException {
        Files.write(Paths.get(path),
            usuarios.stream()
                .map(usuario -> usuario.nombre() + "," + usuario.fechaNacimiento() + "," + usuario.email())
                .collect(Collectors.toList()));
    }
}