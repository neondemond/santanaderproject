package com.santander.mx.notificacion.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.santander.mx.notificacion.service.NotificacionService;

@Component
public class NotificacionConsumer {

    @Autowired
    private NotificacionService notificacionService;

    @RabbitListener(queues = "notificacionesQueue")
    public void procesarNotificacion(String mensaje) {
        // Procesar la notificación y guardarla en la base de datos
        notificacionService.guardarNotificacion(mensaje);
    }
}