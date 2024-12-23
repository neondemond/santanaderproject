package com.santander.mx.notificacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santander.mx.notificacion.model.Notificacion;
import com.santander.mx.notificacion.service.NotificacionProducerService;
import com.santander.mx.notificacion.service.NotificacionService;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionProducerService notificacionProducerService;

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping("/registrar")
    public void registrarNotificacion(@RequestBody String mensaje) {
        notificacionProducerService.enviarNotificacion(mensaje);
    }

    @GetMapping("/historial")
    public List<Notificacion> obtenerHistorial() {
        return notificacionService.obtenerHistorial();
    }
}
