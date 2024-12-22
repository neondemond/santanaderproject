package com.santander.mx.users.service;

import java.time.LocalDate;
import java.time.Period;

public record Usuario(String nombre, LocalDate fechaNacimiento, String email) {
    
    // Calcula la edad del usuario
    public int edad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
}
