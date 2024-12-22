package com.santander.mx.users;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.santander.mx.users.service.Usuario;
import com.santander.mx.users.service.UsuarioService;

@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		//SpringApplication.run(UsersApplication.class, args);
		 String archivoEntrada = "usuarios.txt"; // archivo de entrada
	        String archivoSalida = "usuarios_filtrados.txt"; // archivo de salida

	        UsuarioService usuarioService = new UsuarioService();

	        try {
	            // Leer usuarios desde el archivo
	            List<Usuario> usuarios = usuarioService.leerUsuariosDesdeArchivo(archivoEntrada);

	            // Filtrar los usuarios con edades entre 25 y 40 años (paralelizado)
	            List<Usuario> usuariosFiltrados = usuarioService.filtrarUsuariosPorEdad(usuarios);

	            // Exportar los usuarios filtrados a un nuevo archivo
	            usuarioService.exportarUsuariosAArchivo(usuariosFiltrados, archivoSalida);

	            System.out.println("El proceso de filtrado y exportación se completó correctamente.");

	        } catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("Hubo un error al procesar los archivos.");
	        }
	}

}
