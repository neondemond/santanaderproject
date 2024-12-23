package com.santander.mx.notificacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santander.mx.notificacion.model.Notificacion;
import com.santander.mx.notificacion.repository.NotificacionRepository;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    public void guardarNotificacion(String mensaje) {
        Notificacion notificacion = new Notificacion(mensaje);
        notificacionRepository.save(notificacion);
    }

    public List<Notificacion> obtenerHistorial() {
        return notificacionRepository.findAll();
    }
}
