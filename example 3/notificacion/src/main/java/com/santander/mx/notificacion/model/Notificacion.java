package com.santander.mx.notificacion.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Notificacion {

    @Id
    @GeneratedValue
    private Long id;

    private String mensaje;
    private LocalDateTime fechaCreacion;

    public Notificacion() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Notificacion(String mensaje) {
        this.mensaje = mensaje;
        this.fechaCreacion = LocalDateTime.now();
    }

}
