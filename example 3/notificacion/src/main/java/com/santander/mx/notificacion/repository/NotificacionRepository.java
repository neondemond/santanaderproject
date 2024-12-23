package com.santander.mx.notificacion.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.santander.mx.notificacion.model.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}
