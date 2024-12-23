package com.santander.mx.notificacion.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacionProducerService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    private static final String QUEUE_NAME = "notificacionesQueue";

    public void enviarNotificacion(String mensaje) {
        amqpTemplate.convertAndSend(QUEUE_NAME, mensaje);
    }
}
